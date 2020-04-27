package ch.epfl.rigel.gui;

import org.junit.jupiter.api.Test;

import java.time.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Thomas Bonnardel (319827)
 */
public class DateTimeBeanTest {

    @Test
    void nullValuesOnStart() {
        DateTimeBean bean = new DateTimeBean();
        assertNull(bean.getDate());
        assertNull(bean.getTime());
        assertNull(bean.getZone());
    }

    @Test
    void getAndSetDateWorks() {
        DateTimeBean bean = new DateTimeBean();
        LocalDate expected = LocalDate.of(2002, 11, 30);
        bean.setDate(expected);
        assertEquals(expected.toString(), bean.getDate().toString());

        expected = LocalDate.of(1998, 05, 12);
        bean.setDate(expected);
        assertEquals(expected.toString(), bean.getDate().toString());
    }

    @Test
    void getAndSetTimeWorks() {
        DateTimeBean bean = new DateTimeBean();
        LocalTime expected = LocalTime.of(12, 11, 30);
        bean.setTime(expected);
        assertEquals(expected.toString(), bean.getTime().toString());

        expected = LocalTime.of(23, 12, 11);
        bean.setTime(expected);
        assertEquals(expected.toString(), bean.getTime().toString());
    }

    @Test
    void getAndSetZoneWorks() {
        DateTimeBean bean = new DateTimeBean();
        ZoneId expected = ZoneId.of("UTC");
        bean.setZone(expected);
        assertEquals(expected.toString(), bean.getZone().toString());

        expected = ZoneId.of("UTC+2");
        bean.setZone(expected);
        assertEquals(expected.toString(), bean.getZone().toString());
    }

    @Test
    void getZonedDateTimeWorks() {
        LocalDate d = LocalDate.of(2020, Month.APRIL, 27);
        LocalTime t = LocalTime.of(10, 59);
        ZoneId z = ZoneId.of("UTC+1");

        DateTimeBean bean = new DateTimeBean();
        bean.setDate(d);
        bean.setTime(t);
        bean.setZone(z);

        ZonedDateTime zonedDateTime = bean.getZonedDateTime();
        assertEquals(d.toString(), zonedDateTime.toLocalDate().toString());
        assertEquals(t.toString(), zonedDateTime.toLocalTime().toString());
        assertEquals(z.toString(), zonedDateTime.getZone().toString());
    }

    @Test
    void setZonedDateTimeWorks() {
        ZonedDateTime zonedDateTime = ZonedDateTime.of(
                LocalDate.of(2020, Month.APRIL, 27),
                LocalTime.of(11, 02),
                ZoneId.of("UTC+2")
        );

        DateTimeBean bean = new DateTimeBean();
        bean.setZonedDateTime(zonedDateTime);

        assertEquals(LocalDate.of(2020, Month.APRIL, 27).toString(), bean.getDate().toString());
        assertEquals(LocalTime.of(11, 02).toString(), bean.getTime().toString());
        assertEquals(ZoneId.of("UTC+2").toString(), bean.getZone().toString());
    }
}
