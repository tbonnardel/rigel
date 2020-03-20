package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import static java.lang.Math.asin;
import static java.lang.Math.atan;
import static java.lang.Math.atan2;
import static java.lang.Math.PI;
import static java.lang.Math.sqrt;
import static java.lang.Math.log10;

/**
 * Cette énumération contient les modèles des huit planètes du système solaire.
 *
 * @author Thomas Bonnardel (319827)
 */
public enum PlanetModel implements CelestialObjectModel<Planet> {
    MERCURY("Mercure", 0.24085, 75.5671, 77.612, 0.205627,
            0.387098, 7.0051, 48.449, 6.74, -0.42),
    VENUS("Vénus", 0.615207, 272.30044, 131.54, 0.006812,
            0.723329, 3.3947, 76.769, 16.92, -4.40),
    EARTH("Terre", 0.999996, 99.556772, 103.2055, 0.016671,
            0.999985, 0, 0, 0, 0),
    MARS("Mars", 1.880765, 109.09646, 336.217, 0.093348,
            1.523689, 1.8497, 49.632, 9.36, -1.52),
    JUPITER("Jupiter", 11.857911, 337.917132, 14.6633, 0.048907,
            5.20278, 1.3035, 100.595, 196.74, -9.40),
    SATURN("Saturne", 29.310579, 172.398316, 89.567, 0.053853,
            9.51134, 2.4873, 113.752, 165.60, -8.88),
    URANUS("Uranus", 84.039492, 271.063148, 172.884833, 0.046321,
            19.21814, 0.773059, 73.926961, 65.80, -7.19),
    NEPTUNE("Neptune", 165.84539, 326.895127, 23.07, 0.010483,
            30.1985, 1.7673, 131.879, 62.20, -6.87);


    public static List<PlanetModel> ALL = List.of(MERCURY, VENUS, EARTH, MARS,
            JUPITER, SATURN, URANUS, NEPTUNE);

    private final static double TROPICAL_YEAR = 365.242191;


    private final String frenchName;
    private final double orbitalPeriod;
    private final double J2010Longitude;
    private final double perigeeLongitude;
    private final double eccentricity;
    private final double semiMajorAxis;
    private final double eclipticInclinaison;
    private final double ascendingNodeLongitude;
    private final double angularSize;
    private final double magnitude;

    /**
     * Constructeur prenant en arguments le nom français de la planète suivi des paramètres suivants.
     *
     * @param frenchName le nom français de la planète
     * @param orbitalPeriod la période de révolution de la planète en année tropique
     * @param J2010Longitude la longitude de la planète à J2010 en degrés
     * @param perigeeLongitude la longitude au périgée en degrés
     * @param eccentricity l'excentricité de l'orbite
     * @param semiMajorAxis le demi-grand axe en UA
     * @param eclipticInclinaison l'inclinaison de l'orbite à l'écliptique en degrés
     * @param ascendingNodeLongitude la longitude du noeud ascendant en degrés
     * @param angularSize la taille angulaire en arcsec
     * @param magnitude la magnitude en UA
     */
    PlanetModel(String frenchName,
                double orbitalPeriod, double J2010Longitude, double perigeeLongitude, double eccentricity,
                double semiMajorAxis, double eclipticInclinaison, double ascendingNodeLongitude,
                double angularSize, double magnitude) {
        this.frenchName = frenchName;
        this.orbitalPeriod = orbitalPeriod;
        this.J2010Longitude = Angle.ofDeg(J2010Longitude);
        this.perigeeLongitude = Angle.ofDeg(perigeeLongitude);
        this.eccentricity = eccentricity;
        this.semiMajorAxis = semiMajorAxis;
        this.eclipticInclinaison = Angle.ofDeg(eclipticInclinaison);
        this.ascendingNodeLongitude = Angle.ofDeg(ascendingNodeLongitude);
        this.angularSize = Angle.ofArcsec(angularSize);
        this.magnitude = magnitude;
    }

    /**
     * Méthode qui retourne la planète modélisé par le modèle en fonction des paramètres donnés.
     *
     * @param daysSinceJ2010 nombre de jours après l'époque J2000
     * @param eclipticToEquatorialConversion la conversion pour obtenir ses coordonnées équatoriales
     *                                       à partir de ses coordonnées écliptiques
     * @return
     */
    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        System.out.println("Days: " + daysSinceJ2010);
        double meanAnomaly = calculateMeanAnomaly(daysSinceJ2010);
        System.out.printf("Mean anomaly: %f°%n", Angle.toDeg(meanAnomaly));
        double trueAnomaly = calculateTrueAnomaly(meanAnomaly);
        System.out.printf("True anomaly: %f°%n", Angle.toDeg(trueAnomaly));

        double orbitRadius = calculateOrbitRadius(trueAnomaly);
        System.out.printf("r: %f AU%n", orbitRadius);
        double orbitPlaneLongitude = calculateOrbitPlaneLongitude(trueAnomaly);
        System.out.printf("l: %f°%n", Angle.toDeg(orbitPlaneLongitude));
        double eclipticLatitude = calculateEclipticLatitude(orbitPlaneLongitude);
        System.out.printf("psi: %f°%n", Angle.toDeg(eclipticLatitude));

        double eclipticRadius = calculateEclipticRadius(orbitRadius, eclipticLatitude);
        System.out.printf("r': %f°%n", eclipticRadius);
        double eclipticLongitude = calculateEclipticLongitude(orbitPlaneLongitude);
        System.out.printf("l': %f°%n", Angle.toDeg(eclipticLongitude));

        double geocentricEclipticLongitude;
        if (isInnerPlanet()) {
            System.out.println("INNER PLANET");
            geocentricEclipticLongitude = calculateGeocentricEclipticLongitudeForInnerPlanets(daysSinceJ2010, eclipticRadius, eclipticLongitude);
        } else {
            System.out.println("OUTER PLANET");
            geocentricEclipticLongitude = calculateGeocentricEclipticLongitudeForOuterPlanets(daysSinceJ2010, eclipticRadius, eclipticLongitude);
        }
        System.out.printf("lambda: %f°%n", Angle.toDeg(geocentricEclipticLongitude));
        double geocentricEclipticLatitude = calculateGeocentricEclipticLatitude(daysSinceJ2010, eclipticRadius, eclipticLongitude, eclipticLatitude, geocentricEclipticLongitude);
        System.out.printf("beta: %f°%n", Angle.toDeg(geocentricEclipticLatitude));

        EquatorialCoordinates equatorialPos = eclipticToEquatorialConversion.apply(EclipticCoordinates.of(geocentricEclipticLongitude, geocentricEclipticLatitude));

        double angularSize = calculateAngularSize(daysSinceJ2010, orbitRadius, orbitPlaneLongitude, eclipticLatitude);
        double magnitude = calculateMagnitude(daysSinceJ2010, geocentricEclipticLongitude, orbitPlaneLongitude, orbitRadius, eclipticLatitude);

        return new Planet(frenchName, equatorialPos, (float)angularSize, (float)magnitude);

    }


    /**
     * Méthode privée qui indique si la planète est inférieure ou non.
     *
     * @return true si la planète est inférieure
     */
    private boolean isInnerPlanet() {
        return (semiMajorAxis < 1);
    }

    /**
     * Méthode privée qui calcule l'anomalie moyenne.
     *
     * @param daysSinceJ2010
     * @return l'anomalie moyenne
     */
    private double calculateMeanAnomaly(double daysSinceJ2010) {
        return Angle.normalizePositive((Angle.TAU/TROPICAL_YEAR)*(daysSinceJ2010/orbitalPeriod) + J2010Longitude - perigeeLongitude);
    }

    /**
     * Méthode privée qui calcule l'anomalie vraie.
     *
     * @param meanAnomaly
     * @return l'anomalie vraie
     */
    private double calculateTrueAnomaly(double meanAnomaly) {
        return Angle.normalizePositive(meanAnomaly + 2*eccentricity*sin(meanAnomaly));
    }

    /**
     * Méthode privée qui calcule le rayon de la planète dans le plan de son orbite.
     *
     * @param trueAnomaly
     * @return le rayon de la planète dans le plan de son orbite
     */
    private double calculateOrbitRadius(double trueAnomaly) {
        return (semiMajorAxis*(1-eccentricity*eccentricity)) / (1 + eccentricity*cos(trueAnomaly));
    }

    /**
     * Méthode privée qui calcule la longitude de la planète dans le plan de son orbite.
     *
     * @param trueAnomaly
     * @return la longitude de la planète dans le plan de son orbite
     */
    private double calculateOrbitPlaneLongitude(double trueAnomaly) {
        return Angle.normalizePositive(trueAnomaly + perigeeLongitude);
    }

    /**
     * Méthode privée qui calcule la latitude ecliptique héliocentrique de la planète.
     *
     * @param orbitPlaneLongitude
     * @return la latitude ecliptique héliocentrique de la planète
     */
    private double calculateEclipticLatitude(double orbitPlaneLongitude) {
        return asin(sin(orbitPlaneLongitude - ascendingNodeLongitude)*sin(eclipticInclinaison));
    }

    /**
     * Méthode privée qui calcule le rayon projeté sur le plan de l'écliptique.
     *
     * @param radius
     * @param eclipticLatitude
     * @return le rayon projeté sur le plan de l'écliptique
     */
    private double calculateEclipticRadius(double radius, double eclipticLatitude) {
        return radius*cos(eclipticLatitude);
    }

    /**
     * Méthode privée qui calcule la longitude projetée sur le plan de l'écliptique.
     *
     * @param orbitPlaneLongitude
     * @return la longitude projetée sur le plan de l'écliptique
     */
    private double calculateEclipticLongitude(double orbitPlaneLongitude) {
        return Angle.normalizePositive(atan2(sin(orbitPlaneLongitude - ascendingNodeLongitude) * cos(eclipticInclinaison),
                cos(orbitPlaneLongitude - ascendingNodeLongitude)
        ) + ascendingNodeLongitude);
    }

    /**
     * Méthode privée qui calcule la longitude écliptique géocentrique pour les planètes inférieures.
     *
     * @param daysSinceJ2010
     * @param eclipticRadius
     * @param eclipticLongitude
     * @return la longitude écliptique géocentrique pour les planètes inférieures
     */
    private double calculateGeocentricEclipticLongitudeForInnerPlanets(double daysSinceJ2010, double eclipticRadius, double eclipticLongitude) {
        double L = calculateEarthOrbitPlaneLongitude(daysSinceJ2010);
        double R = calculateEarthOrbitRadius(daysSinceJ2010);
        return Angle.normalizePositive(PI + L + atan2(eclipticRadius*sin(L-eclipticLongitude),
                            R - eclipticRadius*cos(L - eclipticLongitude)));
    }

    /**
     * Méthode privée qui calcule la latitude écliptique géocentrique de la planète.
     *
     * @param daysSinceJ2010
     * @param eclipticRadius
     * @param eclipticLongitude
     * @param eclipticLatitude
     * @param geocentricEclipticLongitude
     * @return la latitude écliptique géocentrique de la planète
     */
    private double calculateGeocentricEclipticLatitude(double daysSinceJ2010, double eclipticRadius, double eclipticLongitude,
                                                       double eclipticLatitude, double geocentricEclipticLongitude) {
        double L = calculateEarthOrbitPlaneLongitude(daysSinceJ2010);
        System.out.printf("L: %f°%n", Angle.toDeg(L));
        double R = calculateEarthOrbitRadius(daysSinceJ2010);
        System.out.printf("R: %f AU%n", R);

        return atan(eclipticRadius*tan(eclipticLatitude)*sin(geocentricEclipticLongitude - eclipticLongitude)
                /(R*sin(eclipticLongitude - L)));
    }

    /**
     * Méthode privée qui calcule la longitude écliptique géocentrique pour les planètes supérieures.
     *
     * @param daysSinceJ2010
     * @param eclipticRadius
     * @param eclipticLongitude
     * @return la longitude écliptique géocentrique pour les planètes supérieures
     */
    private double calculateGeocentricEclipticLongitudeForOuterPlanets(double daysSinceJ2010, double eclipticRadius, double eclipticLongitude) {
        double L = calculateEarthOrbitPlaneLongitude(daysSinceJ2010);
        double R = calculateEarthOrbitRadius(daysSinceJ2010);
        return Angle.normalizePositive(eclipticLongitude + atan2(R*sin(eclipticLongitude - L),
                                    eclipticRadius - R*cos(eclipticLongitude - L)));
    }

    /**
     * Méthode privée qui calcule la distance séparant la Terre de la planète étudiée (en UA).
     *
     * @param daysSinceJ2010
     * @param orbitRadius
     * @param orbitPlaneLongitude
     * @param eclipticLatitude
     * @return la distance séparant la Terre de la planète étudiée (en UA)
     */
    private double calculateDistance(double daysSinceJ2010, double orbitRadius, double orbitPlaneLongitude, double eclipticLatitude) {
        double L = calculateEarthOrbitPlaneLongitude(daysSinceJ2010);
        double R = calculateEarthOrbitRadius(daysSinceJ2010);

        double rhoSquare = Polynomial.of(1,
                -2*R*cos(orbitPlaneLongitude - L)*cos(eclipticLatitude),
                R*R).at(orbitRadius);
        System.out.printf("rho2: %f UA2%n", rhoSquare);
        return sqrt(rhoSquare);
    }

    /**
     * Méthode privée qui calcule la taille angulaire de la planète.
     *
     * @param daysSinceJ2010
     * @param orbitRadius
     * @param orbitPlaneLongitude
     * @param eclipticLatitude
     * @return la taille angulaire de la planète
     */
    private double calculateAngularSize(double daysSinceJ2010, double orbitRadius, double orbitPlaneLongitude, double eclipticLatitude) {
        double rho = calculateDistance(daysSinceJ2010, orbitRadius, orbitPlaneLongitude, eclipticLatitude);
        System.out.printf("rho: %f UA%n", rho);
        return angularSize/rho;
    }

    /**
     * Méthode privée qui calcule la magnitude de la planète.
     *
     * @param daysSinceJ2010
     * @param geocentricEclipticLongitude
     * @param orbitPlaneLongitude
     * @param orbitRadius
     * @param eclipticLatitude
     * @return la magnitude de la planète
     */
    private double calculateMagnitude(double daysSinceJ2010, double geocentricEclipticLongitude, double orbitPlaneLongitude, double orbitRadius, double eclipticLatitude) {
        double F = (1 + cos(geocentricEclipticLongitude - orbitPlaneLongitude))/2;
        double rho = calculateDistance(daysSinceJ2010, orbitRadius, orbitPlaneLongitude, eclipticLatitude);
        return magnitude + 5*log10((orbitRadius*rho)/sqrt(F));
    }

    /**
     * Méthode privée qui calcule le rayon de la Terre dans le plan de son orbite.
     *
     * @param daysSinceJ2010
     * @return le rayon de la Terre dans le plan de son orbite
     */
    private double calculateEarthOrbitRadius(double daysSinceJ2010) {
        double meanAnomaly = EARTH.calculateMeanAnomaly(daysSinceJ2010);
        double trueAnomay = EARTH.calculateTrueAnomaly(meanAnomaly);

        return (EARTH.semiMajorAxis*(1-EARTH.eccentricity*EARTH.eccentricity)) / (1+EARTH.eccentricity*cos(trueAnomay));
    }

    /**
     * Méthode privée qui calcule la longitude de la Terre dans le plan de son orbite.
     *
     * @param daysSinceJ2010
     * @return la longitude de la Terre dans le plan de son orbite
     */
    private double calculateEarthOrbitPlaneLongitude(double daysSinceJ2010) {
        double meanAnomaly = EARTH.calculateMeanAnomaly(daysSinceJ2010);
        double trueAnomay = EARTH.calculateTrueAnomaly(meanAnomaly);

        return Angle.normalizePositive(trueAnomay + EARTH.perigeeLongitude);
    }
}
