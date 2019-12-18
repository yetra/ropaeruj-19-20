package hr.fer.zemris.optjava.dz8.de.crossover;

import java.util.concurrent.ThreadLocalRandom;

public class BinCrossover implements Crossover {

    private double crossoverProbability;

    public BinCrossover(double crossoverProbability) {
        this.crossoverProbability = crossoverProbability;
    }

    @Override
    public double[] of(double[] targetVector, double[] mutantVector) {
        double jRandom = ThreadLocalRandom.current().nextDouble(targetVector.length);
        double[] trialVector = new double[targetVector.length];

        for (int j = 0; j < trialVector.length; j++) {
            double randomNumber = ThreadLocalRandom.current().nextDouble();

            if (randomNumber <= crossoverProbability || j == jRandom) {
                trialVector[j] = mutantVector[j];
            } else {
                trialVector[j] = targetVector[j];
            }
        }

        return trialVector;
    }
}
