package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class TimeAcceleratorTest {

    private final static long NS_PER_SECOND = 1000000000;

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
                LocalTime.of(13, 12, 59),
                ZoneOffset.UTC
        );
        long t = 2* NS_PER_SECOND;

        ZonedDateTime e = TimeAccelerator.continuous(1).adjust(d, t);

        assertEquals(13, e.getHour());
        assertEquals(13, e.getMinute());
        assertEquals(1, e.getSecond());
    }

    @Test
    void continuousWorksWithFactorGreaterThan1() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.FEBRUARY, 15),
                LocalTime.of(13, 12),
                ZoneOffset.UTC
        );
        long t = 2* NS_PER_SECOND;

        ZonedDateTime expected = TimeAccelerator.continuous(3).adjust(d, t);

        assertEquals(13, expected.getHour());
        assertEquals(12, expected.getMinute());
        assertEquals(6, expected.getSecond());
    }

    @Test
    void continuousWorksWithTheExampleOfTheProfessor() {
        ZonedDateTime T0 = ZonedDateTime.of(
                LocalDate.of(2020, Month.APRIL, 20),
                LocalTime.of(21, 00),
                ZoneOffset.UTC
        );

        long deltaT = (long) (2.340* NS_PER_SECOND);
        ZonedDateTime expected = TimeAccelerator
                .continuous(300)
                .adjust(T0, deltaT);

        assertEquals(21, expected.getHour());
        assertEquals(11, expected.getMinute());
        assertEquals(42, expected.getSecond());
    }

    @Test
    void discreteWorksAtInitialTime() {
        ZonedDateTime d = ZonedDateTime.now();

        assertEquals(d.getNano(),
                TimeAccelerator.discrete(1, Duration.ofSeconds(1)).adjust(d, 0).getNano()
        );
        assertEquals(d.getNano(),
                TimeAccelerator.discrete(10, Duration.ofSeconds(1)).adjust(d, 0).getNano()
        );
    }

    @Test
    void discreteWorksWithADayStep() {
        ZonedDateTime T0 = ZonedDateTime.of(
                LocalDate.of(2020, Month.APRIL, 20),
                LocalTime.of(23, 12),
                ZoneOffset.UTC
        );

        ZonedDateTime expected = TimeAccelerator
                .discrete(1, Duration.ofDays(1))
                .adjust(T0, 1* NS_PER_SECOND);

        assertEquals(2020, expected.getYear());
        assertEquals(Month.APRIL, expected.getMonth());
        assertEquals(21, expected.getDayOfMonth());
        assertEquals(23, expected.getHour());
        assertEquals(12, expected.getMinute());
        assertEquals(0, expected.getSecond());
    }

    @Test
    void discreteWorksWithANanoSecondStep() {
        ZonedDateTime T0 = ZonedDateTime.of(
                LocalDate.of(2020, Month.APRIL, 20),
                LocalTime.of(23, 12, 0, 100),
                ZoneOffset.UTC
        );

        ZonedDateTime expected = TimeAccelerator
                .discrete(10, Duration.ofNanos(1))
                .adjust(T0, (long) (1.1* NS_PER_SECOND));

        assertEquals(2020, expected.getYear());
        assertEquals(Month.APRIL, expected.getMonth());
        assertEquals(20, expected.getDayOfMonth());
        assertEquals(23, expected.getHour());
        assertEquals(12, expected.getMinute());
        assertEquals(0, expected.getSecond());
        assertEquals(111, expected.getNano());
    }

    @Test
    void discreteWorksWithTheExampleOfTheProfessor() {
        ZonedDateTime T0 = ZonedDateTime.of(
                LocalDate.of(2020, Month.APRIL, 20),
                LocalTime.of(21, 00),
                ZoneOffset.UTC
        );

        long deltaT = (long) (2.340* NS_PER_SECOND);
        ZonedDateTime expected = TimeAccelerator
                .discrete(10, Duration.parse("PT23H56M4S"))
                .adjust(T0, deltaT);

        assertEquals(2020, expected.getYear());
        assertEquals(Month.MAY, expected.getMonth());
        assertEquals(13, expected.getDayOfMonth());
        assertEquals(19, expected.getHour());
        assertEquals(29, expected.getMinute());
        assertEquals(32, expected.getSecond());
    }
}
