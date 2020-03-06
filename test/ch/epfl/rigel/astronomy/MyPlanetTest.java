package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyPlanetTest {

    private final static double DELTA = 1e-7;

    @Test
    void constructorWorksWithValidParameters() {
        Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
    }

    @Test
    void constructorThrowsExceptionWhenAngularSizeIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), -.5f, -4.9f);
        });
    }

    @Test
    void constructorThrowsExceptionWhenNameIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Planet p = new Planet(null, EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
        });
    }

    @Test
    void constructorThrowsExceptionWhenPositionIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Planet p = new Planet("Vénus", null, .5f, -4.9f);
        });
    }

    @Test
    void constructorThrowsExceptionWhenNameAndPositionAreNull() {
        assertThrows(NullPointerException.class, () -> {
            Planet p = new Planet(null, null, .5f, -4.9f);
        });
    }

    @Test
    void nameWorks() {
        Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
        assertEquals("Vénus", p.name());
    }

    @Test
    void positionWorks() {
        Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
        assertEquals(1.1, p.equatorialPos().ra(), DELTA);
        assertEquals(-.4, p.equatorialPos().dec(), DELTA);
    }

    @Test
    void angualarSizeWorks() {
        Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
        assertEquals(.5, p.angularSize(), DELTA);
    }

    @Test
    void magnitudeWorks() {
        Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
        assertEquals(-4.9, p.magnitude(), DELTA);
    }

    @Test
    void infoWorks() {
        Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
        assertEquals("Vénus", p.info());
    }

    @Test
    void toStringWorks() {
        Planet p = new Planet("Vénus", EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f);
        assertEquals("Vénus", p.toString());
    }
}
