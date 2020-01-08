package hr.fer.zemris.optjava.dz9.selection;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of roulette wheel selection.
 *
 * @author Bruna Dujmović
 *
 */
public class RouletteWheelSelection implements Selection {

    @Override
    public double[][] from(double[][] population, double[] populationFitness, int numberToSelect) {
        double[] cumulativeErrors = new double[population.length];

        cumulativeErrors[0] = populationFitness[0];
        for (int i = 1; i < population.length; i++) {
            cumulativeErrors[i] = cumulativeErrors[i - 1] + populationFitness[i];
        }

        double[][] parents = new double[numberToSelect][];
        for (int i = 0; i < numberToSelect; i++) {
            double randomError = ThreadLocalRandom.current().nextDouble() * cumulativeErrors[population.length - 1];

            int index = Arrays.binarySearch(cumulativeErrors, randomError);
            if (index < 0) {
                index = Math.abs(index + 1);
            }
            if (index == population.length) {
                index--;
            }

            parents[i] = population[index];
        }

        return parents;
    }
}
