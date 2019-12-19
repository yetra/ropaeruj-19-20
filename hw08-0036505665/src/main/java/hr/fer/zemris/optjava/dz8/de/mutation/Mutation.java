package hr.fer.zemris.optjava.dz8.de.mutation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An interface to be implemented by different kinds of DE mutation.
 *
 * @author Bruna Dujmović
 *
 */
public interface Mutation {

    /**
     * Constructs and returns the mutant vector.
     *
     * @param vectors the vector population
     * @param best the best vector found so far by the algorithm
     * @param currentIndex the index of the current target vector
     * @return the mutant vector
     */
    double[] of(double[][] vectors, double[] best, int currentIndex);

    /**
     * Returns {@code indexCount} unique random indexes in range [0, {@code populationSize} - 1].
     * This method makes sure that the indexes are different from the given {@code currentIndex}.
     *
     * @param populationSize the size of the vector population
     * @param currentIndex the index of the current target vector
     * @param indexCount the number of unique random indexes to generate
     * @return {@code indexCount} unique random indexes in range [0, {@code populationSize} - 1]
     */
    default Integer[] getRandomIndexes(int populationSize, int currentIndex, int indexCount) {
        Set<Integer> indexes = new HashSet<>();

        while (indexes.size() < indexCount) {
            int randomIndex = ThreadLocalRandom.current().nextInt(populationSize);
            if (randomIndex == currentIndex) {
                continue;
            }

            indexes.add(randomIndex);
        }

        return indexes.toArray(new Integer[indexCount]);
    }
}
