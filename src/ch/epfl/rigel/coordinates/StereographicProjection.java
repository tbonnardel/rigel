package ch.epfl.rigel.coordinates;

import java.util.Locale;
import java.util.Objects;
import java.util.function.Function;

import static java.lang.Math.sin;
import static java.lang.Math.cos;
import static java.lang.Math.tan;
import static java.lang.Math.atan;
import static java.lang.Math.asin;
import static java.lang.Math.sqrt;

/**
 * Cette classe représente une projection stéréographique de coordonnées horizontales.
 *
 * @author Thomas Bonnardel (319827)
 */
public class StereographicProjection implements Function<HorizontalCoordinates, CartesianCoordinates> {

    private final HorizontalCoordinates center;
    private final double lambda0;
    private final double phi1;
    private final double cosPhi1;
    private final double sinPhi1;

    /**
     * Constructeur qui créé la projection stéréographique centrée en center.
     *
     * @param center le centre de la projection stéréographique
     */
    public StereographicProjection(HorizontalCoordinates center) {
        this.center = center;
        this.lambda0 = center.az();
        this.phi1 = center.alt();
        this.cosPhi1 = cos(phi1);
        this.sinPhi1 = sin(phi1);
    }

    /**
     * Méthode qui retourne les coordonnées du centre du cercle correspondant à la projection
     * du parallèle passant par le point hor. L'ordonnée de ce point peut être infinie.
     *
     * @param hor un point par lequel passe le parallèle étudié
     * @return les coordonnées du centre du cercle correspondant à la projection du parallèle
     */
    public CartesianCoordinates circleCenterForParallel(HorizontalCoordinates hor) {
        double cy = cos(phi1) / (sin(hor.alt()) + sin(phi1));
        return CartesianCoordinates.of(0, cy);
    }

    /**
     * Méthode qui retourne le rayon du cercle correspondant à la projection du parallèle
     * passant par le point de coordonnées hor.
     *
     * @param hor un point par lequel passe le parallèle étudié
     * @return le rayon du cercle correspondant à la projection du parallèle étudié
     */
    public double circleRadiusForParallel(HorizontalCoordinates hor) {
        return cos(phi1) / (sin(hor.alt()) + sin(phi1));
    }

    /**
     * Méthode qui retourne le diamètre projeté d'une sphère de taille angulaire rad
     * centrée au centre de projection, en admettant que celui-ci soit sur l'horizon.
     *
     * @param rad taille angulaire de la sphère étudiée
     * @return le diamètre projeté de la shère étudiée, en admettant que celui-ci soit sur l'horizon
     */
    public double applyToAngle(double rad) {
        return 2*tan(rad/4);
    }

    /**
     * Méthode qui retourne les coordonnées cartésiennes de la projection du point
     * de coordonnées horizontales azAlt.
     *
     * @param azAlt les coordonnées horizontales du point à projeter
     * @return les coordonnées cartésiennes de la projection du point donné
     */
    @Override
    public CartesianCoordinates apply(HorizontalCoordinates azAlt) {
        double phi = azAlt.alt();
        double cosPhi = cos(phi);
        double sinPhi = sin(phi);
        double lambdaD = azAlt.az() - lambda0;
        double cosLambdaD = cos(lambdaD);
        double d = 1 / (1 + sinPhi*sinPhi1 + cosPhi*cosPhi1*cosLambdaD);

        double x = d * cosPhi * sin(lambdaD);
        double y = d * (sinPhi*cosPhi1 - cosPhi*sinPhi1*cosLambdaD);
        return CartesianCoordinates.of(x, y);
    }

    /**
     * Méthode qui retourne les coordonnées horizontales du point dont la projection
     * est le point de coordonnées cartésiennes xy.
     *
     * @param xy les coordonnées cartésiennes du point à convertir
     * @return les coordonnées horizontales du point dont la projection est le point
     * de coordonnées cartésiennes xy
     */
    public HorizontalCoordinates inverseApply(CartesianCoordinates xy) {
        double x = xy.x();
        double y = xy.y();
        double rho = sqrt(x*x+ y*y);
        double sinc = (2*rho) / (rho*rho + 1);
        double cosc = (1 - rho*rho) / (rho*rho + 1);

        double lambda = atan((x*sinc) / (rho*cosPhi1*cosc - y*sinPhi1*sinc)) + lambda0;
        double phi = asin(cosc*sinPhi1 + (y*sinc*cosPhi1)/rho);

        return HorizontalCoordinates.of(lambda, phi);
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
     * Redéfinition de la méthode toString qui retourne la représentation textuelle de
     * la projection stéréographique avec une précision de e-4.
     *
     * @return la représentatioon textuelle de la projection stéréographique
     */
    @Override
    public String toString() {
        return String.format(Locale.ROOT,
                "(cAz=%.4f°, cAlt=%.4f°)",
                center.azDeg(),
                center.altDeg());
    }
}
