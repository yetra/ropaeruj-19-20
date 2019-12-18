package hr.fer.zemris.optjava.dz8.de.mutation;

public class TargetToBestMutation extends Mutation {

    private static final int RANDOM_INDEX_COUNT = 2;

    public TargetToBestMutation(int dimensions, int populationSize, double differentialWeight) {
        super(dimensions, populationSize, differentialWeight);
    }

    @Override
    public double[] of(double[][] vectors, double[] best, int currentIndex) {
        Integer[] indexes = getRandomIndexes(currentIndex, RANDOM_INDEX_COUNT);
        double[] mutantVector = new double[dimensions];

        for (int j = 0; j < dimensions; j++) {
            mutantVector[j] = vectors[currentIndex][j] + differentialWeight * (best[j] - vectors[currentIndex][j])
                    + differentialWeight * (vectors[indexes[0]][j] - vectors[indexes[1]][j]);
        }

        return mutantVector;
    }
}
