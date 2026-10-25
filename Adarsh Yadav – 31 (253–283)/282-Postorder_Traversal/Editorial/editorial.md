# Editorial — Postorder Traversal

## Approach: Recursive DFS

Postorder traversal follows **Left → Right → Root**.

For each node:
1. If the node is `null`, return.
2. Traverse the left subtree.
3. Traverse the right subtree.
4. Add the current node's value to the result.

## Complexity
- **Time:** `O(N)`, where `N` is the number of nodes. Each node is visited once.
- **Auxiliary space:** `O(H)` for the recursion stack, where `H` is the tree height, plus `O(N)` for the result.

## Example
For `[5, 1, 2, 8, null, 4, 5, null, 6]`, postorder traversal is:
`[6, 8, 1, 4, 5, 2, 5]`.
