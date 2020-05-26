package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;

import java.util.List;

/**
 * Cette classe représente un moteur de recherche d'objets célestes.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class CelestialObjectSearchEngine {

    private final List<CelestialObject> visibleObjects;

    public CelestialObjectSearchEngine() {
        this.visibleObjects = null;
    }
}
