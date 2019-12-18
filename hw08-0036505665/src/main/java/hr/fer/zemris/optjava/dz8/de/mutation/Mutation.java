package hr.fer.zemris.optjava.dz8.de.mutation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Mutation {

    int dimensions;

    int populationSize;

    double differentialWeight;

    public Mutation(int dimensions, int populationSize, double differentialWeight) {
        this.dimensions = dimensions;
        this.populationSize = populationSize;
        this.differentialWeight = differentialWeight;
    }

    public abstract double[] of(double[][] vectors, double[] best, int currentIndex);

    Integer[] getRandomIndexes(int currentIndex, int indexCount) {
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
