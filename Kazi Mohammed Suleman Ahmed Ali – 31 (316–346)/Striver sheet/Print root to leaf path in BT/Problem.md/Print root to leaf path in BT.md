# Print root to leaf path in BT

## Problem Statement

Given the root of a binary tree, return all the **root-to-leaf paths** in the binary tree.

A **leaf node** of a binary tree is a node that does not have a left and right child (i.e. `node.left == null` and `node.right == null`).

Each root-to-leaf path must start at the root node and end at a leaf node, listing the node values encountered in order from root to leaf. Paths should be listed in standard left-to-right (pre-order) traversal order.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print all root-to-leaf paths formatted as a nested list enclosed in square brackets:
- Formatted as `[ [path_1] , [path_2] , ... , [path_k] ]` where each `path_i` is a comma-separated list of node values `[val_1, val_2, ...]`.
- Example: `[ [1, 2, 5] , [1, 3, 4] ]`
- If the tree is empty, print `[]`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 3 \times 10^3$ (empty tree $N = 0$ is also supported)
- $-10^3 \le \text{Node.val} \le 10^3$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3 null 5 null 4
```

**Output:**
```text
[ [1, 2, 5] , [1, 3, 4] ]
```

**Explanation:**
The binary tree structure is:
```text
       1
     /   \
    2     3
     \     \
      5     4
```
There are two paths from the root to a leaf:
1. From root `1` to leaf `5`: `1 -> 2 -> 5`, giving path `[1, 2, 5]`
2. From root `1` to leaf `4`: `1 -> 3 -> 4`, giving path `[1, 3, 4]`

---

### Example 2

**Input:**
```text
1 2 3 4 5
```

**Output:**
```text
[ [1, 2, 4] , [1, 2, 5] , [1, 3] ]
```

**Explanation:**
The binary tree structure is:
```text
          1
       /     \
      2       3
     / \
    4   5
```
Leaf nodes are `4`, `5`, and `3`.
- Path to `4`: `[1, 2, 4]`
- Path to `5`: `[1, 2, 5]`
- Path to `3`: `[1, 3]`

---

### Example 3

**Input:**
```text
1 2 3 4 null 5 6 null 7
```

**Output:**
```text
[ [1, 2, 4, 7] , [1, 3, 5] , [1, 3, 6] ]
```

**Explanation:**
The binary tree structure is:
```text
            1
         /     \
        2       3
       /       / \
      4       5   6
       \
        7
```
Leaf nodes are `7`, `5`, and `6`.
- Path to `7`: `[1, 2, 4, 7]`
- Path to `5`: `[1, 3, 5]`
- Path to `6`: `[1, 3, 6]`
