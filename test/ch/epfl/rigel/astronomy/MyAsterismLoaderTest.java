package ch.epfl.rigel.astronomy;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class MyAsterismLoaderTest {

    private static final String HYG_CATALOGUE_NAME =
            "/hygdata_v3.csv";
    private static final String ASTERISMS_CATALOGUE =
            "/asterisms.txt";
    private final static double DELTA = 1e-8;

    @Test
    void asterismsIsCorrectlyInstalled() throws IOException {
        try (InputStream asterismStream = getClass()
                .getResourceAsStream(ASTERISMS_CATALOGUE)) {
            assertNotNull(asterismStream);
        }
    }

    @Test
    void asterimDatabaseIsNotEmpty() throws IOException {
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue.Builder builder = new StarCatalogue.Builder();
            builder.loadFrom(hygStream, HygDatabaseLoader.INSTANCE);

            try (InputStream asterismStream = getClass().getResourceAsStream(ASTERISMS_CATALOGUE)) {
                builder.loadFrom(asterismStream, AsterismLoader.INSTANCE);
                StarCatalogue catalogue = builder.build();
                assertEquals(true, catalogue.asterisms().size() > 0);
            }
        }
    }

    @Test
    void asterimDatabaseContainsOrion() throws IOException {
        try (InputStream hygStream = getClass().getResourceAsStream(HYG_CATALOGUE_NAME)) {
            StarCatalogue.Builder builder = new StarCatalogue.Builder();
            builder.loadFrom(hygStream, HygDatabaseLoader.INSTANCE);

            try (InputStream asterismStream = getClass().getResourceAsStream(ASTERISMS_CATALOGUE)) {
                builder.loadFrom(asterismStream, AsterismLoader.INSTANCE);
                StarCatalogue catalogue = builder.build();
                Asterism orion = null;

                // 1. On construit l'astérisme
                List<Integer> orionIndexStars = new ArrayList<>();
                List<Integer> orionHipparcosStarsId = List.of(24436,27366,26727,27989,28614,29426,28716);
                for (int i = 0; i < catalogue.stars().size(); i++) {
                    if (orionHipparcosStarsId.contains(catalogue.stars().get(i).hipparcosId()))
                        orionIndexStars.add(i);
                }

                for (Asterism a: catalogue.asterisms()) {
                    if (catalogue.asterismIndices(a).containsAll(orionIndexStars)
                            && orionIndexStars.containsAll(catalogue.asterismIndices((a)))) {
                        orion = a;
                    }
                }
                assertNotNull(orion);
            }
        }
    }
}
