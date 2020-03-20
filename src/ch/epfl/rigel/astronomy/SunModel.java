package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

import static java.lang.Math.sin;
import static java.lang.Math.cos;

/**
 * Cette énumération contient un seul élément nommé SUN et représentant un modèle du Soleil.
 *
 * @author Thomas Bonnardel (319827)
 */
public enum SunModel implements CelestialObjectModel<Sun> {

    /**
     * Objet représentant un modèle du Soleil.
     */
    SUN;

    private final static double EPS_G = Angle.ofDeg(279.557208);
    private final static double OMEGA_G = Angle.ofDeg(283.112438);
    private final static double E = 0.016705;
    private final static double TROPICAL_YEAR = 365.242191;
    private final static double THETA_0 = Angle.ofDeg(.533128);

    /**
     * Méthode qui retourne le Soleil modélisé par le modèle en fonction des paramètres donnés.
     *
     * @param daysSinceJ2010 nombre de jours après l'époque J2000
     * @param eclipticToEquatorialConversion la conversion pour obtenir ses coordonnées équatoriales
     *                                       à partir de ses coordonnées écliptiques
     * @return le modèle calculé
     */
    @Override
    public Sun at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        double meanAnomaly = Angle.normalizePositive(calculateMeanAnomaly(daysSinceJ2010));
        double trueAnomaly = Angle.normalizePositive(calculateTrueAnomaly(meanAnomaly));
        double angularSize = Angle.normalizePositive(calculateAngularSize(trueAnomaly));
        double lon = Angle.normalizePositive(trueAnomaly + OMEGA_G);
        double lat = 0;

        EclipticCoordinates eclipticPos = EclipticCoordinates.of(lon, lat);
        EquatorialCoordinates equatorialPos = eclipticToEquatorialConversion.apply(eclipticPos);

        return new Sun(eclipticPos, equatorialPos, (float) angularSize, (float) meanAnomaly);
    }

    /**
     * Méthode privée qui calcule l'anomalie moyenne.
     *
     * @param daysSinceJ2010 nombre de jours après l'époque J2000
     * @return l'anomalie moyenne
     */
    private double calculateMeanAnomaly(double daysSinceJ2010) {
        return (Angle.TAU/TROPICAL_YEAR)*daysSinceJ2010 + EPS_G - OMEGA_G;
    }

    /**
     * Méthode privée qui calcule l'anomalie vraie.
     *
     * @param meanAnomaly l'anomalie moyenne
     * @return l'anomalie vraie
     */
    private double calculateTrueAnomaly(double meanAnomaly) {
        return meanAnomaly + 2*E*sin(meanAnomaly);
    }

    /**
     * Méthode privée qui calcule la taille angulaire.
     *
     * @param trueAnomaly l'anomalie vraie
     * @return la taille angulaire
     */
    private double calculateAngularSize(double trueAnomaly) {
        return THETA_0*((1 + E*cos(trueAnomaly))/(1 - E*E));
    }
}
