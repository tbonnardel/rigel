package ch.epfl.rigel.astronomy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
