package ch.epfl.rigel.gui;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public final class CheckJavaFxTest {

    private final static double DELTA = 1e-9;

    @Test
    void checkIfJavaFXIsCorrectlyInstalled() {
        Color c = Color.RED;
        assertEquals(1.0, c.getRed(), DELTA);
    }
}