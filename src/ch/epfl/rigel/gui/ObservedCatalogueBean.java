package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.CelestialObject;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.math.ClosedInterval;
import ch.epfl.rigel.math.RightOpenInterval;
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

    private static final ClosedInterval VISIBLE_ALTITUDE_INTERVAL = ClosedInterval.of(0, 90);
    private static final RightOpenInterval VISIBLE_AZIMUTH_INTERVAL = RightOpenInterval.of(0, 360);

    private ObjectProperty<Map<String, CelestialObject>> celestialObjectMap;

    /**
     * Constructeur du bean du catalogue observable.
     */
    public ObservedCatalogueBean() {
        celestialObjectMap = new SimpleObjectProperty<>();

        setCelestialObjectMap(new TreeMap<>());
    }

    /**
     * Méthode d'accès qui retourne la table associative des objets célestes.
     *
     * @return la table associative des objets célestes
     */
    public Map<String, CelestialObject> getCelestialObjectMap() {
        return celestialObjectMap.get();
    }

    /**
     * Méthode d'accès qui retourne la propriété celestianObjectMap.
     *
     * @return la propriété celestianObjectMap
     */
    public ObjectProperty<Map<String, CelestialObject>> celestialObjectMapProperty() {
        return celestialObjectMap;
    }

    /**
     * Méthode publique qui (re)initialise la table associative des objets célestes.
     *
     * @param celestialObjectMap la nouvelle table associative des objets célestes
     */
    public void setCelestialObjectMap(Map<String, CelestialObject> celestialObjectMap) {
        this.celestialObjectMap.set(celestialObjectMap);
    }

    /**
     * Méthode qui détermine si un objet céleste est visible ou non.
     * On entend par visible le fait que l'objet soit situé au-dessus
     * de la ligne d'horizon.
     *
     * @param objectCenter les coordonnées horizontales de l'objet céleste
     * @return true si l'objet céleste est visible, false sinon
     */
    public boolean isVisible(HorizontalCoordinates objectCenter) {
        return (VISIBLE_ALTITUDE_INTERVAL.contains(objectCenter.altDeg())
                && VISIBLE_AZIMUTH_INTERVAL.contains(objectCenter.azDeg()));
    }
}
