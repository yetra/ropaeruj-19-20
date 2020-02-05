package hr.fer.zemris.optjava.dz13.nodes;

import hr.fer.zemris.optjava.dz13.ant.Ant;

/**
 * A terminal node that turns the given ant to the right by 90 degrees.
 *
 * @author Bruna Dujmović
 *
 */
public class TurnRightNode extends Node {

    @Override
    public void control(Ant ant) {
        ant.turnRight();
    }

    @Override
    public Node copy() {
        return new TurnRightNode();
    }

    @Override
    public String toString() {
        return "Right";
    }
}
