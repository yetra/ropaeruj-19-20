package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;

/**
 * A function node that acts as an if-else block. If the given ant ant senses food on the cell in front of it,
 * the first child node will be executed. Otherwise, the second child node will be executed.
 *
 * @author Bruna Dujmović
 *
 */
public class IfFoodAheadNode extends Node {

    /**
     * Constructs an {@link IfFoodAheadNode}.
     */
    IfFoodAheadNode() {
        children = new Node[2];
    }

    @Override
    public void control(Ant ant) {
        if (ant.isFoodAhead()) {
            children[0].control(ant);
        } else {
            children[1].control(ant);
        }
    }

    @Override
    public Node copy() {
        IfFoodAheadNode copy = new IfFoodAheadNode();
        for (int i = 0; i < children.length; i++) {
            copy.children[i] = children[i].copy();
        }

        return copy;
    }

    @Override
    public String toString() {
        return "IfFoodAhead(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
