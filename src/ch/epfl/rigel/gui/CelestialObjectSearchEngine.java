package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;

import java.util.Map;
import java.util.TreeMap;

/**
 * Cette classe représente un moteur de recherche d'objets célestes.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class CelestialObjectSearchEngine {

    private Map<String, CelestialObject> celestialObjectMap;

    /**
     * Constructeur de la classe.
     */
    public CelestialObjectSearchEngine() {
        this.celestialObjectMap = new TreeMap<>();
    }

    /**
     * Méthode qui vérifie si l'objet céleste avec le nom spécifié existe.
     *
     * @param name le nom de l'objet à chercher
     * @return true si l'objet existe, false sinon
     */
    public boolean search(String name) {
        return celestialObjectMap.keySet().contains(name);
    }

    /**
     * Méthode qui (re) initialise la table associative des objets célestes.
     *
     * @param celestialObjectMap la nouvelle table associative des objets célestes
     */
    public void setCelestialObjectMap(Map<String, CelestialObject> celestialObjectMap) {
        this.celestialObjectMap = celestialObjectMap;
    }

    /**
     * Méthode qui retourne l'objet céleste dont le nom est spécifié.
     *
     * @param name le nom de l'objet céleste à retourner
     * @return l'objet céleste portant le nom name
     * @throws IllegalArgumentException si le nom name ne coïncide pas
     * avec un objet de la table associative
     */
    public CelestialObject getObject(String name) {
        if (!search(name))
            throw new IllegalArgumentException();

        return celestialObjectMap.get(name);
    }
}
