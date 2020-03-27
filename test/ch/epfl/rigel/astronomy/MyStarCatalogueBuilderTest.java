package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyStarCatalogueBuilderTest {

    @Test
    void constructorWorks() {
        StarCatalogue.Builder b = new StarCatalogue.Builder();
    }

    @Test
    void starsWorksOnEmptyBuilder() {
        StarCatalogue.Builder b = new StarCatalogue.Builder();
        assertEquals(0, b.stars().size());
    }

    @Test
    void asterismsWorksOnEmptyBuilder() {
        StarCatalogue.Builder b = new StarCatalogue.Builder();
        assertEquals(0, b.asterisms().size());
    }

    @Test
    void addStarWorks() {
        StarCatalogue.Builder b = new StarCatalogue.Builder();
        Star s = new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f);
        b.addStar(s);

        assertEquals(1, b.stars().size());
        assertEquals("Etoile1", b.stars().get(0).name());
    }

    @Test
    void addAsterismWorks() {
        StarCatalogue.Builder b = new StarCatalogue.Builder();
        Star s = new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f);
        Asterism a = new Asterism(List.of(s));
        b.addAsterism(a);

        assertEquals(1, b.asterisms().size());
        assertEquals("Etoile1", b.asterisms().get(0).stars().get(0).name());
    }

    @Test
    void loadFromWorks() {
        throw new IllegalArgumentException();
    }
}
