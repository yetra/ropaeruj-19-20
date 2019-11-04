package hr.fer.zemris.optjava.dz5.part1;

import hr.fer.zemris.optjava.dz5.ga.crossover.ICrossover;
import hr.fer.zemris.optjava.dz5.ga.mutation.IMutation;
import hr.fer.zemris.optjava.dz5.ga.selection.ISelection;
import hr.fer.zemris.optjava.dz5.ga.selection.RandomSelection;

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
     * The comparison factor for determining if a child chromosome is successful.
     */
    private static final int COMP_FACTOR = 0;

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
                            int chromosomeSize) {
        this.crossover = crossover;
        this.mutation = mutation;
        this.firstSelection = firstSelection;
        this.secondSelection = secondSelection;
        this.chromosomeSize = chromosomeSize;
    }

    /**
     * Executes the algorithm.
     */
    public void run() {

    }

    /**
     * The main method. Obtains the chromosome size from the command-line arguments,
     * instantiates the {@link GeneticAlgorithm} and runs it.
     *
     * @param args the command-line arguments, 1 expected - chromosome size
     */
    public static void main(String[] args) {

    }
}
