package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.ga.Chromosome;
import hr.fer.zemris.optjava.dz5.ga.crossover.ICrossover;
import hr.fer.zemris.optjava.dz5.ga.crossover.OnePointCrossover;
import hr.fer.zemris.optjava.dz5.ga.mutation.BitFlipMutation;
import hr.fer.zemris.optjava.dz5.ga.mutation.IMutation;
import hr.fer.zemris.optjava.dz5.ga.selection.ISelection;
import hr.fer.zemris.optjava.dz5.ga.selection.RandomSelection;
import hr.fer.zemris.optjava.dz5.ga.selection.TournamentSelection;
import hr.fer.zemris.optjava.dz5.part1.factor.ConstantCompFactor;
import hr.fer.zemris.optjava.dz5.part1.factor.ICompFactor;

import java.util.*;

/**
 * An implementation of Relevant Alleles Preserving Genetic Algorithm (RAPGA).
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
     * The maximum selection pressure.
     */
    private static final double MAX_SEL_PRESS = 100;

    /**
     * The maximum effort for creating a new population.
     */
    private static final int MAX_EFFORT = 100;

    /**
     * The crossover to use for combining parent chromosomes.
     */
    private ICrossover crossover;

    /**
     * The mutation to use for modifying child chromosomes.
     */
    private IMutation mutation;

    /**
     * The first type of GA selection to use.
     */
    private ISelection firstSelection;

    /**
     * The second type of GA selection to use.
     */
    private ISelection secondSelection;

    /**
     * An implementation of random selection.
     */
    private ISelection randomSelection = new RandomSelection();

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
    public GeneticAlgorithm(ICrossover crossover, IMutation mutation,
                            ISelection firstSelection, ISelection secondSelection,
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
        Set<Chromosome> population = new HashSet<>(MAX_POP_SIZE);

        initialize(population);
        evaluate(population);

        double actSelPress = 0.0;
        while (actSelPress < MAX_SEL_PRESS) {
            Chromosome best = Collections.max(population);
            System.out.println("Best: " + best + "- Fitness: " + best.fitness);

            Set<Chromosome> newPopulation = new HashSet<>();
            Set<Chromosome> pool = new HashSet<>();

            int effort = 0;
            while (effort < MAX_EFFORT) {
                Chromosome firstParent = firstSelection.from(population);
                Chromosome secondParent = secondSelection.from(population);

                Collection<Chromosome> children = crossover.of(firstParent, secondParent);

                for (Chromosome child : children) {
                    mutation.mutate(child);
                    child.calculateFitness();

                    if (newPopulation.size() >= MAX_POP_SIZE) {
                        break;
                    }

                    if (isSuccessful(child, firstParent, secondParent)) {
                        newPopulation.add(child);
                    } else {
                        pool.add(child);
                    }
                }

                effort++;
            }

            while (newPopulation.size() < MIN_POP_SIZE) {
                newPopulation.add(randomSelection.from(pool));
            }

            actSelPress = (newPopulation.size() + pool.size()) / population.size();
            population = newPopulation;
        }

    }

    /**
     * Initializes the given population with random chromosomes.
     *
     * @param population the population to initialize
     */
    private void initialize(Collection<Chromosome> population) {
        for (int i = 0, size = population.size(); i < size; i++) {
            population.add(new Chromosome(chromosomeSize, true));
        }
    }

    /**
     * Evaluates the given population.
     *
     * @param population the population to evaluate
     */
    private void evaluate(Collection<Chromosome> population) {
        for (Chromosome chromosome : population) {
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
    private boolean isSuccessful(Chromosome child, Chromosome firstParent, Chromosome secondParent) {
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
        }

        int chromosomeSize = Integer.parseInt(args[0]);

        int tournamentSize = 2;
        ISelection firstSelection = new TournamentSelection(tournamentSize);
        ISelection secondSelection = new RandomSelection();
        // ISelection secondSelection = firstSelection;

        ICompFactor compFactor = new ConstantCompFactor(0.7);
        // ICompFactor compFactor = new LinearCompFactor();

        new GeneticAlgorithm(new OnePointCrossover(), new BitFlipMutation(),
                firstSelection, secondSelection, compFactor, chromosomeSize);
    }
}
