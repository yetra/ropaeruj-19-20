package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.ant.Ant;
import hr.fer.zemris.optjava.dz13.nodes.Node;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Models a GP tree.
 *
 * @author Bruna Dujmović
 */
public class Tree {

    /**
     * The maximum tree depth.
     */
    public static final int MAX_DEPTH = 20;

    /**
     * The maximum number of nodes in thid tree.
     */
    public static final int MAX_NODES = 200;

    /**
     * The coefficient to use for punishing plagiarism.
     */
    private static final double PLAGIARISM_COEFFICIENT = 0.9;

    /**
     * The root node.
     */
    public Node root;

    /**
     * The depth of this tree.
     */
    private int depth;

    /**
     * The fitness of this tree (after applying the plagiarism punishment).
     */
    public double fitness;

    /**
     * The base fitness of this tree (equal to {@link Ant#getFoodEaten()}).
     */
    private double baseFitness;

    /**
     * Constructs a {@link Tree}.
     *
     * @param root the root node
     * @param depth the depth of the tree
     */
    Tree(Node root, int depth) {
        this.root = root;
        this.depth = depth;
    }

    /**
     * Returns the depth of this tree.
     *
     * @return the depth of this tree
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Returns the number of nodes in this tree.
     *
     * @return the number of nodes in this tree
     */
    public int getNodesCount() {
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        int nodesCount = 0;
        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();

            if (currentNode.children == null) {
                continue;
            }
            queue.addAll(Arrays.asList(currentNode.children));

            nodesCount++;
        }

        return nodesCount;
    }

    /**
     * Controls the given ant using the operations contained in this tree and calculates its fitness.
     *
     * @param ant the ant to control
     * @param parent the parent tree to use for punishing plagiarism
     */
    public void calculateFitness(Ant ant, Tree parent) {
        while (ant.getEnergy() > 0) {
            root.control(ant);
        }

        baseFitness = ant.getFoodEaten();
        fitness = baseFitness;

        if (parent != null && baseFitness == parent.baseFitness) {
            fitness *= PLAGIARISM_COEFFICIENT;
        }
    }

    /**
     * Returns {@code true} if this tree breaks the {@link #MAX_DEPTH} and {@link #MAX_NODES} constraints.
     *
     * @return {@code true} if this tree breaks the {@link #MAX_DEPTH} and {@link #MAX_NODES} constraints
     */
    public boolean breaksConstraints() {
        return getDepth() > MAX_DEPTH || getNodesCount() > MAX_NODES;
    }

    /**
     * Returns a duplicate of this tree.
     *
     * @return the duplicated tree
     */
    public Tree copy() {
        Tree copy = new Tree(root.copy(), root.depth);
        copy.baseFitness = baseFitness;
        copy.fitness = fitness;

        return copy;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}
