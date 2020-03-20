package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EclipticCoordinates;
import ch.epfl.rigel.coordinates.EclipticToEquatorialConversion;
import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MySunModelTest {

    private final static double DELTA = 1e-8;
    private final static int HR_TO_DEG = 15;

    @Test
    void sunModelWorksOnCalculateEclipticPos() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );
        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Sun sun = SunModel.SUN.at(Epoch.J2010.daysUntil(d), conversion);

        EclipticCoordinates ecl = sun.eclipticPos();

        assertEquals(Angle.ofDeg(123.580601), ecl.lon(), DELTA);
        assertEquals(Angle.ofDeg(0), ecl.lat(), DELTA);
    }

    @Test
    void sunModelWorksOnCalculateEquatorialPos() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );
        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Sun sun = SunModel.SUN.at(Epoch.J2010.daysUntil(d), conversion);

        EquatorialCoordinates equ = sun.equatorialPos();

        assertEquals(Angle.ofDMS(8*HR_TO_DEG, 23, 33.73), equ.ra(), 1e-1);
        assertEquals(Angle.ofDMS(19, 21, 14.33), equ.dec(), 1e-4);
    }

    @Test
    void sunModelWorksOnCalculateAngularSize() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(1988, Month.JULY, 27),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );
        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Sun sun = SunModel.SUN.at(Epoch.J2010.daysUntil(d), conversion);

        assertEquals(Angle.ofDMS(0, 31, 29.93), sun.angularSize(), 1e-6);
    }

    @Test
    void sunModelWorksOnCalculateMeanAnomaly() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.JULY, 27),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );
        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Sun sun = SunModel.SUN.at(Epoch.J2010.daysUntil(d), conversion);

        assertEquals(Angle.ofDeg(201.159131), sun.meanAnomaly(), 1e-6);
    }
}
