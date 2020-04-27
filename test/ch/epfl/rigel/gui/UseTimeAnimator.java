package ch.epfl.rigel.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.time.ZonedDateTime;

/**
 * Code provenant de la section 3.6 de l'anoncé de l'étape 9 du projet.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class UseTimeAnimator extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) {
        ZonedDateTime simulatedStart =
                ZonedDateTime.parse("2020-06-01T23:55:00+01:00");
        TimeAccelerator accelerator =
                NamedTimeAccelerator.TIMES_3000.getAccelerator();

        DateTimeBean dateTimeB = new DateTimeBean();
        dateTimeB.setZonedDateTime(simulatedStart);

        TimeAnimator timeAnimator = new TimeAnimator(dateTimeB);
        timeAnimator.setAccelerator(accelerator);

        dateTimeB.dateProperty().addListener((p, o, n) -> {
            System.out.printf(" Nouvelle date : %s%n", n);
            Platform.exit();
        });
        dateTimeB.timeProperty().addListener((p, o, n) -> {
            System.out.printf("Nouvelle heure : %s%n", n);
        });
        timeAnimator.start();
    }
}
