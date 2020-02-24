package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Classe qui représente des coordonnées équatoriales.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class EquatorialCoordinates extends SphericalCoordinates {

    private EquatorialCoordinates(double lon, double lat) {
        super(lon, lat);
    }

    /**
     * Méthode statique de construction de coordonnées équatoriales, données en radians.
     *
     * @param ra l'ascension droite en radians
     * @param dec la déclinaison en radians
     * @return les coordonnées équatoriales des paramètres donnés en radians
     * @throws IllegalArgumentException si l'une des composantes est invalide
     */
    public static EquatorialCoordinates of(double ra, double dec) {
        Preconditions.checkArgument(isValidRa(ra));
        Preconditions.checkArgument(isValidDec(dec));

        return new EquatorialCoordinates(ra, dec);
    }

    /**
     * Méthode qui vérifie la validité de la valeur de l'ascension droite en radians.
     *
     * @param ra l'ascension droite en radians
     * @return true ssi la valeur de l'ascension droite en radians est valide
     */
    private static boolean isValidRa(double ra) {
        RightOpenInterval raInterval = RightOpenInterval.of(Angle.ofDeg(0), Angle.ofDeg(360));
        return raInterval.contains(ra);
    }

    /**
     * Méthode qui vérifie la validité de la valeur de la déclinaison en radians.
     *
     * @param dec la déclinaison en radians
     * @return true ssi la valeur de la déclinaison en radians est valide
     */
    private static boolean isValidDec(double dec) {
        ClosedInterval decInterval = ClosedInterval.symmetric(Angle.ofDeg(180));
        return decInterval.contains(dec);
    }

    /**
     * Méthode qui retourne l'ascension droite en radians.
     *
     * @return l'ascension droite en radians
     */
    public double ra() {
        return lon();
    }

    /**
     * Méthode qui retourne l'ascension droite en degrés.
     *
     * @return l'ascension droite en degrés
     */
    public double raDeg() {
        return lonDeg();
    }

    /**
     * Méthode qui retourne l'ascension droite en heures.
     * @return l'ascension droite en heures
     */
    public double raHr() {
        return Angle.toHr(lon());
    }

    /**
     * Méthode qui retourne la déclinaison en radians.
     * @return la déclinaison en radians
     */
    public double dec() {
        return lat();
    }

    public double decDeg() {
        return latDeg();
    }

    /**
     * Redéfinition de la méthode toString pour obtenir la représentation textuelle
     * des coordonnées équatoriales.
     *
     * @return la représentation textuelle des coordonnées équatoriales
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(ra=%.4fh, dec=%.4f°)",
                raHr(),
                decDeg());
    }
}
