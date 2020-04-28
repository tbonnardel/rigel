package ch.epfl.rigel.gui;

import ch.epfl.rigel.astronomy.StarCatalogue;

/**
 * Cette classe est un gestionnaire de canevas sur lequel
 * le ciel est dessiné.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class SkyCanvasManager {

    private StarCatalogue starCatalogue; // TODO: final ?
    private final DateTimeBean dateTimeB;
    private final ViewingParametersBean viewingParametersB;
    private final ObserverLocationBean observerLocationB;


    /**
     * Constructeur de la classe SkyCanvasManager avec ses paramètres
     * d'observation.
     *
     * @param starCatalogue le catalogue d'étoiles et d'astérismes
     * @param dateTimeB le bean contenant l'instant d'observation
     * @param viewingParametersB le bean contenant les paramètres
     *                           déterminant la portion du ciel à afficher
     * @param observerLocationB  le bean contenant la position de
     *                           l'observateur
     */
    public SkyCanvasManager(StarCatalogue starCatalogue, DateTimeBean dateTimeB,
                            ViewingParametersBean viewingParametersB,
                            ObserverLocationBean observerLocationB) {
        this.starCatalogue = starCatalogue;
        this.dateTimeB = dateTimeB;
        this.viewingParametersB = viewingParametersB;
        this.observerLocationB = observerLocationB;

        // 1. crée un certain nombre de propriétés et liens
        // 2. installe un auditeur (listener) pour être informé des mouvements du curseur de la souris, et stocker sa position dans une propriété
        // 3. installe un auditeur pour détecter les clics de la souris sur le canevas et en faire alors le destinataire des événements clavier
        // 4. installe un auditeur pour réagir aux mouvements de la molette de la souris et/ou du trackpad et changer le champ de vue en fonction
        // 5. installe un auditeur pour réagir aux pressions sur les touches du curseur et changer le centre de projection en fonction
        // 6. installe des auditeurs pour être informé des changements des liens et propriétés ayant un impact sur le dessin du ciel, et demander dans ce cas au peintre de le redessiner
    }
}
