package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

/**
 * Classe qui représente des coordonnées horizontales.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class HorizontalCoordinates extends SphericalCoordinates {

    private HorizontalCoordinates(double az, double alt) { // Constructeur privé
        super(az, alt);
    }

    /**
     * Méthode statique qui retourne les coordonnées horizontales dont l'azimut
     * et la hauteur sont donnés en radians.
     *
     * @param az  l'azimut en radians
     * @param alt la hauteur en radians
     * @return les coordonnéees horizontales des paramètres donnés
     * @throws IllegalArgumentException si l'une des composantes n'est pas valide
     */
    public static HorizontalCoordinates of(double az, double alt) {
        // az doit appartenir à l'intervalle [0°, 360°[
        RightOpenInterval azRadInterval = RightOpenInterval.of(0, Angle.ofDeg(360.));
        Preconditions.checkInInterval(azRadInterval, az);

        // alt doit appartenir à l'intervalle [–90°, +90°]
        ClosedInterval altInterval = ClosedInterval.symmetric(Angle.ofDeg(180.));
        Preconditions.checkInInterval(altInterval, alt);

        return new HorizontalCoordinates(az, alt);
    }

    /**
     * Méthode statique qui retourne les coordonnées horizontales dont l'azimut
     * et la hauteur sont donnés en degrés.
     *
     * @param azDeg  l'azimut en degrés
     * @param altDeg la hauteur en degrés
     * @return les coordonnéees horizontales des paramètres donnés
     * @throws IllegalArgumentException si l'une des composantes n'est pas valide
     */
    public static HorizontalCoordinates ofDeg(double azDeg, double altDeg) {
        // az doit appartenir à l'intervalle [0°, 360°[
        RightOpenInterval azDegInterval = RightOpenInterval.of(0, 360.);
        Preconditions.checkInInterval(azDegInterval, azDeg);

        // alt doit appartenir à l'intervalle [–90°, +90°]
        ClosedInterval altDegInterval = ClosedInterval.symmetric(180.);
        Preconditions.checkInInterval(altDegInterval, altDeg);

        return new HorizontalCoordinates(Angle.ofDeg(azDeg), Angle.ofDeg(altDeg));
    }

    /**
     * Méthode qui retourne l'azimut en radians.
     *
     * @return l'azimut en radians
     */
    public double az() {
        return this.lon();
    }

    /**
     * Méthode qui retourne l'azimut en degrés.
     *
     * @return l'azimut en degrés
     */
    public double azDeg() {
        return Angle.toDeg(this.lon());
    }

    /**
     * Méthode qui a pour but de retourner la chaîne représentant l'octant auquel
     * correspond l'azimut des coordonnées.
     *
     * @param n la chaîne correspondant au nord
     * @param e la chaîne correspondant à l'est
     * @param s la chaîne correspondant au sud
     * @param w la chaîne correspondant à l'ouest
     * @return une chaîne correspondant à l'octant dans lequel se trouve l'azimut du récepteur
     */
    public String azOctantName(String n, String e, String s, String w) {
        double azDeg = azDeg();
        StringBuilder stringBuilder = new StringBuilder();

        double MIN_NORTH_IN_OCTANT_NAME = 292.5;
        double MAX_NORTH_IN_OCTANT_NAME = 67.5;
        double MIN_SOUTH_IN_OCTANT_NAME = 112.5;
        double MAX_SOUTH_IN_OCTANT_NAME = 247.5;
        double MIN_WEST_IN_OCTANT_NAME = 202.5;
        double MAX_WEST_IN_OCTANT_NAME = 337.5;
        double MIN_EAST_IN_OCTANT_NAME = 22.5;
        double MAX_EAST_IN_OCTANT_NAME = 157.5;
        if (MIN_NORTH_IN_OCTANT_NAME < azDeg || azDeg < MAX_NORTH_IN_OCTANT_NAME)
            stringBuilder.append(n);
        if (MIN_SOUTH_IN_OCTANT_NAME < azDeg && azDeg < MAX_SOUTH_IN_OCTANT_NAME)
            stringBuilder.append(s);
        if (MIN_EAST_IN_OCTANT_NAME < azDeg && azDeg < MAX_EAST_IN_OCTANT_NAME)
            stringBuilder.append(e);
        if (MIN_WEST_IN_OCTANT_NAME < azDeg && azDeg < MAX_WEST_IN_OCTANT_NAME)
            stringBuilder.append(w);
        return stringBuilder.toString();
    }
}
