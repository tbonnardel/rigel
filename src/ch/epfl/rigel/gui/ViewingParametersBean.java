package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

import static java.lang.Math.tan;

/**
 * Cette classe est un bean JavaFX contenant les paramètres
 * déterminant la portion du ciel visible sur l'image.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class ViewingParametersBean {

    private DoubleProperty fieldOfViewDeg = new SimpleDoubleProperty();
    private ObjectProperty<HorizontalCoordinates> center = new SimpleObjectProperty<>();

    private final static double MIN_FIELD_OF_VIEW_DEG = 30d;
    private final static double MAX_FIELD_OF_VIEW_DEG = 150d;
    private final static double MIN_AZIMUTH_DEG = 0d;
    private final static double MAX_AZIMUTH_DEG = 360d;
    private final static double MIN_ALTITUDE_DEG = 5d;
    private final static double MAX_ALTITUDE_DEG = 90d;


    /**
     * Méthode qui ajoute deg degrés au champ de vue
     * en respectant les limites d'angle (le champ doit
     * être dans l'intervalle [30, 150]).
     *
     * @param deg l'angle en degrés à ajouter au
     *            champ de vue
     */
    public void addFieldOfViewDeg(double deg) {
        setFieldOfViewDeg(
                ClosedInterval.of(
                        MIN_FIELD_OF_VIEW_DEG,
                        MAX_FIELD_OF_VIEW_DEG)
                .clip(getFieldOfViewDeg() + deg));
    }

    /**
     * Méthode qui calcule et retourne le facteur de
     * dilatation actuel.
     *
     * @param width la largeur du canevas
     * @return le facteur de dilatation
     */
    public double getDilationFactor(double width) {
        return (width /
                (2*tan(Angle.ofDeg(getFieldOfViewDeg()) / 4)));
    }

    /**
     * Méthode d'accès retournant la propriété du champ de vue en degrés.
     *
     * @return la propriété du champ de vue en degrés
     */
    public DoubleProperty fieldOfViewDegProperty() { return fieldOfViewDeg; }

    /**
     * Méthode d'accès retournant le contenu de la propriété du champ
     * de vue en degrés.
     *
     * @return le contenu de la propriété du champ de vue en degrés
     */
    public double getFieldOfViewDeg() { return fieldOfViewDeg.getValue(); }

    /**
     * Méthode qui permet de modifier le contenu de la propriété du
     * champ de vue en degrés.
     *
     * @param fieldOfViewDeg le nouveau champ de vue en degrés
     */
    public void setFieldOfViewDeg(double fieldOfViewDeg) { this.fieldOfViewDeg.setValue(fieldOfViewDeg); }


    /**
     * Méthode qui ajoute azDeg degrés à l'azimut de la
     * direction du regard, en suivant les conventions
     * d'angles (azimut dans l'intervalle [0, 360[ ).
     *
     * @param azDeg l'angle en degrés à ajouter à l'azimut
     */
    public void addDegToAzimuth(double azDeg) {
        double newAzDeg = RightOpenInterval.of(MIN_AZIMUTH_DEG, MAX_AZIMUTH_DEG)
                .reduce(getCenter().azDeg() + azDeg);
        setCenter(HorizontalCoordinates.ofDeg(newAzDeg, getCenter().altDeg()));
    }

    /**
     * Méthode qui ajoute altDeg degrés à la hauteur de la
     * direction du regard, en suivant les conventions
     * d'angles pour l'observation (hauteur dans
     * l'intervalle [5, 90] ).
     *
     * @param altDeg l'angle en degrés à ajouter à la hauteur
     */
    public void addDegToAltitude(double altDeg) {
        double newAltDeg =  ClosedInterval.of(MIN_ALTITUDE_DEG, MAX_ALTITUDE_DEG)
                        .clip(getCenter().altDeg() + altDeg);
        setCenter(HorizontalCoordinates.ofDeg(getCenter().azDeg(), newAltDeg));
    }


    /**
     * Méthode d'accès retournant la propriété du centre de projection.
     *
     * @return la propriété du centre de projection
     */
    public ObjectProperty<HorizontalCoordinates> centerProperty() { return center; }

    /**
     * Méthode d'accès retournant le contenu de la propriété du centre
     * de projection.
     *
     * @return le contenu de la propriété du centre de projection
     */
    public HorizontalCoordinates getCenter() { return center.getValue(); }

    /**
     * Méthode qui permet de modifier le contenu de la propriété du
     * centre de projection.
     *
     * @param center le nouveau centre de projection
     */
    public void setCenter(HorizontalCoordinates center) { this.center.setValue(center); }
}
