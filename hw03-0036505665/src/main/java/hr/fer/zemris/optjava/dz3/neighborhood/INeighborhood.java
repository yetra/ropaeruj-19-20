package hr.fer.zemris.optjava.dz3.neighborhood;

/**
 * An interface for obtaining neighboring solutions of a given solution.
 *
 * @param <T> the type of the solution and neighbor
 */
public interface INeighborhood<T> {

    /**
     * Returns a randomly generated neighbor of the given solution.
     *
     * @param solution the solution whose neighbor should be returned
     * @return a randomly generated neighbor of the given solution
     */
    T randomNeighbor(T solution);
}
