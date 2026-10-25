# Editorial — Preorder Traversal

## Approach: Recursive DFS

Preorder traversal follows **Root → Left → Right**.

For each node:
1. If the node is `null`, return.
2. Add the current node's value to the result.
3. Traverse the left subtree.
4. Traverse the right subtree.

## Complexity
- **Time:** `O(N)`, where `N` is the number of nodes. Each node is visited once.
- **Auxiliary space:** `O(H)` for the recursion stack, where `H` is the tree height, plus `O(N)` for the result.

## Example
For `[5, 1, 2, 8, null, 4, 5, null, 6]`, preorder traversal is:
`[5, 1, 8, 6, 2, 4, 5]`.
