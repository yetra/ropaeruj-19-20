package hr.fer.zemris.optjava.dz13.gp;

import hr.fer.zemris.optjava.dz13.Tree;
import hr.fer.zemris.optjava.dz13.TreeGenerator;
import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.ant.Trail;

import java.util.concurrent.ThreadLocalRandom;

public class GP {

    /**
     * The probability of GP reproduction occurring.
     */
    private static final double REPRODUCTION_RATIO = 0.01;

    /**
     * The probability of GP mutation occurring.
     */
    private static final double MUTATION_RATIO = REPRODUCTION_RATIO + 0.14;

    /**
     * The size of the population.
     */
    private int populationSize;

    /**
     * The maximum number of algorithm iterations.
     */
    private int maxIterations;

    /**
     * The minimum fitness value which, if reached, will terminate the algorithm.
     */
    private double minFitness;

    /**
     * The ant trail to use.
     */
    private Trail trail;

    /**
     * The best found GP tree.
     */
    private Tree best;

    /**
     * Constructs a {@link GP} object.
     *
     * @param populationSize the size of the population
     * @param maxIterations the maximum number of algorithm iterations
     * @param minFitness the minimum fitness value which, if reached, will terminate the algorithm
     * @param trail the ant trail to use
     */
    public GP(int populationSize, int maxIterations, double minFitness, Trail trail) {
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.minFitness = minFitness;
        this.trail = trail;
    }

    /**
     * Executes the algorithm.
     *
     * @return the best found GP tree
     */
    public Tree run() {
        Tree[] population = new Tree[populationSize];
        Tree[] nextPopulation = new Tree[populationSize];

        initialize(population);

        int iteration = 0;
        while (iteration < maxIterations && best.fitness < minFitness) {
            System.out.println("Iteration " + iteration + ": " + best.fitness);
            nextPopulation[0] = best; // elitism

            for (int i = 1; i < populationSize;) {
                double randomNumber = ThreadLocalRandom.current().nextDouble();

                if (randomNumber <= REPRODUCTION_RATIO) {
                    nextPopulation[i++] = Selection.from(population).copy();

                } else if (randomNumber <= MUTATION_RATIO) {
                    Tree parent = Selection.from(population);
                    Tree child = Mutation.mutate(parent);

                    evaluate(child, parent);
                    nextPopulation[i++] = child;

                } else { // crossover
                    Tree firstParent = Selection.from(population);
                    Tree secondParent = Selection.from(population);
                    Tree[] children = Crossover.of(firstParent, secondParent);

                    evaluate(children[0], firstParent);
                    nextPopulation[i++] = children[0];

                    if (i == populationSize) {
                        break;
                    }

                    evaluate(children[1], secondParent);
                    nextPopulation[i++] = children[1];
                }
            }

            population = nextPopulation;
            iteration++;
        }

        System.out.println("Iteration " + iteration + ": " + best.fitness);
        return best;
    }

    /**
     * Initializes the given population using ramped-half-and-half tree generation.
     *
     * @param population the population to initialize
     */
    private void initialize(Tree[] population) {
        int added = 0;

        while (added < populationSize) {
            for (int depth = 2; depth <= 6; depth++) {
                population[added] = TreeGenerator.generate(depth, true);
                evaluate(population[added++], null);

                if (added == populationSize) {
                    break;
                }

                population[added] = TreeGenerator.generate(depth, false);
                evaluate(population[added++], null);

                if (added == populationSize) {
                    break;
                }
            }
        }
    }

    /**
     * Evaluates the given tree.
     *
     * @param tree the tree to evaluate
     * @param parent the parent tree to use for punishing plagiarism
     */
    private void evaluate(Tree tree, Tree parent) {
        Ant ant = new Ant(trail.getMatrix());
        tree.calculateFitness(ant, parent);

        if (best == null || tree.fitness > best.fitness) {
            best = tree;
        }
    }
}
