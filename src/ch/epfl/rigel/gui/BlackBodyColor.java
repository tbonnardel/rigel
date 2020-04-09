package ch.epfl.rigel.gui;

import ch.epfl.rigel.Preconditions;
import javafx.scene.paint.Color;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * Cette classe offre une méthode permettant d'obtenir la couleur
 * d'un corps noir étant donnée sa température.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class BlackBodyColor {

    private final static String BBR_FILE_NAME = "/bbr_color.txt";
    private final static int MIN_TEMPERATURE = 1000;
    private final static int MAX_TEMPERATURE = 40000;
    private final static Map<Integer, Color> TEMPERATURE_COLOR_MAP = loadData();

    private BlackBodyColor() {} // Constructeur privée rendant la classe non instantiable

    /**
     * Méthode qui prend en argument une température exprimée en degrés Kelvin
     * et retourne la couleur correspondante, sous la forme d'une instance de
     * la classe Color de JavaFX. Elle lève bien entendu une exception si la
     * température n'est pas dans la plage couverte par le fichier de référence.
     *
     * @param temperature la temperature en degrés Kelvin
     * @return la couleur correspondante
     * @throws IllegalArgumentException si la température n'est pas dans la plage
     * couverte par le fichier de référence ([1000°K, 40000°K])
     */
    public static Color colorForTemperature(int temperature) {
        Preconditions.checkArgument(MIN_TEMPERATURE <= temperature
                && temperature <= MAX_TEMPERATURE);

        if (!(temperature%100 == 0)) {
            temperature = (temperature % 100 < 50)
                    ? (temperature / 100) * 100
                    : (temperature / 100 + 1) * 100;
        }
        return TEMPERATURE_COLOR_MAP.get(temperature);
    }

    private static Map<Integer, Color> loadData() {
        Map<Integer, Color> map = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(
                BlackBodyColor.class.getResourceAsStream(BBR_FILE_NAME),
                Charset.forName("US-ASCII")))) {

            String line = "";
            while ((line = bufferedReader.readLine()) != null && !line.isBlank()) {
                if (line.startsWith("#")) continue; // On ignore les lignes de commentaires
                if (line.contains("2deg")) continue; // On ignore les lignes "2deg"

                line = line.replaceAll(" ", ""); // On enlève tous les espaces
                int temperature = Integer.parseInt(line.split("K")[0]);
                String hexaColor = new StringBuilder()
                        .append('#')
                        .append(line.split("#")[1])
                        .toString();
                map.put(temperature, Color.web(hexaColor));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }
}
