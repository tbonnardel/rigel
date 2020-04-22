package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class NamedTimeAcceleratorTest {

    private final static long NS_PER_SECOND = 1000000000;

    @Test
    void getNameWorksForAllNamedTimeAccelerators() {
        assertEquals("1x", NamedTimeAccelerator.TIMES_1.getName());
        assertEquals("30x", NamedTimeAccelerator.TIMES_30.getName());
        assertEquals("300x", NamedTimeAccelerator.TIMES_300.getName());
        assertEquals("3000x", NamedTimeAccelerator.TIMES_3000.getName());
    }

    @Test
    void getAcceleratorWorksForAllNamedTimeAccelerators() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.FEBRUARY, 15),
                LocalTime.of(13, 12, 59),
                ZoneOffset.UTC
        );

        assertEquals(
                TimeAccelerator.continuous(1).adjust(d, NS_PER_SECOND),
                NamedTimeAccelerator.TIMES_1.getAccelerator().adjust(d, NS_PER_SECOND));
        assertEquals(
                TimeAccelerator.continuous(30).adjust(d, NS_PER_SECOND),
                NamedTimeAccelerator.TIMES_30.getAccelerator().adjust(d, NS_PER_SECOND));
        assertEquals(
                TimeAccelerator.continuous(300).adjust(d, NS_PER_SECOND),
                NamedTimeAccelerator.TIMES_300.getAccelerator().adjust(d, NS_PER_SECOND));
        assertEquals(
                TimeAccelerator.continuous(3000).adjust(d, NS_PER_SECOND),
                NamedTimeAccelerator.TIMES_3000.getAccelerator().adjust(d, NS_PER_SECOND));

        assertEquals(
                TimeAccelerator.discrete(60, Duration.ofDays(1)).adjust(d, NS_PER_SECOND),
                NamedTimeAccelerator.DAY.getAccelerator().adjust(d, NS_PER_SECOND));
        assertEquals(
                TimeAccelerator.discrete(60, Duration.parse("PT23H56M4S")).adjust(d, NS_PER_SECOND),
                NamedTimeAccelerator.SIDERAL_DAY.getAccelerator().adjust(d, NS_PER_SECOND));
    }

    @Test
    void toStringWorksForAllNamedTimeAccelerators() {
        assertEquals("1x", NamedTimeAccelerator.TIMES_1.toString());
        assertEquals("30x", NamedTimeAccelerator.TIMES_30.toString());
        assertEquals("300x", NamedTimeAccelerator.TIMES_300.toString());
        assertEquals("3000x", NamedTimeAccelerator.TIMES_3000.toString());
    }
}
