package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class BlackBodyColorTest {

    @Test
    void colorForTemperatureThrowsExceptionWhenParametersAreOutOfBound() {
        assertThrows(IllegalArgumentException.class, () -> {
           BlackBodyColor.colorForTemperature(999);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            BlackBodyColor.colorForTemperature(40001);
        });

        assertThrows(IllegalArgumentException.class, () -> {
            BlackBodyColor.colorForTemperature(-1);
        });
    }
}
