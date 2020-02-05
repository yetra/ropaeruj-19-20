package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;

/**
 * A function node that executes its two child nodes sequentially.
 *
 * @author Bruna Dujmović
 *
 */
public class Prog2Node extends Node {

    /**
     * Constructs a {@link Prog2Node}.
     */
    Prog2Node() {
        children = new Node[2];
    }

    @Override
    public void control(Ant ant) {
        for (Node child : children) {
            child.control(ant);
        }
    }

    @Override
    public Node copy() {
        Prog2Node copy = new Prog2Node();
        for (int i = 0; i < children.length; i++) {
            copy.children[i] = children[i].copy();
        }

        return copy;
    }

    @Override
    public String toString() {
        return "Prog2(" + children[0].toString() + ", " + children[1].toString() + ")";
    }
}
