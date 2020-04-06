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
        RightOpenInterval raInterval = RightOpenInterval.of(Angle.ofDeg(0), Angle.ofDeg(360));
        ClosedInterval decInterval = ClosedInterval.symmetric(Angle.ofDeg(180));
        Preconditions.checkInInterval(raInterval, ra);
        Preconditions.checkInInterval(decInterval, dec);

        return new EquatorialCoordinates(ra, dec);
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
     *
     * @return l'ascension droite en heures
     */
    public double raHr() {
        return Angle.toHr(lon());
    }

    /**
     * Méthode qui retourne la déclinaison en radians.
     *
     * @return la déclinaison en radians
     */
    public double dec() {
        return lat();
    }

    /**
     * Méthode qui retourne la déclinaison en degrés.
     *
     * @return la déclinaison en degrés
     */
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
