package hr.fer.zemris.optjava.dz13.nodes;

/**
 * An enumeration of all possible GP tree node types.
 *
 * @author Bruna Dujmović
 *
 */
public enum NodeType {

    /**
     * The move node - moves the ant to the next cell.
     */
    MOVE,

    /**
     * The turn right node - turns the ant by 90 degrees to the right.
     */
    RIGHT,

    /**
     * The turn left node - turns the ant by 90 degrees to the left.
     */
    LEFT,

    /**
     * The if food ahead node - acts as an if-else block.
     */
    IF_FOOD_AHEAD,

    /**
     * The prog2 node - executes its two child nodes sequentially.
     */
    PROG_2,

    /**
     * The prog2 node - executes its three child nodes sequentially.
     */
    PROG_3
}
