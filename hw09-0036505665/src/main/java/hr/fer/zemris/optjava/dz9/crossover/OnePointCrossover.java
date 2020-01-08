package hr.fer.zemris.optjava.dz9.crossover;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of one-point crossover which results in two child solutions.
 *
 * A point on both parent solutions (i.e. an {@code index} in the array) is picked randomly.
 * Child solutions will each have {@code index} bits from one parent and {@code solution.length - index}
 * bits from the other.
 *
 * The crossover is performed with a given probability - if no crossover occurs, the parents will be returned.
 *
 * @author Bruna Dujmović
 *
 */
public class OnePointCrossover implements Crossover {

    /**
     * The crossover probability.
     */
    private double probability;

    /**
     * Constructs a {@link OnePointCrossover} instance.
     *
     * @param probability the crossover probability (decimal number between 0 and 1)
     */
    public OnePointCrossover(double probability) {
        this.probability = probability;
    }

    @Override
    public double[][] of(double[] firstParent, double[] secondParent) {
        if (firstParent.length != secondParent.length) {
            throw new IllegalArgumentException("Parents must be of the same size!");
        }

        if (probability > ThreadLocalRandom.current().nextDouble()) {
            return new double[][] {firstParent, secondParent};
        }

        double[] firstChild = firstParent.clone();
        double[] secondChild = secondParent.clone();
        int point = ThreadLocalRandom.current().nextInt(1, firstParent.length);

        for (int i = point; i < firstParent.length; i++) {
            firstChild[i] = secondParent[i];
            secondChild[i] = firstParent[i];
        }

        return new double[][] {firstChild, secondChild};
    }
}
