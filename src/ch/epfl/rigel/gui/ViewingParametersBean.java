package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 * Cette classe est un bean JavaFX contenant les paramètres
 * déterminant la portion du ciel visible sur l'image.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class ViewingParametersBean {

    private DoubleProperty fieldOfViewDeg = new SimpleDoubleProperty();
    private ObjectProperty<HorizontalCoordinates> center = new SimpleObjectProperty<>();


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
