package hr.fer.zemris.optjava.dz5.ga.crossover;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of order-based crossover that results in two child chromosomes.
 *
 * A number of random positions are selected in the parents' values array. Each child
 * chromosome has the same values as one of the parents, except on the selected positions.
 * The first child will be filled with the second parent's values on those positions,
 * and the second child will be filled with the first parent's values on those positions.
 *
 * @param <T>
 */
public class OrderBasedCrossover<T> implements ICrossover<T> {

    /**
     * The number of random positions to select.
     */
    private static final int NUM_OF_POSITIONS = 3;

    @Override
    public Collection<Chromosome<T>> of(Chromosome<T> firstParent, Chromosome<T> secondParent) {
        int[] positions = new int[NUM_OF_POSITIONS];

        for (int i = 0; i < NUM_OF_POSITIONS; i++) {
            positions[i] = ThreadLocalRandom.current().nextInt(firstParent.values.length);
        }

        Chromosome<T> firstChild = firstParent.copy();
        Chromosome<T> secondChild = secondParent.copy();

        for (int i = 0; i < NUM_OF_POSITIONS; i++) {
            firstChild.values[positions[i]] = secondParent.values[positions[i]];
            secondChild.values[positions[i]] = firstParent.values[positions[i]];
        }

        return Arrays.asList(firstChild, secondChild);
    }
}
