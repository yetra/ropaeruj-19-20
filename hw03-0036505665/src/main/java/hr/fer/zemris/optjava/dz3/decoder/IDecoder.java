package hr.fer.zemris.optjava.dz3.decoder;

/**
 * An interface for decoding optimization algorithm solutions.
 *
 * @param <T> type of the solution to decode
 * @see hr.fer.zemris.optjava.dz3.solution.SingleObjectiveSolution
 * @author Bruna Dujmović
 *
 */
public interface IDecoder<T> {

    /**
     * Decodes the given solution into an array of doubles.
     *
     * @param solution the solution to decode
     * @return a double array containing the decoded solution
     */
    double[] decode(T solution);

    /**
     * Decodes the given solution into the specified array of doubles.
     *
     * @param solution the solution to decode
     * @param values the array into which the solution should be decoded
     */
    void decode(T solution, double[] values);
}
