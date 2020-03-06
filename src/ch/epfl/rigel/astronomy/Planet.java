package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

/**
 * Cette classe est la sous-classe de CelestialObject représentant une planète.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Planet extends CelestialObject {

    /**
     * Constructeur qui construit une planète portant le nom name,
     * situé aux coordonnées équatoriales equatorialPos, de taille angulaire angularSize et
     * de magnitude magnitude, ou lève IllegalArgumentException si la taille angulaire est négative,
     * ou NullPointerException si le nom ou la position équatoriale sont nuls (c-à-d égaux à null).
     *
     * @param name le nom de la planète
     * @param equatorialPos les coordonnées équatoriales de la planète
     * @param angularSize la taille angulaire de la planète
     * @param magnitude la magnitude de la planète
     * @throws IllegalArgumentException si la taille angulaire est négative
     * @throws NullPointerException si le nom ou la position équatoriale sont nuls
     */
    public Planet(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        super(name, equatorialPos, angularSize, magnitude);
    }
}
