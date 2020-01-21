package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.ICrossover;
import hr.fer.zemris.optjava.rng.IRNG;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of one-point crossover which results in two child solutions.
 *
 * A point on both parent solutions (i.e. an {@code index} in their values array) is picked randomly.
 * Child solutions will each have {@code index} bits from one parent and {@code values.length - index}
 * bits from the other.
 *
 * The crossover is performed with a given probability - if no crossover occurs, the parents will be returned.
 *
 * @author Bruna Dujmović
 */
public class OnePointCrossover implements ICrossover<int[]> {

    /**
     * The default crossover probability.
     */
    private static final double DEFAULT_PROBABILITY = 0.9;

    /**
     * The crossover probability.
     */
    private double probability;

    /**
     * The random number generator to use.
     */
    private IRNG rng;

    /**
     * Constructs a {@link OnePointCrossover} of the default probability.
     */
    public OnePointCrossover() {
        this.probability = DEFAULT_PROBABILITY;
    }

    /**
     * Constructs a {@link OnePointCrossover} of the given probability.
     *
     * @param probability the crossover probability (decimal number between 0 and 1)
     */
    public OnePointCrossover(double probability) {
        this.probability = probability;
    }

    @Override
    public Collection<GASolution<int[]>> of(GASolution<int[]> firstParent, GASolution<int[]> secondParent) {
        if (firstParent.data.length != secondParent.data.length) {
            throw new IllegalArgumentException("Parent solutions are not of the same size!");
        }

        if (probability > ThreadLocalRandom.current().nextDouble()) {
            return Arrays.asList(firstParent, secondParent);
        }

        GASolution<int[]> firstChild = firstParent.duplicate();
        GASolution<int[]> secondChild = secondParent.duplicate();
        int point = rng.nextInt(0, firstParent.data.length);

        for (int i = point; i < firstParent.data.length; i++) {
            firstChild.data[i] = secondParent.data[i];
            secondChild.data[i] = firstParent.data[i];
        }

        return Arrays.asList(firstChild, secondChild);
    }
}
