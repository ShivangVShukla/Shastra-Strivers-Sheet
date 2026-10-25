# Editorial — Level Order Traversal

## Approach: Breadth-First Search (BFS)

Use a queue to visit the tree level by level.

1. If the root is `null`, return an empty list.
2. Add the root to a queue.
3. While the queue is not empty:
   - Record the queue's current size; this is the number of nodes at the current level.
   - Remove exactly that many nodes, append their values to the current level, and add each node's non-null left and right children to the queue.
   - Append the current level to the answer.

## Complexity
- **Time:** `O(N)`, since each node is enqueued and dequeued once.
- **Auxiliary space:** `O(N)` for the queue and output, where `N` is the number of nodes.

## Example
For `[5, 1, 2, 8, null, 4, 5, null, 6]`, the levels are:
`[[5], [1, 2], [8, 4, 5], [6]]`.
