# Editorial: LCA in BST

## Difficulty

Medium

## Prerequisites

- Binary search trees
- tree traversal
- ordering invariants

## Problem Understanding

Given a binary search tree and two values p and q that are guaranteed to exist, return the value of their lowest common ancestor (LCA). The LCA is the deepest node that is an ancestor of both values; a node may be an ancestor of itself. BST values are unique.

## Approach

Exploit the BST ordering invariant: every value in a node's left subtree is smaller, and every value in its right subtree is larger. Starting at the root, if both p and q are smaller than the current value, move left. If both are larger, move right. Otherwise, the current node lies between p and q, or equals one of them; it is the lowest common ancestor and can be returned immediately.

The first split point is the lowest shared ancestor because all deeper nodes lie in only one of the two relevant directions. The guarantee that both values exist means no missing-node validation is required.

## Step-by-Step Algorithm

1. Start at the root.
2. If both target values are smaller than the current node, move to the left child.
3. If both are greater, move to the right child.
4. Otherwise, return the current node: it is the split point or one target itself.

## Worked Example

In `[5, 3, 6, 2, 4, null, 7]`, values 2 and 4 both lie in the left subtree of 5 but split at 3; therefore the LCA is 3. For 2 and 7, the paths split at 5, so the LCA is 5.

## Correctness Argument

When both targets lie on one side of a node, the BST property guarantees that their LCA lies on that same side, so moving there cannot skip the answer. At the first node where they diverge sides, or one target equals the node, both targets are in its subtree but no child subtree contains both; therefore it is their lowest common ancestor.

## Complexity Analysis

**Time:** O(h), where h is BST height (O(log n) for a balanced tree and O(n) worst case). **Auxiliary space:** O(1), iterative traversal.

## Conclusion

BST ordering makes it possible to find the first shared branching point without searching both subtrees.
