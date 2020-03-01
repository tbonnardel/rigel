package ch.epfl.rigel.coordinates;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class EclipticToEquatorialConversionTest {

    @Test
    void EclipticToEquatorialConversionTestWorksWithValidParameters() {
        EclipticToEquatorialConversion c = new EclipticToEquatorialConversion(ZonedDateTime.of(
           LocalDate.of(2010, Month.FEBRUARY, 28),
           LocalTime.of(11, 45, 19),
           ZoneOffset.UTC
        ));
    }
}
