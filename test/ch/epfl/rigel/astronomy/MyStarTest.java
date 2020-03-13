package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyStarTest {

    private final static double DELTA = 1e-9;

    @Test
    void constructorWorksWithValidParameters() {
        Star s = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
    }

    @Test
    void constructorThrowsExceptionWhenHipparcosIdIsNegative() {
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(-1, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
        });
    }

    @Test
    void constructorThrowsExceptionWhenIndexColorIsIllegal() {
        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.55f);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Star s = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, 5.51f);
        });
    }

    @Test
    void angularSizeWorks() {
        Star s = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
        assertEquals(0f, s.angularSize(), DELTA);
    }

    @Test
    void colorTemperatureWorks() {
        int delta = 20;

        Star rigel = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
        assertEquals(10500, rigel.colorTemperature(), delta);

        Star betelgeuse = new Star(27989, "Bételgeuse", EquatorialCoordinates.of(1.1, 0.7), -.1f, 1.5f);
        assertEquals(3800, betelgeuse.colorTemperature(), delta);
    }

    @Test
    void hipparcosIdWorks() {
        Star rigel = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
        assertEquals(24436, rigel.hipparcosId());

        Star betelgeuse = new Star(27989, "Bételgeuse", EquatorialCoordinates.of(1.1, 0.7), -.1f, 1.5f);
        assertEquals(27989, betelgeuse.hipparcosId());
    }
}
