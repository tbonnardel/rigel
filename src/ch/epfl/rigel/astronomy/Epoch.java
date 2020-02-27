package ch.epfl.rigel.astronomy;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Cette énumération représente une époque astronomique.
 *
 * @author Thomas Bonnardel (319827)
 */
public enum Epoch {
    /**
     * Epoque standard représentant le 1er janvier 2000 à 12h00 UTC
     */
    J2000(ZonedDateTime.of(
            LocalDate.of(2000, Month.JANUARY, 1),
            LocalTime.of(12, 0),
            ZoneOffset.UTC
    )),

    /**
     * Epoque standard représentant le 31 décembre 2009 à 0h00 UTC
     */
    J2010(ZonedDateTime.of(
            LocalDate.of(2010, Month.JANUARY, 1).minusDays(1),
            LocalTime.of(0,0),
            ZoneOffset.UTC
    ));

    private ZonedDateTime epoch;
    private static double MILLIS_PER_DAYS = ((1000 * 60) * 60) * 24; // Jour solaire ?
    private static double DAYS_PER_JULIAN_CENTURIES = 36525;
    private static double MILLIS_PER_JULIAN_CENTURIES = MILLIS_PER_DAYS * DAYS_PER_JULIAN_CENTURIES;

    private Epoch(ZonedDateTime epoch) {
        this.epoch = epoch;
    }

    /**
     * Méthode qui retourne le nombre de jours (pas forcément entier) entre l'époque
     * à laquelle on l'applique (this) et l'instant when. Les valeurs négatives correspondant
     * à des dates antérieures à l'époque et les valeurs positives à des dates postérieures.
     *
     * @param when l'instant à comparer
     * @return le nombre de jours (pas forcément entier) entre l'époque appliquée et l'instant when
     */
    public double daysUntil(ZonedDateTime when) {
        double millisUntil = this.epoch.until(when, ChronoUnit.MILLIS);
        return millisUntil / MILLIS_PER_DAYS;
    }

    /**
     * Méthode qui retourne le nombre de siècles juliens entre l'époque à laquelle on l'applique
     * et when, de manière similaire à daysUntil. Les valeurs négatives correspondant
     * à des dates antérieures à l'époque et les valeurs positives à des dates postérieures.
     *
     * @param when l'instant à comparer
     * @return le nombre de siècles juliens (pas forcément entier) entre l'époque appliquée et l'instant when
     */
    public double julianCenturiesUntil(ZonedDateTime when) {
        double millisUntil = this.epoch.until(when, ChronoUnit.MILLIS);
        return millisUntil / MILLIS_PER_JULIAN_CENTURIES;
    }
}
