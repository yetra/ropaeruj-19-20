package hr.fer.zemris.optjava.dz13.gp;

import hr.fer.zemris.optjava.dz13.Tree;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of 7-tournament selection.
 *
 * Seven GP trees will be chosen from the population to form a tournament,
 * and the best one among them will be selected.
 *
 * This implementation does not make sure that all trees in a tournament are unique.
 *
 * @author Bruna Dujmović
 *
 */
class Selection {

    /**
     * The size of the tournament.
     */
    private static final int TOURNAMENT_SIZE = 7;

    /**
     * Selects a tree from the given population using 7-tournament selection.
     *
     * @param population the population to select from
     * @return the selected tree
     */
    static Tree from(Tree[] population) {
        Tree best = null;

        int iteration = 0;
        while (iteration < TOURNAMENT_SIZE) {
            int randomIndex = ThreadLocalRandom.current().nextInt(population.length);

            if (best == null || best.fitness < population[randomIndex].fitness) {
                best = population[randomIndex];
            }

            iteration++;
        }

        return best;
    }
}
