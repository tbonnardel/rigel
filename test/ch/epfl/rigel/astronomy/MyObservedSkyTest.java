package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.CartesianCoordinates;
import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import ch.epfl.rigel.coordinates.StereographicProjection;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.time.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyObservedSkyTest {

    private final static String HYG_CATALOGUE_NAME = "/hygdata_v3.csv";
    private final static String ASTERISMS_CATALOGUE = "/asterisms.txt";

    private final ZonedDateTime d;
    private final GeographicCoordinates where;
    private final StereographicProjection stereographicProjection;
    private final StarCatalogue catalogue;

    MyObservedSkyTest() throws IOException {
        StarCatalogue catalogue;
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue.Builder builder = new StarCatalogue.Builder();
            builder.loadFrom(hygStream, HygDatabaseLoader.INSTANCE);

            try (InputStream asterismStream = getClass().getResourceAsStream(ASTERISMS_CATALOGUE)) {
                builder.loadFrom(asterismStream, AsterismLoader.INSTANCE);
                catalogue = builder.build();
                assertEquals(true, catalogue.asterisms().size() > 0);
            }
        }
        this.catalogue = catalogue;
        this.d = ZonedDateTime.of(
                LocalDate.of(2020, Month.FEBRUARY, 17),
                LocalTime.of(20, 15),
                ZoneOffset.ofHours(1)   // UTC+1
        );
        this.where = GeographicCoordinates.ofDeg(46.519012, 6.567923);
        this.stereographicProjection = new StereographicProjection(HorizontalCoordinates.of(0, 0));
    }

    @Test
    void catalogueOfTheInstanceIsComplete() {
        assertEquals(5067, this.catalogue.stars().size());
        assertEquals(153, catalogue.asterisms().size());
    }

    @Test
    void constructorWorks() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
    }

    @Test
    void sunWorks() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        Sun s = observedSky.sun();
        assertEquals("Soleil", s.name());
    }

    @Test
    void moonWorks() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        Moon m = observedSky.moon();
        assertEquals("Lune", m.name());
    }

    @Test
    void planetsWorks() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        List<Planet> p = observedSky.planets();
        assertEquals(7, p.size());
    }

    @Test
    void starsWorks() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        List<Star> s = observedSky.stars();
        assertEquals(5067, s.size());
    }

    // TODO: Add tests of positions

    @Test
    void asterismsWorks() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        Set<Asterism> asterisms = observedSky.asterisms();
        assertTrue(catalogue.asterisms().containsAll(asterisms));
    }

    @Test
    void asterismIndicesWorks() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        Asterism asterism = catalogue.asterisms().iterator().next();
        List<Integer> list = observedSky.asterismIndices(asterism);
        assertTrue(catalogue.asterismIndices(asterism).containsAll(list));
    }

    @Test
    void objectClosestToWorksWithANotNullReturn() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        // Ici, sunPosition = (x=-0.5182, y=-0.9860)
        CartesianCoordinates point = CartesianCoordinates.of(-0.52, -0.99);
        Optional<CelestialObject> closestObject = observedSky.objectClosestTo(point, 0.1);
        assertTrue(closestObject.isPresent());
        assertEquals("Soleil", closestObject.get().name());
    }

    @Test
    void objectClosestToWorksWithANullReturn() {
        ObservedSky observedSky = new ObservedSky(d, where, stereographicProjection, catalogue);
        CartesianCoordinates point = CartesianCoordinates.of(-0.52, -0.1);
        Optional<CelestialObject> closestObject = observedSky.objectClosestTo(point, 0.01);
        assertTrue(closestObject.isEmpty());
    }
}
