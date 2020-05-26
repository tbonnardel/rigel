package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import javafx.scene.transform.Transform;

import java.util.Map;

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
        return celestialObjectMap.keySet().contains(name);
    }

    public HorizontalCoordinates findCenter(String name) {
        if (search(name)) {
            CelestialObject celestialObject = celestialObjectMap.get(name);
            //celestialObject.
            // TODO: WIP
        }
        return null;
    }
}
