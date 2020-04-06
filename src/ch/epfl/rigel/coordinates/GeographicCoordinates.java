package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Classe qui représente des coordonnées géographiques.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class GeographicCoordinates extends SphericalCoordinates {

    private GeographicCoordinates(double lon, double lat) { // Constructeur privé
        super(lon, lat);
    }

    /**
     * Méthode statique qui retourne les coordonnées géographiques dont la longitude
     * et la latitude sont donnés en degrés.
     *
     * @param lonDeg la longitude en degrés
     * @param latDeg la latitude en degrés
     * @return les coordonnées géographiques des paramètres donnés
     * @throws IllegalArgumentException si l'une des composantes est invalide
     */
    public static GeographicCoordinates ofDeg(double lonDeg, double latDeg) {
        Preconditions.checkArgument(isValidLonDeg(lonDeg));
        Preconditions.checkArgument(isValidLatDeg(latDeg));

        return new GeographicCoordinates(Angle.ofDeg(lonDeg), Angle.ofDeg(latDeg));
    }

    /**
     * Méthode statique qui vérifie si l'angle de longitude passé en degrés est valide.
     *
     * @param lonDeg la longitude en degrés à tester
     * @return vrai ssi l'angle qui lui est passé représente une longitude valide en degrés
     */
    public static boolean isValidLonDeg(double lonDeg) {
        RightOpenInterval lonDegInterval = RightOpenInterval.symmetric(360);
        return lonDegInterval.contains(lonDeg);
    }

    /**
     * Méthode statique qui vérifie si l'angle de latitude passé en degrés est valide.
     *
     * @param latDeg la latitude en degrés à tester
     * @return vrai ssi l'angle qui lui est passé représente une latitude valide en degrés
     */
    public static boolean isValidLatDeg(double latDeg) {
        ClosedInterval latDegInterval = ClosedInterval.symmetric(180);
        return latDegInterval.contains(latDeg);
    }

    /**
     * Méthode qui retourne la longitude en radians.
     *
     * @return la longitude en radians
     */
    @Override
    public double lon() {
        return super.lon();
    }

    /**
     * Méthode qui retourne la longitude en degrés.
     *
     * @return la longitude en degrés
     */
    @Override
    public double lonDeg() {
        return super.lonDeg();
    }

    /**
     * Méthode qui retourne la latitude en radians.
     *
     * @return la latitude en radians
     */
    @Override
    public double lat() {
        return super.lat();
    }

    /**
     * Méthode qui retourne la latitude en degrés.
     *
     * @return la latitude en degrés
     */
    @Override
    public double latDeg() {
        return super.latDeg();
    }

    /**
     * Redéfinition de la méthode toString qui retourne la représentation textuelle
     * des coordonnées géographiques, en degrés avec une précision de e-4.
     *
     * @return la représentation textuelle des coordonnées géographiques
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(lon=%.4f°, lat=%.4f°)",
                lonDeg(),
                latDeg());
    }
}
