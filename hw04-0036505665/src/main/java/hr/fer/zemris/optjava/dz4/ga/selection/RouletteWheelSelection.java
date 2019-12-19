package hr.fer.zemris.optjava.dz4.ga.selection;

import hr.fer.zemris.optjava.dz4.ga.Chromosome;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of roulette wheel selection.
 *
 * @author Bruna Dujmović
 *
 */
public class RouletteWheelSelection implements ISelection {

    @Override
    public Chromosome from(List<Chromosome> population) {
        double errorSum = 0;
        double minError = population.get(0).error;

        for (Chromosome chromosome : population) {
            errorSum += chromosome.error;

            if (minError > chromosome.error) {
                minError = chromosome.error;
            }
        }
        errorSum = population.size() * minError - errorSum;

        double accumulatedSum = 0;
        double randomNumber = ThreadLocalRandom.current().nextDouble() * errorSum;
        for (Chromosome chromosome : population) {
            accumulatedSum += minError - chromosome.error;

            if (randomNumber < accumulatedSum) {
                return chromosome;
            }
        }

        return population.get(population.size() - 1);
    }
}
