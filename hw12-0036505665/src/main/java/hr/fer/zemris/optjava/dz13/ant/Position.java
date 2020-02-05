package hr.fer.zemris.optjava.dz13.ant;

/**
 * Models a position determined by its x and y coordinates on an ant trail.
 *
 * @author Bruna Dujmović
 *
 */
public class Position {

    /**
     * The x coordinate.
     */
    public int x;

    /**
     * The y coordinate.
     */
    public int y;

    /**
     * Constructs a {@link Position}.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     */
    Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Updates this position to the next trail cell based on the given direction.
     *
     * @param trail the trail matrix
     * @param direction the current direction
     */
    void next(boolean[][] trail, Direction direction) {
        switch (direction) {
            case EAST:
                x = (x + 1) % trail[0].length;
                break;

            case WEST:
                x = (x - 1) % trail[0].length;
                if (x < 0) {
                    x += trail[0].length;
                }
                break;

            case NORTH:
                y = (y - 1) % trail.length;
                if (y < 0) {
                    y += trail[0].length;
                }
                break;

            case SOUTH:
                y = (y + 1) % trail.length;
                break;
        }
    }

    /**
     * Returns the next trail cell based on the given direction.
     *
     * @param trail the trail matrix
     * @param direction the current direction
     * @return the next trail cell
     */
    Position getNext(boolean[][] trail, Direction direction) {
        Position nextPosition = copy();
        nextPosition.next(trail, direction);

        return nextPosition;
    }

    /**
     * Returns a duplicate of this position.
     *
     * @return a duplicate of this position
     */
    public Position copy() {
        return new Position(x, y);
    }
}
