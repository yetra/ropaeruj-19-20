package hr.fer.zemris.optjava.dz6;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Models a TSP city.
 *
 * @author Bruna Dujmović
 *
 */
public class City {
    /**
     * The index of this city.
     */
    public int index;

    /**
     * The x-coordinate of this city.
     */
    public double x;

    /**
     * The y-coordinate of this city.
     */
    public double y;

    /**
     * A list of the closest cities to this city.
     */
    public List<City> closestCities;

    /**
     * Constructs a {@link City} of the given arguments.
     *
     * @param index the index of this city
     * @param x the x-coordinate of this city
     * @param y the y-coordinate of this city
     */
    public City(int index, double x, double y) {
        this.index = index;
        this.x = x;
        this.y = y;
    }

    /**
     * Returns the Euclidean distance from this city to a given other city.
     *
     * @param other the other city
     * @return the Euclidean distance from this city to a given other city
     */
    public double distanceTo(City other) {
        return Math.sqrt((this.x - other.x) * (this.x - other.x) + (this.y - other.y) * (this.y - other.y));
    }

    /**
     * Finds a given number of cities that are the closest to this city.
     *
     * @param closestCount the number of closest cities to find
     * @param cities a list of all cities
     * @param distances the distances from this city to all other cities
     */
    public void findClosest(int closestCount, List<City> cities, double[] distances) {
        List<City> copy = new ArrayList<>(cities);
        copy.sort(Comparator.comparingDouble(c -> distances[c.index]));

        closestCities = copy.subList(1, closestCount + 1);
    }

    @Override
    public String toString() {
        return Integer.toString(index + 1);
    }
}
