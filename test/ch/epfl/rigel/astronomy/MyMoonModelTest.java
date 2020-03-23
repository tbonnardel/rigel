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
public class MyMoonModelTest {

    private final static double DELTA = 1e-8;

    @Test
    void atWorksWithTheExampleOfTheBookForEquatorialPosition() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.SEPTEMBER, 1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );

        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Moon moon = MoonModel.MOON.at(Epoch.J2010.daysUntil(d), conversion);

        EquatorialCoordinates expected = conversion.apply(EclipticCoordinates.of(
                Angle.ofDeg(214.862515),
                Angle.ofDeg(1.716257)
        ));

        assertEquals(expected.ra(), moon.equatorialPos().ra(), DELTA);
        assertEquals(expected.dec(), moon.equatorialPos().dec(), DELTA);
    }

    @Test
    void atWorksWithTheExampleOfTheBookForAngularSize() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.SEPTEMBER, 1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );

        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Moon moon = MoonModel.MOON.at(Epoch.J2010.daysUntil(d), conversion);

        assertEquals(Angle.ofDMS(0, 32, 49), moon.angularSize(), 1e-5);
    }

    @Test
    void atWorksWithTheExampleOfTheBookForMagnitude() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.SEPTEMBER, 1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );

        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Moon moon = MoonModel.MOON.at(Epoch.J2010.daysUntil(d), conversion);

        assertEquals(0, moon.magnitude(), DELTA);
    }

    @Test
    void atWorksWithTheExampleOfTheBookForPhase() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.SEPTEMBER, 1),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );

        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Moon moon = MoonModel.MOON.at(Epoch.J2010.daysUntil(d), conversion);

        assertEquals("Lune (22.5%)", moon.info());
    }
}
