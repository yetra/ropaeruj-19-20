package hr.fer.zemris.optjava.dz8.de.mutation;

public class RandMutation extends Mutation {

    private static final int RANDOM_INDEX_COUNT = 3;

    public RandMutation(int dimensions, int populationSize, double differentialWeight) {
        super(dimensions, populationSize, differentialWeight);
    }

    @Override
    public double[] of(double[][] vectors, double[] best, int currentIndex) {
        Integer[] indexes = getRandomIndexes(currentIndex, RANDOM_INDEX_COUNT);
        double[] mutantVector = new double[dimensions];

        for (int j = 0; j < dimensions; j++) {
            mutantVector[j] = vectors[indexes[0]][j]
                    + differentialWeight * (vectors[indexes[1]][j] - vectors[indexes[2]][j]);
        }

        return mutantVector;
    }
}
