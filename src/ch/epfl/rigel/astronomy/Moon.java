package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.Preconditions;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.ClosedInterval;

import java.util.Locale;

/**
 * Cette classe est la sous-classe de CelestialObject représentant la Lune
 * à un instant donné.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class Moon extends CelestialObject {

    private final static String MOON_NAME = "Lune";
    private final float phase;

    /**
     * Constructeur qui construit (un objet représentant) la Lune avec la position, la taille
     * angulaire, la magnitude et la phase données, ou lève IllegalArgumentException si la phase
     * n'est pas comprise dans l'intervalle [0, 1].
     *
     * @param equatorialPos les coordonnées équatoriales de la Lune
     * @param angularSize la taille angulaire de la Lune
     * @param magnitude la magnitude de la Lune
     * @param phase la phase de la Lune
     * @throws IllegalArgumentException si la taille angulaire est négative
     * ou la phase n'est pas comprise entre 0 et 1
     * @throws NullPointerException si la position équatoriale est nulle
     */
    public Moon(EquatorialCoordinates equatorialPos, float angularSize, float magnitude, float phase) {
        super(MOON_NAME, equatorialPos, angularSize, magnitude);

        Preconditions.checkInInterval(ClosedInterval.of(0,1), phase);
        this.phase = phase;
    }

    /**
     * Redéfinition de la méthode info pour que la phase apparaisse après le nom, entre parenthèses
     * et exprimé en pourcent, avec une décimale.
     *
     * @return la description textuelle de la Lune, contenant entre autre la phase
     */
    @Override
    public String info() {
        return String.format(Locale.ROOT,
                "%s (%.1f%%)",
                MOON_NAME,
                phase*100f);
    }
}
