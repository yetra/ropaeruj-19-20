package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.ga.chromosome.Chromosome;
import hr.fer.zemris.optjava.dz5.ga.chromosome.MaxOnesChromsome;
import hr.fer.zemris.optjava.dz5.ga.crossover.ICrossover;
import hr.fer.zemris.optjava.dz5.ga.crossover.OnePointCrossover;
import hr.fer.zemris.optjava.dz5.ga.factor.LinearCompFactor;
import hr.fer.zemris.optjava.dz5.ga.mutation.BitFlipMutation;
import hr.fer.zemris.optjava.dz5.ga.mutation.IMutation;
import hr.fer.zemris.optjava.dz5.ga.selection.ISelection;
import hr.fer.zemris.optjava.dz5.ga.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.ga.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz5.ga.factor.ConstantCompFactor;
import hr.fer.zemris.optjava.dz5.ga.factor.ICompFactor;

import java.util.*;

/**
 * An implementation of Relevant Alleles Preserving Genetic Algorithm (RAPGA)
 * that is used for solving the Max-Ones problem.
 *
 * @author Bruna Dujmović
 *
 */
public class GeneticAlgorithm {

    /**
     * The minimum size of the population.
     */
    private static final int MIN_POP_SIZE = 10;

    /**
     * The maximum size of the population.
     */
    private static final int MAX_POP_SIZE = 100;

    /**
     * The maximum effort for creating a new population.
     */
    private static final int MAX_EFFORT = 100;

    /**
     * The maximum number of iterations before the algorithm terminates.
     */
    private static final int MAX_ITERATIONS = 10_000;

    /**
     * The crossover to use for combining parent chromosomes.
     */
    private ICrossover<Boolean> crossover;

    /**
     * The mutation to use for modifying child chromosomes.
     */
    private IMutation<Boolean> mutation;

    /**
     * The first type of GA selection to use.
     */
    private ISelection<Boolean> firstSelection;

    /**
     * The second type of GA selection to use.
     */
    private ISelection<Boolean> secondSelection;

    /**
     * The comparison factor for determining if a child chromosome is successful.
     */
    private ICompFactor compFactor;

    /**
     * The size of the chromosomes.
     */
    private int chromosomeSize;

    /**
     * Constructs an instance of {@link GeneticAlgorithm}.
     *
     * @param crossover the crossover to use for combining parent chromosomes
     * @param mutation the mutation to use for modifying child chromosomes
     * @param firstSelection the first type of GA selection to use
     * @param secondSelection the second type of GA selection to use
     * @param chromosomeSize the size of the chromosomes
     */
    public GeneticAlgorithm(ICrossover<Boolean> crossover, IMutation<Boolean> mutation,
                            ISelection<Boolean> firstSelection, ISelection<Boolean> secondSelection,
                            ICompFactor compFactor, int chromosomeSize) {
        this.crossover = crossover;
        this.mutation = mutation;
        this.firstSelection = firstSelection;
        this.secondSelection = secondSelection;
        this.compFactor = compFactor;
        this.chromosomeSize = chromosomeSize;
    }

    /**
     * Executes the algorithm.
     */
    public void run() {
        Set<Chromosome<Boolean>> population = new HashSet<>();

        initialize(population);
        evaluate(population);

        int iteration = 0;
        while (iteration < MAX_ITERATIONS && population.size() >= MIN_POP_SIZE) {
            Chromosome<Boolean> best = Collections.max(population);
            System.out.println(best + " - " + best.fitness);

            Set<Chromosome<Boolean>> newPopulation = new HashSet<>();

            int effort = 0;
            while (effort < MAX_EFFORT) {
                Chromosome<Boolean> firstParent = firstSelection.from(population);
                Chromosome<Boolean> secondParent = secondSelection.from(population);

                Collection<Chromosome<Boolean>> children = crossover.of(firstParent, secondParent);

                for (Chromosome<Boolean> child : children) {
                    mutation.mutate(child);
                    child.calculateFitness();

                    if (newPopulation.size() >= MAX_POP_SIZE) {
                        break;
                    }

                    if (isSuccessful(child, firstParent, secondParent)) {
                        newPopulation.add(child);
                    }
                }

                effort += 2;
            }

            population = newPopulation;
            iteration++;
        }

    }

    /**
     * Initializes the given population with random chromosomes.
     *
     * @param population the population to initialize
     */
    private void initialize(Collection<Chromosome<Boolean>> population) {
        while (population.size() < MIN_POP_SIZE) {
            population.add(new MaxOnesChromsome(chromosomeSize, true));
        }
    }

    /**
     * Evaluates the given population.
     *
     * @param population the population to evaluate
     */
    private void evaluate(Collection<Chromosome<Boolean>> population) {
        for (Chromosome<Boolean> chromosome : population) {
            chromosome.calculateFitness();
        }
    }

    /**
     * Returns {@code true} if a given child is successful when compared to its parents.
     *
     * @param child the child to check
     * @param firstParent the first parent of the child
     * @param secondParent the second parent of the child
     * @return {@code true} if a given child is successful
     */
    private boolean isSuccessful(Chromosome<Boolean> child, Chromosome<Boolean> firstParent,
                                 Chromosome<Boolean> secondParent) {
        double worseFitness = Math.min(firstParent.fitness, secondParent.fitness);
        double betterFitness = Math.max(firstParent.fitness, secondParent.fitness);

        double thresholdFitness = worseFitness + compFactor.getFactor() * (betterFitness - worseFitness);

        return child.fitness >= thresholdFitness;
    }

    /**
     * The main method. Obtains the chromosome size from the command-line arguments,
     * instantiates the {@link GeneticAlgorithm} and runs it.
     *
     * @param args the command-line arguments, 1 expected - chromosome size
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Expected 1 argument, got " + args.length);
            System.exit(1);
        }

        int chromosomeSize = Integer.parseInt(args[0]);

        int tournamentSize = 2;
        ISelection<Boolean> firstSelection = new TournamentSelection<>(tournamentSize);
        ISelection<Boolean> secondSelection = new RandomSelection<>();
        // ISelection<Boolean> secondSelection = firstSelection;

        ICompFactor compFactor = new ConstantCompFactor(0.7);
        // ICompFactor compFactor = new LinearCompFactor();

        new GeneticAlgorithm(new OnePointCrossover<>(), new BitFlipMutation(),
                firstSelection, secondSelection, compFactor, chromosomeSize).run();
    }
}
