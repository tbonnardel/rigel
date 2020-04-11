package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.transform.Transform;

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
}
