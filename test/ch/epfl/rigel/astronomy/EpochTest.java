package ch.epfl.rigel.astronomy;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class EpochTest {

    private final static double NB_DAYS_IN_CENTURY = 36525;
    private final static double NB_HOURS_IN_CENTURY = NB_DAYS_IN_CENTURY * 24.;
    private final static double NB_MINUTES_IN_CENTURY = NB_HOURS_IN_CENTURY * 60.;

    @Test
    void daysUntilWorksWithExactZonedDateTimeAndJ2000() {
        ZonedDateTime target = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 1),
                LocalTime.of(12, 0),
                ZoneOffset.UTC);

        assertEquals(0, Epoch.J2000.daysUntil(target), 1e-10);
    }

    @Test
    void daysUntilWorksWithStandardZonedDateTimeAndJ2000() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);

        assertEquals(2.25, Epoch.J2000.daysUntil(d), 1e-10);
    }

    @Test
    void julianCenturiesUntilWorksWithExactZonedDateTimeAndJ2000() {
        ZonedDateTime target = ZonedDateTime.of(
                LocalDate.of(2000, Month.JANUARY, 1),
                LocalTime.of(12, 0),
                ZoneOffset.UTC);

        assertEquals(0, Epoch.J2000.julianCenturiesUntil(target), 1e-10);
    }

    @Test
    void julianCenturiesUntilWorksWithStandardZonedDateTimeAndJ2000() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2100, Month.JANUARY, 3),
                LocalTime.of(18, 10),
                ZoneOffset.UTC);

        double expected = 1. + 2./NB_DAYS_IN_CENTURY + (18. - 12.)/NB_HOURS_IN_CENTURY + 10./NB_MINUTES_IN_CENTURY;
        assertEquals(expected, Epoch.J2000.julianCenturiesUntil(d), 1e-10);
    }

    @Test
    void daysUntilWorksWithExactZonedDateTimeAndJ2010() {
        ZonedDateTime target = ZonedDateTime.of(
                LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC);

        assertEquals(0, Epoch.J2010.daysUntil(target), 1e-10);
    }

    @Test
    void daysUntilWorksWithStandardZonedDateTimeAndJ2010() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2010, Month.JANUARY, 3),
                LocalTime.of(12, 0),
                ZoneOffset.UTC);

        assertEquals(3.5, Epoch.J2010.daysUntil(d), 1e-10);
    }

    @Test
    void julianCenturiesUntilWorksWithExactZonedDateTimeAndJ2010() {
        ZonedDateTime target = ZonedDateTime.of(
                LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC);

        assertEquals(0, Epoch.J2010.julianCenturiesUntil(target), 1e-10);
    }

    @Test
    void julianCenturiesUntilWorksWithStandardZonedDateTimeAndJ2010() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2110, Month.JANUARY, 3),
                LocalTime.of(18, 10),
                ZoneOffset.UTC);
        double expected = 1. + 2./NB_DAYS_IN_CENTURY + 18./NB_HOURS_IN_CENTURY + 10./NB_MINUTES_IN_CENTURY;
        assertEquals(expected, Epoch.J2010.julianCenturiesUntil(d), 1e-10);
    }

    // TODO: Faire un test avec des untils qui sont négatifs
}
