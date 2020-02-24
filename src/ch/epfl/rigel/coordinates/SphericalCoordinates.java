package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;

import java.util.Objects;

/**
 * Classe mère à toutes les classes représentant des coordonnées sphériques.
 *
 * @author Thomas Bonnardel (319827)
 */
abstract class SphericalCoordinates {

    private double lon;
    private double lat;

    SphericalCoordinates(double lon, double lat) {
        this.lon = lon;
        this.lat = lat;
    }

    /**
     * Méthode qui retourne la longitude en radians.
     *
     * @return la longitude en radians.
     */
    double lon() {
        return lon;
    }

    /**
     * Méthode qui retourne la longitude en degrés.
     *
     * @return la longitude en degrés
     */
    double lonDeg() {
        return Angle.toDeg(lon);
    }

    /**
     * Méthode qui retourne la latitude en radians.
     *
     * @return la latitude en radians
     */
    double lat() {
        return lat;
    }

    /**
     * Méthode qui retourne la latitude en degrés.
     *
     * @return la latitude en degrés
     */
    double latDeg() {
        return Angle.toDeg(lat);
    }

    /**
     * Redéfinition de equal de Object en levant l'exception UnsupportedOperationException.
     *
     * @param o l'objet à tester
     * @return la valeur de l'égalité des deux objets (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Redéfinition de hashCode de Object en levant l'exception UnsupportedOperationException.
     *
     * @return le hashCode (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
