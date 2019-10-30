package hr.fer.zemris.optjava.dz4.ga.crossover;

import hr.fer.zemris.optjava.dz4.ga.Chromosome;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of BLX-alpha crossover.
 *
 * @author Bruna Dujmović
 *
 */
public class BLXAlphaCrossover implements ICrossover {

    /**
     * The default alpha parameter of the crossover.
     */
    private static final double DEFAULT_ALPHA = 0.5;

    /**
     * The alpha parameter of the crossover.
     */
    private double alpha;

    /**
     * Constructs a {@link BLXAlphaCrossover} of the {@link #DEFAULT_ALPHA} parameter.
     */
    public BLXAlphaCrossover() {
        this.alpha = DEFAULT_ALPHA;
    }

    /**
     * Constructs a {@link BLXAlphaCrossover} of the given parameter.
     *
     * @param alpha the alpha parameter of the crossover
     */
    public BLXAlphaCrossover(double alpha) {
        this.alpha = alpha;
    }

    @Override
    public Collection<Chromosome> of(Chromosome firstParent, Chromosome secondParent) {
        if (firstParent.values.length != secondParent.values.length) {
            throw new IllegalArgumentException("Parent chromosomes are not the same size!");
        }

        Chromosome child = new Chromosome(firstParent.values.length);

        for (int i = 0; i < firstParent.values.length; i++) {
            double minValue = Math.min(firstParent.values[i], secondParent.values[i]);
            double maxValue = Math.min(firstParent.values[i], secondParent.values[i]);
            double delta = maxValue - minValue;

            child.values[i] = ThreadLocalRandom.current().nextDouble(minValue - delta * alpha, maxValue + delta * alpha);
        }

        return Collections.singletonList(child);
    }
}
