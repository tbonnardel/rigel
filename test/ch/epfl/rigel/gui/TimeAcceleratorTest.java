package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class TimeAcceleratorTest {

    private final static long NB_NS_IN_A_SECOND = 1000000000;

    @Test
    void continuousWorksAtInitialTime() {
        ZonedDateTime d = ZonedDateTime.now();

        assertEquals(d.getNano(),
                TimeAccelerator.continuous(1).adjust(d, 0).getNano()
        );
        assertEquals(d.getNano(),
                TimeAccelerator.continuous(10).adjust(d, 0).getNano()
        );
    }

    @Test
    void continuousWorksWithFactor1() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.FEBRUARY, 15),
                LocalTime.of(13, 12),
                ZoneOffset.UTC
        );
        long t = 2*NB_NS_IN_A_SECOND;

        ZonedDateTime e = TimeAccelerator.continuous(1).adjust(d, t);

        assertEquals(d.getHour(), e.getHour());
        assertEquals(d.getMinute(), e.getMinute());
        assertEquals(d.getSecond()+2, e.getSecond());
    }

    @Test
    void continuousWorksWithFactorGreaterThan1() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.FEBRUARY, 15),
                LocalTime.of(13, 12),
                ZoneOffset.UTC
        );
        long t = 2*NB_NS_IN_A_SECOND;

        ZonedDateTime e = TimeAccelerator.continuous(3).adjust(d, t);

        assertEquals(d.getHour(), e.getHour());
        assertEquals(d.getMinute(), e.getMinute());
        assertEquals(d.getSecond()+2*3, e.getSecond());
    }
}
