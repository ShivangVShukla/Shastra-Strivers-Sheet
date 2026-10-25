# Preorder Traversal

Given the root of a binary tree, return the **preorder** traversal of the binary tree.

Preorder traversal visits nodes in this order: **Root → Left subtree → Right subtree**.

## Examples

### Example 1
**Input:** `root = [1, 4, null, 4, 2]`  
**Output:** `[1, 4, 4, 2]`

### Example 2
**Input:** `root = [1]`  
**Output:** `[1]`

### Example 3
**Input:** `root = [5, 1, 2, 8, null, 4, 5, null, 6]`  
**Output:** `[5, 1, 8, 6, 2, 4, 5]`

## Constraints
- `1 <= Number of Nodes <= 100`
- `-100 <= Node.val <= 100`

## Input Format Used in This Package
Each test case contains one line: a level-order representation of the tree in JSON-like array notation. Use `null` for missing nodes, for example:
`[1, 4, null, 4, 2]`

## Output Format
Print the preorder traversal as a list, for example:
`[1, 4, 4, 2]`
