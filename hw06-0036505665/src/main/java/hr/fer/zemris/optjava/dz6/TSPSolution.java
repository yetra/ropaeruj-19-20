package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Models an ant for solving the TSP.
 *
 * @author Bruna Dujmović
 *
 */
public class TSPSolution {

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
     * Constructs an {@link TSPSolution}.
     *
     * @param cityCount the number of TSP cities
     */
    public TSPSolution(int cityCount) {
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
}
