package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyStarCatalogueTest {

    @Test
    void constructorWorksWithSingleAsterism() {
        List<Star> stars1 = new ArrayList<>();
        stars1.add(new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars1.add(new Star(2, "Etoile2", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars1.add(new Star(3, "Etoile3", EquatorialCoordinates.of(0, 0), -1f, 1f));

        Asterism asterism1 = new Asterism(stars1);

        StarCatalogue catalogue = new StarCatalogue(stars1, List.of(asterism1));
    }

    @Test
    void constructorWorksWithMultipleAsterisms() {
        List<Star> stars = new ArrayList<>();
        List<Star> stars1 = new ArrayList<>();
        stars1.add(new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars1.add(new Star(2, "Etoile2", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars1.add(new Star(3, "Etoile3", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars.add(new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars.add(new Star(2, "Etoile2", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars.add(new Star(3, "Etoile3", EquatorialCoordinates.of(0, 0), -1f, 1f));

        List<Star> stars2 = new ArrayList<>();
        stars2.add(new Star(11, "Etoile11", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars2.add(new Star(12, "Etoile12", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars.add(new Star(11, "Etoile11", EquatorialCoordinates.of(0, 0), -1f, 1f));
        stars.add(new Star(12, "Etoile12", EquatorialCoordinates.of(0, 0), -1f, 1f));

        Asterism asterism1 = new Asterism(stars1);
        Asterism asterism2 = new Asterism(stars2);

        StarCatalogue catalogue = new StarCatalogue(stars, List.of(asterism1, asterism2));
    }

    @Test
    void constructorThrowsExceptionWithStarsNotEmptyAndAsterismEmpty() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<Star> stars1 = new ArrayList<>();
            stars1.add(new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f));
            stars1.add(new Star(2, "Etoile2", EquatorialCoordinates.of(0, 0), -1f, 1f));
            stars1.add(new Star(3, "Etoile3", EquatorialCoordinates.of(0, 0), -1f, 1f));

            Asterism asterism1 = new Asterism(new ArrayList<Star>());

            StarCatalogue catalogue = new StarCatalogue(stars1, List.of(asterism1));
        });
    }

    @Test
    void constructorThrowsExceptionWithUnvalidParameters() {
        assertThrows(IllegalArgumentException.class, () -> {
            List<Star> stars = new ArrayList<>();
            List<Star> stars1 = new ArrayList<>();
            stars1.add(new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f));
            stars1.add(new Star(2, "Etoile2", EquatorialCoordinates.of(0, 0), -1f, 1f));
            stars1.add(new Star(3, "Etoile3", EquatorialCoordinates.of(0, 0), -1f, 1f));

            stars.add(new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f));
            stars.add(new Star(2, "Etoile2", EquatorialCoordinates.of(0, 0), -1f, 1f));

            Asterism asterism1 = new Asterism(stars1);

            StarCatalogue catalogue = new StarCatalogue(stars, List.of(asterism1));
        });
    }


}
