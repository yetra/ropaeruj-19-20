package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IMutation;
import hr.fer.zemris.optjava.rng.IRNG;
import hr.fer.zemris.optjava.rng.RNG;

/**
 * An {@link IMutation} implementation that swaps two values in a given {@link GASolution}.
 *
 * @author Bruna Dujmović
 */
public class ExchangeMutation implements IMutation<int[]> {

    /**
     * The random number generator to use.
     */
    private IRNG rng = RNG.getRNG();

    @Override
    public GASolution<int[]> of(GASolution<int[]> solution) {
        GASolution<int[]> copy = solution.duplicate();
        mutate(copy);

        return copy;
    }

    @Override
    public void mutate(GASolution<int[]> solution) {
        int firstIndex = rng.nextInt(0, solution.data.length);
        int secondIndex = rng.nextInt(0, solution.data.length);

        int firstValue = solution.data[firstIndex];
        solution.data[firstIndex] = solution.data[secondIndex];
        solution.data[secondIndex] = firstValue;
    }
}
