package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;

import java.util.Locale;

/**
 * Classe qui représente des coordonnées écliptiques.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class EclipticCoordinates extends SphericalCoordinates {

    private EclipticCoordinates(double lon, double lat) {
        super(lon, lat);
    }

    /**
     * Méthode statique de construction de coordonnées écliptiques, données en radians.
     *
     * @param lon la longitude écliptique en radians
     * @param lat la latitude écliptiques en radians
     * @return les coordonnées écliptiques des paramètres donnés en radians
     * @throws IllegalArgumentException si l'une des composantes est invalide
     */
    public static EclipticCoordinates of(double lon, double lat) {
        RightOpenInterval lonInterval = RightOpenInterval.of(Angle.ofDeg(0), Angle.ofDeg(360));
        ClosedInterval latInterval = ClosedInterval.symmetric(Angle.ofDeg(180));
        Preconditions.checkInInterval(lonInterval, lon);
        Preconditions.checkInInterval(latInterval, lat);

        return new EclipticCoordinates(lon, lat);
    }

    /**
     * Méthode qui retourne la longitude écliptique en radians.
     *
     * @return la longitude écliptique en radians
     */
    public double lon() {
        return super.lon();
    }

    /**
     * Méthode qui retourne la longitude écliptique en degrés.
     *
     * @return la longitude écliptique en degrés
     */
    public double lonDeg() {
        return super.lonDeg();
    }

    /**
     * Méthode qui retourne la latitude écliptique en radians.
     *
     * @return la latitude écliptiques en radians
     */
    public double lat() {
        return super.lat();
    }

    /**
     * Méthode qui retourne la latitude écliptique en degrés.
     *
     * @return la latitude écliptique en degrés
     */
    public double latDeg() {
        return super.latDeg();
    }

    /**
     * Redéfinition de toString pour retourner la représentation textuelle
     * des coordonnées écliptiques.
     *
     * @return la représentation textuelle des coordonnées écliptiques
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(λ=%.4f°, β=%.4f°)",
                this.lonDeg(),
                this.latDeg());
    }
}
