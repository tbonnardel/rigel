package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import java.util.Objects;

/**
 * Cette classe sert de classe mère à toutes les classes représentant des objets célestes.
 *
 * @author Thomas Bonnardel (319827)
 */
public abstract class CelestialObject {

    private final String name;
    private final EquatorialCoordinates equatorialPos;
    private final float angularSize;
    private final float magnitude;

    /**
     * Constructeur *package private* qui construit un objet céleste portant le nom name,
     * situé aux coordonnées équatoriales equatorialPos, de taille angulaire angularSize et
     * de magnitude magnitude, ou lève IllegalArgumentException si la taille angulaire est négative,
     * ou NullPointerException si le nom ou la position équatoriale sont nuls (c-à-d égaux à null).
     *
     * @param name le nom de l'objet céleste
     * @param equatorialPos les coordonnées équatoriales de l'objet céleste
     * @param angularSize la taille angulaire de l'objet céleste
     * @param magnitude la magnitude de l'objet céleste
     * @throws IllegalArgumentException si la taille angulaire est négative
     * @throws NullPointerException si le nom ou la position équatoriale sont nuls
     */
    CelestialObject(String name, EquatorialCoordinates equatorialPos, float angularSize, float magnitude) {
        if (angularSize < 0)
            throw new IllegalArgumentException();

        this.name = Objects.requireNonNull(name);
        this.equatorialPos = Objects.requireNonNull(equatorialPos);
        this.angularSize = angularSize;
        this.magnitude = magnitude;
    }

    /**
     * Méthode d'accès qui retourne le nom de l'objet céleste.
     *
     * @return le nom de l'objet céleste
     */
    public String name() {
        return name;
    }

    /**
     * Méthode d'accès qui retourne la taille angulaire de l'objet céleste.
     *
     * @return la taille angulaire de l'objet céleste
     */
    public double angularSize() {
        return angularSize;
    }

    /**
     * Méthode d'accès qui retourne la magnitude de l'objet céleste.
     *
     * @return la magnitude de l'objet céleste
     */
    public double magnitude() {
        return magnitude;
    }

    /**
     * Méthode d'accès qui retourne les coordonnées équatoriales de l'objet céleste.
     * @return les coordonnées équatoriales de l'objet céleste
     */
    public EquatorialCoordinates equatorialPos() {
        return equatorialPos;
    }

    /**
     * Méthode qui retourne un (court) texte informatif au sujet de l'objet céleste,
     * destiné à être montré à l'utilisateur.
     *
     * @return le court texte informatif au sujet de l'objet céleste
     */
    public String info() {
        return name;
    }

    /**
     * Redéfinition de la méthode toString qui retourne la représentation textuelle de l'objet.
     *
     * @return la représentation textuelle de l'objet céleste
     */
    @Override
    public String toString() {
        return info();
    }
}
