package hr.fer.zemris.optjava.dz5.ga.selection;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link ISelection} implementation that selects a random chromosome from the given population.
 *
 * @author Bruna Dujmović
 *
 */
public class RandomSelection<T> implements ISelection<T> {

    @Override
    public Chromosome<T> from(Collection<Chromosome<T>> population) {
        List<Chromosome<T>> populationList = new ArrayList<>(population);
        int randomIndex = ThreadLocalRandom.current().nextInt(population.size());

        return populationList.get(randomIndex);
    }
}
