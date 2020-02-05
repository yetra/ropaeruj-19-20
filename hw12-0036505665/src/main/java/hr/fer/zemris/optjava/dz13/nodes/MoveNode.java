package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;

/**
 * A terminal node for moving the specified ant to the next cell.
 */
public class MoveNode extends Node {

    @Override
    public void control(Ant ant) {
        ant.move();
    }

    @Override
    public Node copy() {
        return new MoveNode();
    }

    @Override
    public String toString() {
        return "Move";
    }
}
