package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.RightOpenInterval;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class SiderealTimeTest {

    private final static double DELTA = 1e-4;

    @Test
    void greenwichWorksWithTheExampleOfTheBook() {
        // For example, what was the GST at 14h 36m 51.67s UT on Greenwich date 22 April 1980?
        // It is 4h 40m 5.23s
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(1980, Month.APRIL, 22),
                LocalTime.of(14, 36, 51, 670000000),
                ZoneOffset.UTC
        );
        double expectedInHours = 4. + 40./60. + 5.53/3600.;
        double expected = Angle.ofHr(RightOpenInterval.of(0, 24).reduce(expectedInHours));
        assertEquals(expected, SiderealTime.greenwich(d), DELTA);
    }

    @Test
    void greenwichWorksWithTwoDateSeparateOfOneSideralDay() {
        ZonedDateTime d1 = ZonedDateTime.of(
                LocalDate.of(2010, Month.FEBRUARY, 1),
                LocalTime.of(0, 0, 0),
                ZoneOffset.UTC
        );

        ZonedDateTime d2 = d1.plusHours(23).plusMinutes(56).plusSeconds(4);
        assertEquals(0, SiderealTime.greenwich(d2) - SiderealTime.greenwich(d1), DELTA);
    }

    @Test
    void localWorksWithTheExampleOfTheBook() {
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(-64, 11.1);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(1980, Month.APRIL, 22),
                LocalTime.of(14, 36, 51, 670000000),
                ZoneOffset.UTC
        );
        double expectedInHours = 4. + 40./60. + 5.53/3600. + Angle.toHr(Angle.ofDeg(-64));
        double expected = Angle.ofHr(expectedInHours);
        assertEquals(expected, SiderealTime.local(d, gc), DELTA);
    }

    @Test
    void localWorksWithTwoDateSeparateOfOneSideralDay() {
        ZonedDateTime d1 = ZonedDateTime.of(
                LocalDate.of(2010, Month.FEBRUARY, 1),
                LocalTime.of(0, 0, 0),
                ZoneOffset.UTC
        );
        ZonedDateTime d2 = d1.plusHours(23).plusMinutes(56).plusSeconds(4);
        GeographicCoordinates gc = GeographicCoordinates.ofDeg(-64, 11.1);
        assertEquals(0, SiderealTime.local(d2, gc) - SiderealTime.local(d1, gc), DELTA);
    }
}
