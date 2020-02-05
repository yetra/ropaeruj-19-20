package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;

/**
 * A terminal node that turns the given ant to the left by 90 degrees.
 *
 * @author Bruna Dujmović
 *
 */
public class TurnLeftNode extends Node {

    @Override
    public void control(Ant ant) {
        ant.turnLeft();
    }

    @Override
    public Node copy() {
        return new TurnLeftNode();
    }

    @Override
    public String toString() {
        return "Left";
    }
}
