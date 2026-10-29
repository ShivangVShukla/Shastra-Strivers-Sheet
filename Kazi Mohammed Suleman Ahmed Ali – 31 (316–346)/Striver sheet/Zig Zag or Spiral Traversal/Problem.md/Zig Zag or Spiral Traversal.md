# Zig Zag or Spiral Traversal

## Problem Statement

Given the root of a binary tree, return the **zigzag level order traversal** of its nodes' values.

In a zigzag (spiral) level order traversal:
- The traversal begins at the root (Level 0) moving from **left to right**.
- At the next level (Level 1), the traversal alternates and moves from **right to left**.
- The direction continues to alternate at each subsequent depth level:
  - Even levels ($0, 2, 4, \dots$): traversed **left to right**.
  - Odd levels ($1, 3, 5, \dots$): traversed **right to left**.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print the 2D array representation of the zigzag level order traversal in standard bracket format:
- Each level is enclosed in square brackets `[...]` with comma-separated values (e.g., `[1, 2, 3]`).
- The entire set of levels is enclosed in outer square brackets `[[...], [...], ...]`.
- If the tree is empty, print `[]`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 10^4$
- $-10^3 \le \text{Node.val} \le 10^3$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3 null 4 8 5
```

**Output:**
```text
[[1], [3, 2], [4, 8, 5]]
```

**Explanation:**
The binary tree has the following structure:
```text
Level 0 (L -> R):        1
                       /   \
Level 1 (R -> L):     2     3
                       \   / \
Level 2 (L -> R):       4 8   5
```
- **Level 0 (Left to Right):** `[1]`
- **Level 1 (Right to Left):** `[3, 2]`
- **Level 2 (Left to Right):** `[4, 8, 5]`

Combined zigzag traversal: `[[1], [3, 2], [4, 8, 5]]`.

---

### Example 2

**Input:**
```text
3 9 20 null null 15 7
```

**Output:**
```text
[[3], [20, 9], [15, 7]]
```

**Explanation:**
```text
Level 0 (L -> R):        3
                       /   \
Level 1 (R -> L):     9     20
                           /  \
Level 2 (L -> R):         15   7
```
- **Level 0 (Left to Right):** `[3]`
- **Level 1 (Right to Left):** `[20, 9]`
- **Level 2 (Left to Right):** `[15, 7]`

Combined zigzag traversal: `[[3], [20, 9], [15, 7]]`.

---

### Example 3

**Input:**
```text
5 1 2 8 null 4 5 null 6
```

**Output:**
```text
[[5], [2, 1], [8, 4, 5], [6]]
```

**Explanation:**
```text
Level 0 (L -> R):            5
                           /   \
Level 1 (R -> L):         1     2
                         /     / \
Level 2 (L -> R):       8     4   5
                         \
Level 3 (R -> L):         6
```
- Level 0 (L $\to$ R): `[5]`
- Level 1 (R $\to$ L): `[2, 1]`
- Level 2 (L $\to$ R): `[8, 4, 5]`
- Level 3 (R $\to$ L): `[6]`

Output: `[[5], [2, 1], [8, 4, 5], [6]]`.

---

### Example 4 (Empty Tree)

**Input:**
```text
null
```

**Output:**
```text
[]
```

**Explanation:**
An empty binary tree has no nodes, producing an empty list `[]`.

---

### Example 5 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
[[42]]
```

**Explanation:**
A tree consisting solely of the root node has one level containing `[42]`.
