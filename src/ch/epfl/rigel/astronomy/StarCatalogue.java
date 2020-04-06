package ch.epfl.rigel.astronomy;

import java.io.IOException;
import java.io.InputStream;
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

        return Collections.unmodifiableList(catalogue.get(asterism)); // TODO: Est-ce correct ?
    }

    /**
     * Méthode qui retourne l'index de l'étoile donnée de la liste stars.
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




    /**
     * Cette classe imbriquée statique représente un bâtisseur de catalogue d'étoiles.
     */
    public final static class Builder {
        private List<Asterism> asterisms;
        private List<Star> stars;

        /**
         * Constructeur par défaut qui initialise le bâtisseur de manière à ce que
         * le catalogue en construction soit initialement vide.
         */
        public Builder() {
            this.asterisms = new ArrayList<>();
            this.stars = new ArrayList<>();
        }

        /**
         * Méthode qui ajoute l'étoile donnée au catalogue en cours de construction,
         * et retourne le bâtisseur.
         *
         * @param star l'étoile à ajouter
         * @return le bâtisseur
         */
        public Builder addStar(Star star) {
            this.stars.add(star);
            return this;
        }

        /**
         * méthode d'accès qui retourne une vue non modifiable — mais pas immuable — 
         * sur les étoiles du catalogue en cours de construction.
         *
         * @return une vue non modifiable — mais pas immuable - sur les étoiles
         * du catalogue en cours de construction
         */
        public List<Star> stars() {
            return Collections.unmodifiableList(this.stars);
        }

        /**
         * Méthode qui ajoute l'astérisme donné au catalogue en cours de construction,
         * et retourne le bâtisseur.
         *
         * @param asterism l'astérisme à ajouter
         * @return le bâtisseur
         */
        public Builder addAsterism(Asterism asterism) {
            this.asterisms.add(asterism);
            return this;
        }

        /**
         * Méthode qui retourne une vue non modifiable — mais pas immuable — sur les
         * astérismes du catalogue en cours de construction.
         *
         * @return une vue non modifiable — mais pas immuable — sur les astérismes
         * du catalogue en cours de construction
         */
        public List<Asterism> asterisms() {
            return Collections.unmodifiableList(this.asterisms);
        }

        /**
         * Méthode qui demande au chargeur loader d'ajouter au catalogue les étoiles
         * et/ou astérismes qu'il obtient depuis le flot d'entrée inputStream,
         * et retourne le bâtisseur, ou lève IOException en cas d'erreur d'entrée/sortie.
         *
         * @param inputStream le flot d'entrée
         * @param loader le loader
         * @return le bâtisseur du catalogue des étoiles et/ou astérismes contenus
         * dans le flot d'entrée
         * @throws IOException en cas d'erreur d'entrée/sortie
         */
        public Builder loadFrom(InputStream inputStream, Loader loader) throws IOException {
            loader.load(inputStream, this);
            return this;
        }

        /**
         * Méthode qui retourne le catalogue contenant les étoiles et astérismes ajoutés
         * jusqu'alors au bâtisseur.
         *
         * @return le catalogue construit conformément au bâtisseur
         */
        public StarCatalogue build() {
            return new StarCatalogue(this.stars, this.asterisms);
        }
    }



    /**
     * Cette interface publique représente un chargeur de catalogue d'étoiles et d'astérismes.
     */
    public interface Loader {
        /**
         * Méthode qui charge les étoiles et/ou astérismes du flot d'entrée inputStream et les
         * ajoute au catalogue en cours de construction du bâtisseur builder, ou lève IOException
         * en cas d'erreur d'entrée/sortie.
         *
         * @param inputStream le flot d'entrée contenant les étoiles ou astérismes à charger
         * @param builder le bâtisseur qui doit se voir ajouter les étoiles et astérismes du flot d'entrée
         * @throws IOException en cas d'erreur d'entrée/sortie
         */
        public abstract void load(InputStream inputStream, Builder builder) throws IOException;
    }
}
