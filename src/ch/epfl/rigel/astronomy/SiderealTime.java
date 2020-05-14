package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.GeographicCoordinates;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.time.*;
import java.time.temporal.ChronoUnit;

/**
 * Cette classe propose des méthodes statiques permettant de calculer le temps sidéral.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class SiderealTime {

    private final static double NB_MILLIS_PER_HOUR = 3600.*1000.;
    private final static double S1_CONSTANT = 1.002737909;
    private final static Polynomial S0_POLYNOM = Polynomial.of(0.000025862, 2400.051336, 6.697374558);

    private SiderealTime() {} // Constructeur privée pour rendre la classe non instantiable

    /**
     * Méthode statique qui retourne le temps sidéral de Greenwich, en radians
     * et compris dans l'intervalle [0, τ[, pour le couple date/heure when.
     *
     * @param when le couple date/heure à convertir
     * @return le temps sidéral de Greenwich en radians et compris dans l'intervalle [0, τ[
     * pour le couple date/heure donné
     */
    public static double greenwich(ZonedDateTime when) {
        ZonedDateTime whenInUTC = when.withZoneSameInstant(ZoneOffset.UTC);
        ZonedDateTime whenInUTCTruncatedToDays = whenInUTC.truncatedTo(ChronoUnit.DAYS);

        // Calculs des paramètres définis dans le paragraphe 2.5 de l'énoncé de l'étape 3
        double T = Epoch.J2000.julianCenturiesUntil(whenInUTCTruncatedToDays);
        double t = whenInUTCTruncatedToDays.until(whenInUTC, ChronoUnit.MILLIS) / NB_MILLIS_PER_HOUR;
        double S0 = S0_POLYNOM.at(T);
        double S1 = S1_CONSTANT*t;

        double SgInHoursAndNotNormalised = S0 + S1;

        return Angle.normalizePositive(Angle.ofHr(SgInHoursAndNotNormalised));
    }

    /**
     * Méthode statique qui retourne le temps sidéral local, en radians et compris
     * dans l'intervalle [0, τ[, pour le couple date/heure when et la position where.
     *
     * @param when le couple date/heure à convertir
     * @param where la position actuelle
     * @return le temps sidéral local, en radians et compris dans l'intervalle [0, τ[
     */
    public static double local(ZonedDateTime when, GeographicCoordinates where) {
        return Angle.normalizePositive(greenwich(when) + where.lon());
    }
}
