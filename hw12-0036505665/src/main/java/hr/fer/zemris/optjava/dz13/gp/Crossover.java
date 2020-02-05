package hr.fer.zemris.optjava.dz13.gp;

import hr.fer.zemris.optjava.dz13.Tree;
import hr.fer.zemris.optjava.dz13.nodes.Node;

/**
 * An implementation of GP crossover that picks a random point in both parent trees
 * and switches the subtrees on those points.
 *
 * @author Bruna Dujmović
 *
 */
class Crossover {

    /**
     * Crosses the given parent trees by picking a random point in both parent trees
     * and switching the subtrees on those points.
     *
     * This method will not modify the parent trees.
     *
     * @param firstParent the first parent tree
     * @param secondParent the second parent tree
     * @return an array of two child trees
     */
    static Tree[] of(Tree firstParent, Tree secondParent) {
        Tree firstChild = firstParent.copy();
        Tree secondChild = secondParent.copy();

        BFSUtil.Point first = BFSUtil.findRandomPointIn(firstChild);
        BFSUtil.Point second = BFSUtil.findRandomPointIn(secondChild);

        Node firstSubtree = first.root.children[first.childIndex];
        first.root.children[first.childIndex] = second.root.children[second.childIndex];
        second.root.children[second.childIndex] = firstSubtree;

        if (firstChild.breaksConstraints()) {
            firstChild = firstParent.copy();
        }
        if (secondChild.breaksConstraints()) {
            secondChild = secondParent.copy();
        }

        return new Tree[] {firstChild, secondChild};
    }
}
