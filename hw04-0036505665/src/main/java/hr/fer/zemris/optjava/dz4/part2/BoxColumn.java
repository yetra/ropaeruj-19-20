package hr.fer.zemris.optjava.dz4.part2;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Models a box column to be used in the {@link BoxFilling} algorithm.
 *
 * @author Bruna Dujmović
 *
 */
public class BoxColumn {

    /**
     * The maximum height of this column.
     */
    private int maxHeight;

    /**
     * The current height of this column.
     */
    private int currentHeight;

    /**
     * A list of elements contained in this column.
     */
    private List<BoxElement> elements;

    /**
     * Constructs a {@link BoxColumn} of the given maximum height.
     *
     * @param maxHeight the maximum height of this column
     */
    public BoxColumn(int maxHeight) {
        this.elements = new ArrayList<>();
        this.maxHeight = maxHeight;
        this.currentHeight = 0;
    }

    /**
     * Returns the maximum height of this column.
     *
     * @return the maximum height of this column
     */
    public int getMaxHeight() {
        return maxHeight;
    }

    /**
     * Adds the given {@link BoxElement} to this column.
     *
     * @param element the element to add
     * @return {@code true} if the element was successfully added
     */
    public boolean add(BoxElement element) {
        if (currentHeight + element.getHeight() > maxHeight) {
            return false;
        }

        elements.add(element);
        currentHeight += element.getHeight();

        return true;
    }

    /**
     * Removes the {@link BoxElement} on the specified index.
     *
     * @param index the index of the element to remove
     */
    public void remove(int index) {
        if (index < 0 || index > elements.size()) {
            throw new IllegalArgumentException();
        }

        elements.remove(index);
    }

    /**
     * Returns {@code true} if this column contains no elements.
     *
     * @return {@code true} if this column contains no elements
     */
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    /**
     * Returns {@code true} if this column contains the specified {@link BoxElement}.
     *
     * @param element the element to check
     * @return {@code true} if this column contains the specified {@link BoxElement}
     */
    public boolean contains(BoxElement element) {
        return elements.contains(element);
    }

    /**
     * Returns a list of elements contained in this column.
     *
     * @return a list of elements contained in this column
     */
    public List<BoxElement> getElements() {
        return elements;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoxColumn column = (BoxColumn) o;
        return currentHeight == column.currentHeight &&
                Objects.equals(elements, column.elements);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currentHeight, elements);
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();

        for (BoxElement element : elements) {
            s.append(element.toString()).append(" ");
        }

        return s.toString();
    }
}
