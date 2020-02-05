package hr.fer.zemris.optjava.dz13.gp;

import hr.fer.zemris.optjava.dz13.Tree;
import hr.fer.zemris.optjava.dz13.TreeGenerator;

import java.util.concurrent.ThreadLocalRandom;

/**
 * An implementation of GP mutation that mutates a given tree by selecting a random node
 * and replacing it with a randomly generated subtree.
 *
 * @author Bruna Dujmović
 *
 */
class Mutation {

    /**
     * Mutates the given tree by selecting a random node and replacing it with a randomly generated subtree.
     *
     * NOTE: this method will not change the parent.
     *
     * @param parent the tree to mutate
     * @return the child tree obtained by mutating the parent
     */
    static Tree mutate(Tree parent) {
        Tree child = parent.copy();

        BFSUtil.Point result = BFSUtil.findRandomPointIn(child);

        int maxSubtreeDepth = Tree.MAX_DEPTH - result.root.depth;
        int subtreeDepth = ThreadLocalRandom.current().nextInt(1, maxSubtreeDepth);

        Tree subtree = TreeGenerator.generate(subtreeDepth, false);
        result.root.children[result.childIndex] = subtree.root;

        if (child.breaksConstraints()) {
            return parent.copy();
        }

        return child;
    }
}
