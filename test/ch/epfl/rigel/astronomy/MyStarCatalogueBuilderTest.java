package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.EquatorialCoordinates;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyStarCatalogueBuilderTest {

    private static final String HYG_CATALOGUE_NAME =
            "/hygdata_v3.csv";

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
    void buildWorks() {
        StarCatalogue.Builder b = new StarCatalogue.Builder();
        Star s = new Star(1, "Etoile1", EquatorialCoordinates.of(0, 0), -1f, 1f);
        Asterism a = new Asterism(List.of(s));
        b.addStar(s);
        b.addAsterism(a);

        StarCatalogue catalogue = b.build();
        assertEquals(List.of(s), catalogue.stars());
        assertEquals(Set.of(a), catalogue.asterisms());
    }
}
