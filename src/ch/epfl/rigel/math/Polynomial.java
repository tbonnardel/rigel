package ch.epfl.rigel.math;

import ch.epfl.rigel.Preconditions;

import java.util.Arrays;
import java.util.Locale;

/**
 * Classe qui représente une fonction polynomiale.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Polynomial {

    private final double[] coefficients;

    private Polynomial(double[] coefficients) { this.coefficients = coefficients.clone(); } // Constructeur privé

    /**
     * Méthode qui retourne la fonction polynomiale avec les coefficients donnés,
     * dans l'ordre décroissant.
     *
     * @param coefficientN le coefficient de plus haut degré
     * @param coefficients les coefficients suivants, dans l'ordre décroissant
     * @return la fonction polynomiale avec les coefficients données
     * @throws IllegalArgumentException si le coefficient de plus haut degré est nul
     */
    public static Polynomial of(double coefficientN, double... coefficients) {
        Preconditions.checkArgument(coefficientN != 0.);

        int N = coefficients.length + 1;
        double[] listOfCoefficients = new double[N];

        listOfCoefficients[0] = coefficientN;
        System.arraycopy(coefficients, 0, listOfCoefficients, 1, coefficients.length);
        return new Polynomial(listOfCoefficients);
    }

    /**
     * Méthode qui retourne la valeur du polynôme évalué au paramètre donné.
     * Cette méthode utilise l'algorithme de Horner.
     *
     * @param x la valeur à évaluer
     * @return la valeur du polynôme évalué en x
     */
    public double at(double x) {
        double evaluation = 0.;

        evaluation = coefficients[0];
        for (int i = 1; i < coefficients.length; i++) {
            evaluation = evaluation * x + coefficients[i];
        }
        return evaluation;
    }

    /**
     * Redéfintion de la méthode toString de la classe Object pour représenter
     * de manière textuelle le polynôme.
     *
     * @return la représentation textuelle du polynôme
     */
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        int N = coefficients.length;
        for (int i = 0; i < N; i++) {
            stringBuilder.append(addTermToString(coefficients[i], N-i-1));
        }

        return stringBuilder.toString();
    }

    /**
     * Méthode privée qui formatte correctement l'ajout d'un terme à la représentation
     * textuelle d'un polynôme.
     *
     * @param coefficient le coefficient du terme à formatter
     * @param degree le degré du terme à formatter
     * @return la représentation textuelle du terme donné
     */
    private String addTermToString(double coefficient, int degree) {
        if (coefficient == 0)
            return "";

        StringBuilder stringBuilder = new StringBuilder();
        if (degree != (coefficients.length - 1) && coefficient > 0) // Ajout du signe + si nécessaire
            stringBuilder.append("+");

        if (degree == 0) { // Cas du terme de degré 0
            stringBuilder.append(coefficient);
            return stringBuilder.toString();
        }

        if (degree == 1) { // Cas particulier du terme de degré 1
            if (coefficient == 1.)
                stringBuilder.append("x");
            else if (coefficient == -1.)
                stringBuilder.append("-x");
            else
                stringBuilder.append(String.format(Locale.ROOT, "%sx", coefficient));
            return stringBuilder.toString();
        }

        // Cas des termes de degré 2 ou plus
        if (coefficient == 1.)
            stringBuilder.append(String.format(Locale.ROOT, "x^%d", degree));
        else if (coefficient == -1.)
            stringBuilder.append(String.format(Locale.ROOT, "-x^%d", degree));
        else
            stringBuilder.append(String.format(Locale.ROOT, "%sx^%d", coefficient, degree));

        return stringBuilder.toString();
    }

    /**
     * Redéfinition de equal de Object en levant l'exception UnsupportedOperationException.
     *
     * @param o l'objet à comparer
     * @return la valeur de l'égalité des deux objets (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Redéfinition de hashCode de Object en levant l'exception UnsupportedOperationException.
     *
     * @return le hashCode (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
