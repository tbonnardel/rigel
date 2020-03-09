package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyEclipticToEquatorialConversionTest {

    private final static double DELTA = 1e-4;

    @Test
    void EclipticToEquatorialConversionTestWorksWithValidParameters() {
        EclipticToEquatorialConversion c = new EclipticToEquatorialConversion(ZonedDateTime.of(
           LocalDate.of(2010, Month.FEBRUARY, 28),
           LocalTime.of(11, 45, 19),
           ZoneOffset.UTC
        ));
    }

    @Test
    void conversionWorksWithTheExampleOfTheBook() {
        // Page 54
        EclipticCoordinates ecl = EclipticCoordinates.of(
                Angle.ofDMS(139, 41, 10.),
                Angle.ofDMS(4, 52, 31.)
        );
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2009, Month.JULY, 6),
                LocalTime.of(20, 0),    // N'importe pas
                ZoneOffset.UTC
        );
        EquatorialCoordinates equExpected = EquatorialCoordinates.of(
                2.508430994,
                0.340962273
        );

        EclipticToEquatorialConversion conversionObj = new EclipticToEquatorialConversion(d);
        EquatorialCoordinates equ = conversionObj.apply(ecl);

        assertEquals(equExpected.ra(), equ.ra(), DELTA);
        assertEquals(equExpected.dec(), equ.dec(), DELTA);
    }
}
