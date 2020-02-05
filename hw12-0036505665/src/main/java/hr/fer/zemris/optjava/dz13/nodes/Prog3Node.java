package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;

/**
 * A function node that executes its three child nodes sequentially.
 *
 * @author Bruna Dujmović
 *
 */
public class Prog3Node extends Node {

    /**
     * Constructs a {@link Prog3Node}.
     */
    Prog3Node() {
        children = new Node[3];
    }

    @Override
    public void control(Ant ant) {
        for (Node child : children) {
            child.control(ant);
        }
    }

    @Override
    public Node copy() {
        Prog3Node copy = new Prog3Node();
        for (int i = 0; i < children.length; i++) {
            copy.children[i] = children[i].copy();
        }

        return copy;
    }

    @Override
    public String toString() {
        return "Prog3(" + children[0].toString()
                + ", " + children[1].toString()
                + ", " + children[2].toString() + ")";
    }
}
