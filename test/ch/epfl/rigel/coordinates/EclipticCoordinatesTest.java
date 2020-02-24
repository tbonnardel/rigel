package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class EclipticCoordinatesTest {

    @Test
    void ofWorksWithValidParameters() {
        EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(-12.5));
        assertEquals(Angle.ofDeg(11), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(-12.5), ec.lat(), 1e-10);

        ec = EclipticCoordinates.of(Angle.ofDeg(0), Angle.ofDeg(-12.5));
        assertEquals(Angle.ofDeg(0), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(-12.5), ec.lat(), 1e-10);

        ec = EclipticCoordinates.of(Angle.ofDeg(359.99), Angle.ofDeg(-12.5));
        assertEquals(Angle.ofDeg(359.99), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(-12.5), ec.lat(), 1e-10);

        ec = EclipticCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(-90));
        assertEquals(Angle.ofDeg(11), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(-90), ec.lat(), 1e-10);

        ec = EclipticCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(90));
        assertEquals(Angle.ofDeg(11), ec.lon(), 1e-10);
        assertEquals(Angle.ofDeg(90), ec.lat(), 1e-10);
    }

    @Test
    void ofFailedWithIllegalParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(360), Angle.ofDeg(-12.5));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(375), Angle.ofDeg(-12.5));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(-0.1), Angle.ofDeg(-12.5));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(12), Angle.ofDeg(-90.01));
        });

        assertThrows(IllegalArgumentException.class, () -> {
            EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(12), Angle.ofDeg(90.01));
        });
    }

    @Test
    void lonWorksOnValidObject() {
        EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(-12.5));
        assertEquals(Angle.ofDeg(11), ec.lon(), 1e-10);
    }

    @Test
    void lonDegWorksOnValidObject() {
        EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(-12.5));
        assertEquals(11, ec.lonDeg(), 1e-10);
    }

    @Test
    void latWorksOnValidObject() {
        EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(-12.5));
        assertEquals(Angle.ofDeg(-12.5), ec.lat(), 1e-10);
    }

    @Test
    void latDegWorksOnValidObject() {
        EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(11), Angle.ofDeg(-12.5));
        assertEquals(-12.5, ec.latDeg(), 1e-10);
    }

    @Test
    void toStringWorksWithValidObjects() {
        EclipticCoordinates ec = EclipticCoordinates.of(Angle.ofDeg(22.5), Angle.ofDeg(18.));
        assertEquals("(λ=22.5000°, β=18.0000°)", ec.toString());
        ec = EclipticCoordinates.of(Angle.ofDeg(1.12344), Angle.ofDeg(18.11119));
        assertEquals("(λ=1.1234°, β=18.1112°)", ec.toString());
    }
}
