package hr.fer.zemris.optjava.dz9.crossover;

/**
 * An interface to be implemented by different types of GA crossover.
 * Each implementation should provide a method that returns two child
 * solutions obtained by crossing two parent solutions.
 *
 * @author Bruna Dujmović
 *
 */
public interface Crossover {

    /**
     * Performs the crossover on two given parent solutions.
     *
     * @param firstParent the first parent
     * @param secondParent the second parent
     * @return two child solutions obtained by crossing the given parents
     * @throws IllegalArgumentException if the parent solutions are not of the same size
     */
    double[][] of(double[] firstParent, double[] secondParent);
}
