package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import javafx.scene.transform.Transform;

import java.util.Map;
import java.util.Set;

/**
 * Cette classe représente un moteur de recherche d'objets célestes.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class CelestialObjectSearchEngine {

    private final Map<String, CelestialObject> celestialObjectMap;
    private final Transform transform;

    public CelestialObjectSearchEngine(Map<String, CelestialObject> celestialObjectMap, Transform transform) {
        this.celestialObjectMap = celestialObjectMap;
        this.transform = transform;
    }

    public boolean search(String name) {
        return getObjectNameSet().contains(name);
    }

    public Set<String> getObjectNameSet() {return celestialObjectMap.keySet(); }

    public CelestialObject getObject(String name) {
        if (!search(name))
            throw new IllegalArgumentException();

        return celestialObjectMap.get(name);
    }
}
