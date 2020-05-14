package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.PI;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyStereographicProjectionTest {

    private final static double DELTA = 1e-8;

    @Test
    void constructorWorksWithStandardParameters() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(11, -3.1));
    }

    @Test
    void circleCenterForParallelWorksWithStandardParameters() {
        double cy = cos(0.2) / (sin(0.3) + sin(0.2));
        CartesianCoordinates expected = CartesianCoordinates.of(0, cy);

        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.1, 0.2));
        CartesianCoordinates value = sp.circleCenterForParallel(HorizontalCoordinates.of(0.1, 0.3));

        assertEquals(expected.x(), value.x(), DELTA);
        assertEquals(expected.y(), value.y(), DELTA);
    }

    @Test
    void circleCenterForParallelWorksWhenBothLatitudeEqualZero() {
        // Cas où les deux latitudes valent zéros, on s'attend à avoir une ordonnée infinie
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.1, 0));
        CartesianCoordinates value = sp.circleCenterForParallel(HorizontalCoordinates.of(0.4, 0));

        assertEquals(0, value.x(), DELTA);
        assertEquals(Double.POSITIVE_INFINITY, value.y());
    }

    @Test
    void circleRadiusForParallelWorksWithStandardParameters() {
        double expected = cos(0.3) / (sin(0.3) + sin(0.2));

        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.1, 0.2));
        double value = sp.circleRadiusForParallel(HorizontalCoordinates.of(0.1, 0.3));

        assertEquals(expected, value, DELTA);
    }

    @Test
    void circleRadiusForParallelWorksWhenBothLatitudeEqualZero() {
        // Cas où les deux latitudes valent zéros, on s'attend à avoir un rayon infinie
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.1, 0));
        double value = sp.circleRadiusForParallel(HorizontalCoordinates.of(0.1, 0));

        assertEquals(Double.POSITIVE_INFINITY, value);
    }

    @Test
    void applyToAngleWorks() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(11, -3.1));
        assertEquals(2., sp.applyToAngle(Angle.ofDeg(180)), DELTA);
    }

    @Test
    void applyWorks() {
        /*
        lambda = 1.4 rad
        phi = .3 rad
        lambda0 = .1 rad
        phi1 = .2 rad

        lambdaD = 1.3 rad
        d = .76384380212
         */
        double x = 0.70313524892;
        double y = 0.18245116046;
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.1, 0.2));
        CartesianCoordinates value = sp.apply(HorizontalCoordinates.of(1.4, .3));

        assertEquals(x, value.x(), DELTA);
        assertEquals(y, value.y(), DELTA);
    }

    @Test
    void inverseApplyWorksWithStandardParameters() {
        /*
        x = .5
        y = .3
        lambda0 = .1 rad
        phi1 = .2 rad

        rho = 0.58309518948
        sinc = 0.87029132758
        cosc = 0.49253731343
         */
        double lambda = 1.18528017;
        double phi = 0.566506807;
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.1, 0.2));
        HorizontalCoordinates value = sp.inverseApply(CartesianCoordinates.of(.5, .3));

        assertEquals(lambda, value.az(), DELTA);
        assertEquals(phi, value.alt(), DELTA);
    }

    @Test
    void inverseApplyWorksWithMoreComplexParameters() {
        /*
        x = 1.4
        y = -1.2
        lambda0 = .1 rad
        phi1 = .2 rad

        rho = 1.84390889146
        sinc = 0.8381404052
        cosc = -0.54545454545
         */
        double lambda = Angle.normalizePositive(-0.880648981) - PI;
        double phi = -0.698339604;
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.of(0.1, 0.2));
        HorizontalCoordinates value = sp.inverseApply(CartesianCoordinates.of(1.4, -1.2));

        assertEquals(lambda, value.az(), DELTA);
        assertEquals(phi, value.alt(), DELTA);
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
        assertEquals("StereographicProjection (cAz=11.0000°, cAlt=-3.1000°)", sp.toString());
    }

    @Test
    void toStringWorksWithMoreComplexParameters() {
        StereographicProjection sp = new StereographicProjection(HorizontalCoordinates.ofDeg(0.12345, -3.100231));
        assertEquals("StereographicProjection (cAz=0.1235°, cAlt=-3.1002°)", sp.toString());
    }
}
