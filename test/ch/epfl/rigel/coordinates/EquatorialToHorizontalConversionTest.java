package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.math.Angle;
import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class EquatorialToHorizontalConversionTest {

    private final static double DELTA = 1e-7;

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

    // @Test
    void conversionWorksWithTheExampleOfTheBook() {
        // Page 48

        // Fonctionne en mettant ces lignes dans la méthode apply :
        // double H = Angle.ofHr(5.862222); // 5h 51m 44s
        // System.out.println("H: " + Angle.toHr(H) + "h");

        EquatorialCoordinates equ = EquatorialCoordinates.of(
                0,
                Angle.ofDMS(23, 13, 10)
        );

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(Angle.normalizePositive(Angle.ofDeg(-64)), 52);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(1980, Month.APRIL, 22),
                LocalTime.of(12, 0, 0),
                ZoneOffset.UTC
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

    //@Test
    void conversionWorksWithAWebsiteConverter() {
        // Source : http://xjubier.free.fr/en/site_pages/astronomy/coordinatesConverter.html
        EquatorialCoordinates equ = EquatorialCoordinates.of(
                Angle.ofDMS(2*15, 0, 0),
                Angle.ofDMS(3, 0, 0)
        );

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(0, 0);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2000, Month.JULY, 1),
                LocalTime.of(0, 1, 0),
                ZoneOffset.UTC
        );

        EquatorialToHorizontalConversion conversionObject = new EquatorialToHorizontalConversion(d, gc);
        HorizontalCoordinates hc = conversionObject.apply(equ);

        HorizontalCoordinates hcExpected = HorizontalCoordinates.of(
                Angle.normalizePositive(Angle.ofDeg(-20.4)),
                Angle.ofDeg(86.8)
        );

        assertEquals(hcExpected.az(), hc.az(), DELTA);
        assertEquals(hcExpected.alt(), hc.alt(), DELTA);
    }

    //@Test
    void conversionWorksWithAnotherWebsite() {
        // Source : http://infomesh.net/stuff/coords
        EquatorialCoordinates equ = EquatorialCoordinates.of(
                Angle.ofDeg(11),
                Angle.ofDeg(-5)
        );

        GeographicCoordinates gc = GeographicCoordinates.ofDeg(46.53, 6.62);
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2020, Month.MARCH, 3),
                LocalTime.of(22, 57, 30),
                ZoneOffset.ofHours(1)
        );

        EquatorialToHorizontalConversion conversionObject = new EquatorialToHorizontalConversion(d, gc);
        HorizontalCoordinates hc = conversionObject.apply(equ);

        HorizontalCoordinates hcExpected = HorizontalCoordinates.of(
                Angle.ofDeg(147.404),
                Angle.ofDeg(-33.228)
        );

        assertEquals(hcExpected.az(), hc.az(), DELTA);
        assertEquals(hcExpected.alt(), hc.alt(), DELTA);
    }
}
