package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.*;
import ch.epfl.rigel.coordinates.StereographicProjection;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

import static java.lang.Math.tan;

/**
 * Cette classe instanciable représente un « peintre de ciel »,
 * c'est-à-dire un objet capable de dessiner le ciel sur un canevas.
 *
 * @author Thomas Bonnardel (319827)
 */
public class SkyCanvasPainter {

    private final Canvas canvas; // TODO: Remove final ?

    private final static Color BACKGROUND_COLOR = Color.BLACK;
    private final static Color ASTERISMS_COLOR = Color.BLUE;
    private final static Color PLANETS_COLOR = Color.LIGHTGRAY;
    private final static Color MOON_COLOR = Color.WHITE;
    private final static Color ANNOTATIONS_COLOR = Color.RED;

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
     * @param projection la projection stéréographique utilisée
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawStars(ObservedSky sky, StereographicProjection projection,
                          Transform planeToCanvas) {
        // TODO: A implémenter
    }

    /**
     * Méthode qui dessine les planètes sur le canevas.
     *
     * @param sky le ciel observé
     * @param projection la projection stéréographique utilisée
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawPlanets(ObservedSky sky, StereographicProjection projection,
                            Transform planeToCanvas) {
        // TODO: A implémenter
    }

    /**
     * Méthode qui dessine le Soleil sur le canevas.
     *
     * @param sky le ciel observé
     * @param projection la projection stéréographique utilisée
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawSun(ObservedSky sky, StereographicProjection projection,
                        Transform planeToCanvas) {
        // TODO: A implémenter
    }

    /**
     * Méthode qui dessine la Lune sur le canevas.
     *
     * @param sky le ciel observé
     * @param projection la projection stéréographique utilisée
     * @param planeToCanvas la transformation entre le repère de la
     *                      projection et celui du canevas
     */
    public void drawMoon(ObservedSky sky, StereographicProjection projection,
                         Transform planeToCanvas) {
        // TODO: A implémenter
    }

    public void drawHorizon() {
        // TODO A implémenter
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
