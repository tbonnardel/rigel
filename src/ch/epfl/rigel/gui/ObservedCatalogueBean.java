package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.util.Map;
import java.util.TreeMap;

/**
 * Cette classe représente un bean du catalogue de l'instant observé.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class ObservedCatalogueBean {

    private ObjectProperty<Map<String, CelestialObject>> celestialObjectMap;

    public ObservedCatalogueBean() {
        celestialObjectMap = new SimpleObjectProperty<>();

        setCelestialObjectMap(new TreeMap<>());
    }

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
