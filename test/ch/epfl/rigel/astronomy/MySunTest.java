package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MySunTest {

    /*
        La plupart des tests ont été effectué dans la classe MyPlanetTest
     */

    @Test
    void constructorWorksWithValidParameters() {
        //Sun s = new Sun(EquatorialCoordinates.of(.22, -.3692812), 2.5f, -26.7);
    }

    @Test
    void constructorThrowsExceptionWhenEclipticPositionIsNull() {

    }
}
