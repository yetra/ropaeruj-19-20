package hr.fer.zemris.optjava.dz4.part1.selection;

import hr.fer.zemris.optjava.dz4.part1.Chromosome;

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
        double fitnessSum = 0;
        double maxFitness = population.get(0).fitness;

        for (Chromosome chromosome : population) {
            fitnessSum += chromosome.fitness;

            if (maxFitness < chromosome.fitness) {
                maxFitness = chromosome.fitness;
            }
        }
        fitnessSum = population.size() * maxFitness - fitnessSum;

        double accumulatedSum = 0;
        double randomNumber = ThreadLocalRandom.current().nextDouble() * fitnessSum;
        for (Chromosome chromosome : population) {
            accumulatedSum += maxFitness - chromosome.fitness;

            if (randomNumber < accumulatedSum) {
                return chromosome;
            }
        }

        return population.get(population.size() - 1);
    }
}
