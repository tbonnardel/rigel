package ch.epfl.rigel.astronomy;

import java.util.*;

/**
 * Cette classe représente un catalogue d'étoiles et d'astérismes.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class StarCatalogue {

    private final List<Star> stars;
    private final Map<Asterism, List<Integer>> catalogue;

    /**
     * Constructeur qui construit un catalogue constitué des étoiles stars et des astérismes
     * asterisms ou lève une exception si l'un des astérismes contient une étoile qui ne fait
     * pas partie de la liste d'étoiles .
     *
     * @param stars la liste des étoiles à ajouter au catalogue
     * @param asterisms la liste des astérismes à ajouter au catalogue
     * @throws IllegalArgumentException si l'un des astérismes contient une étoile qui ne fait
     * pas partie de la liste d'étoiles
     */
    public StarCatalogue(List<Star> stars, List<Asterism> asterisms) {
        this.stars = List.copyOf(stars);

        Map<Asterism, List<Integer>> catalogue = new HashMap<>();
        for (Asterism asterism : asterisms) {
            List<Integer> starsIndex = new ArrayList<>();
            for (Star star: asterism.stars()) {
                starsIndex.add(getStarIndex(star));
            }
            catalogue.put(asterism, starsIndex);
        }

        this.catalogue = Map.copyOf(catalogue);
    }

    /**
     * Méthode d'accès qui retourne la liste des étoiles du catalogue.
     *
     * @return la liste des étoiles du catalogue
     */
    public List<Star> stars() {
        return stars;
    }

    /**
     * Méthode d'accès qui retourne l'ensemble des astérismes du catalogue.
     *
     * @return l'ensemble des astérismes du catalogue
     */
    public Set<Asterism> asterisms() {
        return catalogue.keySet();
    }

    /**
     * Méthode d'accès qui retourne la liste des index — dans le catalogue — des étoiles
     * constituant l'astérisme donné, ou lève IllegalArgumentException si l'astérisme
     * donné ne fait pas partie du catalogue.
     *
     * @param asterism l'astérisme dont on souhaite avoir les étoiles
     * @return la liste des index — dans le catalogue — des étoiles constituant
     * l'astérisme donné
     * @throws IllegalArgumentException si l'astérisme donné ne fait pas partie du catalogue
     */
    public List<Integer> asterismIndices(Asterism asterism) {
        if (!catalogue.containsKey(asterism)) {
            throw new IllegalArgumentException();
        }

        return catalogue.get(asterism);
    }

    /**
     * Méthode qui retourne l'index de l'étoile donnée de la liste stars
     *
     * @param star l'étoile recherchée
     * @return l'index de l'étoile donnée en paramètre dans la liste stars
     * @throws IllegalArgumentException si l'étoile star n'est pas dans la liste stars
     */
    private int getStarIndex(Star star) {
        int hipparcosIdTarget = star.hipparcosId();
        for(int i = 0; i < stars.size(); i++) {
            if (stars.get(i).hipparcosId() == hipparcosIdTarget) {
                return i;
            }
        }

        throw new IllegalArgumentException();
    }
}
