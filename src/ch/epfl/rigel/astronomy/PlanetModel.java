package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.math.Angle;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
     * @param angularSize la taille angulaire en UA
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
        this.angularSize = angularSize;
        this.magnitude = magnitude;
    }

    @Override
    public Planet at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        // TODO: To implement
        return null;
    }
}
