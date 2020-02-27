package ch.epfl.rigel.astronomy;
import org.junit.jupiter.api.Test;

import java.time.*;
import java.time.temporal.ChronoUnit;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class EpochTest {

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
                LocalTime.of(18, 0),
                ZoneOffset.UTC);

        assertEquals(1.0000616016427104, Epoch.J2000.julianCenturiesUntil(d), 1e-10); // TODO: vérifier cette valeur
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
                LocalDate.of(2100, Month.JANUARY, 3),
                LocalTime.of(18, 0),
                ZoneOffset.UTC);

        assertEquals(0.9000889801505818, Epoch.J2010.julianCenturiesUntil(d), 1e-10); // TODO: vérifier cette valeur
    }

    // TODO: Faire un test avec des untils qui sont négatifs
}
