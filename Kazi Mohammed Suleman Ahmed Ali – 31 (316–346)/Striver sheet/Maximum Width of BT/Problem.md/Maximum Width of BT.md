# Maximum Width of BT

## Problem Statement

Given the root of a binary tree, return the **maximum width** of the given tree.

The **maximum width** of a tree is the maximum width among **all levels**:
- The **width of a level** is defined as the length between the end-nodes (the leftmost and rightmost non-null nodes), where the null nodes between the end-nodes that would be present in a complete binary tree extending down to that level are also counted into the length calculation.
- It is guaranteed that the answer will fit in a 32-bit signed integer.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print a single integer representing the maximum width of the binary tree:
- Example: `4`
- If the tree is empty, print `0`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 3000$ (empty tree $N = 0$ is also supported)
- $-1000 \le \text{Node.val} \le 1000$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 3 2 5 3 null 9
```

**Output:**
```text
4
```

**Explanation:**
The binary tree structure is:
```text
           1            <-- Level 0: Width = 1
         /   \
        3     2         <-- Level 1: Width = 2
       / \     \
      5   3     9       <-- Level 2: Width = 4
```
At Level 2:
- Leftmost node is `5` (conceptual index 0).
- Rightmost node is `9` (conceptual index 3).
- The intermediate positions are occupied by `3` (index 1) and a `null` node (index 2).
- The width is $3 - 0 + 1 = 4$.

The maximum width across all levels is $\max(1, 2, 4) = 4$.

---

### Example 2

**Input:**
```text
1 3 2 5 null null 9 6 null 7
```

**Output:**
```text
7
```

**Explanation:**
The binary tree structure is:
```text
             1                <-- Level 0: Width = 1
          /     \
         3       2            <-- Level 1: Width = 2
        /         \
       5           9          <-- Level 2: Width = 4
      /           /
     6           7            <-- Level 3: Width = 7
```
At Level 3:
- The leftmost node is `6` (derived from index $0$ of level 2: $2 \times 0 + 1 = 1$, normalized to index $0$).
- The rightmost node is `7` (derived from index $3$ of level 2: $2 \times 3 + 1 = 7$, normalized to index $6$).
- Width at Level 3 is $6 - 0 + 1 = 7$.

The maximum width across all levels is $7$.

---

### Example 3

**Input:**
```text
1 2 3 null 4 5
```

**Output:**
```text
2
```

**Explanation:**
The binary tree structure is:
```text
          1            <-- Level 0: Width = 1
       /     \
      2       3        <-- Level 1: Width = 2
       \     /
        4   5          <-- Level 2: Width = 2
```
At Level 2:
- Node `4` is right child of `2` (conceptual index 1).
- Node `5` is left child of `3` (conceptual index 2).
- Width is $2 - 1 + 1 = 2$.

The maximum width across all levels is $2$.
