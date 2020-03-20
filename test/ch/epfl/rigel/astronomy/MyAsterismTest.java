package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyAsterismTest {

    @Test
    void constructorWorksWithValidParameters() {
        Star rigel = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
        Star betelgeuse = new Star(27989, "Bételgeuse", EquatorialCoordinates.of(1.1, 0.7), -.1f, 1.5f);

        List<Star> stars = new ArrayList<>();
        stars.add(rigel);
        stars.add(betelgeuse);

        Asterism asterism = new Asterism(stars);
    }

    @Test
    void constructorThrowsExceptionWhenStarsIsEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            Asterism asterism = new Asterism(new ArrayList<Star>());
        });

    }

    @Test
    void starsWorks() {
        Star rigel = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
        Star betelgeuse = new Star(27989, "Bételgeuse", EquatorialCoordinates.of(1.1, 0.7), -.1f, 1.5f);

        List<Star> stars = new ArrayList<>();
        stars.add(rigel);
        stars.add(betelgeuse);

        Asterism asterism = new Asterism(stars);
        assertEquals(2, asterism.stars().size());
    }

    @Test
    void starsWorksEventhoughImmuabilityIssues() {
        Star rigel = new Star(24436, "Rigel", EquatorialCoordinates.of(1.1, 0.7), -.1f, -.03f);
        Star betelgeuse = new Star(27989, "Bételgeuse", EquatorialCoordinates.of(1.1, 0.7), -.1f, 1.5f);

        List<Star> stars = new ArrayList<>();
        stars.add(rigel);
        stars.add(betelgeuse);

        Asterism asterism = new Asterism(stars);
        stars.remove(rigel);

        assertEquals(1, stars.size());
        assertEquals(2, asterism.stars().size());
    }
}
