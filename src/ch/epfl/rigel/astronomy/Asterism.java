package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;

import java.util.List;

/**
 * Cette classe représente un astérisme.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Asterism {

    private final List<Star> stars;

    /**
     * Constrcuteur qui construit un astérisme composé de la liste d'étoiles données,
     * ou lève IllegalArgumentException si celle-ci est vide.
     *
     * @param stars la liste d'étoiles de l'astérisme
     * @throws IllegalArgumentException si la liste d'étoiles stars données est vide
     */
    public Asterism(List<Star> stars) {
        Preconditions.checkArgument(!stars.isEmpty());
        this.stars = List.copyOf(stars);
    }

    /**
     * Méthode d'accès qui retourne la liste d'étoiles qui composent l'astérisme.
     *
     * @return la liste d'étoiles qui composent l'astérisme
     */
    public List<Star> stars() {
        return stars;
    }
}
