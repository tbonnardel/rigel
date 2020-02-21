package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import static java.lang.Math.PI;
import static java.lang.Math.toDegrees;
import static java.lang.Math.toRadians;

/**
 * Classe non instantiable proposant des outils pour manipuler des angles.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Angle {

    /**
     * Représente la constante Tau, définie comme 2xPi
     */
    public final static double TAU = 2. * PI;

    private final static double RAD_PER_HR = TAU / 24.;
    private final static double HR_PER_RAD = 1 / RAD_PER_HR;
    private final static double RAD_PER_ARCMIN = TAU / (60. * 360.);
    private final static double RAD_PER_ARCSEC = RAD_PER_ARCMIN / 60.;


    private Angle() {} // Constructeur privé pour rendre la classe non instantiable

    /**
     * Méthode qui normalise l'angle donné en paramètre en le réduisant à l'intervalle [0, TAU[.
     *
     * @param rad l'angle à normaliser (en radian)
     * @return la valeur de l'angle normalisé, réduit à l'intervalle [0, TAU[
     */
    public static double normalizePositive(double rad) {
        RightOpenInterval interval = RightOpenInterval.of(0, TAU);
        return interval.reduce(rad);
    }

    /**
     * Méthode qui convertit l'angle en nombre de secondes d'arc donné, qui peut être quelconque
     * (y compris négatif) en radian.
     *
     * @param sec l'angle en seconde d'arc à convertir
     * @return la valeur en radians de l'angle donné
     */
    public static double ofArcsec(double sec) {
        return sec * RAD_PER_ARCSEC;
    }

    /**
     * Méthode qui convertit l'angle au format deg​° min​′ sec​″ en radians.
     *
     * @param deg le nombre de degrés
     * @param min le nombre de minutes d'arc
     * @param sec le nombre de secondes d'arc
     * @return la valeur en radians de l'angle donné
     * @throws IllegalArgumentException si l'angle fournit en paramètre ne respecte pas
     * la norme deg​° min​′ sec​″
     */
    public static double ofDMS(int deg, int min, double sec) {
        Preconditions.checkArgument((0 <= min && min< 60));
        Preconditions.checkArgument((0 <= sec && sec < 60));

        double rad = 0.;
        rad += toRadians(deg);
        rad += RAD_PER_ARCMIN * min;
        rad += RAD_PER_ARCSEC * sec;
        return rad;
    }

    /**
     * Méthode qui convertit l'angle en degrés donné en radians.
     *
     * @param deg l'angle en degrés à convertir
     * @return la valeur en radians de l'angle donné
     */
    public static double ofDeg(double deg) {
        return toRadians(deg);
    }

    /**
     * Méthode qui convertit l'angle en radians donné en degrés.
     *
     * @param rad l'angle en radians à convertir
     * @return la valeur en degrés de l'angle donné
     */
    public static double toDeg(double rad) {
        return toDegrees(rad);
    }

    /**
     * Méthode qui convertit l'angle en heures donné en radians.
     *
     * @param hr l'angle en heures à convertir
     * @return la valeur en radians de l'angle donné
     */
    public static double ofHr(double hr) {
        return RAD_PER_HR * hr;
    }

    /**
     * Méthode qui convertit l'angle en radians donné en heures.
     *
     * @param rad l'angle en radians à convertir
     * @return la valeur en heures de l'angle donné
     */
    public static double toHr(double rad) {
        return HR_PER_RAD * rad;
    }
}
