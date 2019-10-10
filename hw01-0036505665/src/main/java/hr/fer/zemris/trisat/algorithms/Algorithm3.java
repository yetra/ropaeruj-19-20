package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;
import hr.fer.zemris.trisat.SATFormulaStats;

import java.util.*;

/**
 * This class is a modification of {@link Algorithm2}.
 *
 * Statistical data is collected in each iteration and used to compute the solutions' fitness.
 *
 * @author Bruna Dujmović
 *
 */
public class Algorithm3 extends Algorithm {

    /**
     * The precision used for {@code double} comparison.
     */
    private static final double DOUBLE_PRECISION = 1E-10;

    /**
     * The maximum number of iterations.
     */
    private static final int MAX_TRIES = 100_000;

    /**
     * An object storing statistical data for the formula.
     */
    private SATFormulaStats stats;

    /**
     * Constructs an {@link Algorithm3} object for the given formula.
     *
     * @param formula the formula whose solution should be found
     */
    public Algorithm3(SATFormula formula) {
        this.formula = formula;
        this.stats = new SATFormulaStats(formula);
    }

    /**
     * Executes the algorithm on the given formula.
     *
     * @return a solution if found, else {@code null}
     */
    public BitVector execute() {
        Random rand = new Random();
        BitVector assignment = new BitVector(rand, formula.getNumberOfVariables());

        int t = 0;
        while (t < MAX_TRIES) {

            if (formula.isSatisfied(assignment)) {
                solution = assignment;
                break;
            }

            stats.setAssignment(assignment, true);

            BitVectorNGenerator generator = new BitVectorNGenerator(assignment);
            BitVector[] neighborhood = generator.createNeighborhood();
            BitVector[] bestNeighbors = bestOf(neighborhood);

            int index = rand.nextInt(bestNeighbors.length);
            assignment = bestNeighbors[index];

            t++;
        }

        return solution;
    }

    /**
     * Calculates the fitness of a given solution based on the number of clauses it satisfies
     * and the computed percentage bonus.
     *
     * @param assignment the solution whose fitness should be calculated
     * @return the fitness of the given solution
     */
    private double fit(BitVector assignment) {
        stats.setAssignment(assignment, false);

        int numberOfClauses = formula.getNumberOfClauses();
        int fitness = 0;

        for (int i = 0; i < numberOfClauses; i++) {
            if (formula.getClause(i).isSatisfied(assignment)) {
                fitness++;
            }
        }

        return fitness + stats.getPercentageBonus();
    }

    /**
     * Returns an array of {@link SATFormulaStats#NUMBER_OF_BEST} solutions with
     * the highest fitness from a given neighborhood.
     *
     * @param neighborhood the neighborhood of solutions
     * @return an array of solutions with the highest fitness from a given neighborhood
     */
    private BitVector[] bestOf(BitVector[] neighborhood) {
        List<BitVector> neighbors = Arrays.asList(neighborhood);
        neighbors.sort(Comparator.comparingDouble(this::fit).reversed());

        List<BitVector> bestNeighbors = neighbors.subList(0, SATFormulaStats.NUMBER_OF_BEST);

        return (BitVector[]) bestNeighbors.toArray();
    }
}
