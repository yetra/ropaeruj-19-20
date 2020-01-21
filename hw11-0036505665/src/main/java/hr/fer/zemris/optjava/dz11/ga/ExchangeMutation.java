package hr.fer.zemris.optjava.dz11.ga;

import hr.fer.zemris.generic.ga.GASolution;
import hr.fer.zemris.generic.ga.IMutation;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link IMutation} implementation that swaps two values in a given {@link GASolution}.
 *
 * @author Bruna Dujmović
 */
public class ExchangeMutation implements IMutation<int[]> {

    @Override
    public GASolution<int[]> of(GASolution<int[]> solution) {
        GASolution<int[]> copy = solution.duplicate();
        mutate(copy);

        return copy;
    }

    @Override
    public void mutate(GASolution<int[]> solution) {
        int firstIndex = ThreadLocalRandom.current().nextInt(solution.data.length);
        int secondIndex = ThreadLocalRandom.current().nextInt(solution.data.length);

        int firstValue = solution.data[firstIndex];
        solution.data[firstIndex] = solution.data[secondIndex];
        solution.data[secondIndex] = firstValue;
    }
}
