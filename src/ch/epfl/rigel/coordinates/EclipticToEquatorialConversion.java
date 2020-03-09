package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.Epoch;
import ch.epfl.rigel.math.Angle;
import ch.epfl.rigel.math.Polynomial;

import java.time.ZonedDateTime;
import java.util.function.Function;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.tan;
import static java.lang.Math.atan2;
import static java.lang.Math.asin;

/**
 * Cette classe représente un changement de système de coordonnées depuis
 * les coordonnées écliptiques vers les coordonnées équatoriales, à un instant donné.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class EclipticToEquatorialConversion implements Function<EclipticCoordinates, EquatorialCoordinates> {

    private final double cosOfEclipticObliquity;
    private final double sinOfEclipticObliquity;

    /**
     * Constructeur qui construit un changement de système de coordonnées entre
     * les coordonnées écliptiques et les coordonnées équatoriales pour le couple date/heure when.
     *
     * @param when le couple date/heure de référence pour les conversions
     */
    public EclipticToEquatorialConversion(ZonedDateTime when) {
        double eclipticObliquity = calculateEclipticObliquity(when);
        this.cosOfEclipticObliquity = cos(eclipticObliquity);
        this.sinOfEclipticObliquity = sin(eclipticObliquity);
    }

    /**
     * Méthode privée qui calcule l'obliquité de l'écliptique en fonction du couple date/heure when.
     * @param when le couple date/heure de référence pour les conversions
     * @return l'obliquité de l'écliptique en radians
     */
    private double calculateEclipticObliquity(ZonedDateTime when) {
        // Formule provenant du paragraphe 2.6 de l'énoncé de l'étape 3
        Polynomial P = Polynomial.of(
                Angle.ofDMS(0,0, 0.00181),
                -1.*Angle.ofDMS(0, 0, 0.0006),
                -1.*Angle.ofDMS(0, 0, 46.815),
                Angle.ofDMS(23, 26, 21.45));
        double T = Epoch.J2000.julianCenturiesUntil(when);

        return P.at(T);
    }

    /**
     * Méthode qui retourne les coordonnées équatoriales correspondant
     * aux coordonnées écliptiques ecl.
     *
     * @param ecl les coordonnées écliptiques à convertir
     * @return les coordonnées équatoriales correspondant aux coordonnées écliptiques données
     */
    @Override
    public EquatorialCoordinates apply(EclipticCoordinates ecl) {
        double lon = ecl.lon();
        double lat = ecl.lat();

        // Calculs de termes récurrents dans les formules
        double sinLon = sin(lon);
        double cosLat = cos(lat);

        // Formules données dans le paragraphe 2.6 de l'énoncé de l'étape 3
        double ra = atan2(
                sinLon*cosOfEclipticObliquity - tan(lat)*sinOfEclipticObliquity,
                cos(lon));
        double dec = asin(sin(lat)*cosOfEclipticObliquity + cosLat*sinOfEclipticObliquity*sinLon);
        return EquatorialCoordinates.of(Angle.normalizePositive(ra), dec);
    }

    /**
     * Redéfinition de equal de Object en levant l'exception UnsupportedOperationException.
     *
     * @param o l'objet à tester
     * @return la valeur de l'égalité des deux objets (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public boolean equals(Object o) {
        throw new UnsupportedOperationException();
    }

    /**
     * Redéfinition de hashCode de Object en levant l'exception UnsupportedOperationException.
     *
     * @return le hashCode (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public int hashCode() {
        throw new UnsupportedOperationException();
    }
}
