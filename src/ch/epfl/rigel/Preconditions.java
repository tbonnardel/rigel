package ch.epfl.rigel;

import ch.epfl.rigel.math.Interval;

/**
 * Cette classe vérifie que les préconditions des arguments passés aux fonctions sont valides.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Preconditions {

    /**
     * Méthode qui lève l'exception IllegalArgumentException si son argument est faux,
     * et ne fait rien sinon.
     *
     * @param isTrue le paramètre à tester
     * @throws IllegalArgumentException si l'argument isTrue est faux
     */
    public static void checkArgument(boolean isTrue) {
        if (!isTrue)
            throw new IllegalArgumentException();
    }

    /**
     * Méthode qui lève l'exception IllegalArgumentException si value n'appartient pas
     * à interval, et retourne value sinon.
     *
     * @param interval l'intervalle en question
     * @param value la valeur à étudier
     * @return la valeur de value si elle appartient à interval
     * @throws IllegalArgumentException si value n'appartient pas à interval
     */
    public static double checkInInterval(Interval interval, double value) {
        // TODO: A implémenter une fois la classe Interval créée
        return 0;
    }
}
