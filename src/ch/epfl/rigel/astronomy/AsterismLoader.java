package ch.epfl.rigel.astronomy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ce type énuméré contient un seul élément nommé INSTANCE et
 * représentant un chargeur de catalogue d'astérismes.
 *
 * @author Thomas Bonnardel (319827)
 */
public enum AsterismLoader implements StarCatalogue.Loader {

    /**
     * Objet représentant un chargeur de catalogue HYG.
     */
    INSTANCE;

    /**
     * Cette méthode ajoute au bâtisseur de catalogue l'ensemble des astérismes contenus
     * dans le flot d'entré donné.
     *
     * @param inputStream le flot d'entré contenant les astérismes à charger
     * @param builder le bâtisseur qui doit se voir ajouter les astérismes du flot d'entrée
     * @throws IOException
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        List<Star> stars = builder.stars();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")))) {

            String line = "";
            while ((line = bufferedReader.readLine()) != null && !line.isBlank()) {
                String[] tab = line.split(",");
                List<Star> asterismStars = this.getStarsListFromHipparcosIdTab(tab, stars);
                builder.addAsterism(new Asterism(asterismStars));
            }
        }
    }

    /**
     * Méthode privée qui retourne la liste des étoiles dont l'identifiant Hipparcos est présent
     * dans le tableau donné.
     *
     * @param hipparcosIdTab le tableau des identifiants Hipparcos (en chaîne de caractères)
     * @param stars la liste de l'ensemble des étoiles du catalogue
     * @return la liste des étoiles dont l'identifiant Hipparcos est présent dans hipparcosIdTab
     */
    private List<Star> getStarsListFromHipparcosIdTab(String[] hipparcosIdTab, List<Star> stars) {
        List<Star> result = new ArrayList<>();
        Map<Integer, Star> hipparcosStarMap = new HashMap<>();
        for (Star s: stars)
            hipparcosStarMap.put(s.hipparcosId(), s);

        for (int i = 0; i < hipparcosIdTab.length; i++) {
            result.add(hipparcosStarMap.get(Integer.parseInt(hipparcosIdTab[i])));
        }

        return result;
    }
}
