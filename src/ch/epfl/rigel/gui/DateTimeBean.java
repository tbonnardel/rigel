package ch.epfl.rigel.gui;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

/**
 * Cette classe est un bean JavaFX contenant l'instant d'observation,
 * c'est-à-dire le triplet date, heure, fuseau horaire d'observation.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class DateTimeBean {

    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>();
    private ObjectProperty<LocalTime> time = new SimpleObjectProperty<>();
    private ObjectProperty<ZoneId> zone = new SimpleObjectProperty<>();


    /**
     * Méthode qui retourne l'instant d'observation sous la forme d'une valeur
     * de type ZonedDateTime.
     *
     * @return l'instant d'observation sous la forme d'une ZonedDateTime
     */
    public ZonedDateTime getZonedDateTime() {
        return ZonedDateTime.of(
                getDate(),
                getTime(),
                getZone()
        );
    }

    /**
     * Méthode qui modifie l'instant d'observation pour qu'il soit égal
     * à la valeur de type ZonedDateTime qu'on lui passe en argument.
     *
     * @param zonedDateTime le nouvel instant d'observation
     */
    public void setZonedDateTime(ZonedDateTime zonedDateTime) {
        setDate(zonedDateTime.toLocalDate());
        setTime(zonedDateTime.toLocalTime());
        setZone(zonedDateTime.getZone());
    }


    /**
     * Méthode d'accès retournant la propriété date.
     *
     * @return la propriété date
     */
    public ObjectProperty<LocalDate> dateProperty() { return date; }

    /**
     * Méthode d'accès retournant le contenu de la propriété date.
     *
     * @return le contenu de la propriété date
     */
    public LocalDate getDate() { return date.getValue(); }

    /**
     * Méthode qui permet de modifier le contenu de la propriété date.
     *
     * @param date la nouvelle date
     */
    public void setDate(LocalDate date) { this.date.setValue(date); }


    /**
     * Méthode d'accès retournant la propriété time.
     *
     * @return la propriété time
     */
    public ObjectProperty<LocalTime> timeProperty() { return time; }

    /**
     * Méthode d'accès retournant le contenu de la propriété time.
     *
     * @return le contenu de la propriété time
     */
    public LocalTime getTime() { return time.getValue(); }

    /**
     * Méthode qui permet de modifier le contenu de la propriété time.
     *
     * @param time la nouvelle time
     */
    public void setTime(LocalTime time) { this.time.setValue(time); }


    /**
     * Méthode d'accès retournant la propriété zone.
     *
     * @return la propriété zone
     */
    public ObjectProperty<ZoneId> zoneProperty() { return zone; }

    /**
     * Méthode d'accès retournant le contenu de la propriété zone.
     *
     * @return le contenu de la propriété zone
     */
    public ZoneId getZone() { return zone.getValue(); }

    /**
     * Méthode qui permet de modifier le contenu de la propriété zone.
     *
     * @param zone la nouvelle zone
     */
    public void setZone(ZoneId zone) { this.zone.setValue(zone); }
}
