package ch.epfl.rigel.gui;

import java.time.Duration;

/**
 * Ce type énuméré représente un accélérateur de temps nommé,
 * c'est-à-dire une paire (nom, accélérateur).
 *
 * @author Thomas Bonnardel (319827)
 */
public enum NamedTimeAccelerator {

    /**
     * Accélérateur continu de facteur 1
     */
    TIMES_1("1x", TimeAccelerator.continuous(1)),

    /**
     * Accélérateur continu de facteur 30
     */
    TIMES_30("30x", TimeAccelerator.continuous(30)),

    /**
     * Accélérateur continu de facteur 300
     */
    TIMES_300("300x", TimeAccelerator.continuous(300)),

    /**
     * Accélérateur continu de facteur 3000
     */
    TIMES_3000("3000x", TimeAccelerator.continuous(3000)),

    /**
     * Accélérateur discret de pas de un jour civil
     */
    DAY("jour", TimeAccelerator.discrete(60, Duration.ofDays(1))),

    /**
     * Accélérateur discret de pas de un jour civil
     */
    SIDERAL_DAY("jour sidéral", TimeAccelerator.discrete(
            60, Duration.parse("PT23H56M4S")));

    private final String name;
    private final TimeAccelerator accelerator;

    NamedTimeAccelerator(String name, TimeAccelerator accelerator) {
        this.name = name;
        this.accelerator = accelerator;
    }


    /**
     * Méthode d'accès qui retourne le nom de l'accélérateur.
     *
     * @return le nom de l'accélérateur
     */
    public String getName() {
        return name;
    }

    /**
     * méthode d'accès qui retourne l'accélérateur.
     *
     * @return l'accélérateur
     */
    public TimeAccelerator getAccelerator() {
        return accelerator;
    }

    /**
     * Redéfintion de la méthode toString de object,
     * qui retourne le nom de l'accélérateur.
     *
     * @return le nom de l'accélérateur
     */
    @Override
    public String toString() {
        return name;
    }
}
