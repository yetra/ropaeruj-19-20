package hr.fer.zemris.optjava.dz4.part2;

/**
 * Models a box element to be used in the {@link BoxFilling} algorithm.
 *
 * @author Bruna Dujmović
 *
 */
public class BoxElement {

    /**
     * The height of this element.
     */
    private int height;

    /**
     * Constructs a {@link BoxElement} of the specified height.
     *
     * @param height the height of this element
     */
    public BoxElement(int height) {
        this.height = height;
    }

    /**
     * Returns the height of this element.
     *
     * @return the height of this element
     */
    public int getHeight() {
        return height;
    }

    @Override
    public String toString() {
        return Integer.toString(height);
    }
}
