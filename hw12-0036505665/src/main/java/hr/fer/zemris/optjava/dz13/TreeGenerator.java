package hr.fer.zemris.optjava.dz13;

import hr.fer.zemris.optjava.dz13.nodes.Node;
import hr.fer.zemris.optjava.dz13.nodes.NodeFactory;
import hr.fer.zemris.optjava.dz13.nodes.NodeType;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class for generating GP trees.
 *
 * @author Bruna Dujmović
 *
 */
public class TreeGenerator {

    /**
     * An array of terminal node types.
     */
    private static final NodeType[] terminals = {NodeType.MOVE, NodeType.LEFT, NodeType.RIGHT};

    /**
     * An array of function node types.
     */
    private static final NodeType[] functions = {NodeType.IF_FOOD_AHEAD, NodeType.PROG_2, NodeType.PROG_3};

    /**
     * An array of primitive node types (terminals + functions).
     */
    private static final NodeType[] primitives = {NodeType.MOVE, NodeType.LEFT, NodeType.RIGHT,
                                                  NodeType.IF_FOOD_AHEAD, NodeType.PROG_2, NodeType.PROG_3};

    /**
     * Generates a tree of the specified depth.
     *
     * @param depth the depth of the tree to generate
     * @param full {@code true} if the full method should be used, {@code false} if the grow method should be used
     * @return the generated tree
     */
    public static Tree generate(int depth, boolean full) {
        if (depth == 1) {
            return new Tree(generateNodeFrom(terminals), depth);
        }

        Node root = generateNodeFrom(functions);
        root.depth = 1;
        int generatedDepth = 1;

        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node currentNode = queue.remove();

            if (currentNode.children == null) {
                continue;
            }

            for (int i = 0; i < currentNode.children.length; i++) {
                if (currentNode.depth == depth - 1) {
                    currentNode.children[i] = generateNodeFrom(terminals);
                } else if (full) {
                    currentNode.children[i] = generateNodeFrom(functions);
                } else {
                    currentNode.children[i] = generateNodeFrom(primitives);
                }

                currentNode.children[i].depth = currentNode.depth + 1;
                if (currentNode.depth + 1 > generatedDepth) {
                    generatedDepth = currentNode.depth + 1;
                }
                queue.add(currentNode.children[i]);
            }
        }

        return new Tree(root, generatedDepth);
    }

    /**
     * Generates a node of a randomly chosen {@link NodeType}.
     *
     * @param nodeTypes the node types to randomly choose from
     * @return the generated node
     */
    private static Node generateNodeFrom(NodeType[] nodeTypes) {
        int randomIndex = ThreadLocalRandom.current().nextInt(nodeTypes.length);

        return NodeFactory.create(nodeTypes[randomIndex]);
    }
}
