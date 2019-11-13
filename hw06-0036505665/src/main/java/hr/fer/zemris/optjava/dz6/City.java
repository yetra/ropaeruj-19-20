package hr.fer.zemris.optjava.dz6;

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
}
