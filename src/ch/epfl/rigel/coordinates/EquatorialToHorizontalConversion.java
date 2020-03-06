package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;
import ch.epfl.rigel.math.Angle;

import java.time.ZonedDateTime;
import java.util.function.Function;

import static java.lang.Math.*;

/**
 * Cette classe représente un changement de système de coordonnées depuis
 * les coordonnées équatoriales vers les coordonnées horizontales, à un instant
 * et pour un lieu donné.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class EquatorialToHorizontalConversion implements Function<EquatorialCoordinates, HorizontalCoordinates> {

    private final double localSideralTime;
    private final double cosLat;
    private final double sinLat;

    /**
     * Constructeur qui construit un changement de système de coordonnées entre
     * les coordonnées équatoriales et les coordonnées horizontales pour le couple date/heure when
     * et la localisation d'observation where.
     *
     * @param when le couple date/heure de l'observation
     * @param where les coordonnées du lieu d'observation
     */
    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where) {
        this.localSideralTime =  SiderealTime.local(when, where);
        this.cosLat = cos(where.lat());
        this.sinLat = sin(where.lat());
    }


    /**
     * Méthode qui retourne les coordonnées horizontales correspondant
     * aux coordonnées équatoriales equ.
     *
     * @param equ les coordonnnées équatoriales à convertir
     * @return les coordonnées horizontales correspondant aux coordonnées équatoriales données
     */
    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equ) {
        double H = this.localSideralTime - equ.ra();
        double cosDec = cos(equ.dec());
        double sinDec = sin(equ.dec());

        double sinH = sinDec*sinLat + cosDec*cosLat*cos(H);
        double A = Angle.normalizePositive(atan2(
                -cosDec*cosLat*sin(H),
                sinDec - sinLat*sinH
        ));

        double h = asin(sinH);
        return HorizontalCoordinates.of(A, h);
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

    /**
     * Redéfinition de equal de Object en levant l'exception UnsupportedOperationException.
     *
     * @param obj l'objet à tester
     * @return la valeur de l'égalité des deux objets (jamais retourné)
     * @throws UnsupportedOperationException dans tous les cas
     */
    @Override
    public boolean equals(Object obj) {
        throw new UnsupportedOperationException();
    }
}
