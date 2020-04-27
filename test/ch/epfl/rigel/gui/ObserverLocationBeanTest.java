package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class ObserverLocationBeanTest {

    private final static double DELTA = 1e-9;

    @Test
    void zeroValuesOnStart() {
        ObserverLocationBean bean = new ObserverLocationBean();
        assertEquals(0, bean.getLonDeg(), DELTA);
        assertEquals(0, bean.getLatDeg(), DELTA);
        assertEquals(0, bean.getCoordinates().lonDeg(), DELTA);
        assertEquals(0, bean.getCoordinates().latDeg(), DELTA);
    }

    @Test
    void getAndSetLonDegWorks() {
        ObserverLocationBean bean = new ObserverLocationBean();
        double expected = 11.3;
        bean.setLonDeg(expected);
        assertEquals(expected, bean.getLonDeg(), DELTA);

        expected = 111.67;
        bean.setLonDeg(expected);
        assertEquals(expected, bean.getLonDeg(), DELTA);
    }

    @Test
    void getAndSetLatDegWorks() {
        ObserverLocationBean bean = new ObserverLocationBean();
        double expected = 11.3;
        bean.setLatDeg(expected);
        assertEquals(expected, bean.getLatDeg(), DELTA);

        expected = 111.67;
        bean.setLatDeg(expected);
        assertEquals(expected, bean.getLatDeg(), DELTA);
    }

    @Test
    void getCoordinatesWorksWithBinding() {
        ObserverLocationBean bean = new ObserverLocationBean();
        assertEquals(0, bean.getCoordinates().lonDeg(), DELTA);
        assertEquals(0, bean.getCoordinates().latDeg(), DELTA);

        bean.setLonDeg(1.1);
        assertEquals(1.1, bean.getCoordinates().lonDeg(), DELTA);
        assertEquals(0, bean.getCoordinates().latDeg(), DELTA);

        bean.setLatDeg(23.9);
        assertEquals(1.1, bean.getCoordinates().lonDeg(), DELTA);
        assertEquals(23.9, bean.getCoordinates().latDeg(), DELTA);

        bean.setLonDeg(28);
        bean.setLatDeg(12);
        assertEquals(28, bean.getCoordinates().lonDeg(), DELTA);
        assertEquals(12, bean.getCoordinates().latDeg(), DELTA);
    }
}
