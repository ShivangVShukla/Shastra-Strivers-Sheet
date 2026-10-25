# Editorial — Inorder Traversal

## Approach: Recursive DFS

Inorder traversal follows **Left → Root → Right**.

For each node:
1. If the node is `null`, return.
2. Traverse the left subtree.
3. Add the current node's value to the result.
4. Traverse the right subtree.

Because the tree has at most 100 nodes, recursion is comfortably within the constraints.

## Complexity
- **Time:** `O(N)`, where `N` is the number of nodes. Each node is visited once.
- **Auxiliary space:** `O(H)` for the recursion stack, where `H` is the tree height, plus `O(N)` for the result.

## Example
For `[5, 1, 2, 8, null, 4, 5, null, 6]`, inorder traversal is:
`[8, 6, 1, 5, 4, 2, 5]`.
