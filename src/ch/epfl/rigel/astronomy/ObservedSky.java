package ch.epfl.rigel.astronomy;

import ch.epfl.rigel.coordinates.*;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Cette classe représente un ensemble d'objets célestes projetés dans le plan
 * par une projection stéréographique. En d'autres termes, elle représente une
 * sorte de photographie du ciel à un instant et un endroit d'observation donnés.
 *
 * @author Thomas Bonnardel (319827)
 */
public final class ObservedSky {

    private final Sun sun;
    private final CartesianCoordinates sunPosition;
    private final Moon moon;
    private final CartesianCoordinates moonPosition;
    private final List<Planet> planets;
    private final double[] planetPositions;
    private final List<Star> stars;
    private final double[] starPositions;

    private final StarCatalogue catalogue;
    private final Map<CelestialObject, CartesianCoordinates> celestialObjectsMap;


    /**
     * Constructeur prenant en argument tous les paramètres décrivant l'observation,
     * et calcule la position projetée dans le plan de tous les objets célestes à
     * l'exception de la Terre.
     *
     * @param observationMoment l'intant d'observation
     * @param observationPosition la position d'observation
     * @param stereographicProjection la projection stéréographique à utiliser
     * @param catalogue le catalogue contenant les étoiles et astérismes
     */
    public ObservedSky(ZonedDateTime observationMoment, GeographicCoordinates observationPosition,
                       StereographicProjection stereographicProjection, StarCatalogue catalogue) {
        // 1. Variables communes
        double daysSinceJ2010 = Epoch.J2010.daysUntil(observationMoment);
        EclipticToEquatorialConversion eclToEqu = new EclipticToEquatorialConversion(observationMoment);
        EquatorialToHorizontalConversion equToHrz = new EquatorialToHorizontalConversion(
                observationMoment,
                observationPosition);

        // 2. Calculs du Soleil et de la Lune
        this.sun = SunModel.SUN.at(daysSinceJ2010, eclToEqu);
        this.sunPosition = stereographicProjection.apply(equToHrz.apply(this.sun.equatorialPos()));
        this.moon = MoonModel.MOON.at(daysSinceJ2010, eclToEqu);
        this.moonPosition = stereographicProjection.apply(equToHrz.apply(this.moon.equatorialPos()));

        // 3. Calculs des planètes extraterrestres
        int nbPlanets = PlanetModel.ALL_EXTRA_TERRESTRIAL.size();
        List<Planet> planets = new ArrayList<>();
        double[] planetPositions = new double[2*nbPlanets];
        for(int i = 0; i < nbPlanets; i++) {
            Planet p = PlanetModel.ALL_EXTRA_TERRESTRIAL.get(i).at(daysSinceJ2010, eclToEqu);
            CartesianCoordinates coordinates = stereographicProjection.apply(equToHrz.apply(p.equatorialPos()));

            planets.add(p);
            planetPositions[2*i] = coordinates.x();
            planetPositions[2*i + 1] = coordinates.y();
        }
        this.planets = planets;
        this.planetPositions = planetPositions;

        // 4. Calculs des étoiles du catalogue
        int nbStars = catalogue.stars().size();
        List<Star> stars = new ArrayList<>();
        double[] starPositions = new double[2*nbStars];
        for (int i = 0; i < nbStars; i++) {
            Star s = catalogue.stars().get(i);
            CartesianCoordinates coordinates = stereographicProjection.apply(equToHrz.apply(s.equatorialPos()));

            stars.add(s);
            starPositions[2*i] = coordinates.x();
            starPositions[2*i + 1] = coordinates.y();
        }
        this.stars = stars;
        this.starPositions = starPositions;

        this.catalogue = catalogue;
        this.celestialObjectsMap = getAllCelestialObjectsWithPosition();
    }

    /**
     * Méthode d'accès qui retourne le Soleil sous la forme d'une instance de Sun.
     *
     * @return le Soleil
     */
    public Sun sun() {
        return this.sun;
    }

    /**
     * Méthode d'accès qui retourne la position du Soleil dans le plan, sous la
     * forme d'une instance de CartesianCoordinates.
     *
     * @return la position du Soleil dans le plan
     */
    public CartesianCoordinates sunPosition() {
        return this.sunPosition;
    }

    /**
     * Méthode d'accès qui retourne la Lune sous la forme d'une instance de Moon.
     *
     * @return la Lune
     */
    public Moon moon() {
        return this.moon;
    }

    /**
     * Méthode d'accès qui retourne la position de la Lune dans le plan, sous la
     * forme d'une instance de CarthesianCoordinates.
     *
     * @return la position de la Lune dans le plan
     */
    public CartesianCoordinates moonPosition() {
        return this.moonPosition;
    }

    /**
     * Méthode d'accès qui retourne la liste des sept planètes extraterrestres du
     * système solaire.
     *
     * @return la liste des sept planètes extraterrestres du système solaire
     */
    public List<Planet> planets() {
        return this.planets;
    }

    /**
     * Méthode d'accès qui retourne les coordonnées cartésiennes des sept planètes
     * extraterrestres du système solaire dans un tableau de double.
     * Le tableau retourné par planetPositions contient à la position 0 la coordonnée x
     * de la première planète retournée par planets, à la position 1 la coordonnée y
     * de cette même planète, et ainsi de suite. Il contient donc en tout 14 valeurs.
     *
     * @return le tableau des positions des planètes
     */
    public double[] planetPositions() {
        return this.planetPositions;
    }

    /**
     * Méthode d'accès qui retourne la liste des étoiles contenues dans le catalogue.
     *
     * @return la liste des étoiles contenues dans le catalogue
     */
    public List<Star> stars() {
        return this.stars;
    }

    /**
     * Méthode d'accès qui retourne les coordonnées cartésiennes de la liste des étoiles
     * contenues dans le catalogue dans un tableau de double.
     * Le tableau retourné par starPositions contient à la position 0 la coordonnée x
     * de la première étoile retournée par stars, à la position 1 la coordonnée y
     * de cette même étoile, et ainsi de suite.
     *
     * @return le tableau des positions des étoiles
     */
    public double[] starPositions() {
        return this.starPositions;
    }

    /**
     * Méthode d'accès qui retourne l'ensemble des astérismes du catalogue.
     *
     * @return l'ensemble des astérismes du catalogue
     */
    public Set<Asterism> asterisms() {
        return catalogue.asterisms();
    }

    /**
     * Méthode d'accès qui retourne la liste des index — dans le catalogue — des étoiles
     * constituant l'astérisme donné, ou lève IllegalArgumentException si l'astérisme
     * donné ne fait pas partie du catalogue.
     *
     * @param asterism l'astérisme dont on souhaite avoir les étoiles
     * @return la liste des index — dans le catalogue — des étoiles constituant
     * l'astérisme donné
     * @throws IllegalArgumentException si l'astérisme donné ne fait pas partie du catalogue
     */
    public List<Integer> asterismIndices(Asterism asterism) {
        return catalogue.asterismIndices(asterism);
    }

    /**
     * Méthode qui, étant donné les coordonnées d'un point du plan et une distance maximale,
     * retourne l'objet céleste le plus proche de ce point, pour peu qu'il se trouve à une
     * distance inférieure à la distance maximale.
     * Si aucun objet n'est trouvé, elle retourne null.
     *
     * @param coordinates les coordonnées du point du plan à étudier
     * @param maxDistance la distance maximale autorisée
     * @return l'objet céleste le plus proche du point spécifié ou null si aucun objet
     * n'a été trouvé à la distance maximale autorisée spécifiée
     */
    public CelestialObject objectClosestTo(CartesianCoordinates coordinates, double maxDistance) {
        CelestialObject closestObject = null;
        double minSquareDistance = maxDistance*maxDistance;
        double x = coordinates.x();
        double y = coordinates.y();

        for (CelestialObject object: celestialObjectsMap.keySet()) {
            CartesianCoordinates objectCoord = celestialObjectsMap.get(object);
            double distance = (objectCoord.x()-x)*(objectCoord.x()-x)
                    + (objectCoord.y()-y)*(objectCoord.y()-y);

            if (distance < minSquareDistance) {
                closestObject = object;
                minSquareDistance = distance;
            }
        }
        return closestObject;
    }


    /**
     * Méthode privée qui retourne la table associative dont les clés
     * sont les objets célestes et les valeurs sont leurs coordonnées.
     *
     * @return la table associative dont les clés sont les objets célestes
     * et les valeurs sont leurs coordonnées
     */
    private Map<CelestialObject, CartesianCoordinates> getAllCelestialObjectsWithPosition() {
        Map<CelestialObject, CartesianCoordinates> map = new HashMap<>();
        map.put(sun, sunPosition);
        map.put(moon, moonPosition);

        for (int i = 0; i < planets.size(); i++) {
            map.put(planets.get(i), CartesianCoordinates.of(
                    planetPositions[2*i], planetPositions[2*i + 1]
            ));
        }
        for (int i = 0; i < stars.size(); i++) {
            map.put(stars.get(i), CartesianCoordinates.of(
                    starPositions[2*i], starPositions[2*i + 1]
            ));
        }
        return map;
    }
}
