package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;

/**
 * Cette interface représente un modèle d'objet céleste, c'est-à-dire d'une manière
 * de calculer les caractéristiques de cet objet à un instant donné.
 *
 * @author Thomas Bonnardel (319827)
 */
public interface CelestialObjectModel<O> {

    /**
     * Méthode qui retourne l'objet modélisé par le modèle pour le nombre (éventuellement négatif)
     * de jours après l'époque J2010 donné, en utilisant la conversion donnée pour obtenir
     * ses coordonnées équatoriales à partir de ses coordonnées écliptiques.
     *
     * @param daysSinceJ2010 nombre de jours après l'époque J2000
     * @param eclipticToEquatorialConversion la conversion pour obtenir ses coordonnées équatoriales
     *                                       à partir de ses coordonnées écliptiques
     * @return l'objet modélisé par le modèle pour les paramètres donnés
     */
    public abstract O at(double daysSinceJ2010, EclipticToEquatorialConversion eclipticToEquatorialConversion);
}
