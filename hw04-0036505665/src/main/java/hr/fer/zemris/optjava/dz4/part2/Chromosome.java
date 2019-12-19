package hr.fer.zemris.optjava.dz4.part2;

import java.util.*;

/**
 * The chromosome to be used in {@link BoxFilling}.
 *
 * @author Bruna Dujmović
 *
 */
public class Chromosome implements Comparable<Chromosome> {

    /**
     * The maximum allowed height for a {@link BoxElement}.
     */
    private static final int MAX_HEIGHT = 20;

    /**
     * The columns containing {@link BoxElement} objects.
     */
    List<BoxColumn> columns;

    /**
     * The fitness of this chromosome.
     */
    double fitness;

    /**
     * Constructs an empty {@link Chromosome}.
     */
    public Chromosome() {
        this.columns = new ArrayList<>();
    }

    /**
     * Constructs a {@link Chromosome} containing the given elements.
     *
     * @param elements the elements to add to this chromosome's {@link #columns}
     */
    public Chromosome(List<BoxElement> elements) {
        this.columns = new ArrayList<>();

        Collections.shuffle(elements);
        insert(elements);
    }

    /**
     * Inserts the given elements to this chromosome's {@link #columns}.
     *
     * @param elements the elements to add
     */
    public void insert(List<BoxElement> elements) {
        for (BoxElement element : elements) {
            boolean added = false;

            for (BoxColumn column : columns) {
                if (column.add(element)) {
                    added = true;
                    break;
                }
            }

            if (!added) {
                BoxColumn newColumn = new BoxColumn(MAX_HEIGHT);
                newColumn.add(element);
                columns.add(newColumn);
            }
        }
    }

    /**
     * Inserts the given elements to this chromosome's {@link #columns} sorted by height from highest to lowest.
     *
     * @param elements the elements to add
     */
    public void insertSorted(List<BoxElement> elements) {
        elements.sort(Comparator.comparingInt(BoxElement::getHeight).reversed());
        insert(elements);
    }

    /**
     * Adds the given {@link BoxElement} to the {@link BoxColumn} on the specified index.
     *
     * @param element the element to add
     * @param index the index of the column
     * @return {@code true} if the element was successfully added
     */
    public boolean add(BoxElement element, int index) {
        if (index < 0 || index >= columns.size()) {
            throw new IllegalArgumentException();
        }

        return columns.get(index).add(element);
    }

    /**
     * Removes the {@link BoxColumn} on the specified index.
     *
     * @param index the index of the column to remove
     */
    public void remove(int index) {
        if (index < 0 || index >= columns.size()) {
            throw new IllegalArgumentException();
        }

        columns.remove(index);
    }

    /**
     * Returns a list of {@link BoxElement} objects contained in the {@link BoxColumn} that is on the specified index.
     *
     * @param index the index of the {@link BoxElement} objects
     * @return a list of {@link BoxElement} objects contained in the {@link BoxColumn} that is on the specified index
     */
    public List<BoxElement> getElementsOn(int index) {
        if (index < 0 || index >= columns.size()) {
            throw new IllegalArgumentException();
        }

        return columns.get(index).getElements();
    }

    /**
     * Returns {@code true} if this chromosome contains the given {@link BoxElement}.
     *
     * @param element the element to check
     * @return {@code true} if this chromosome contains the given {@link BoxElement}
     */
    public boolean contains(BoxElement element) {
        for (BoxColumn column : columns) {
            if (column.contains(element)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int compareTo(Chromosome o) {
        return Double.compare(this.fitness, o.fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chromosome that = (Chromosome) o;
        return Double.compare(that.fitness, fitness) == 0 &&
                Objects.equals(columns, that.columns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(columns, fitness);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (BoxColumn column : columns) {
            sb.append(column.toString()).append("\n");
        }

        return sb.toString();
    }
}
