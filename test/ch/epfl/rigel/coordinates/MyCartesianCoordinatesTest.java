package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyCartesianCoordinatesTest {

    @Test
    void ofWorksWithStandardParameters() {
        CartesianCoordinates cc = CartesianCoordinates.of(12.1, -4.1);
    }

    @Test
    void xWorksWithStandardParameters() {
        CartesianCoordinates cc = CartesianCoordinates.of(12.1, -4.1);
        assertEquals(12.1, cc.x());
    }

    @Test
    void yWorksWithStandardParameters() {
        CartesianCoordinates cc = CartesianCoordinates.of(12.1, -4.1);
        assertEquals(-4.1, cc.y());
    }

    @Test
    void equalThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            CartesianCoordinates cc = CartesianCoordinates.of(12.1, -4.1);
            cc.equals(cc);
        });
    }

    @Test
    void hashCodeThrowsException() {
        assertThrows(UnsupportedOperationException.class, () -> {
            CartesianCoordinates cc = CartesianCoordinates.of(12.1, -4.1);
            cc.hashCode();
        });
    }

    @Test
    void toStringWorksWithStandardParameters() {
        CartesianCoordinates cc = CartesianCoordinates.of(12.1, -4.1);
        assertEquals("(x=12.1000, y=-4.1000)", cc.toString());
    }

    @Test
    void toStringWorksWithMoreComplexParameters() {
        CartesianCoordinates cc = CartesianCoordinates.of(12.123456, -4.1953109);
        assertEquals("(x=12.1235, y=-4.1953)", cc.toString());
    }
}
