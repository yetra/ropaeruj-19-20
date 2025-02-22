package hr.fer.zemris.optjava.dz5.part2;

import hr.fer.zemris.optjava.dz5.ga.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz5.ga.crossover.ICrossover;
import hr.fer.zemris.optjava.dz5.ga.mutation.IMutation;
import hr.fer.zemris.optjava.dz5.ga.selection.ISelection;
import hr.fer.zemris.optjava.dz5.ga.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.ga.factor.ICompFactor;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * An implementation of Offspring Selection to be used in SASEGASA.
 *
 * @author Bruna Dujmović
 *
 */
public class OffspringSelection {

    /**
     * The maximum selection pressure.
     */
    private static final double MAX_SEL_PRESS = 100;

    /**
     * The ratio of successful children that need to exist in each population.
     */
    private static final double SUCC_RATIO = 0.9;

    /**
     * The maximum number of iterations before the algorithm terminates.
     */
    private static final int MAX_ITERATIONS = 1000;

    /**
     * The crossover to use for combining parent chromosomes.
     */
    private ICrossover<Integer> crossover;

    /**
     * The mutation to use for modifying child chromosomes.
     */
    private IMutation<Integer> mutation;

    /**
     * The type of GA selection to use.
     */
    private ISelection<Integer> selection;

    /**
     * An implementation of random selection.
     */
    private ISelection<Integer> randomSelection = new RandomSelection<>();

    /**
     * The comparison factor for determining if a child chromosome is successful.
     */
    private ICompFactor compFactor;

    /**
     * Constructs an instance of {@link hr.fer.zemris.optjava.dz5.part1.GeneticAlgorithm}.
     *
     * @param crossover the crossover to use for combining parent chromosomes
     * @param mutation the mutation to use for modifying child chromosomes
     * @param selection the type of GA selection to use
     */
    public OffspringSelection(ICrossover<Integer> crossover, IMutation<Integer> mutation,
                              ISelection<Integer> selection, ICompFactor compFactor) {
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
        this.compFactor = compFactor;
    }

    /**
     * Executes the algorithm.
     */
    public Set<Chromosome<Integer>> run(Set<Chromosome<Integer>> population) {
        final int popSize = population.size();

        int i = 0;
        double actSelPress = 0.0;
        while (i < MAX_ITERATIONS && actSelPress < MAX_SEL_PRESS) {
            Set<Chromosome<Integer>> newPopulation = new HashSet<>();
            Set<Chromosome<Integer>> pool = new HashSet<>();
            double factor = compFactor.getFactor();

            while (newPopulation.size() < SUCC_RATIO * popSize
                    && (newPopulation.size() + pool.size()) < population.size() * MAX_SEL_PRESS) {
                Chromosome<Integer> firstParent = selection.from(population);
                Chromosome<Integer> secondParent = selection.from(population);

                Collection<Chromosome<Integer>> children = crossover.of(firstParent, secondParent);

                for (Chromosome<Integer> child : children) {
                    mutation.mutate(child);
                    child.calculateFitness();

                    if (newPopulation.size() >= SUCC_RATIO * popSize) {
                        break;
                    }

                    if (isSuccessful(child, firstParent, secondParent, factor)) {
                        newPopulation.add(child);
                    } else {
                        pool.add(child);
                    }
                }
            }

            actSelPress = (double) (newPopulation.size() + pool.size()) / popSize;

            while (newPopulation.size() < popSize) {
                newPopulation.add(randomSelection.from(pool));
            }

            population = newPopulation;
            i++;
        }

        return population;
    }

    /**
     * Returns {@code true} if a given child is successful when compared to its parents.
     *
     * @param child        the child to check
     * @param firstParent  the first parent of the child
     * @param secondParent the second parent of the child
     * @param factor the current value of the {@link #compFactor}
     * @return {@code true} if a given child is successful
     */
    private boolean isSuccessful(Chromosome<Integer> child, Chromosome<Integer> firstParent,
                                 Chromosome<Integer> secondParent, double factor) {
        double worseFitness = Math.min(firstParent.fitness, secondParent.fitness);
        double betterFitness = Math.max(firstParent.fitness, secondParent.fitness);

        double thresholdFitness = worseFitness + factor * (betterFitness - worseFitness);

        return child.fitness >= thresholdFitness;
    }
}
