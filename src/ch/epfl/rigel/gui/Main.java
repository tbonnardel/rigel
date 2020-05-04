package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.InputStream;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.function.UnaryOperator;

/**
 * Cette classe contient le programme principal du projet.
 *
 * @author Thomas Bonnardel (319827)
 */
public class Main extends Application {

    private ObserverLocationBean observerLocationBean = new ObserverLocationBean();
    private ViewingParametersBean viewingParametersBean = new ViewingParametersBean();
    private DateTimeBean dateTimeBean = new DateTimeBean();
    private TimeAnimator timeAnimator = new TimeAnimator(dateTimeBean);

    private final static String APPLICATION_NAME = "Rigel";
    private final static double MIN_WIDTH = 800;
    private final static double MIN_HEIGHT = 600;
    private final static String FIELD_STYLE = "-fx-pref-width: 60; -fx-alignment: baseline-right;";
    private final static String CONTROL_BAR_HBOX_CHILDREN_STYLE = "-fx-spacing: inherit; -fx-alignment: baseline-left;";
    private final static String UNDO_ICON = "\uf0e2";
    private final static String PLAY_ICON = "\uf04b";
    private final static String PAUSE_ICON = "\uf04c";

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
        TextFormatter<Number> lonTextFormatter = createTextFormatterForLon();
        lonTextFormatter.valueProperty().addListener(
                (p, o, n) -> observerLocationBean.setLonDeg(n.doubleValue())); // TODO A vérifier
        lonTextField.setTextFormatter(lonTextFormatter);
        lonTextField.setStyle(FIELD_STYLE);

        Label latLabel = new Label("Latitude (°) :");
        TextField latTextField = new TextField();
        TextFormatter<Number> latTextFormatter = createTextFormatterForLat();
        latTextFormatter.valueProperty().addListener(
                (p, o, n) -> observerLocationBean.setLatDeg(n.doubleValue())); // TODO: A vérifier
        latTextField.setTextFormatter(latTextFormatter);
        latTextField.setStyle(FIELD_STYLE);

        HBox observedLocationHBox = new HBox(
                lonLabel, lonTextField, latLabel, latTextField);
        observedLocationHBox.setStyle(CONTROL_BAR_HBOX_CHILDREN_STYLE);


        Label dateLabel = new Label("Date :");
        DatePicker datePicker = new DatePicker();
        datePicker.valueProperty().addListener(
                (p, o, n) -> dateTimeBean.setDate(n)); // TODO: A vérifier
        datePicker.setStyle("-fx-pref-width: 120;");

        Label timeLabel = new Label("Heure :");
        TextField timeTextField = new TextField();
        TextFormatter<LocalTime> timeTextFormatter = createLocalTimeFormatter();
        timeTextFormatter.valueProperty().addListener(
                (p, o, n) -> dateTimeBean.setTime(n)); // TODO: A vérifier
        timeTextField.setTextFormatter(timeTextFormatter);
        timeTextField.setStyle("-fx-pref-width: 75; -fx-alignment: baseline-right;");

        ComboBox timeZoneComboBox = new ComboBox();
        timeZoneComboBox.setStyle("-fx-pref-width: 180;");
        timeZoneComboBox.setItems(FXCollections.observableArrayList(
                ZoneId.getAvailableZoneIds()).sorted());
        timeZoneComboBox.valueProperty().addListener(
                (p, o, n) -> dateTimeBean.setZone(ZoneId.of(n.toString()))); // TODO: A vérifier

        // TODO: Désactiver la saisie de données quand une animation est en cours

        HBox observedDateTimeHBox = new HBox(dateLabel, datePicker, timeLabel, timeTextField, timeZoneComboBox);
        observedDateTimeHBox.setStyle(CONTROL_BAR_HBOX_CHILDREN_STYLE);


        ChoiceBox acceleratorChoiceBox = new ChoiceBox();
        acceleratorChoiceBox.setItems(FXCollections.observableList(NamedTimeAccelerator.ALL));
        //acceleratorChoiceBox.valueProperty().bind(Bindings.select(timeAnimator.acceleratorProperty(), "name")); // TODO: A vérifier
        // timeAnimator.set // TODO: A vérifier

        // TODO: Améliorer la lisibilité
        // TODO: fermer le flot correctement
        InputStream fontStream = getClass()
                .getResourceAsStream("/Font Awesome 5 Free-Solid-900.otf");
        Font fontAwesome = Font.loadFont(fontStream, 15);

        Button resetButton = new Button(UNDO_ICON);
        resetButton.setFont(fontAwesome);
        // TODO: lier le bouton au bean associé
        Button playPauseButton = new Button(PLAY_ICON);
        playPauseButton.setFont(fontAwesome);
        // TODO: lier le bouton au bean associé
        // TODO: changer l'icone si nécessaire

        HBox timeAnimationHBox = new HBox(acceleratorChoiceBox, resetButton, playPauseButton);
        timeAnimationHBox.setStyle("-fx-spacing: inherit;");

        HBox controlBar = new HBox(
                observedLocationHBox, new Separator(Orientation.VERTICAL),
                observedDateTimeHBox, new Separator(Orientation.VERTICAL),
                timeAnimationHBox);
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

    /**
     * Méthode privée qui retourne un formateur de texte pour
     * la gestion du temps.
     *
     * @return un formateur de texte pour le temps
     */
    private TextFormatter<LocalTime> createLocalTimeFormatter() {
        DateTimeFormatter hmsFormatter =
                DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalTimeStringConverter stringConverter =
                new LocalTimeStringConverter(hmsFormatter, hmsFormatter);
        return new TextFormatter<>(stringConverter);
    }
}
