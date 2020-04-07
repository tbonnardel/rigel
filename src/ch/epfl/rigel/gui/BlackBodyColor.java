package ch.epfl.rigel.gui;

import javafx.scene.paint.Color;

/**
 * Cette classe offre une méthode permettant d'obtenir la couleur
 * d'un corps noir étant donnée sa température.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class BlackBodyColor {

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
        return null;
    }
}
