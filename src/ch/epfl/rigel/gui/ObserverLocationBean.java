package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

/**
 * Cette classe est un bean JavaFX contenant la position de
 * l'observateur, en degrés.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class ObserverLocationBean {

    private DoubleProperty lonDeg = new SimpleDoubleProperty();
    private DoubleProperty latDeg = new SimpleDoubleProperty();
    private ObjectBinding<GeographicCoordinates> coordinates;


    /**
     * Constructeur de la classe, qui permet de créé le lien entre
     * les propriétés de longitude et latitude et l'objet contenant
     * les coordonnées de la position de l'observateur.
     */
    public ObserverLocationBean() {
        this.coordinates = Bindings.createObjectBinding(
                () -> GeographicCoordinates.ofDeg(lonDeg.get(), latDeg.get()),
                lonDeg, latDeg
        );
    }


    /**
     * Méthode d'accès retournant la propriété de la longitude
     * en degrés de la position de l'observateur.
     *
     * @return la propriété de la longitude en degrés
     */
    public DoubleProperty lonDegProperty() { return lonDeg; }

    /**
     * Méthode d'accès retournant le contenu de la longitude
     * en degrés de la position de l'observateur.
     *
     * @return le contenu de la longitude en degrés
     */
    public double getLonDeg() { return lonDeg.get(); }

    /**
     * Méthode qui permet de modifier le contenu de la longitude
     * en degrés de la position de l'observateur.
     *
     * @param lonDeg la nouvelle longitude en degrés
     */
    public void setLonDeg(double lonDeg) { this.lonDeg.set(lonDeg); }


    /**
     * Méthode d'accès retournant la propriété de la latitude
     * en degrés de la position de l'observateur.
     *
     * @return la propriété de la latitude en degrés
     */
    public DoubleProperty latDegProperty() { return latDeg; }

    /**
     * Méthode d'accès retournant le contenu de la latitude
     * en degrés de la position de l'observateur.
     *
     * @return le contenu de la latitude en degrés
     */
    public double getLatDeg() { return latDeg.get(); }

    /**
     * Méthode qui permet de modifier le contenu de la latitude
     * en degrés de la position de l'observateur.
     *
     * @param latDeg la nouvelle latitude en degrés
     */
    public void setLatDeg(double latDeg) { this.latDeg.set(latDeg); }


    /**
     * Méthode d'accès retournant le contenu de la propriété
     * des coordonnées de la position de l'observateur.
     *
     * @return le contenu de la propriété des coordonnées
     */
    public GeographicCoordinates getCoordinates() { return coordinates.get(); }

    /**
     * Méthode qui permet de modifier les coordonnées de
     * la position de l'observateur.
     *
     * @param coordinates les nouvelles coordonnées
     */
    public void setCoordinates(GeographicCoordinates coordinates) {
        setLonDeg(coordinates.lonDeg());
        setLatDeg(coordinates.latDeg());
    }
}
