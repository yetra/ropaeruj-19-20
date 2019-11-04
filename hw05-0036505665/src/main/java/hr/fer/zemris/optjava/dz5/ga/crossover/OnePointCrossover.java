package hr.fer.zemris.optjava.dz5.ga.crossover;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.Collection;

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
        return null;
    }
}
