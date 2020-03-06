package ch.epfl.rigel.coordinates;

import java.util.Locale;

/**
 * Cette classe représente des coordonnées cartésiennes.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class CartesianCoordinates {

    private final double x;
    private final double y;

    private CartesianCoordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Méthode de construction qui retourne les coordonnées cartésiennes
     * d'abscisse x et d'ordonnée y.
     *
     * @param x l'abscisse du point
     * @param y l'ordonnée du point
     * @return les coordonnées cartésiennes du point donné
     */
    public static CartesianCoordinates of(double x, double y) {
        return new CartesianCoordinates(x, y);
    }

    /**
     * Méthode d'accès qui retourne l'abscisse du point représenté par
     *
     * @return l'abscisse du point
     */
    public double x() {
        return this.x;
    }

    /**
     * Méthode d'accès qui retourne l'ordonnée du point représenté par
     * ses coordonnées cartésiennes.
     *
     * @return l'ordonnée du point
     */
    public double y() {
        return this.y;
    }

    /**
     * Redéfinition de equal de Object en levant l'exception UnsupportedOperationException.
     *
     * @param o l'objet à tester
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

    /**
     * Redéfinition de la méthode toString qui retourne la représentation textuelle des coordonnées cartésiennes,
     * avec une précision de e-4.
     *
     * @return la représentation textuelle des coordonnées cartésiennes
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(x=%.4f, y=%.4f)",
                x(),
                y());
    }
}
