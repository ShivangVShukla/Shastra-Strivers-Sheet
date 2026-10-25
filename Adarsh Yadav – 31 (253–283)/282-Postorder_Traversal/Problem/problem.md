# Postorder Traversal

Given the root of a binary tree, return the **postorder** traversal of the binary tree.

Postorder traversal visits nodes in this order: **Left subtree → Right subtree → Root**.

## Examples

### Example 1
**Input:** `root = [1, 4, null, 4, 2]`  
**Output:** `[4, 2, 4, 1]`

### Example 2
**Input:** `root = [1, null, 2, 3]`  
**Output:** `[3, 2, 1]`

### Example 3
**Input:** `root = [5, 1, 2, 8, null, 4, 5, null, 6]`  
**Output:** `[6, 8, 1, 4, 5, 2, 5]`

## Constraints
- `1 <= Number of Nodes <= 100`
- `-100 <= Node.val <= 100`

## Input Format Used in This Package
Each test case contains one line: a level-order representation of the tree in JSON-like array notation. Use `null` for missing nodes, for example:
`[1, 4, null, 4, 2]`

## Output Format
Print the postorder traversal as a list, for example:
`[4, 2, 4, 1]`
