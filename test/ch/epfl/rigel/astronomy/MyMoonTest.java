package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyMoonTest {

    /*
        La plupart des tests ont été effectué dans la classe MyPlanetTest
     */

    @Test
    void constructorWorksWithValidParameters() {
        Moon m = new Moon(EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f, .33f);
    }

    @Test
    void constructorThrowsExceptionWhenPhaseIsInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Moon m = new Moon(EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f, -.33f);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            Moon m = new Moon(EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f, 1.33f);
        });
    }

    @Test
    void infoWorksWithStandardParameters() {
        Moon m = new Moon(EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f, .375f);
        assertEquals("Lune (37.5%)", m.info());
    }

    @Test
    void infoWorksWithComplexParameters() {
        Moon m = new Moon(EquatorialCoordinates.of(1.1, -.4), .5f, -4.9f, .37491f);
        assertEquals("Lune (37.5%)", m.info());
    }
}
