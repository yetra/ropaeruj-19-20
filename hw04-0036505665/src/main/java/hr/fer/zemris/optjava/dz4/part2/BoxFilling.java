package hr.fer.zemris.optjava.dz4.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An algorithm for solving the Box Filling problem.
 *
 * @author Bruna Dujmović
 *
 */
public class BoxFilling {

    /**
     * The maximum number of mutations.
     */
    private static final int MAX_MUTATIONS = 3;

    /**
     * The probability of a mutation occuring.
     */
    private static final double MUTATION_PROBABILITY = 0.2;

    /**
     * A population of elements to optimize.
     */
    private List<BoxElement> elements;

    /**
     * The size of the population.
     */
    private int populationSize;

    /**
     * The maximum number of iterations before the algorithm terminates.
     */
    private int maxIterations;

    /**
     * The selection tournament size.
     */
    private int selectionTournamentSize;

    /**
     * The replacement tournament size - for finding the worst chromosome.
     */
    private int replacementTournamentSize;

    /**
     * {@code true} if conditional replace should be performed.
     *
     */
    private boolean conditionalReplace;

    /**
     * The acceptable size of the solution.
     */
    private int acceptableSize;

    /**
     * Constructs a {@link BoxFilling} object.
     *
     * @param elements a population of elements to optimize
     * @param populationSize the size of the population
     * @param maxIterations the maximum number of iterations before the algorithm terminates
     * @param selectionTournamentSize the selection tournament size
     * @param replacementTournamentSize the replacement tournament size - for finding the worst chromosome
     * @param conditionalReplace {@code true} if conditional replace should be performed
     * @param acceptableSize the acceptable size of the solution
     */
    public BoxFilling(List<BoxElement> elements, int populationSize, int maxIterations, int selectionTournamentSize,
                      int replacementTournamentSize, boolean conditionalReplace, int acceptableSize) {
        this.elements = elements;
        this.populationSize = populationSize;
        this.maxIterations = maxIterations;
        this.selectionTournamentSize = selectionTournamentSize;
        this.replacementTournamentSize = replacementTournamentSize;
        this.conditionalReplace = conditionalReplace;
        this.acceptableSize = acceptableSize;
    }

    /**
     * Executes the algorithm.
     */
    public void run() {
        List<Chromosome> population = new ArrayList<>(populationSize);

        initialize(population);
        evaluate(population);

        Chromosome best = null;

        for (int iteration = 0; iteration < maxIterations; iteration++) {
            Chromosome populationBest = Collections.min(population, Chromosome::compareTo);
            if (best == null || populationBest.fitness > best.fitness) {
                System.out.println(populationBest.toString() + "Fitness: " + populationBest.fitness);
                best = populationBest;
            }

            if (best.fitness <= acceptableSize) {
                return;
            }

            Chromosome firstParent = selectFrom(population, selectionTournamentSize, true);
            Chromosome secondParent = selectFrom(population, selectionTournamentSize, true);

            Chromosome child = crossoverOf(firstParent, secondParent);
            mutate(child);

            Chromosome worst = selectFrom(population, replacementTournamentSize, false);
            if (!conditionalReplace || child.fitness >= worst.fitness) {
                population.add(population.indexOf(worst), child);
            }

            evaluate(population);
        }
    }

    /**
     * Initializes the given population with random chromosomes.
     *
     * @param population the population to initialize
     */
    private void initialize(Collection<Chromosome> population) {
        for (int i = 0; i < populationSize; i++) {
            population.add(new Chromosome(elements));
        }
    }

    /**
     * Evaluates the given population.
     *
     * @param population the population to evaluate
     */
    private void evaluate(Collection<Chromosome> population) {
        for (Chromosome chromosome : population) {
            chromosome.fitness = chromosome.columns.size();
        }
    }

    /**
     * Selects a chromosome from the given population using tournament selection.
     *
     * @param population the population to select from
     * @param tournamentSize the size of the tournament
     * @param findBest {@code true} if the best chromosome should be selected from the tournament,
     *                 {@code false} if the worst chromosome should be selected from the tournament
     * @return the selected chromosome
     */
    private Chromosome selectFrom(List<Chromosome> population, int tournamentSize, boolean findBest) {
        List<Chromosome> tournament = new ArrayList<>();

        while (tournament.size() < tournamentSize) {
            int randomIndex = ThreadLocalRandom.current().nextInt(population.size());
            tournament.add(population.get(randomIndex));
        }

        if (findBest) {
            return Collections.min(tournament, Chromosome::compareTo);
        } else {
            return Collections.max(tournament, Chromosome::compareTo);
        }
    }

    /**
     * Performs a crossover of the given parent chromosomes.
     *
     * @param firstParent the first parent to cross
     * @param secondParent the second parent to cross
     * @return the child chromosome
     */
    private Chromosome crossoverOf(Chromosome firstParent, Chromosome secondParent) {
        int maxCrossoverPoint = Math.min(firstParent.columns.size(), secondParent.columns.size());
        int crossoverPoint = ThreadLocalRandom.current().nextInt(0, maxCrossoverPoint);
        int childSize = secondParent.columns.size();

        List<BoxColumn> childColumns = new ArrayList<>();
        Set<BoxElement> addedElements = new HashSet<>();
        for (int i = 0; i < crossoverPoint; i++) {
            BoxColumn column = firstParent.columns.get(i);
            childColumns.add(column);
            addedElements.addAll(column.getElements());
        }

        for (int i = crossoverPoint; i < childSize; i++) {
            BoxColumn column = secondParent.columns.get(i);

            boolean wouldHaveDuplicate = false;
            for (BoxElement element : elements) {
                if (addedElements.contains(element)) {
                    wouldHaveDuplicate = true;
                    break;
                }
            }

            if (!wouldHaveDuplicate) {
                childColumns.add(column);
            }
        }

        List<BoxElement> unassignedElements = new ArrayList<>(elements);
        unassignedElements.removeAll(addedElements);

        Chromosome child = new Chromosome();
        child.columns = childColumns;
        child.insertSorted(unassignedElements);

        return child;
    }

    /**
     * Mutates the given chromosome.
     *
     * @param chromosome the chromosome to mutate.
     */
    private void mutate(Chromosome chromosome) {
        List<BoxElement> unassignedElements = new ArrayList<>();

        for (int i = 0; i < MAX_MUTATIONS; i++) {
            if (ThreadLocalRandom.current().nextDouble() < MUTATION_PROBABILITY) {
                int randomIndex = ThreadLocalRandom.current().nextInt(chromosome.columns.size());

                unassignedElements.addAll(chromosome.getElementsOn(randomIndex));
                chromosome.remove(randomIndex);
            }
        }

        chromosome.insertSorted(unassignedElements);
    }

    /**
     * The main method that runs the {@link BoxFilling} algorithm.
     *
     * @param args the command-line arguments, 7 expected
     */
    public static void main(String[] args) {
        if (args.length != 7)  {
            System.out.println("Expected 7 arguments, got " + args.length);
            System.exit(1);
        }

        try {
            Path filePath = Paths.get(args[0]);
            List<BoxElement> elements = parseElements(filePath);

            int populationSize = Integer.parseInt(args[1]);

            int n = Integer.parseInt(args[2]);
            int m = Integer.parseInt(args[3]);
            boolean p = Boolean.parseBoolean(args[4]);
            if (n < 2 || m < 2) {
                throw new IllegalArgumentException();
            }

            int maxIterations = Integer.parseInt(args[5]);
            int acceptableSize = Integer.parseInt(args[6]);

            BoxFilling boxFillingGA = new BoxFilling(elements, populationSize, maxIterations, n, m, p, acceptableSize);
            boxFillingGA.run();

        } catch (IllegalArgumentException | IOException e) {
            System.out.println(e.getClass() + ": " + e.getMessage());
            System.exit(1);
        }
    }

    /**
     * Parses the given file into {@link BoxElement} objects.
     *
     * @param filePath a path to the file to parse
     * @return a list of {@link BoxElement} objects parsed from the given file
     * @throws IOException if an I/O error occurs
     */
    private static List<BoxElement> parseElements(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        if (lines.size() != 1) {
            throw new IllegalArgumentException();
        }

        String line = lines.get(0);
        if (!line.startsWith("[") || !line.endsWith("]")) {
            throw new IllegalArgumentException();
        }

        String[] parts = line.substring(1, line.length() - 1).split(", ");
        List<BoxElement> elements = new ArrayList<>();
        for (String part : parts) {
            int height = Integer.parseInt(part);

            elements.add(new BoxElement(height));
        }

        return elements;
    }
}
