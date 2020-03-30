package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyHygDatabaseLoaderTest {

    private static final String HYG_CATALOGUE_NAME =
            "/hygdata_v3.csv";
    private final static double DELTA = 1e-8;

    @Test
    void hygDatabaseIsCorrectlyInstalled() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            assertNotNull(hygStream);
        }
    }

    @Test
    void hygDatabaseContainsRigel() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            Star rigel = null;
            for (Star s : catalogue.stars()) {
                if (s.name().equalsIgnoreCase("rigel")) {
                    rigel = s;
                }
            }
            assertNotNull(rigel);
        }
    }

    @Test
    void hyDatabaseContainsInfoOfRigel() throws IOException {
        try (InputStream hygStream = getClass()
                .getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue catalogue = new StarCatalogue.Builder()
                    .loadFrom(hygStream, HygDatabaseLoader.INSTANCE)
                    .build();
            Star rigel = null;
            for (Star s : catalogue.stars()) {
                if (s.name().equalsIgnoreCase("rigel")) {
                    rigel = s;
                }
            }
            assertEquals("Rigel", rigel.name());
            assertEquals(24436, rigel.hipparcosId());
            assertEquals(10516., rigel.colorTemperature());
            assertEquals(0.18, rigel.magnitude(), DELTA);
            assertEquals(1.37243036 ,rigel.equatorialPos().ra(), DELTA);
            assertEquals(-0.14314563, rigel.equatorialPos().dec(), DELTA);
        }
    }
}
