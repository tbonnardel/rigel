package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.tan;

/**
 * Cette classe instanciable représente un « peintre de ciel »,
 * c'est-à-dire un objet capable de dessiner le ciel sur un canevas.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class SkyCanvasPainter {

    private final Canvas canvas;

    private final static Color BACKGROUND_COLOR = Color.BLACK;
    private final static Color ASTERISMS_COLOR = Color.BLUE;
    private final static Color PLANETS_COLOR = Color.LIGHTGRAY;
    private final static Color MOON_COLOR = Color.WHITE;
    private final static Color SUN_CENTER_COLOR = Color.WHITE;
    private final static Color SUN_MIDDLE_COLOR = Color.YELLOW;
    private final static Color SUN_HALO_COLOR = Color.YELLOW.deriveColor(1, 1, 1, 0.25);
    private final static Color ANNOTATIONS_COLOR = Color.RED;

    private final static String NORTH_LABEL = "N";
    private final static String EAST_LABEL = "E";
    private final static String SOUTH_LABEL = "S";
    private final static String WEST_LABEL = "O";
    private final static double OCTANT_LABEL_ALT_DEG_OFFSET = 0.5;
    private final static HorizontalCoordinates OCTANT_LABEL_COORDS[] = initOctantLabelCoords();


    /**
     * Constructeur de la classe SkyCanvasPainter.
     *
     * @param canvas le canevas à utiliser pour le dessin
     */
    public SkyCanvasPainter(Canvas canvas) {
        this.canvas = canvas;
    }


    /**
     * Méthode qui efface le canevas.
     */
    public void clear() {
        GraphicsContext ctx = canvas.getGraphicsContext2D();

        ctx.setFill(BACKGROUND_COLOR);
        ctx.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }


    /**
     * Méthode qui dessine les astérismes et les étoiles sur le canevas.
     *
     * @param sky le ciel observé
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawStars(ObservedSky sky, Transform planeToCanvas) {

        drawVisibleAsterisms(sky, planeToCanvas);

        for (int i = 0; i < sky.stars().size(); i++) {
            Star star = sky.stars().get(i);
            drawDisk(
                    CartesianCoordinates.of(
                            sky.starPositions()[2*i],
                            sky.starPositions()[2*i+1]),
                    size(star),
                    BlackBodyColor.colorForTemperature(star.colorTemperature()),
                    planeToCanvas);
        }
    }

    /**
     * Méthode qui dessine les planètes sur le canevas.
     *
     * @param sky le ciel observé
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawPlanets(ObservedSky sky, Transform planeToCanvas) {
        for (int i = 0; i < sky.planets().size(); i++) {
            drawDisk(
                    CartesianCoordinates.of(
                            sky.planetPositions()[2*i],
                            sky.planetPositions()[2*i+1]),
                    size(sky.planets().get(i)),
                    PLANETS_COLOR,
                    planeToCanvas
                    );
        }
    }

    /**
     * Méthode qui dessine le Soleil sur le canevas.
     *
     * @param sky le ciel observé
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawSun(ObservedSky sky, Transform planeToCanvas) {
        Sun sun = sky.sun();
        CartesianCoordinates absCenter = sky.sunPosition();
        double sunSize = size(sun);

        final double HALO_COEF = 2.2;
        drawDisk(
                absCenter,
                sunSize*HALO_COEF,
                SUN_HALO_COLOR,
                planeToCanvas
        );
        drawDisk(
                absCenter,
                sunSize + abs(2/planeToCanvas.getMxx()),
                SUN_MIDDLE_COLOR,
                planeToCanvas
        );
        drawDisk(
                absCenter,
                size(sun),
                SUN_CENTER_COLOR,
                planeToCanvas
        );
    }

    /**
     * Méthode qui dessine la Lune sur le canevas.
     *
     * @param sky le ciel observé
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawMoon(ObservedSky sky, Transform planeToCanvas) {
        drawDisk(
                sky.moonPosition(),
                size(sky.moon()),
                MOON_COLOR,
                planeToCanvas
        );
    }

    /**
     * Méthode qui dessine l'horizon et les points cardinaux et intercardinaux.
     *
     * @param projection la projection stéréographique utilisée
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawHorizon(StereographicProjection projection,
                            Transform planeToCanvas) {
        drawHorizonLine(projection, planeToCanvas);
        drawOctantLabels(projection, planeToCanvas);
    }

    /**
     * Méthode privée qui dessine à l'écran un disque à la position et
     * le diamètre spécifiés. Notez bien que des coordonnées et le diamètre
     * doivent être donné en absolu, c'est-à-dire que la transformation
     * de planeToCanvas n'a pas encore été appliquée.
     *
     * @param absCenter le centre du disque (coordonnées non transformées)
     * @param absSize le diamètre du disque (valeur non transformée)
     * @param color la couleur du disque
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    private void drawDisk(CartesianCoordinates absCenter, double absSize,
                          Color color, Transform planeToCanvas) {
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        ctx.setFill(color);

        Point2D center = planeToCanvas.transform(
                absCenter.x(),
                absCenter.y());
        Point2D dimension = planeToCanvas.deltaTransform(absSize, absSize);
        ctx.fillOval(
                center.getX() - dimension.getX()/2,
                center.getY() + dimension.getY()/2,
                abs(dimension.getX()),
                abs(dimension.getY()));
    }

    /**
     * Méthode privée qui dessine les astérismes visibles, c'est-à-dire relier
     * par un trait les étoiles d'un même astérismes si ces deux étoiles sont
     * visibles.
     *
     * @param sky le ciel observé
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    private void drawVisibleAsterisms(ObservedSky sky,
                                      Transform planeToCanvas) {
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        ctx.setStroke(ASTERISMS_COLOR);
        ctx.setLineWidth(1);

        Bounds boundsCanvas = canvas.getBoundsInLocal();
        for (Asterism asterism : sky.asterisms()) {
            ctx.beginPath();
            List<Integer> starIndices = sky.asterismIndices(asterism);
            for (int i = 0; i < starIndices.size() - 1; i++) {
                Point2D star1 = planeToCanvas.transform(
                        sky.starPositions()[2*starIndices.get(i)],
                        sky.starPositions()[2*starIndices.get(i)+1]
                );
                Point2D star2 = planeToCanvas.transform(
                        sky.starPositions()[2*starIndices.get(i+1)],
                        sky.starPositions()[2*starIndices.get(i+1)+1]
                );

                if (boundsCanvas.contains(star1) || boundsCanvas.contains(star2)) {
                    ctx.moveTo(star1.getX(), star1.getY());
                    ctx.lineTo(star2.getX(), star2.getY());
                }
            }
            ctx.stroke();
        }
    }

    /**
     * Méthode privée qui dessine la ligne d'horizon
     *
     * @param projection la projection stéréographique utilisée
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    private void drawHorizonLine(StereographicProjection projection,
                                 Transform planeToCanvas) {
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        ctx.setStroke(ANNOTATIONS_COLOR);
        ctx.setLineWidth(2);

        CartesianCoordinates absoluteCenterCoordinates =
                projection.circleCenterForParallel(HorizontalCoordinates.of(0, 0));

        if (abs(absoluteCenterCoordinates.y()) < Double.POSITIVE_INFINITY) {
            Point2D center = planeToCanvas.transform(
                    absoluteCenterCoordinates.x(),
                    absoluteCenterCoordinates.y());
            double radius = projection.circleRadiusForParallel(HorizontalCoordinates.of(0, 0));
            double width = radius*2*planeToCanvas.getMxx();
            double height = radius*2*planeToCanvas.getMyy();
            ctx.strokeOval(
                    center.getX() - width/2,
                    center.getY() + height/2 ,
                    abs(width),
                    abs(height));
        } else { // Cas limite où le cercle à un rayon infini
            double centerY = canvas.getHeight() / 2;
            ctx.strokeLine(
                    0, centerY,
                    canvas.getWidth(), centerY);
        }
    }

    /**
     * Méthode privée qui dessine les différents libellés désignant
     * les points cardinaux.
     *
     * @param projection la projection stéréographique utilisée
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    private void drawOctantLabels(StereographicProjection projection,
                                  Transform planeToCanvas) {
        GraphicsContext ctx = canvas.getGraphicsContext2D();
        ctx.setFill(ANNOTATIONS_COLOR);

        for (HorizontalCoordinates hc : OCTANT_LABEL_COORDS) {
            String text = hc.azOctantName(NORTH_LABEL, EAST_LABEL, SOUTH_LABEL, WEST_LABEL);
            CartesianCoordinates coords = projection.apply(hc);
            Point2D upperLeftBound = planeToCanvas.transform(
                    coords.x(),
                    coords.y());
            ctx.fillText(text, upperLeftBound.getX(), upperLeftBound.getY());
        }
    }

    /**
     * Méthode statique et privée qui retourne le tableau des coordonnées horizontales
     * des labels des points cardinaux.
     *
     * @return le tableau des coordonnées horizontales des labels des points cardinaux
     */
    private static HorizontalCoordinates[] initOctantLabelCoords() {
        final int NB_OCTANTS = 8;
        final double DEG_STEP = 45;
        HorizontalCoordinates[] coords = new HorizontalCoordinates[NB_OCTANTS];
        for (int i = 0; i < NB_OCTANTS; i++) {
            coords[i] = HorizontalCoordinates.ofDeg(DEG_STEP*i, OCTANT_LABEL_ALT_DEG_OFFSET);
        }
        return  coords;
    }

    /**
     * Méthode qui retourne le diamètre du disque représentant
     * le Soleil dans notre projection.
     *
     * @param sun le Soleil à représenter
     * @return le diamètre du disque représentant le Soleil
     */
    private double size(Sun sun) {
        return effectiveSize(sun.angularSize());
    }

    /**
     * Méthode qui retourne le diamètre du disque représentant
     * la Lune dans notre projection.
     *
     * @param moon la Lune à représenter
     * @return le diamètre du disque représentant la Lune
     */
    private double size(Moon moon) {
        return effectiveSize(moon.angularSize());
    }

    /**
     * Méthode qui retourne le diamètre du disque représentant
     * la planète spécifiée dans notre projection.
     *
     * @param planet la planète à représenter
     * @return le diamètre du disque représentant la planète
     */
    private double size(Planet planet) {
        return basedOnMagnitudeSize(planet.magnitude());
    }

    /**
     * Méthode qui retourne le diamètre du disque représentant
     * l'étoile spécifiée dans notre projection.
     *
     * @param star l'étoile à représenter
     * @return le diamètre du disque représentant l'étoile
     */
    private double size(Star star) {
        return basedOnMagnitudeSize(star.magnitude());
    }

    /**
     * Méthode privée qui calcule la taille effective de l'objet celeste
     * projeté dans notre projection stéréographique en fonction de sa
     * taille angulaire.
     * Notez que cette formule n'est valable que pour le Soleil et la Lune.
     *
     * @param angularSize la taille angulaire
     * @return la taille effective
     */
    private double effectiveSize(double angularSize) {
        return 2*tan(angularSize/4);
    }

    /**
     * Méthode privée qui calcule la taille du disque de l' objet céleste
     * projeté dans notre projection stéréographique en fonction de sa magnitude.
     * Notez que cette méthode de calcul n'est valable que pour les planètes
     * et les étoiles (hors Soleil).
     *
     * @param magnitude la magnitude de l'objet céleste
     * @return la taille de l'objet céleste
     */
    private double basedOnMagnitudeSize(double magnitude) {
        double clipedMagnitude = ClosedInterval.of(-2, 5).clip(magnitude);
        double f = (99 - 17*clipedMagnitude) / 140;
        return f*effectiveSize(Angle.ofDeg(.5));
    }
}
