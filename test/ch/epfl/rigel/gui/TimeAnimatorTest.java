package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class TimeAnimatorTest {

    @Test
    void valueOfPropertiesAreNullOrFalseAtBegining() {
        TimeAnimator timeAnimator = new TimeAnimator(new DateTimeBean());

        assertNull(timeAnimator.getAccelerator());
        assertFalse(timeAnimator.getRunning());
    }
}
