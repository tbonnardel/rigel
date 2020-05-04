package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import java.util.function.UnaryOperator;

/**
 * Cette classe contient le programme principal du projet.
 *
 * @author Thomas Bonnardel (319827)
 */
public class Main extends Application {

    private final static String APPLICATION_NAME = "Rigel";
    private final static double MIN_WIDTH = 800;
    private final static double MIN_HEIGHT = 600;
    private final static String FIELD_STYLE = "-fx-pref-width: 60; -fx-alignment: baseline-right;";

    /**
     * Méthode main du projet. Elle lance l'interface graphique
     * via la méthode launch en passant en arguments son tableau args.
     *
     * @param args les arguments passés au programme
     */
    public static void main(String[] args) { launch(args); }

    /**
     * Méthode start de la classe Application.
     * Elle se charge d'initialiser et d'afficher l'interface graphique.
     *
     * @param primaryStage la fenêtre principale de l'application
     * @throws Exception en cas d'erreur
     */
    @Override
    public void start(Stage primaryStage) throws Exception {
        BorderPane root = new BorderPane(createControlBar());

        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    /**
     * Méthode privée qui créée et retourne la barre de contrôle.
     *
     * @return le panneau horizontal représentant la barre de contrôle
     */
    private HBox createControlBar() {
        Label lonLabel = new Label("Longitude (°) :");
        TextField lonTextField = new TextField();
        lonTextField.setTextFormatter(createTextFormatterForLon());
        lonTextField.setStyle(FIELD_STYLE);

        Label latLabel = new Label("Latitude (°) :");
        TextField latTextField = new TextField();
        latTextField.setTextFormatter(createTextFormatterForLat());
        latTextField.setStyle(FIELD_STYLE);

        HBox observedLocationHBox = new HBox(
                lonLabel, lonTextField, latLabel, latTextField);


        HBox observedDateTimeHBox = new HBox();
        HBox timeAnimatorHBox = new HBox();

        HBox controlBar = new HBox(
                observedLocationHBox, new Separator(Orientation.VERTICAL),
                observedDateTimeHBox, new Separator(Orientation.VERTICAL),
                timeAnimatorHBox);
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");
        return controlBar;
    }

    //TODO: Refactoriser les deux méthodes suivantes et changer son nom en nom unique
    //TODO: Documenter la méthode
    private TextFormatter<Number> createTextFormatterForLon() {
        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00");

        UnaryOperator<TextFormatter.Change> filter = (change -> {
            try {
                String newText =
                        change.getControlNewText();
                double newValue =
                        stringConverter.fromString(newText).doubleValue();
                return GeographicCoordinates.isValidLonDeg(newValue)
                        ? change
                        : null;
            } catch (Exception e) {
                return null;
            }
        });

        return new TextFormatter<>(stringConverter, 0, filter);
    }

    private TextFormatter<Number> createTextFormatterForLat() {
        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00");

        UnaryOperator<TextFormatter.Change> filter = (change -> {
            try {
                String newText =
                        change.getControlNewText();
                double newValue =
                        stringConverter.fromString(newText).doubleValue();
                return GeographicCoordinates.isValidLatDeg(newValue)
                        ? change
                        : null;
            } catch (Exception e) {
                return null;
            }
        });

        return new TextFormatter<>(stringConverter, 0, filter);
    }
}
