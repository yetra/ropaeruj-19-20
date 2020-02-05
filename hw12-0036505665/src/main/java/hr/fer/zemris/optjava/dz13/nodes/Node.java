package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;

/**
 * The base class for all GP tree nodes.
 *
 * @author Bruna Dujmović
 *
 */
public abstract class Node {

    /**
     * The depth of this node.
     */
    public int depth;

    /**
     * The child nodes of this node.
     */
    public Node[] children;

    /**
     * Controls the given ant using the operations contained in this tree.
     *
     * @param ant the ant to control
     */
    public abstract void control(Ant ant);

    /**
     * Returns a duplicate of this node.
     *
     * @return a duplicate of this node.
     */
    public abstract Node copy();
}
