package hr.fer.zemris.optjava.dz5.ga.crossover;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of one-point crossover which results in two child chromosomes.
 *
 * A point on both parent chromosomes (i.e. an {@code index} in their values array) is picked randomly.
 * Child chromosomes will each have {@code index} bits from one parent and {@code values.length - index}
 * bits from the other.
 *
 * The crossover is performed with a given probability - if no crossover occurs, the parents will be returned.
 *
 * @author Bruna Dujmović
 */
public class OnePointCrossover implements ICrossover {

    /**
     * The default crossover probability.
     */
    private static final double DEFAULT_PROBABILITY = 0.9;

    /**
     * The crossover probability.
     */
    private double probability;

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
    public Collection<Chromosome> of(Chromosome firstParent, Chromosome secondParent) {
        if (firstParent.values.length != secondParent.values.length) {
            throw new IllegalArgumentException("Parent chromosomes are not of the same size!");
        }

        if (probability > ThreadLocalRandom.current().nextDouble()) {
            return Arrays.asList(firstParent, secondParent);
        }

        Chromosome firstChild = new Chromosome(firstParent.values.length);
        Chromosome secondChild = new Chromosome(firstParent.values.length);
        int point = ThreadLocalRandom.current().nextInt(firstParent.values.length);

        for (int i = 0; i < point; i++) {
            firstChild.values[i] = firstParent.values[i];
            secondChild.values[i] = secondParent.values[i];
        }
        for (int i = point; i < firstParent.values.length; i++) {
            firstChild.values[i] = secondParent.values[i];
            secondChild.values[i] = firstParent.values[i];
        }

        return Arrays.asList(firstChild, secondChild);
    }
}
