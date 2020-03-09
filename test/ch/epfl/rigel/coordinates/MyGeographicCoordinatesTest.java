package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyGeographicCoordinatesTest {

    @Test
    void ofDegWorksOnValidParameters() {
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(-15., 21);
        assertEquals(-15., gc.lonDeg(), 1e-10);
        assertEquals(21., gc.latDeg(), 1e-10);
        gc = GeographicCoordinates.ofDeg(-180., 21);
        assertEquals(-180., gc.lonDeg(), 1e-10);
        assertEquals(21., gc.latDeg(), 1e-10);
        gc = GeographicCoordinates.ofDeg(-179.9, 21);
        assertEquals(-179.9, gc.lonDeg(), 1e-10);
        assertEquals(21., gc.latDeg(), 1e-10);
        gc = GeographicCoordinates.ofDeg(-21, -90.);
        assertEquals(-21., gc.lonDeg(), 1e-10);
        assertEquals(-90., gc.latDeg(), 1e-10);
        gc = GeographicCoordinates.ofDeg(-21, 90.);
        assertEquals(-21., gc.lonDeg(), 1e-10);
        assertEquals(90., gc.latDeg(), 1e-10);
    }

    @Test
    void ofDegFailedOnIllegalLongitude() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates gc = GeographicCoordinates.ofDeg(-181., 21);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates gc = GeographicCoordinates.ofDeg(180., 21);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates gc = GeographicCoordinates.ofDeg(200., 21);
        });
    }

    @Test
    void ofDegFailedOnIllegalLatitude() {
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates gc = GeographicCoordinates.ofDeg(11., -90.1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates gc = GeographicCoordinates.ofDeg(11., -100);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            GeographicCoordinates gc = GeographicCoordinates.ofDeg(11., 90.1);
        });
    }

    @Test
    void onStringWorksWithValidParameters() {
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(12.3456, 7.8901);
        assertEquals("(lon=12.3456°, lat=7.8901°)", gc.toString());

        gc =GeographicCoordinates.ofDeg(6.57, 46.52);
        assertEquals("(lon=6.5700°, lat=46.5200°)", gc.toString());
    }
}
