package hr.fer.zemris.optjava.dz5.ga.selection;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An {@link ISelection} implementation that selects a random chromosome from the given population.
 *
 * @author Bruna Dujmović
 *
 */
public class RandomSelection implements ISelection {

    @Override
    public Chromosome from(List<Chromosome> population) {
        int randomIndex = ThreadLocalRandom.current().nextInt(population.size());

        return population.get(randomIndex);
    }
}
