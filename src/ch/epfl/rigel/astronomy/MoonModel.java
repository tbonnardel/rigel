package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.asin;
import static java.lang.Math.atan2;

/**
 * Cette énumération représente un modèle de la Lune.
 *
 * @author Thomas Bonnardel (319827)
 */
public enum MoonModel implements CelestialObjectModel<Moon> {
    /**
     * Objet représentant un modèle de la Lune
     */
    MOON;


    private final static double L_0 = Angle.ofDeg(91.929336);
    private final static double P_0 = Angle.ofDeg(130.143076);
    private final static double N_0 = Angle.ofDeg(291.682547);
    private final static double I = Angle.ofDeg(5.145396);
    private final static double E = 0.0549;
    private final static double THETA_0 = Angle.ofDeg(0.5181);

    private final static double MAGNITUDE = 0; // Par défaut

    /**
     * Méthode qui retourne la Lune modélisée par le modèle en fonction des paramètres donnés.
     * @param daysSinceJ2010 nombre de jours après l'époque J2000
     * @param eclipticToEquatorialConversion la conversion pour obtenir ses coordonnées équatoriales
     *                                       à partir de ses coordonnées écliptiques
     * @return
     */
    @Override
    public Moon at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        // 1. Calcul de la longitude orbital
        double meanOrbitalLongitude = Angle.ofDeg(13.1763966)*daysSinceJ2010 + L_0;
        double meanAnomaly = meanOrbitalLongitude - Angle.ofDeg(0.1114041)*daysSinceJ2010 - P_0;

        Sun sun = SunModel.SUN.at(daysSinceJ2010, eclipticToEquatorialConversion);
        double sunMeanAnomaly = sun.meanAnomaly();
        double sunGeocentricEclipticLongitude = sun.eclipticPos().lon();

        double eV = Angle.ofDeg(1.2739)*sin(2*(meanOrbitalLongitude - sunGeocentricEclipticLongitude) - meanAnomaly);
        double aE = Angle.ofDeg(0.1858)*sin(sunMeanAnomaly);
        double a3 = Angle.ofDeg(0.37)*sin(sunMeanAnomaly);
        double correctedMeanAnomaly = meanAnomaly + eV - aE - a3;

        double eC = Angle.ofDeg(6.2886)*sin(correctedMeanAnomaly);
        double a4 = Angle.ofDeg(0.214)*sin(2*correctedMeanAnomaly);
        double correctedMeanOrbitalLongitude = meanOrbitalLongitude + eV + eC - aE + a4;

        double v = Angle.ofDeg(0.6583)*sin(2*(correctedMeanOrbitalLongitude - sunGeocentricEclipticLongitude));
        double trueOrbitalLongitude = correctedMeanOrbitalLongitude + v;


        // 2. Calcul de la position écliptique
        double meanLongitude = N_0 - Angle.ofDeg(0.0529539)*daysSinceJ2010;
        double ascendingNodeCorrectedLongitude = meanLongitude - Angle.ofDeg(0.16)*sin(sunMeanAnomaly);

        double eclipticLongitude = Angle.normalizePositive(atan2(
                sin(trueOrbitalLongitude - ascendingNodeCorrectedLongitude)*cos(I),
                cos(trueOrbitalLongitude - ascendingNodeCorrectedLongitude)
        ) + ascendingNodeCorrectedLongitude);
        double eclipticLatitude = asin(sin(trueOrbitalLongitude - ascendingNodeCorrectedLongitude)*sin(I));
        EclipticCoordinates ecl = EclipticCoordinates.of(eclipticLongitude, eclipticLatitude);


        // 3. Calcul de la phase
        double phase = (1 - cos(trueOrbitalLongitude - sunGeocentricEclipticLongitude))/2;

        // 4. Calcul de la taille angulaire
        double rho = (1 - E*E)/(1 + E*cos(correctedMeanAnomaly + eC));
        double angularSize = THETA_0/rho;

        return new Moon(eclipticToEquatorialConversion.apply(ecl), (float)angularSize, (float)MAGNITUDE, (float)phase);
    }
}
