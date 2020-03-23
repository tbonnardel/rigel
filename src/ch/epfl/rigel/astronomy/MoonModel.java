package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * Cette énumération représente un modèle de la Lune.
 *
 * @author Thomas Bonnardel (319827)
 */
public enum MoonModel implements CelestialObjectModel<Moon> {
    /**
     * Objet représentant un modèle de la Lune
     */
    MOON;


    /**
     * Méthode qui retourne la Lune modélisée par le modèle en fonction des paramètres donnés.
     * @param daysSinceJ2010 nombre de jours après l'époque J2000
     * @param eclipticToEquatorialConversion la conversion pour obtenir ses coordonnées équatoriales
     *                                       à partir de ses coordonnées écliptiques
     * @return
     */
    @Override
    public Moon at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion) {
        return null;
    }
}
