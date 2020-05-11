package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Locale;

/**
 * Classe qui représente un intervalle fermé.
 * Elle hérite naturellement de la classe Interval.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class ClosedInterval extends Interval {

    private ClosedInterval(double lowBound, double highBound) { // Constructeur privé
        super(lowBound, highBound);
    }

    /**
     * Méthode qui retourne un intervalle fermé avec les bornes spécifiées dans les paramètres.
     *
     * @param low la borne inférieure
     * @param high la borne supérieure
     * @return un intervalle fermé avec les bornes spécifiées en paramètres
     * @throws IllegalArgumentException si la valeur de low est supérieure ou égale à high
     */
    public static ClosedInterval of(double low, double high) {
        Preconditions.checkArgument(low < high);

        return new ClosedInterval(low, high);
    }

    /**
     * Méthode qui retourne un intervalle fermé, centré en 0, et de taille size.
     *
     * @param size la taille de l'intervalle
     * @return un intervalle fermé centré en 0 et de taille size
     * @throws IllegalArgumentException si la taille spécifiée n'est pas strictement positive
     */
    public static ClosedInterval symmetric(double size) {
        Preconditions.checkArgument(size > 0);

        double extremeValue = size / 2d;
        return new ClosedInterval(-extremeValue, extremeValue);
    }

    /**
     * Méthode qui vérifie si la valeur spécifiée en paramètre appartient à l'intervalle.
     *
     * @param v la valeur étudiée
     * @return vrai si et seulement si la valeur v appartient à l'intervalle
     */
    @Override
    public boolean contains(double v) {
        return (low() <= v && v <= high());
    }

    /**
     * Méthode qui écrête son argument à l'intervalle.
     *
     * @param v la valeur à écrêter
     * @return la valeur écrêter conformément à l'intervalle
     */
    public double clip(double v) {
        if (v < low())
            return low();
        if (high() < v)
            return high();

        return v;
    }

    /**
     * Redéfinition de la méthode toString de Object afin de retourner une représentation textuelle
     * de cet intervalle fermé.
     *
     * @return la représentation textuelle de cet intervalle
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "[%.2f,%.2f]",      // Affichage des bornes avec une précision de e-2
                low(),
                high());
    }
}
