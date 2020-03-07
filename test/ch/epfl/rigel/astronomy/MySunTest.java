package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MySunTest {

    /*
        La plupart des tests ont été effectué dans la classe MyPlanetTest
     */

    private final static double DELTA = 1e-6;

    @Test
    void constructorWorksWithValidParameters() {
        //public Sun(EclipticCoordinates eclipticPos, EquatorialCoordinates equatorialPos, float angularSize, float meanAnomaly) {
        Sun s = new Sun(EclipticCoordinates.of(1.1, -.2), EquatorialCoordinates.of(1, .2), 10.1f, 2.9f);
    }

    @Test
    void constructorThrowsExceptionWhenEclipticPositionIsNull() {
        assertThrows(NullPointerException.class, () -> {
            Sun s = new Sun(null, EquatorialCoordinates.of(1, .2), 10.1f, 2.9f);
        });
    }

    @Test
    void toStringWorks() {
        Sun s = new Sun(EclipticCoordinates.of(1.1, -.2), EquatorialCoordinates.of(1, .2), 10.1f, 2.9f);
        assertEquals("Soleil", s.toString());
    }

    @Test
    void magnitudeWorks() {
        Sun s = new Sun(EclipticCoordinates.of(1.1, -.2), EquatorialCoordinates.of(1, .2), 10.1f, 2.9f);
        assertEquals(-26.7, s.magnitude(), DELTA);
    }

    @Test
    void eclipticPositionWorks() {
        Sun s = new Sun(EclipticCoordinates.of(1.1, -.2), EquatorialCoordinates.of(1, .2), 10.1f, 2.9f);
        assertEquals(1.1, s.eclipticPos().lon(), DELTA);
        assertEquals(-.2, s.eclipticPos().lat(), DELTA);
    }

    @Test
    void meanAnomalyWorks() {
        Sun s = new Sun(EclipticCoordinates.of(1.1, -.2), EquatorialCoordinates.of(1, .2), 10.1f, 2.9f);
        assertEquals(2.9, s.meanAnomaly(), DELTA);
    }
}
