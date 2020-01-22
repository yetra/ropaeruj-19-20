package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.ICrossover;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

import java.util.Arrays;
import java.util.Collection;

/**
 * An implementation of uniform GA crossover.
 *
 * @author Bruna Dujmović
 * 
 */
public class UniformCrossover implements ICrossover<int[]> {

    /**
     * The random number generator to use.
     */
    private IRNG rng = RNG.getRNG();

    @Override
    public Collection<GASolution<int[]>> of(GASolution<int[]> firstParent, GASolution<int[]> secondParent) {
        GASolution<int[]> firstChild = firstParent.duplicate();
        GASolution<int[]> secondChild = secondParent.duplicate();

        int n = firstParent.data.length;
        for (int i = 0; i < n; i++) {
            if (rng.nextBoolean()) {
                firstChild.data[i] = secondParent.data[i];
                secondChild.data[i] = firstParent.data[i];
            }
        }

        return Arrays.asList(firstChild, secondChild);
    }
}
