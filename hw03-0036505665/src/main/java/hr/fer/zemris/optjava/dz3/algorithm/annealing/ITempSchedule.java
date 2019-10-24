package hr.fer.zemris.optjava.dz3.algorithm.annealing;

/**
 * An interface for describing temperature change schedules used in simulated annealing.
 *
 * @author Bruna Dujmović
 *
 */
public interface ITempSchedule {

    /**
     * Returns the next temperature.
     *
     * @return the next temperature
     */
    double getNextTemperature();

    /**
     * Returns the maximum number of iterations for the inner loop.
     *
     * @return the maximum number of iterations for the inner loop
     */
    int getInnerLoopCount();

    /**
     * Returns the maximum number of iterations for the outer loop.
     *
     * @return the maximum number of iterations for the outer loop
     */
    int getOuterLoopCount();
}
