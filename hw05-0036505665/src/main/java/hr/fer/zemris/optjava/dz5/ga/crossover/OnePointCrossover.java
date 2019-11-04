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

    @Override
    public Collection<Chromosome> of(Chromosome firstParent, Chromosome secondParent) {
        return null;
    }
}
