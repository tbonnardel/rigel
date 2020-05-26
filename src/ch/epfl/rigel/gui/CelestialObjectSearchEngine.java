package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;

import java.util.Map;

/**
 * Cette classe représente un moteur de recherche d'objets célestes.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class CelestialObjectSearchEngine {

    private final Map<String, CelestialObject> visibleObjects;

    public CelestialObjectSearchEngine() {
        this.visibleObjects = null;
    }
}
