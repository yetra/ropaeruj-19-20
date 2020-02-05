package hr.fer.zemris.optjava.dz13.nodes;

/**
 * A factory class for generating GP tree nodes based on the given {@link NodeType}.
 *
 * @author Bruna Dujmović
 *
 */
public class NodeFactory {

    /**
     * Creates a node of the specified type.
     *
     * @param type the type of the node to create
     * @return the created node
     * @throws IllegalArgumentException if the specified type is invalid
     */
    public static Node create(NodeType type) {
        switch (type) {
            case RIGHT:
                return new TurnRightNode();

            case LEFT:
                return new TurnLeftNode();

            case IF_FOOD_AHEAD:
                return new IfFoodAheadNode();

            case MOVE:
                return new MoveNode();

            case PROG_2:
                return new Prog2Node();

            case PROG_3:
                return new Prog3Node();

            default:
                throw new IllegalArgumentException("Unknown node type " + type.name());
        }
    }
}
