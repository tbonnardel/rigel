package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyHorizontalCoordinatesTest {

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

        hc = HorizontalCoordinates.ofDeg(335, 22);
        assertEquals("NO", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(0, 0);
        assertEquals("N", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(338, 0);
        assertEquals("N", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(337, 22);
        assertEquals("NO", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(292.6, 0);
        assertEquals("NO", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(292, 0);
        assertEquals("O", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(247.6, 0);
        assertEquals("O", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(247.45, 0);
        assertEquals("SO", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(202.51, 22);
        assertEquals("SO", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(202.49, 0);
        assertEquals("S", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(157.51, 0);
        assertEquals("S", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(157.499, 0);
        assertEquals("SE", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(112.512, 22);
        assertEquals("SE", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(112.4, 0);
        assertEquals("E", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(67.55, 0);
        assertEquals("E", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(67.4999, 0);
        assertEquals("NE", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(22.51, 22);
        assertEquals("NE", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(22.26, 0);
        assertEquals("N", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(0.1, 0);
        assertEquals("N", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(270, 0);
        assertEquals("W", hc.azOctantName("N", "E", "S", "W"));
    }

    @Test
    void azOctantNameWorksOnLimitParameters() {
        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(22.5, 0);
        assertEquals("N", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(67.5, 0);
        assertEquals("E", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(112.5, 0);
        assertEquals("E", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(157.5, 0);
        assertEquals("S", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(202.5, 0);
        assertEquals("S", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(247.5, 0);
        assertEquals("O", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(292.5, 0);
        assertEquals("O", hc.azOctantName("N", "E", "S", "O"));

        hc = HorizontalCoordinates.ofDeg(337.5, 0);
        assertEquals("N", hc.azOctantName("N", "E", "S", "O"));
    }

    @Test
    void angularDistanceToWorksOnValidObjects() {
        HorizontalCoordinates epfl = HorizontalCoordinates.ofDeg(6.5682, 46.5183);
        HorizontalCoordinates epfz = HorizontalCoordinates.ofDeg(8.5476, 47.3763);

        assertEquals(0.0279, epfl.angularDistanceTo(epfz), 1e-4);
    }

    @Test
    void toStringWorksOnRegularHorizontalCoordinates() {
        HorizontalCoordinates epfl = HorizontalCoordinates.ofDeg(6.5682, 46.5183);
        assertEquals("(az=6.5682°, alt=46.5183°)", epfl.toString());

        HorizontalCoordinates hc = HorizontalCoordinates.ofDeg(350, 7.2);
        assertEquals("(az=350.0000°, alt=7.2000°)", hc.toString());
    }
}
