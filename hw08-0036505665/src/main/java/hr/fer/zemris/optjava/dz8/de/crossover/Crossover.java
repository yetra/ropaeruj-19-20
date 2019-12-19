package hr.fer.zemris.optjava.dz8.de.crossover;

/**
 * An interface to be implemented by different kinds of DE crossover.
 *
 * @author Bruna Dujmović
 *
 */
public interface Crossover {

    /**
     * Performs the crossover on the given vectors.
     *
     * @param targetVector the target vector
     * @param mutantVector the mutant vector
     * @return the trial vector created out of the given vectors
     */
    double[] of(double[] targetVector, double[] mutantVector);
}
