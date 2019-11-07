package hr.fer.zemris.optjava.dz5.ga.mutation;

import hr.fer.zemris.optjava.dz5.ga.chromosome.Chromosome;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link IMutation} implementation that swaps two values in a given {@link Chromosome}.
 *
 * @param <T> the type of the chromosome's values
 * @author Bruna Dujmović
 */
public class ExchangeMutation<T> implements IMutation<T> {

    @Override
    public Chromosome<T> of(Chromosome<T> chromosome) {
        Chromosome<T> copy = chromosome.copy();
        mutate(copy);

        return copy;
    }

    @Override
    public void mutate(Chromosome<T> chromosome) {
        int firstIndex = ThreadLocalRandom.current().nextInt(chromosome.values.length);
        int secondIndex = ThreadLocalRandom.current().nextInt(chromosome.values.length);

        T firstValue = chromosome.values[firstIndex];
        chromosome.values[firstIndex] = chromosome.values[secondIndex];
        chromosome.values[secondIndex] = firstValue;
    }
}
