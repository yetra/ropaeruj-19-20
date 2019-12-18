package hr.fer.zemris.optjava.dz8.de.mutation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public interface Mutation {

    double[] of(double[][] vectors, double[] best, int currentIndex);

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
