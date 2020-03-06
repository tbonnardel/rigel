package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyStereographicProjectionTest {

    @Test
    void constructorWorksWithStandardParameters() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(11, -3.1));
    }



    @Test
    void equalsThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(11, -3.1));
            sp.equals(sp);
        });
    }

    @Test
    void hashCodeThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(11, -3.1));
            sp.hashCode();
        });
    }

    @Test
    void toStringWorksWithStandardParameters() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(11, -3.1));
        assertEquals("(cAz=11.0000°, cAlt=-3.1000°)", sp.toString());
    }

    @Test
    void toStringWorksWithMoreComplexParameters() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(0.12345, -3.100231));
        assertEquals("(cAz=0.1235°, cAlt=-3.1002°)", sp.toString());
    }
}
