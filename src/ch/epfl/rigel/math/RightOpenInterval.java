package ch.epfl.rigel.math;

import java.util.Locale;

import static java.lang.Math.floor;

/**
 * Classe qui représente un intervalle semi-ouvert à droite.
 * Elle hérite naturellement de la classe Interval.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class RightOpenInterval extends Interval {

    private RightOpenInterval(double lowBound, double highBound) { // Constructeur privé
        super(lowBound, highBound);
    }

    /**
     * Méthode qui retourne un intervalle semi-ouvert à droite avec les bornes spécifiées
     * dans les paramètres.
     *
     * @param low la borne inférieure
     * @param high la borne supérieure
     * @return un intervalle semi-ouvert à droite avec les bornes spécifiées en paramètres
     * @throws IllegalArgumentException si la valeur de low est supérieure ou égale à high
     */
    public static RightOpenInterval of(double low, double high) {
        if (!(low < high)) {
            throw new IllegalArgumentException();
        }

        return new RightOpenInterval(low, high);
    }

    /**
     * Méthode qui retourne un intervalle semi-ouvert à droite, centré en 0, et de taille size.
     *
     * @param size la taille de l'intervalle
     * @return un intervalle semi-ouvert à droite, centré en 0 et de taille size
     * @throws IllegalArgumentException si la taille spécifiée n'est pas strictement positive
     */
    public static RightOpenInterval symmetric(double size) {
        if (!(size > 0)) {
            throw new IllegalArgumentException();
        }

        double extremeValue = size / 2d;
        return new RightOpenInterval(-extremeValue, extremeValue);
    }

    /**
     * Méthode qui vérifie si la valeur spécifiée en paramètre appartient à l'intervalle.
     *
     * @param v la valeur étudiée
     * @return vrai si et seulement si la valeur v appartient à l'intervalle
     */
    @Override
    public boolean contains(double v) {
        return (low() <= v && v < high());
    }

    /**
     * Méthode qui réduit son argument à l'intervalle étudié
     *
     * @param v la valeur à réduire
     * @return la valeur réduite conformément à l'intervalle
     */
    public double reduce(double v) {
        double a = low();
        double b = high();

        return (a + floorMod(v - a, b - a));
    }

    /**
     * Méthode privée qui calcule le reste de la partie entière par défaut.
     * Pour plus d'information, voir l'énoncé de l'étape 1, paragraphe 2.1.2.
     *
     * @param x
     * @param y
     * @return le reste de la partie entière par défaut
     */
    private double floorMod(double x, double y) {
        return (x - y * floor(x/y));
    }

    /**
     * Redéfinition de la méthode toString de Object afin de retourner une représentation textuelle
     * de cet intervalle semi-ouvert à droite.
     *
     * @return la représentation textuelle de cet intervalle
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "[%.2f,%.2f[",      // Affichage des bornes avec une précision de e-2
                low(),
                high());
    }
}
