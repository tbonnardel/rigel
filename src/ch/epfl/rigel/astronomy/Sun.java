package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * Cette classe est la sous-classe de CelestialObject représentant la Soleil
 * à un instant donné.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Sun extends CelestialObject {

    private final static String SUN_NAME = "Soleil";
    private final static float SUN_MAGNITUDE = -26.7f;
    private final EclipticCoordinates eclipticPos;
    private final float meanAnomaly;

    /**
     * Méthode qui construit (un objet représentant) le Soleil avec la position écliptique,
     * la position équatoriale, la taille angulaire et l'anomalie moyenne données, ou lève
     * NullPointerException si la position écliptique est nulle.
     *
     * @param eclipticPos la position écliptique du Soleil
     * @param equatorialPos la position équatoriale du Soleil
     * @param angularSize la taille angulaire du Soleil
     * @param meanAnomaly l'anomalie moyenne du Soleil
     * @throws IllegalArgumentException si la taille angulaire est négative
     * @throws NullPointerException si les position équatoriale ou écliptique sont nulles
     */
    public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly) {
        super(SUN_NAME, equatorialPos, angularSize, SUN_MAGNITUDE);

        this.eclipticPos = Objects.requireNonNull(eclipticPos);
        this.meanAnomaly = meanAnomaly;
    }
}
