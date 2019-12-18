package hr.fer.zemris.optjava.dz8.de.mutation;

public class TargetToBestMutation implements Mutation {

    private static final int RANDOM_INDEX_COUNT = 2;

    private double differentialWeight;

    public TargetToBestMutation(double differentialWeight) {
        this.differentialWeight = differentialWeight;
    }

    @Override
    public double[] of(double[][] vectors, double[] best, int currentIndex) {
        Integer[] indexes = getRandomIndexes(vectors.length, currentIndex, RANDOM_INDEX_COUNT);
        double[] mutantVector = new double[vectors[0].length];

        for (int j = 0; j < mutantVector.length; j++) {
            mutantVector[j] = vectors[currentIndex][j] + differentialWeight * (best[j] - vectors[currentIndex][j])
                    + differentialWeight * (vectors[indexes[0]][j] - vectors[indexes[1]][j]);
        }

        return mutantVector;
    }
}
