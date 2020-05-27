package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.NonInvertibleTransformException;
import javafx.scene.transform.Transform;

import java.util.Map;
import java.util.Optional;

import static java.lang.Math.*;

/**
 * Cette classe est un gestionnaire de canevas sur lequel
 * le ciel est dessiné.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class SkyCanvasManager {

    private final StarCatalogue starCatalogue;
    private final DateTimeBean dateTimeB;
    private final ViewingParametersBean viewingParametersB;
    private final ObserverLocationBean observerLocationB;
    private final ObservedCatalogueBean observedCatalogueB;

    private Map<String, CelestialObject> celestialObjectMap = null;

    private final ObjectProperty<Canvas> canvas;

    private final static double DEFAULT_CANVAS_WIDTH = 800;
    private final static double DEFAULT_CANVAS_HEIGHT = 600;
    private final static double MAX_DISTANCE_OBJECT_UNDER_MOUSE = 10d;
    private final static double AZIMUTH_DEG_OFFSET = 10d;
    private final static double ALTITUDE_DEG_OFFSET = 5d;

    // Liens externes
    private final DoubleBinding mouseAzDeg;
    private final DoubleBinding mouseAltDeg;
    private final ObjectBinding<CelestialObject> objectUnderMouse;

    // Liens internes
    private final ObjectBinding<StereographicProjection> projection;
    private final ObjectBinding<Transform> planeToCanvas;
    private final ObjectBinding<ObservedSky> observedSky;
    private final ObjectProperty<CartesianCoordinates> mousePosition;
    private final ObjectBinding<HorizontalCoordinates> mouseHorizontalPosition;


    /**
     * Constructeur de la classe SkyCanvasManager avec ses paramètres
     * d'observation.
     *
     * @param starCatalogue le catalogue d'étoiles et d'astérismes
     * @param dateTimeB le bean contenant l'instant d'observation
     * @param observerLocationB  le bean contenant la position de
     *                           l'observateur
     * @param viewingParametersB le bean contenant les paramètres
     *                           déterminant la portion du ciel à afficher
     * @param observedCatalogueB le bean contenant le catalogue du ciel affiché
     */
    public SkyCanvasManager(StarCatalogue starCatalogue, DateTimeBean dateTimeB,
                            ObserverLocationBean observerLocationB,
                            ViewingParametersBean viewingParametersB,
                            ObservedCatalogueBean observedCatalogueB) {
        this.starCatalogue = starCatalogue;
        this.dateTimeB = dateTimeB;
        this.viewingParametersB = viewingParametersB;
        this.observerLocationB = observerLocationB;
        this.observedCatalogueB = observedCatalogueB;

        this.canvas = new SimpleObjectProperty<>();
        this.canvas.setValue(new Canvas(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT));

        // 1. crée un certain nombre de propriétés et liens
        projection = Bindings.createObjectBinding(
                () -> new StereographicProjection(viewingParametersB.getCenter()),
                viewingParametersB.centerProperty());
        observedSky = Bindings.createObjectBinding(
                () -> new ObservedSky(
                        dateTimeB.getZonedDateTime(),
                        observerLocationB.getCoordinates(),
                        projection.get(),
                        starCatalogue),
                dateTimeB.dateProperty(), dateTimeB.timeProperty(), dateTimeB.zoneProperty(),
                observerLocationB.lonDegProperty(), observerLocationB.latDegProperty(),
                projection);
        mousePosition = new SimpleObjectProperty<>();
        planeToCanvas = Bindings.createObjectBinding(
                () -> Transform.affine(
                        viewingParametersB.getDilationFactor(canvas().getWidth()),
                        0,
                        0,
                        -viewingParametersB.getDilationFactor(canvas().getWidth()),
                        canvas.get().getWidth() / 2,
                        canvas.get().getHeight() / 2),
                projection, canvas().widthProperty(), canvas().heightProperty(),
                viewingParametersB.fieldOfViewDegProperty());
        objectUnderMouse = Bindings.createObjectBinding(
                () -> {
                    try {
                        Point2D point2D = getPlaneToCanvas().inverseTransform(
                                getMousePosition().x(), getMousePosition().y());
                        Optional<CelestialObject> closestObject = getObservedSky().objectClosestTo(
                                CartesianCoordinates.of(point2D.getX(), point2D.getY()),
                                MAX_DISTANCE_OBJECT_UNDER_MOUSE);

                        return (closestObject.isPresent())
                                ? closestObject.get()
                                : null;
                    } catch (NonInvertibleTransformException | NullPointerException e) {
                        // Bloc try catch pour contrer les messages d'avertissements au lancement du programme par JavaFX
                        return null;
                    }
                },
                observedSky, mousePosition, planeToCanvas);
        mouseHorizontalPosition = Bindings.createObjectBinding(
                () -> {
                    try {
                        Point2D mousePosInPlane =
                                getPlaneToCanvas()
                                        .inverseTransform(
                                                getMousePosition().x(),
                                                getMousePosition().y());
                        return getProjection().inverseApply(CartesianCoordinates.of(
                                mousePosInPlane.getX(),
                                mousePosInPlane.getY()
                        ));
                    } catch(NullPointerException e) {
                        // Bloc try catch pour contrer l'initialisation de la souris de JavaFX
                        return null;
                    }
                },
                mousePosition, projection, planeToCanvas);
        mouseAzDeg = Bindings.createDoubleBinding(
                () -> {
                    try {
                        return mouseHorizontalPosition.get().azDeg();
                    } catch (NullPointerException e) {
                        // Bloc try catch pour contrer l'initialisation de la souris de JavaFX
                        return 0d;
                    }},
                mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(
                () -> {
                    try {
                        return mouseHorizontalPosition.get().altDeg();
                    } catch (NullPointerException e) {
                        // Bloc try catch pour contrer l'initialisation de la souris de JavaFX
                        return 0d;
                    }},
                mouseHorizontalPosition);


        addAllListeners();
    }

    /**
     * Méthode publique qui retourne la table associative des objets célestes dessinés.
     *
     * @return la table associative des objets célestes dessinés
     */
    public Map<String, CelestialObject> getCelestialObjectMap() {
        return celestialObjectMap;
    }

    /**
     * Méthode privée qui dessine le ciel sur le canevas.
     */
    private void drawSky() {
        SkyCanvasPainter painter = new SkyCanvasPainter(canvas());
        ObservedSky sky = getObservedSky();
        StereographicProjection projection = getProjection();
        Transform planeToCanvas = getPlaneToCanvas();

        painter.clear();
        painter.drawStars(sky, planeToCanvas);
        painter.drawPlanets(sky, planeToCanvas);
        painter.drawSun(sky, planeToCanvas);
        painter.drawMoon(sky, planeToCanvas);
        painter.drawHorizon(projection, planeToCanvas);
        celestialObjectMap = painter.getCelestialObjectMap();
    }

    /**
     * Méthode privée qui ajoute les auditeurs utiles.
     */
    private void addAllListeners() {
        // 2. installe un auditeur (listener) pour être informé des
        // mouvements du curseur de la souris, et stocker sa position dans une propriété
        canvas().setOnMouseMoved(event -> setMousePosition(
                CartesianCoordinates.of(event.getX(), event.getY())));

        // 3. installe un auditeur pour détecter les clics de la souris
        // sur le canevas et en faire alors le destinataire des événements clavier
        canvas().setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown())
                canvas.get().requestFocus();
        });

        // 4. installe un auditeur pour réagir aux mouvements de la molette
        // de la souris et/ou du trackpad et changer le champ de vue en fonction
        canvas().setOnScroll(event -> {
            double absDelta = max(abs(event.getDeltaX()), abs(event.getDeltaY()));
            viewingParametersB.addFieldOfViewDeg(absDelta * signum(-event.getDeltaY()));
        });

        // 5. installe un auditeur pour réagir aux pressions sur les touches
        // du curseur et changer le centre de projection en fonction
        canvas().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    viewingParametersB.addDegToAzimuth(-AZIMUTH_DEG_OFFSET);
                    event.consume();
                    break;
                case RIGHT:
                    viewingParametersB.addDegToAzimuth(AZIMUTH_DEG_OFFSET);
                    event.consume();
                    break;
                case UP:
                    viewingParametersB.addDegToAltitude(ALTITUDE_DEG_OFFSET);
                    event.consume();
                    break;
                case DOWN:
                    viewingParametersB.addDegToAltitude(-ALTITUDE_DEG_OFFSET);
                    event.consume();
                    break;
                default:
                    break;
            }
        });

        // 6. installe des auditeurs pour être informé des changements des liens et propriétés
        // ayant un impact sur le dessin du ciel, et demander dans ce cas au peintre de le redessiner
        observerLocationB.lonDegProperty().addListener((p, o, n) -> drawSky());
        observerLocationB.latDegProperty().addListener((p, o, n) -> drawSky());
        dateTimeB.dateProperty().addListener((p, o, n) -> drawSky());
        dateTimeB.timeProperty().addListener((p, o, n) -> drawSky());
        dateTimeB.zoneProperty().addListener((p, o, n) -> drawSky());
        viewingParametersB.centerProperty().addListener((p, o, n) -> drawSky());
        viewingParametersB.fieldOfViewDegProperty().addListener((p, o, n) -> drawSky());
        canvas().widthProperty().addListener((p, o, n) -> drawSky());
        canvas().heightProperty().addListener((p, o, n) -> drawSky());
    }


    /**
     * Méthode d'accès qui retourne le canevas.
     *
     * @return le canevas
     */
    public Canvas canvas() {
        return canvas.get();
    }

    /**
     *  Méthode d'accès retournant le contenu de l'azimut
     *  en degrés de la position du curseur de la souris.
     *
     * @return le contenu de l'azimut de la souris
     */
    public double getMouseAzDeg() {
        return mouseAzDeg.get();
    }

    /**
     * Méthode d'accès retournant la propriété de l'azimut
     * en degrés de la position du curseur de la souris.
     *
     * @return l'azimut en degrés de la souris
     */
    public DoubleBinding mouseAzDegProperty() {
        return mouseAzDeg;
    }

    /**
     * Méthode d'accès retournant le contenu de la hauteur
     * en degrés de la position du curseur de la souris.
     *
     * @return le contenu de la hauteur de la souris
     */
    public double getMouseAltDeg() {
        return mouseAltDeg.get();
    }

    /**
     * Méthode d'accès retournant la propriété de la hauteur
     * en degrés de la position du curseur de la souris.
     *
     * @return la hauteur en degrés de la souris
     */
    public DoubleBinding mouseAltDegProperty() {
        return mouseAltDeg;
    }

    /**
     * Méthode d'accès retournant le contenu de la propriété
     * de l'objet céleste le plus proche du curseur de la souris,
     * à moins de 10 unités dans le repère du canevas.
     *
     * @return l'objet céleste le plus
     * près de la souris
     */
    public CelestialObject getObjectUnderMouse() {
        return objectUnderMouse.get();
    }

    /**
     * Méthode d'accès retournant la propriété de l'objet
     * céleste le plus proche du curseur de la souris,
     * à moins de 10 unités dans le repère du canevas.
     *
     * @return la propriété de l'objet céleste le plus
     * près de la souris
     */
    public ObjectBinding<CelestialObject> objectUnderMouseProperty() {
        return objectUnderMouse;
    }


    // ------- LIENS INTERNES ----------

    private ObserverLocationBean getObserverLocationB() {
        return observerLocationB;
    }

    private StereographicProjection getProjection() {
        return projection.get();
    }

    private Transform getPlaneToCanvas() {
        return planeToCanvas.get();
    }

    private ObservedSky getObservedSky() {
        return observedSky.get();
    }

    private CartesianCoordinates getMousePosition() {
        return mousePosition.get();
    }

    private void setMousePosition(CartesianCoordinates mousePosition) {
        this.mousePosition.set(mousePosition);
    }

    private ObjectProperty<CartesianCoordinates> mousePositionProperty() {
        return mousePosition;
    }

    private HorizontalCoordinates getMouseHorizontalPosition() {
        return mouseHorizontalPosition.get();
    }
}
