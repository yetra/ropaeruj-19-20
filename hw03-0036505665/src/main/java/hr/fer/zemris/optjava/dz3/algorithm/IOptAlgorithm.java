package hr.fer.zemris.optjava.dz3.algorithm;

/**
 * An interface for representing optimization algorithms.
 *
 * @param <T> type of the solutions used by the optimization algorithm
 */
public interface IOptAlgorithm<T> {

    /**
     * Runs the optimization algorithm.
     */
    void run();
}
