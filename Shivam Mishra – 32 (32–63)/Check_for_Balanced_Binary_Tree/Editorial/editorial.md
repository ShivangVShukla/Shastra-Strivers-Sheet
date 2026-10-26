# Editorial: Check for Balanced Binary Tree

## Difficulty

Easy

## Prerequisites

- Binary trees
- DFS
- postorder traversal

## Problem Understanding

Given a binary tree, determine whether it is height-balanced. A tree is balanced when, at every node, the heights of its left and right subtrees differ by at most one.

## Approach

Use a postorder depth-first traversal that computes subtree height and balance simultaneously. Define `heightOrUnbalanced(node)` to return the subtree height if balanced, or -1 if any subtree is unbalanced. The null tree has height zero. Recursively compute the left result; if it is -1, propagate -1 immediately. Do the same for the right result. If the absolute difference exceeds one, return -1; otherwise return `1 + max(leftHeight, rightHeight)`. The tree is balanced exactly when the root result is not -1.

This avoids repeatedly recalculating subtree heights. Each node is processed once, unlike the straightforward approach that separately recomputes heights at every node.

## Step-by-Step Algorithm

1. Treat a null subtree as height 0.
2. Recursively obtain the left subtree height; propagate -1 if it is unbalanced.
3. Recursively obtain the right subtree height; propagate -1 if it is unbalanced.
4. If the height difference exceeds 1, return -1.
5. Otherwise return one plus the larger subtree height. The root is balanced if its result is not -1.

## Worked Example

For `[3, 9, 20, null, null, 15, 7]`, the root has left and right subtree heights 1 and 2, and all nodes satisfy the condition; output `Yes`. For `[1, 2, null, null, 3]`, node 1 has a height difference of 2, so output `No`.

## Correctness Argument

By induction on subtree size, the helper returns the exact height for every balanced subtree and -1 for every unbalanced subtree. It checks both child subtrees and the current node’s height difference, so the root result is nonnegative exactly when every node satisfies the balance condition.

## Complexity Analysis

**Time:** O(n), each node is processed once. **Auxiliary space:** O(h) recursion stack, where h is tree height (O(n) worst case).

## Conclusion

A postorder height function with an unbalanced sentinel checks the entire tree in linear time without repeated height computations.
