package ch.epfl.rigel.coordinates;

import ch.epfl.rigel.astronomy.SiderealTime;

import java.time.ZonedDateTime;
import java.util.function.Function;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static java.lang.Math.atan2;
import static java.lang.Math.asin;

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

    public EquatorialToHorizontalConversion(ZonedDateTime when, GeographicCoordinates where) {
        this.localSideralTime =  SiderealTime.local(when, where);
        this.cosLat = cos(where.lat());
        this.sinLat = sin(where.lat());
    }

    @Override
    public HorizontalCoordinates apply(EquatorialCoordinates equ) {
        double H = this.localSideralTime - equ.ra();
        double cosDec = cos(equ.dec());
        double sinDec = sin(equ.dec());

        double h = asin(sinDec*sinLat + cosDec*cosLat*cos(H));
        double A = atan2(
                -1.*cosDec*cosLat*sin(H),
                sinDec - sinLat*sin(h)
        );
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
