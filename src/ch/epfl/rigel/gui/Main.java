package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.AsterismLoader;
import ch.epfl.rigel.astronomy.HygDatabaseLoader;
import ch.epfl.rigel.astronomy.StarCatalogue;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.geometry.Orientation;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.converter.LocalTimeStringConverter;
import javafx.util.converter.NumberStringConverter;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
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
    private final static GeographicCoordinates DEFAULT_LOCATION =
            GeographicCoordinates.ofDeg(6.57, 46.52);
    private final static ZonedDateTime DEFAULT_ZONED_DATE_TIME =
            ZonedDateTime.now();
    private final static HorizontalCoordinates DEFAULT_VIEWING_CENTER =
            HorizontalCoordinates.ofDeg(180.000000000001, 15);
    private final static double DEFAULT_DEG_FIELD_OF_VIEW = 100;
    private final static NamedTimeAccelerator DEFAULT_TIME_ANIMATOR = NamedTimeAccelerator.TIMES_300;

    private final static String CONTROL_BAR_HBOX_CHILDREN_STYLE = "-fx-spacing: inherit; -fx-alignment: baseline-left;";
    private final static String UNDO_ICON = "\uf0e2";
    private final static String PLAY_ICON = "\uf04b";
    private final static String PAUSE_ICON = "\uf04c";

    private final static int LONGITUDE_FORMATTER = 0;
    private final static int LATITUDE_FORMATTER = 1;

    private ObserverLocationBean observerLocationBean = new ObserverLocationBean();
    private ViewingParametersBean viewingParametersBean = new ViewingParametersBean();
    private DateTimeBean dateTimeBean = new DateTimeBean();
    private TimeAnimator timeAnimator = new TimeAnimator(dateTimeBean);


    private SkyCanvasManager skyCanvasManager;

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

        observerLocationBean.setCoordinates(DEFAULT_LOCATION);
        dateTimeBean.setZonedDateTime(DEFAULT_ZONED_DATE_TIME);
        viewingParametersBean.setCenter(DEFAULT_VIEWING_CENTER);
        viewingParametersBean.setFieldOfViewDeg(DEFAULT_DEG_FIELD_OF_VIEW);
        timeAnimator.setAccelerator(NamedTimeAccelerator.TIMES_300.getAccelerator());

        BorderPane root = new BorderPane(createSky(), createControlBar(), null, createInfoBar(), null);

        primaryStage.setTitle(APPLICATION_NAME);
        primaryStage.setMinWidth(MIN_WIDTH);
        primaryStage.setMinHeight(MIN_HEIGHT);

        primaryStage.setScene(new Scene(root));
        primaryStage.show();

        skyCanvasManager.canvas().requestFocus();
    }

    /**
     * Méthode privée qui créée et retourne la barre de contrôle.
     *
     * @return le panneau horizontal représentant la barre de contrôle
     * @throws IOException en cas d'erreur entrée / sortie
     */
    private HBox createControlBar() throws IOException {
        HBox observedLocationHBox = createObservedLocationHBox();
        HBox observedDateTimeHBox = createObservedDateTimeHBox();
        HBox timeAnimationHBox = createTimeAnimationHBox();

        HBox controlBar = new HBox(
                observedLocationHBox, new Separator(Orientation.VERTICAL),
                observedDateTimeHBox, new Separator(Orientation.VERTICAL),
                timeAnimationHBox);
        controlBar.setStyle("-fx-spacing: 4; -fx-padding: 4;");

        return controlBar;
    }


    /**
     * Méthode privée qui créé et retourne le panneau de
     * la position d'observation.
     *
     * @return le panneau de la position d'observation
     */
    private HBox createObservedLocationHBox() {
        final String FIELD_STYLE = "-fx-pref-width: 60; -fx-alignment: baseline-right;";

        Label lonLabel = new Label("Longitude (°) :");
        TextField lonTextField = new TextField();
        TextFormatter<Number> lonTextFormatter = createTextFormatter(LONGITUDE_FORMATTER);
        lonTextFormatter.valueProperty().bindBidirectional(observerLocationBean.lonDegProperty());
        lonTextField.setTextFormatter(lonTextFormatter);
        lonTextField.setStyle(FIELD_STYLE);

        Label latLabel = new Label("Latitude (°) :");
        TextField latTextField = new TextField();
        TextFormatter<Number> latTextFormatter = createTextFormatter(LATITUDE_FORMATTER);
        latTextFormatter.valueProperty().bindBidirectional(observerLocationBean.latDegProperty());
        latTextField.setTextFormatter(latTextFormatter);
        latTextField.setStyle(FIELD_STYLE);

        HBox observedLocationHBox = new HBox(
                lonLabel, lonTextField, latLabel, latTextField);
        observedLocationHBox.setStyle(CONTROL_BAR_HBOX_CHILDREN_STYLE);

        return observedLocationHBox;
    }

    /**
     * Méthode privée qui créé et retourne le panneau de
     * l'instant d'observation.
     *
     * @return le panneau de l'instant d'observation
     */
    private HBox createObservedDateTimeHBox() {
        Label dateLabel = new Label("Date :");
        DatePicker datePicker = new DatePicker();
        datePicker.valueProperty().bindBidirectional(dateTimeBean.dateProperty());
        datePicker.setStyle("-fx-pref-width: 120;");

        Label timeLabel = new Label("Heure :");
        TextField timeTextField = new TextField();
        TextFormatter<LocalTime> timeTextFormatter = createLocalTimeFormatter();
        timeTextFormatter.valueProperty().bindBidirectional(dateTimeBean.timeProperty());
        timeTextField.setTextFormatter(timeTextFormatter);
        timeTextField.setStyle("-fx-pref-width: 75; -fx-alignment: baseline-right;");


        ComboBox timeZoneComboBox = new ComboBox();
        timeZoneComboBox.setStyle("-fx-pref-width: 180;");
        timeZoneComboBox.setItems(FXCollections.observableArrayList(
                ZoneId.getAvailableZoneIds()).sorted());
        timeZoneComboBox.valueProperty().addListener(
                (p, o, n) -> dateTimeBean.setZone(ZoneId.of(n.toString()))); // TODO: Plus propre que deux auditeurs ?
        dateTimeBean.zoneProperty().addListener((p, o, n) -> timeZoneComboBox.setValue(dateTimeBean.getZone().toString()));
        timeZoneComboBox.setValue(DEFAULT_ZONED_DATE_TIME.getZone().toString()); // Initialisation au lancement


        // Désactivation des champs lors d'une animation
        datePicker.disableProperty().bind(timeAnimator.runningProperty());
        timeTextField.disableProperty().bind(timeAnimator.runningProperty());
        timeZoneComboBox.disableProperty().bind(timeAnimator.runningProperty());

        HBox observedDateTimeHBox = new HBox(dateLabel, datePicker, timeLabel, timeTextField, timeZoneComboBox);
        observedDateTimeHBox.setStyle(CONTROL_BAR_HBOX_CHILDREN_STYLE);

        return observedDateTimeHBox;
    }

    /**
     * Méthode privée qui retourne le stream contenu dans le dossier
     * resources avec le nom spécifié.
     * Cette méthode a été reprise de l'énoncé de l'étape 10 du projet,
     * dans la classe UseSkyCanvasManager.
     *
     * @param resourceName le nom de la ressource
     * @return le stream associé
     */
    private InputStream resourceStream(String resourceName) {
        return getClass().getResourceAsStream(resourceName);
    }

    /**
     * Méthode privée qui créé et retourne le panneau de
     * l'écoulement du temps.
     *
     * @return le panneau de l'écoulement du temps
     * @throws IOException en cas d'erreur entrée / sortie
     */
    private HBox createTimeAnimationHBox() throws IOException {
        ChoiceBox acceleratorChoiceBox = new ChoiceBox();
        acceleratorChoiceBox.setItems(FXCollections.observableList(NamedTimeAccelerator.ALL));

        acceleratorChoiceBox.valueProperty().addListener(
                (p, o, n) -> timeAnimator.setAccelerator(
                        NamedTimeAccelerator.ACCELERATOR_NAME_MAP.getOrDefault(
                                n.toString(), DEFAULT_TIME_ANIMATOR.getAccelerator())));
        acceleratorChoiceBox.setValue(DEFAULT_TIME_ANIMATOR); // Initialisation au lancement

        Button resetButton = new Button(UNDO_ICON);
        Button playPauseButton = new Button(PLAY_ICON);

        try (InputStream fontStream = resourceStream(
                "/Font Awesome 5 Free-Solid-900.otf")) {
            Font fontAwesome = Font.loadFont(fontStream, 15);
            resetButton.setFont(fontAwesome);
            playPauseButton.setFont(fontAwesome);
        }

        resetButton.setOnMousePressed(e -> {
            dateTimeBean.setZonedDateTime(ZonedDateTime.now());
            if (timeAnimator.getRunning()) {
                timeAnimator.stop();
                playPauseButton.setText(PLAY_ICON); // TODO: éviter la répétition
            }
        });

        playPauseButton.setOnMousePressed(e -> {
            if (! timeAnimator.getRunning()) {
                playPauseButton.setText(PAUSE_ICON);
                timeAnimator.start();
            } else {
                playPauseButton.setText(PLAY_ICON);
                timeAnimator.stop();
            }
        });

        HBox timeAnimationHBox = new HBox(acceleratorChoiceBox, resetButton, playPauseButton);
        timeAnimationHBox.setStyle("-fx-spacing: inherit;");

        return timeAnimationHBox;
    }

    /**
     * Méthode privée qui retourne le panneau du ciel.
     *
     * @return le panneau du ciel
     * @throws IOException en cas d'erreur entrée / sortie
     */
    private Pane createSky() throws IOException {
        Pane skyPane = null;
        try (InputStream hs1 = resourceStream("/hygdata_v3.csv")) {
            try (InputStream hs2 = resourceStream("/asterisms.txt")) { // TODO: Eviter ce double bloc try
                StarCatalogue catalogue = new StarCatalogue.Builder()
                        .loadFrom(hs1, HygDatabaseLoader.INSTANCE)
                        .loadFrom(hs2, AsterismLoader.INSTANCE)
                        .build();

                skyCanvasManager =
                        new SkyCanvasManager(catalogue,
                                dateTimeBean, observerLocationBean, viewingParametersBean);
                Canvas skyCanvas = skyCanvasManager.canvas();
                skyPane = new Pane(skyCanvas);
                skyCanvas.widthProperty().bind(skyPane.widthProperty());
                skyCanvas.heightProperty().bind(skyPane.heightProperty());
            }
        }
        return skyPane;
    }

    /**
     * Méthode privée qui crée et retourne la barre d'information.
     *
     * @return le panneau de la barre d'information
     */
    private BorderPane createInfoBar() {
        Label fieldOfViewLabel = new Label("Champ de vue : %.1f°");
        Label closestObjectLabel = new Label();
        Label horizontalPositionLabel = new Label("Azimut : <az>°, hauteur : <alt>°");

        fieldOfViewLabel.textProperty().bind(Bindings.format(
                "Champ de vue : %.1f°", viewingParametersBean.fieldOfViewDegProperty()));
        skyCanvasManager.objectUnderMouseProperty().addListener(
                (p, o, n) -> {if (n != null) closestObjectLabel.setText(n.info());});
        horizontalPositionLabel.textProperty().bind(Bindings.format(
                "Azimut : %.2f°, hauteur : %.2f°", skyCanvasManager.mouseAzDegProperty(), skyCanvasManager.mouseAltDegProperty()
        ));

        BorderPane infoBar = new BorderPane(closestObjectLabel,
                null, horizontalPositionLabel, null, fieldOfViewLabel);
        infoBar.setStyle("-fx-padding: 4; -fx-background-color: white;");
        return infoBar;
    }

    /**
     * Méthode privée qui retourne un formateur de texte adapté au type donné.
     *
     * @param formatterType le type de formateur (longitude ou latitude)
     * @return un formateur de texte adapté au type donné
     */
    private TextFormatter<Number> createTextFormatter(int formatterType) {
        NumberStringConverter stringConverter =
                new NumberStringConverter("#0.00");

        UnaryOperator<TextFormatter.Change> filter = (change -> {
            try {
                String newText =
                        change.getControlNewText();
                double newValue =
                        stringConverter.fromString(newText).doubleValue();
                switch (formatterType) {
                    case LONGITUDE_FORMATTER:
                        return GeographicCoordinates.isValidLonDeg(newValue)
                                ? change
                                : null;
                    case LATITUDE_FORMATTER:
                        return GeographicCoordinates.isValidLatDeg(newValue)
                                ? change
                                : null;
                    default:
                        return null;
                }
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
