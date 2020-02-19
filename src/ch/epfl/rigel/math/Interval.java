package ch.epfl.rigel.math;

/**
 * La classe Interval est abstraite et représente un intervalle.
 * Elle a pour but de servir de classe mère aux deux classes représentant
 * respectivement un intervalle fermé et un intervalle semi-ouvert à droite.
 *
 * @author Thomas Bonnardel (319827)
 */
public abstract class Interval {

    /**
     * La borne inférieure de l'intervalle
     */
    private final double lowBound;

    /**
     * La borne supérieure de l'intervalle
     */
    private final double highBound;


    /**
     * L'unique contructeur d'Interval, avec ses deux bornes.
     *
     * @param lowBound la borne inférieure de l'intervalle
     * @param highBound la borne supérieure de l'intervalle
     */
    protected Interval(double lowBound, double highBound) {
        this.lowBound = lowBound;
        this.highBound = highBound;
    }

    /**
     * Méthode qui retourne la borne inférieure de l'intervalle.
     *
     * @return la borne inférieure de l'intervalle
     */
    public double low() {
        return this.lowBound;
    }

    /**
     * Méthode qui retourne la borne supérieure de l'intervalle.
     *
     * @return la borne supérieure de l'intervalle
     */
    public double high() {
        return this.highBound;
    }

    /**
     * Méthode qui retourne la taille de l'intervalle
     *
     * @return la taille de l'intervalle
     */
    public double size() {
        return (this.highBound - this.lowBound);
    }

    /**
     * Méthode qui vérifie si la valeur passée en paramètre appartient à l'intervalle.
     *
     * @param v la valeur étudiée
     * @return vrai si et seulement si le paramètre v appartient à l'intervalle
     */
    public abstract boolean contains(double v);

    /**
     * Redéfinition de hashCode de Object en levant l'exception UnsupportedOperationException.
     * Remarque : il n'est pas pertinent de comparer deux intervalles dans la mesure où les approximations
     * des flottants en Java rend toute comparaison délicate ...
     *
     * @return le hashCode (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public final int hashCode() {
        throw new UnsupportedOperationException();
    }

    /**
     * Redéfinition de hashCode de Object en levant l'exception UnsupportedOperationException.
     * Remarque : il n'est pas pertinent de comparer deux intervalles dans la mesure où les approximations
     * des flottants en Java rend toute comparaison délicate ...
     *
     * @param obj l'objet à tester
     * @return la valeur de l'égalité des deux objets (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public final boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}
