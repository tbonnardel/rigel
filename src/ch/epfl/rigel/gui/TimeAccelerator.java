package ch.epfl.rigel.gui;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalUnit;

import static java.lang.Math.floor;

/**
 * Cette interface représente un "accélérateur de temps",
 * c'est-à-dire une fonction permettant de calculer le temps
 * simulé en fonction du temps réel.
 *
 * @author Thomas Bonnardel (319827)
 */
@FunctionalInterface
public interface TimeAccelerator {

    /**
     * Méthode qui a pour but de calculer le temps simulé
     * en fonction des paramètres spécifiées.
     *
     * @param T0 le temps simulé initial
     * @param nsFromStart le temps réel écoulé depuis le début
     *                    de l'animation en nanosecondes
     * @return le temps simulé
     */
    abstract ZonedDateTime adjust(ZonedDateTime T0, long nsFromStart);


    /**
     * Méthode statique qui crée un accélérateur continu dont
     * le facteur d'accélération est spécifié en paramètre.
     *
     * @param alpha le facteur d'accélération
     * @return un accélérateur continu avec le facteur
     * d'accélération spécifié
     */
    static TimeAccelerator continuous(int alpha) {
        return (T0, nsFromStart) -> T0.plusNanos(alpha*nsFromStart);
    }

    /**
     * Méthode statique qui crée un accélérateur discret dont
     * la fréquence et le pas sont spécifiés en paramètres.
     *
     * @param f la fréquence d'avancement en Hertz
     * @param step le pas discret du temps simulé
     * @return
     */
    static TimeAccelerator discrete(int f, Duration step) {
        final double NS_PER_SECOND = 1_000_000_000;
        return (T0, nsFromStart) -> T0.plusNanos(
                (long) (floor(f * nsFromStart/NS_PER_SECOND) * step.toNanos())
        );
    }
}
