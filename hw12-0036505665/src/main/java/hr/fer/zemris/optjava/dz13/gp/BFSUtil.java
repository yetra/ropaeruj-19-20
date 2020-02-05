package hr.fer.zemris.optjava.dz13.gp;

import hr.fer.zemris.optjava.dz13.Tree;
import hr.fer.zemris.optjava.dz13.nodes.Node;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A utility class that uses the breadth-first search algorithm
 * for finding a random point in a given tree.
 *
 * @author Bruna Dujmović
 *
 */
class BFSUtil {

    /**
     * Finds a random point in the given tree using the BFS algorithm.
     *
     * @param tree the tree for finding the random point
     * @return the random point
     */
    static Point findRandomPointIn(Tree tree) {
        int randomNodeNumber = ThreadLocalRandom.current().nextInt(1, tree.getNodesCount() + 1);

        Queue<Node> queue = new LinkedList<>();
        queue.add(tree.root);

        Point result = null;
        while (result == null) {
            Node currentNode = queue.remove();

            if (currentNode.children == null) {
                continue;
            }

            for (int i = 0; i < currentNode.children.length; i++) {
                randomNodeNumber--;
                if (randomNodeNumber == 0) {
                    result = new Point(i, currentNode);
                    break;
                }

                queue.add(currentNode.children[i]);
            }
        }

        return result;
    }

    /**
     * Models a BFS search result for {@link #findRandomPointIn(Tree)}.
     */
    static class Point {

        /**
         * The index of the child node of the point.
         */
        int childIndex;

        /**
         * The root node of the point.
         */
        Node root;

        /**
         * Constructs a {@link Point}.
         *
         * @param childIndex the index of the child node of the point
         * @param root the root node of the point
         */
        Point(int childIndex, Node root) {
            this.childIndex = childIndex;
            this.root = root;
        }
    }
}
