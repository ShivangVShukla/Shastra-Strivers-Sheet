# Editorial: Right and Left View of a Binary Tree

## Difficulty

Medium

## Prerequisites

- Binary trees
- breadth-first search
- level-order traversal

## Problem Understanding

Return the left view and right view of a binary tree. The left view contains the first node visible at each level from the left; the right view contains the last node visible at each level from the left. Return each view from root to deepest level.

## Approach

Use breadth-first search (BFS) one level at a time. For each level, the first node dequeued is the leftmost visible node and the last node dequeued is the rightmost visible node. Append those values to the left-view and right-view lists respectively. Enqueue each node's left child before its right child to preserve left-to-right level order.

A node can be the visible node even when its parent-side direction seems counterintuitive: visibility is determined by position among all nodes at the same depth, not by whether the node is reached using only left edges or only right edges.

## Step-by-Step Algorithm

1. If the root is null, both views are empty.
2. Push the root into a queue.
3. For each BFS level, record the first node as the left-view value and the final node as the right-view value.
4. Enqueue each node’s left child, then its right child.
5. Repeat until the queue is empty.

## Worked Example

For a tree whose levels are `[1]`, `[2,3]`, `[4,5]`, `[6]`, the left view is `[1,2,4,6]` and the right view is `[1,3,5,6]`. At each level, BFS identifies the outermost nodes.

## Correctness Argument

BFS processes nodes in nondecreasing depth and left-to-right order within each level. Therefore the first node processed at a level is exactly the leftmost node and the last is exactly the rightmost node. Recording those values once per level yields both views in top-to-bottom order.

## Complexity Analysis

**Time:** O(n), each node is enqueued once. **Auxiliary space:** O(w) for the BFS queue, where w is the maximum width of the tree; view output requires O(h) space.

## Conclusion

Level-order traversal naturally exposes the first and last node at each depth, producing both views in one pass.
