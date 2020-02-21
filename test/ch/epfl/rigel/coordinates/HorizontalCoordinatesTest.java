package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class HorizontalCoordinatesTest {

    @Test
    void ofWorksWithValidParameters() {
        HorizontalCoordinates hc = HorizontalCoordinates.of(1.1, .6);
        assertEquals(1.1, hc.lon());
        assertEquals(.6, hc.lat());
    }

    @Test
    void ofFailedOnIllegalAzimuth() {
        // az doit appartenir à [0°, 360°[
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.of(Angle.ofDeg(-0.1), .6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.of(Angle.ofDeg(360.), .6);
        });
    }

    @Test
    void ofFailedOnIllegalAltitude() {
        // alt doit appartenir à [–90°, +90°]
        assertThrows(IllegalArgumentException.class, () -> {
        HorizontalCoordinates hc = HorizontalCoordinates.of(0.1, Angle.ofDeg(-90.1));
        });
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.of(0.1, Angle.ofDeg(90.1));
        });
    }

    @Test
    void ofDegWorksWithValidParameters() {
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(1.1, .6);
        assertEquals(1.1, hc.lonDeg());
        assertEquals(.6, hc.latDeg());
    }

    @Test
    void ofDegFailedOnIllegalAzimuth() {
        // az doit appartenir à [0°, 360°[
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(-.6, .6);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(360., .6);
        });
    }

    @Test
    void ofDegFailedOnIllegalAltitude() {
        // alt doit appartenir à [–90°, +90°]
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(0.1, -90.1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(0.1, 90.1);
        });
    }

    @Test
    void azWorksWithAValidObject() {
        HorizontalCoordinates hc = HorizontalCoordinates.of(0.1, 0.3);
        assertEquals(0.1, hc.az(), 1e-10);
    }

    @Test
    void azDegWorksWithAValidObject() {
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(12.3, 11);
        assertEquals(12.3, hc.azDeg(), 1e-10);
    }

    @Test
    void azOctantNameWorksOnValidParameters() {
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(335, 0);
        assertEquals("NO", hc.azOctantName("N", "E", "S", "O"));
    }
}
