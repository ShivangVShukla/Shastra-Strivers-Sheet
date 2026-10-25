# Level Order Traversal

Given the root of a binary tree, return the **level order** traversal of its nodes' values (from left to right, level by level).

## Examples

### Example 1
**Input:** `root = [3, 9, 20, null, null, 15, 7]`  
**Output:** `[[3], [9, 20], [15, 7]]`

### Example 2
**Input:** `root = [1, 4, null, 4, 2]`  
**Output:** `[[1], [4], [4, 2]]`

### Example 3
**Input:** `root = [5, 1, 2, 8, null, 4, 5, null, 6]`  
**Output:** `[[5], [1, 2], [8, 4, 5], [6]]`

## Constraints
- `0 <= Number of Nodes <= 2000`
- `-1000 <= Node.val <= 2000`

## Input Format Used in This Package
Each test case contains one line: a level-order representation of the tree in JSON-like array notation. Use `null` for missing nodes. An empty tree is represented by `[]`.

## Output Format
Print the level-order traversal as a list of lists, for example:
`[[3], [9, 20], [15, 7]]`
