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

    @Test
    void colorForTemperatureWorksWithMultipleOfTen() {
        assertEquals("0xff3800ff", BlackBodyColor.colorForTemperature(1000).toString());
        assertEquals("0xffece0ff", BlackBodyColor.colorForTemperature(5500).toString());
        assertEquals("0xb1caffff", BlackBodyColor.colorForTemperature(15700).toString());
        assertEquals("0xa4c2ffff", BlackBodyColor.colorForTemperature(23300).toString());
        assertEquals("0x9bbcffff", BlackBodyColor.colorForTemperature(40000).toString());

    }

    @Test
    void colorForTemperatureWorksWithNotMultipleOfTen() {
        assertEquals("0xff3800ff", BlackBodyColor.colorForTemperature(1001).toString());
        assertEquals("0xff3800ff", BlackBodyColor.colorForTemperature(1002).toString());
        assertEquals("0xff3800ff", BlackBodyColor.colorForTemperature(1003).toString());
        assertEquals("0xff3800ff", BlackBodyColor.colorForTemperature(1004).toString());
        assertEquals("0xff3800ff", BlackBodyColor.colorForTemperature(1049).toString());
        assertEquals("0xff4700ff", BlackBodyColor.colorForTemperature(1050).toString());
        assertEquals("0xff4700ff", BlackBodyColor.colorForTemperature(1051).toString());
        assertEquals("0xff4700ff", BlackBodyColor.colorForTemperature(1099).toString());
        assertEquals("0xffece0ff", BlackBodyColor.colorForTemperature(5495).toString());
        assertEquals("0xb1caffff", BlackBodyColor.colorForTemperature(15703).toString());
        assertEquals("0xa4c2ffff", BlackBodyColor.colorForTemperature(23304).toString());
        assertEquals("0x9bbcffff", BlackBodyColor.colorForTemperature(39999).toString());

    }
}
