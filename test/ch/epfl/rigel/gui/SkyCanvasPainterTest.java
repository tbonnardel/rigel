package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.ObservedSky;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.image.WritableImage;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.time.ZonedDateTime;

/**
 * Librement inspiré de code fourni dans l'énoncé de l'étape 8 du projet.
 *
 * @author Thomas Bonnardel (319827)
 */
public class SkyCanvasPainterTest extends Application {

    @Test
    public static void main(String[] args) { launch(args); }

    private InputStream resourceStream(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try (InputStream hs = resourceStream("/hygdata_v3.csv")) {
            try (InputStream hs2 = resourceStream("/asterisms.txt")) {
                StarCatalogue catalogue = new StarCatalogue.Builder()
                        .loadFrom(hs, HygDatabaseLoader.INSTANCE)
                        .loadFrom(hs2, AsterismLoader.INSTANCE)
                        .build();

                ZonedDateTime when =
                        ZonedDateTime.parse("2020-02-17T20:15:00+01:00");
                GeographicCoordinates where =
                        GeographicCoordinates.ofDeg(6.57, 46.52);
                HorizontalCoordinates projCenter =
                        HorizontalCoordinates.ofDeg(180, 45);
                StereographicProjection projection =
                        new StereographicProjection(projCenter);
                ObservedSky sky =
                        new ObservedSky(when, where, projection, catalogue);

                Canvas canvas =
                        new Canvas(800, 600);
                Transform planeToCanvas =
                        Transform.affine(1300, 0, 0, -1300, 400, 300);
                SkyCanvasPainter painter =
                        new SkyCanvasPainter(canvas);

                painter.clear();
                painter.drawStars(sky, projection, planeToCanvas);
                painter.drawPlanets(sky, projection, planeToCanvas);
                painter.drawSun(sky, projection, planeToCanvas);
                painter.drawMoon(sky, projection, planeToCanvas);
                painter.drawHorizon();

                WritableImage fxImage =
                        canvas.snapshot(null, null);
                BufferedImage swingImage =
                        SwingFXUtils.fromFXImage(fxImage, null);
                ImageIO.write(swingImage, "png", new File("out/test/Rigel/img/sky.png"));
            }
        }
        Platform.exit();
    }

    //@Test
    void skyCanvasPainterWorks() {
        // TODO: Implémenter un test qui vérifie si l'image générée
        //  est la même que la correction.
    }
}
