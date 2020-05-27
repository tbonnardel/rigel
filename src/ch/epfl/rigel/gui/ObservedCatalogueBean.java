package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import javafx.beans.property.ObjectProperty;

import java.util.Map;

/**
 * Cette classe représente un bean du catalogue de l'instant observé.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class ObservedCatalogueBean {

    private ObjectProperty<Map<String, CelestialObject>> celestialObjectMap;


    public Map<String, CelestialObject> getCelestialObjectMap() {
        return celestialObjectMap.get();
    }

    public ObjectProperty<Map<String, CelestialObject>> celestialObjectMapProperty() {
        return celestialObjectMap;
    }

    public void setCelestialObjectMap(Map<String, CelestialObject> celestialObjectMap) {
        this.celestialObjectMap.set(celestialObjectMap);
    }
}
