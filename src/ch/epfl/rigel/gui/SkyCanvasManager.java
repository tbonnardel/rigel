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
import javafx.scene.canvas.Canvas;
import javafx.scene.transform.Transform;

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

    private final ObjectProperty<Canvas> canvas;

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
     */
    public SkyCanvasManager(StarCatalogue starCatalogue, DateTimeBean dateTimeB,
                            ObserverLocationBean observerLocationB,
                            ViewingParametersBean viewingParametersB) {
        this.starCatalogue = starCatalogue;
        this.dateTimeB = dateTimeB;
        this.viewingParametersB = viewingParametersB;
        this.observerLocationB = observerLocationB;

        this.canvas = new SimpleObjectProperty<>();
        this.canvas.setValue(new Canvas(800, 600)); // TODO: utiliser un attribut final

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
        mousePosition.setValue(CartesianCoordinates.of(0, 0)); // TODO: utiliser un attribut final
        planeToCanvas = Bindings.createObjectBinding(
                () -> Transform.affine(
                        viewingParametersB.getDilationFactor(canvas().getWidth()),
                        0,
                        0,
                        -viewingParametersB.getDilationFactor(canvas().getWidth()), // TODO : sur de cette ligne (et le -)
                        canvas.get().getWidth() / 2,
                        canvas.get().getHeight() / 2),
                projection, canvas, viewingParametersB.fieldOfViewDegProperty()); // TODO: problème je n'ai pas à utiliser projection ici alors que l'énoncé dit oui ...
        objectUnderMouse = Bindings.createObjectBinding(
                () -> {
                    Optional<CelestialObject> closestObject = observedSky.get().objectClosestTo(
                            mousePosition.get(),
                            MAX_DISTANCE_OBJECT_UNDER_MOUSE);

                    if (closestObject.isPresent())
                        return closestObject.get();
                    else
                        return null; // TODO: Utiliser ici un ternaire () ? :
                    // TODO: problème, je n'utilise pas le planeToCanvas ici ...
                },
                observedSky, mousePosition);
        mouseHorizontalPosition = Bindings.createObjectBinding(
                () -> projection.get().inverseApply(mousePosition.get()),
                projection, planeToCanvas); // TODO: problème je n'ai pas à utiliser projection ici alors que l'énoncé dit oui ...
        mouseAzDeg = Bindings.createDoubleBinding(
                () -> mouseHorizontalPosition.get().azDeg(),
                mouseHorizontalPosition);
        mouseAltDeg = Bindings.createDoubleBinding(
                () -> mouseHorizontalPosition.get().altDeg(),
                mouseHorizontalPosition);


        // 2. installe un auditeur (listener) pour être informé des mouvements du curseur de la souris, et stocker sa position dans une propriété
        canvas().setOnMouseMoved(event -> setMousePosition(
                CartesianCoordinates.of(event.getX(), event.getY())));


        // 3. installe un auditeur pour détecter les clics de la souris sur le canevas et en faire alors le destinataire des événements clavier
        canvas().setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown())
                canvas.get().requestFocus();
        });


        // 4. installe un auditeur pour réagir aux mouvements de la molette de la souris et/ou du trackpad et changer le champ de vue en fonction
        canvas().setOnScroll(event -> {
            double absDelta = max(abs(event.getDeltaX()), abs(event.getDeltaY()));
            viewingParametersB.addFieldOfViewDeg(absDelta*signum(-event.getDeltaY())); // TODO: Pas très propre
            // TODO: On ne parle pas ici de bien la valeur signée qui doit être ajoutée
            //  au champ de vue, afin de permettre le zoom dans les deux sens.
        });

        // 5. installe un auditeur pour réagir aux pressions sur les touches du curseur et changer le centre de projection en fonction

        canvas().setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case LEFT:
                    observerLocationB.addDegToAzimuth(-AZIMUTH_DEG_OFFSET);
                    event.consume();
                    break;
                case RIGHT:
                    observerLocationB.addDegToAzimuth(AZIMUTH_DEG_OFFSET);
                    event.consume();
                    break;
                case UP:
                    observerLocationB.addDegToAltitude(ALTITUDE_DEG_OFFSET);
                    event.consume();
                    break;
                case DOWN:
                    observerLocationB.addDegToAltitude(-ALTITUDE_DEG_OFFSET);
                    event.consume();
                    break;
                default:
                    break;
            }
        });

        // 6. installe des auditeurs pour être informé des changements des liens et propriétés ayant un impact sur le dessin du ciel, et demander dans ce cas au peintre de le redessiner
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
     *  Méthode d'accès retournant le contenu de la hauteur
     *  en degrés de la position du curseur de la souris.
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
     * Méthode d'accès retournant le contenu de la prorpiété
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
