package hr.fer.zemris.optjava.dz5.ga.crossover;

import hr.fer.zemris.optjava.dz5.ga.chromosome.Chromosome;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of order-based crossover (OX2) that results in two child chromosomes.
 *
 * @author Bruna Dujmović
 *
 */
public class OrderBasedCrossover implements ICrossover<Integer> {

    /**
     * The number of random positions to select.
     */
    private static final int NUM_OF_POSITIONS = 3;

    @Override
    public Collection<Chromosome<Integer>> of(Chromosome<Integer> firstParent, Chromosome<Integer> secondParent) {
        Set<Integer> positionsSet = new HashSet<>();
        while (positionsSet.size() < NUM_OF_POSITIONS) {
            positionsSet.add(ThreadLocalRandom.current().nextInt(firstParent.values.length));
        }
        Integer[] positions = positionsSet.toArray(new Integer[0]);

        Chromosome<Integer> firstChild = firstParent.copy();
        Chromosome<Integer> secondChild = secondParent.copy();

        List<Integer> selectedInFirst = new ArrayList<>(NUM_OF_POSITIONS);
        List<Integer> selectedInSecond = new ArrayList<>(NUM_OF_POSITIONS);

        for (int i = 0; i < NUM_OF_POSITIONS; i++) {
            selectedInFirst.add(firstParent.values[positions[i]]);
            selectedInSecond.add(secondParent.values[positions[i]]);
        }

        fillWithSelected(firstChild, selectedInSecond);
        fillWithSelected(secondChild, selectedInFirst);

        return Arrays.asList(firstChild, secondChild);
    }

    /**
     * Finds the positions in the child that contain elements from {@code selected} and
     * fills them in the order in which the elements appear in {@code selected}.
     *
     * @param child the child to fill
     * @param selected the selected values
     */
    private void fillWithSelected(Chromosome<Integer> child, List<Integer> selected) {
        int indexOfSelected = 0;

        for (int i = 0; i < child.values.length && indexOfSelected < NUM_OF_POSITIONS; i++) {
            if (selected.contains(child.values[i])) {
                child.values[i] = selected.get(indexOfSelected++);
            }
        }
    }
}
