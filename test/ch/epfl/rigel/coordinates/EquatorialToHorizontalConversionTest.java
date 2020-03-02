package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class EquatorialToHorizontalConversionTest {

    private final static double DELTA = 1e-0;

    @Test
    void EquatorialToHorizontalConversionWorksWithValidParameters() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.now(),
                LocalTime.now(),
                ZoneOffset.UTC
        );

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(11, 12);

        EquatorialToHorizontalConversion conversionObject = new EquatorialToHorizontalConversion(d, gc);
    }

    @Test
    void conversionWorksWithTheExampleOfTheBook() {
        // Page 48
        EquatorialCoordinates equ = EquatorialCoordinates.of(
                Angle.ofDMS(5, 51, 44),
                Angle.ofDMS(23, 13, 10)
        );

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(-64, 52);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(1980, Month.APRIL, 22),
                LocalTime.of(14, 36, 51),
                ZoneOffset.ofHours(-4)
        );

        EquatorialToHorizontalConversion conversionObject = new EquatorialToHorizontalConversion(d, gc);
        HorizontalCoordinates hc = conversionObject.apply(equ);

        HorizontalCoordinates hcExpected = HorizontalCoordinates.of(
                Angle.ofDMS(283, 16, 15.7),
                Angle.ofDMS(19, 20, 3.64)
        );

        assertEquals(hcExpected.az(), hc.az(), DELTA);
        assertEquals(hcExpected.alt(), hc.alt(), DELTA);
    }

    @Test
    void conversionWorksWithAWebsiteConverter() {
        // Source : http://xjubier.free.fr/en/site_pages/astronomy/coordinatesConverter.html
        EquatorialCoordinates equ = EquatorialCoordinates.of(
                Angle.ofDMS(11, 12, 13),
                Angle.ofDMS(56, 1, 47.16)
        );

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(0, 0);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2005, Month.JULY, 22),
                LocalTime.of(12, 28, 49),
                ZoneOffset.UTC
        );

        EquatorialToHorizontalConversion conversionObject = new EquatorialToHorizontalConversion(d, gc);
        HorizontalCoordinates hc = conversionObject.apply(equ);

        HorizontalCoordinates hcExpected = HorizontalCoordinates.of(
                Angle.ofDeg(69.19),
                Angle.ofDeg(-13.40)
        );

        assertEquals(hcExpected.az(), hc.az(), DELTA);
        assertEquals(hcExpected.alt(), hc.alt(), DELTA);
    }
}
