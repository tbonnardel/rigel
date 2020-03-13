package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

/**
 * Cette classe représente une étoile. Elle hérite de CelestialObject car,
 * dans le cadre de ce projet en tout cas, les étoiles sont fixes dans le
 * repère équatorial et n'ont donc pas de modèle associé.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Star extends CelestialObject {

    private final static float STAR_ANGULAR_SIZE = 0f;

    private final int hipparcosId;
    private final int colorTemperature;

    /**
     * Constructeur publique qui construit une étoile avec le numéro Hipparcos, le nom,
     * la position équatoriale, la magnitude et l'indice de couleur donnés ;
     * lève IllegalArgumentException si le numéro Hipparcos est négatif, ou si l'indice
     * de couleur n'est pas compris dans l'intervalle [-0.5, 5.5].
     *
     * @param hipparcosId le numéro Hipparcos de l'étoile
     * @param name le nom de l'étoile
     * @param equatorialPos la position équatoriale de l'étoile
     * @param magnitude la magnitude de l'étoile
     * @param colorIndex l'indice de couleur de l'étoile
     * @throws IllegalArgumentException si le numéro Hipparcos est négatif ou si
     * l'indice de couleur n'est pas compris dans l'intervalle [-0.5, 5.5]
     */
    public Star(int hipparcosId, String name, EquatorialCoordinates equatorialPos, float magnitude, float colorIndex) {
        super(name, equatorialPos, STAR_ANGULAR_SIZE ,magnitude);
        Preconditions.checkArgument(hipparcosId > 0);
        Preconditions.checkInInterval(ClosedInterval.of(-.5, 5.5), colorIndex);

        this.hipparcosId = hipparcosId;
        this.colorTemperature = calculateColorTemperature(colorIndex);
    }

    /**
     * Méthode privée qui calcule la température de couleur en fonction
     * de l'index de couleur de l'étoile.
     *
     * @param colorIndex l'index de couleur de l'étoile
     * @return la température de couleur de l'étoile
     */
    private int calculateColorTemperature(float colorIndex) {
        return (int) (4600*(1/(.92*colorIndex + 1.7 ) + 1/(.92*colorIndex + .62)));
    }

    /**
     * Méthode d'accès qui retourne le numéro Hipparcos de l'étoile.
     *
     * @return le numéro Hipparcos de l'étoile
     */
    public int hipparcosId() {
        return hipparcosId;
    }

    /**
     * Méthode d'accès qui retourne la température de couleur de l'étoile.
     *
     * @return la température de couleur de l'étoile
     */
    public int colorTemperature() {
        return colorTemperature;
    }
}
