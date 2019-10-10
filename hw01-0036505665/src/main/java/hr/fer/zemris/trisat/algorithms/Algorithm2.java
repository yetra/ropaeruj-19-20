package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;
import hr.fer.zemris.trisat.BitVectorNGenerator;
import hr.fer.zemris.trisat.SATFormula;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class represents a hill climbing algorithm for solving the 3-SAT problem.
 *
 * Starting from an arbitrary solution, the algorithm computes the fitness of all
 * solutions differing in only one bit from the current one, and selects the best
 * such solution for the next iteration.
 *
 * @author Bruna Dujmović
 *
 */
public class Algorithm2 extends Algorithm {

    /**
     * The maximum number of iterations.
     */
    static final int MAX_TRIES = 100_000;

    /**
     * Constructs an {@link Algorithm2} object for the given formula.
     *
     * @param formula the formula whose solution should be found
     */
    public Algorithm2(SATFormula formula) {
        this.formula = formula;
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

            BitVectorNGenerator generator = new BitVectorNGenerator(assignment);
            BitVector[] neighborhood = generator.createNeighborhood();
            BitVector[] bestNeighbors = bestOf(neighborhood);

            if (fit(bestNeighbors[0]) < fit(assignment)) {
                System.out.println("Upali smo u lokalni minimum...");
                break;
            }

            int index = rand.nextInt(bestNeighbors.length);
            assignment = bestNeighbors[index];

            t++;
        }

        return solution;
    }

    /**
     * Calculates the fitness of a given solution based on the number of clauses it satisfies.
     *
     * @param assignment the solution whose fitness should be calculated
     * @return the fitness of the given solution
     */
    int fit(BitVector assignment) {
        int numberOfClauses = formula.getNumberOfClauses();
        int fitness = 0;

        for (int i = 0; i < numberOfClauses; i++) {
            if (formula.getClause(i).isSatisfied(assignment)) {
                fitness++;
            }
        }

        return fitness;
    }

    /**
     * Returns an array of solutions with the highest fitness from a given neighborhood.
     *
     * @param neighborhood the neighborhood of solutions
     * @return an array of solutions with the highest fitness from a given neighborhood
     */
    BitVector[] bestOf(BitVector[] neighborhood) {
        List<BitVector> bestNeighbors = new ArrayList<>();
        int bestFitness = 0;

        for (BitVector neighbor : neighborhood) {
            int fitness = fit(neighbor);

            if (fitness > bestFitness) {
                bestFitness = fitness;
            }
        }

        for (BitVector neighbor : neighborhood) {
            if (fit(neighbor) == bestFitness) {
                bestNeighbors.add(neighbor);
            }
        }

        return (BitVector[]) bestNeighbors.toArray();
    }
}
