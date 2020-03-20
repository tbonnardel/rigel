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
public class MyPlanetModelTest {

    private final static int HOUR_TO_DEG = 15;
    private final static double DELTA = 1e-8;

    @Test
    void attributeALLWorks() {
        assertEquals(8, PlanetModel.ALL.size());
    }

    @Test
    void atWorksWithMercuryWithTheExampleOfTheBook() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );

        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Planet mercury = PlanetModel.MERCURY.at(Epoch.J2010.daysUntil(d), conversion);

        EquatorialCoordinates expected = conversion.apply(EclipticCoordinates.of(
                Angle.ofDeg(253.929758),
                Angle.ofDeg(-2.044057)
        ));

        assertEquals(expected.ra(), mercury.equatorialPos().ra(), DELTA);
        assertEquals(expected.dec(), mercury.equatorialPos().dec(), DELTA);
    }

    @Test
    void atWorksWithJupiterWithTheExampleOfTheBook() {
        ZonedDateTime d = ZonedDateTime.of(
                LocalDate.of(2003, Month.NOVEMBER, 22),
                LocalTime.of(0, 0),
                ZoneOffset.UTC
        );

        EclipticToEquatorialConversion conversion = new EclipticToEquatorialConversion(d);
        Planet jupiter = PlanetModel.JUPITER.at(Epoch.J2010.daysUntil(d), conversion);

        EquatorialCoordinates expected = conversion.apply(EclipticCoordinates.of(
                Angle.ofDeg(166.310510),
                Angle.ofDeg(1.036466)
        ));

        assertEquals(expected.ra(), jupiter.equatorialPos().ra(), DELTA);
        assertEquals(expected.dec(), jupiter.equatorialPos().dec(), DELTA);
    }
}
