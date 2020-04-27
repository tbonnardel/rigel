package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.*;

import java.time.ZonedDateTime;

/**
 * Cette classe représente un « animateur de temps ».
 * Son but est de modifier périodiquement, via un accélérateur de temps,
 * l'instant d'observation dans le but d'animer (indirectement) le ciel.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class TimeAnimator extends AnimationTimer {

    private final DateTimeBean dateTimeBean;
    private long startNsTime;
    private ZonedDateTime initialZonedDateTime;
    private boolean isFirstFrame;

    private ObjectProperty<TimeAccelerator> accelerator = new SimpleObjectProperty<>();
    private BooleanProperty running = new SimpleBooleanProperty();


    /**
     * Constructeur de la classe TimeAnimator prenant en paramètre le bean
     * de l'instant d'observation.
     *
     * @param dateTimeBean le bean de l'instant d'observation
     */
    public TimeAnimator(DateTimeBean dateTimeBean) {
        this.dateTimeBean = dateTimeBean;
    }


    /**
     * Définition de la méthode handle de la classe abstraite AnimationTimer.
     * Actualise la valeur de l'instant de l'observation en fonction de
     * l'accélérateur donné.
     *
     * @param now le timestamp de l'appel actuel en nanosecondes
     */
    @Override
    public void handle(long now) {
        if (isFirstFrame) {
            startNsTime = now;
            initialZonedDateTime = dateTimeBean.getZonedDateTime();
            isFirstFrame = false;
        }

        long nsFromStart = now - startNsTime;
        dateTimeBean.setZonedDateTime(
                getAccelerator().adjust(
                        initialZonedDateTime,
                        nsFromStart)
        );


    }

    /**
     * Redéfinition de la méthode start de AnimationTimer.
     * Méthode qui permet de démarrer l'animation.
     */
    @Override
    public void start() {
        super.start();
        setRunning(true);
        isFirstFrame = true;
    }

    /**
     * Redéfinition de la méthode stop de AnimationTimer.
     * Méthode qui permet d'arrêter l'animation.
     */
    @Override
    public void stop() {
        super.stop();
        setRunning(false);
    }

    /**
     * Méthode d'accès retournant la propriété de l'accélérateur de temps.
     *
     * @return la propriété de l'accélérateur de temps
     */
    public ObjectProperty<TimeAccelerator> acceleratorProperty() { return accelerator; }

    /**
     * Méthode d'accès retournant le contenu de la propriété de l'accélérateur
     * de temps.
     *
     * @return le contenu de la propriété de l'accélérateur de temps
     */
    public TimeAccelerator getAccelerator() { return accelerator.getValue(); }

    /**
     * Méthode qui permet de modifier le contenu de la propriété de
     * l'accélérateur de temps.
     *
     * @param accelerator le nouvel accélérateur de temps
     */
    public void setAccelerator(TimeAccelerator accelerator) { this.accelerator.setValue(accelerator); }


    /**
     * Méthode d'accès retournant la propriété de l'état de l'animateur.
     *
     * @return la propriété de l'état de l'animateur
     */
    public ReadOnlyBooleanProperty runningProperty() { return running; }

    /**
     * Méthode d'accès retournant le contenu de la propriété de l'état de
     * l'animateur.
     *
     * @return le contenu de la propriété de l'état de l'animateur
     */
    public boolean getRunning() { return running.getValue(); }

    /**
     * Méthode qui permet de modifier le contenu de la propriété de l'état de
     * l'animateur. Méthode rendue privée pour rendre sa valeur en lecture seule.
     *
     * @param running le nouvel état de l'animateur
     */
    private void setRunning(boolean running) { this.running.setValue(running); }
}
