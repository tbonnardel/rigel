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

    public CelestialObjectSearchEngine() {
        this.celestialObjectMap = new TreeMap<>();
    }

    public boolean search(String name) {
        return celestialObjectMap.keySet().contains(name);
    }

    public void setCelestialObjectMap(Map<String, CelestialObject> celestialObjectMap) {
        this.celestialObjectMap = celestialObjectMap;
    }

    public CelestialObject getObject(String name) {
        if (!search(name))
            throw new IllegalArgumentException();

        return celestialObjectMap.get(name);
    }
}
