package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * Ce type énuméré contient un seul élément nommé INSTANCE et représentant
 * un chargeur de catalogue HYG.
 *
 * @author Thomas Bonnardel (319827)
 */
public enum HygDatabaseLoader implements StarCatalogue.Loader {

    /**
     * Objet représentant un chargeur de catalogue HYG.
     */
     INSTANCE;

    /**
     * Cette méthode ajoute au bâtisseur de catalogue toutes les étoiles obtenues
     * du catalogue HYG en utilisant le contenu des colonnes de la manière suivante,
     * où la valeur par défaut mentionnée est celle à utiliser si la colonne est vide :
     *      - le numéro Hipparcos de l'étoile est obtenu de la colonne hip (0 par défaut)
     *      - le nom de l'étoile est obtenu de la colonne proper si elle n'est pas vide et
     *      sinon par concaténation de la colonne bayer (? par défaut [point d'interrogation]),
     *      d'un espace et de la colonne con, qui n'est jamais vide
     *      - les coordonnées équatoriales de l'étoile sont obtenues des colonnes rarad et decrad,
     *      qui ne sont jamais vides
     *      - la magnitude de l'étoile est obtenue de la colonne mag (0 par défaut)
     *      - l'indice de couleur B-V est obtenu de la colonne ci (0 par défaut)
     *
     * @param inputStream le flot d'entrée contenant les étoiles ou astérismes à charger
     * @param builder le bâtisseur qui doit se voir ajouter les étoiles et astérismes du flot d'entrée
     * @throws IOException en cas d'erreur d'entrée/sortie
     */
    @Override
    public void load(InputStream inputStream, StarCatalogue.Builder builder) throws IOException {
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("US-ASCII")))) {
            String line = bufferedReader.readLine(); // Lecture de la ligne d'en-tête

            while ((line = bufferedReader.readLine()) != null && !line.isBlank()) {
                String[] tab = line.split(",");
                int hipparcosId = (!tab[Columns.HIP.ordinal()].isBlank())
                        ? Integer.parseInt(tab[Columns.HIP.ordinal()])
                        : 0;

                String name = tab[Columns.PROPER.ordinal()];
                if (name.isBlank()) {
                    StringBuilder sb = new StringBuilder();
                    if (tab[Columns.BAYER.ordinal()].isBlank()) {
                        sb.append("?");
                    } else {
                        sb.append(tab[Columns.BAYER.ordinal()]);
                    }
                    sb.append(" ");
                    sb.append(tab[Columns.CON.ordinal()]);

                    name = sb.toString();
                }

                double ra = Angle.normalizePositive(Double.parseDouble(tab[Columns.RARAD.ordinal()]));
                double dec = Double.parseDouble(tab[Columns.DECRAD.ordinal()]);
                EquatorialCoordinates equatorialPos = EquatorialCoordinates.of(ra, dec);

                double magnitude = (!tab[Columns.MAG.ordinal()].isBlank())
                        ? Double.parseDouble(tab[Columns.MAG.ordinal()])
                        : 0;

                double colorIndex = (!tab[Columns.CI.ordinal()].isBlank())
                        ? Double.parseDouble(tab[Columns.CI.ordinal()])
                        : 0;

                builder.addStar(new Star(hipparcosId, name, equatorialPos, (float)magnitude, (float)colorIndex));
            }
        }
    }

    /**
     * Ce type énuméré privé possède un membre pour chaque colonne d'un fichier de catalogue HYG.
     */
    private enum Columns {
        ID, HIP, HD, HR, GL, BF, PROPER, RA, DEC, DIST, PMRA, PMDEC,
        RV, MAG, ABSMAG, SPECT, CI, X, Y, Z, VX, VY, VZ,
        RARAD, DECRAD, PMRARAD, PMDECRAD, BAYER, FLAM, CON,
        COMP, COMP_PRIMARY, BASE, LUM, VAR, VAR_MIN, VAR_MAX;
    }
}
