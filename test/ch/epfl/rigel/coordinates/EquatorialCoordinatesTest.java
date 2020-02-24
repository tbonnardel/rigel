package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class EquatorialCoordinatesTest {

    @Test
    void ofWorksOnValidParameters() {
        EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(15), Angle.ofDeg(-11.5));
        assertEquals(Angle.ofDeg(15), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(-11.5), ec.lat(), 1e-10);

        ec = EquatorialCoordinates.of(Angle.ofDeg(0), Angle.ofDeg(-11.5));
        assertEquals(Angle.ofDeg(0), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(-11.5), ec.lat(), 1e-10);

        ec = EquatorialCoordinates.of(Angle.ofDeg(15), Angle.ofDeg(-90));
        assertEquals(Angle.ofDeg(15), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(-90), ec.lat(), 1e-10);

        ec = EquatorialCoordinates.of(Angle.ofDeg(15), Angle.ofDeg(90));
        assertEquals(Angle.ofDeg(15), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(90), ec.lat(), 1e-10);
    }

    @Test
    void ofFailedWithIllegalParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(360), Angle.ofDeg(-11.5));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(-0.1), Angle.ofDeg(-11.5));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(-90.001));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(90.1));
        });
    }

    @Test
    void raWorksWithValidObject() {
        EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(15), Angle.ofDeg(-11.5));
        assertEquals(Angle.ofDeg(15), ec.ra(), 1e-10);
    }

    @Test
    void raDegWorksWithValidObject() {
        EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(15), Angle.ofDeg(-11.5));
        assertEquals(15, ec.raDeg(), 1e-10);
    }

    @Test
    void raHrWorksWithValidObject() {
        EquatorialCoordinates ec = EquatorialCoordinates.of(1.1, Angle.ofDeg(-11.5));
        assertEquals(Angle.toHr(1.1), ec.raHr(), 1e-10);
    }

    @Test
    void decWorksWithValidObject() {
        EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(15), Angle.ofDeg(-11.5));
        assertEquals(Angle.ofDeg(-11.5), ec.dec(), 1e-10);
    }

    @Test
    void decDegWorksWithValidObject() {
        EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofDeg(15), Angle.ofDeg(-11.5));
        assertEquals(-11.5, ec.decDeg(), 1e-10);
    }

    @Test
    void toStringWorksWithValidObject() {
        EquatorialCoordinates ec = EquatorialCoordinates.of(Angle.ofHr(1.5), Angle.ofDeg(45.));
        assertEquals("(ra=1.5000h, dec=45.0000°)", ec.toString());
    }
}
