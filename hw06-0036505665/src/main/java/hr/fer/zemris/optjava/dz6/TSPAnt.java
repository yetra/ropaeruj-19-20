package hr.fer.zemris.optjava.dz6;

import java.util.*;

/**
 * Models an ant for solving the TSP.
 *
 * @author Bruna Dujmović
 *
 */
public class TSPAnt {

    /**
     * The length of this ant's tour.
     */
    public int tourLength;

    /**
     * The tour of cities.
     */
    public List<City> tour;

    /**
     * A set of visited cities.
     */
    public Set<City> visited;

    /**
     * Constructs a {@link TSPAnt}.
     *
     * @param cityCount the number of TSP cities
     */
    public TSPAnt(int cityCount) {
        tour = new ArrayList<>(cityCount);
        visited = new HashSet<>();
    }

    /**
     * Visits the given city.
     *
     * @param city the city to visit
     */
    public void visit(City city) {
        tour.add(city);
        visited.add(city);
    }

    /**
     * Returns {@code true} if the given city has been visited by this ant.
     *
     * @param city the city to check
     * @return {@code true} if the given city has been visited by this ant
     */
    public boolean visited(City city) {
        return visited.contains(city);
    }

    /**
     * Returns {@code true} if each city in the given list has been visited by this ant.
     *
     * @param cities the list of cities to check
     * @return {@code true} if each city in the given list has been visited by this ant
     */
    public boolean visitedAll(List<City> cities) {
        return visited.containsAll(cities);
    }

    /**
     * Returns the first city in this ant's tour.
     *
     * @return the first city in this ant's tour
     */
    public City getInitialCity() {
        return tour.get(0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TSPAnt tspAnt = (TSPAnt) o;
        return tour.equals(tspAnt.tour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tour);
    }
}
