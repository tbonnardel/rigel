package ch.epfl.rigel.gui;

import ch.epfl.rigel.coordinates.HorizontalCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class ViewingParametersBeanTest {

    @Test
    void nullOrZeroValuesOnStart() {
        ViewingParametersBean bean = new ViewingParametersBean();
        assertEquals(0, bean.getFieldOfViewDeg());
        assertNull(bean.getCenter());
    }

    @Test
    void getAndSetFieldOfViewDegWorks() {
        ViewingParametersBean bean = new ViewingParametersBean();
        double expected = 11.3;
        bean.setFieldOfViewDeg(expected);
        assertEquals(expected, bean.getFieldOfViewDeg());

        expected = 111.45;
        bean.setFieldOfViewDeg(expected);
        assertEquals(expected, bean.getFieldOfViewDeg());
    }

    @Test
    void getAndSetCenterWorks() {
        ViewingParametersBean bean = new ViewingParametersBean();
        HorizontalCoordinates expected = HorizontalCoordinates.of(1.1, 1.23);
        bean.setCenter(expected);
        assertEquals(expected.toString(), bean.getCenter().toString());

        expected = HorizontalCoordinates.of(2.31, 0.7);
        bean.setCenter(expected);
        assertEquals(expected.toString(), bean.getCenter().toString());
    }
}
