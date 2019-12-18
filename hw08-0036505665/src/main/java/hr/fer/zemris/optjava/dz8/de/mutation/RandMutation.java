package hr.fer.zemris.optjava.dz8.de.mutation;

public class RandMutation implements Mutation {

    private static final int RANDOM_INDEX_COUNT = 3;

    private double differentialWeight;

    public RandMutation(double differentialWeight) {
        this.differentialWeight = differentialWeight;
    }

    @Override
    public double[] of(double[][] vectors, double[] best, int currentIndex) {
        Integer[] indexes = getRandomIndexes(vectors.length, currentIndex, RANDOM_INDEX_COUNT);
        double[] mutantVector = new double[vectors[0].length];

        for (int j = 0; j < mutantVector.length; j++) {
            mutantVector[j] = vectors[indexes[0]][j]
                    + differentialWeight * (vectors[indexes[1]][j] - vectors[indexes[2]][j]);
        }

        return mutantVector;
    }
}
