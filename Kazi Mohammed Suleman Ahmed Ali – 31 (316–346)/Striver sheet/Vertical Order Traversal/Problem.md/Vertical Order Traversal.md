# Vertical Order Traversal

## Problem Statement

Given the root of a binary tree, calculate the **vertical order traversal** of the binary tree.

### Coordinate System & Rules

For each node at coordinates $(\text{col}, \text{row})$:
- The **root** node is located at $(0, 0)$.
- Its **left child** is located at $(\text{col} - 1, \text{row} + 1)$.
- Its **right child** is located at $(\text{col} + 1, \text{row} + 1)$.

The vertical order traversal is a list of top-to-bottom node values for each vertical column, ordered from the leftmost column to the rightmost column:
1. **Primary Ordering:** Nodes are grouped by their column index from smallest (leftmost) to largest (rightmost).
2. **Secondary Ordering:** Within the same column, nodes are ordered by their row index from top to bottom (increasing row index).
3. **Tertiary Ordering (Tie-Breaking):** If two or more nodes have the **exact same row and column coordinates**, they must be sorted in **ascending order of their values**.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print the vertical order traversal as a 2D list enclosed in square brackets `[[...], [...], ...]`:
- Each column is enclosed in square brackets with comma-separated values (e.g., `[9]`, `[3, 15]`).
- The entire collection of columns is ordered from the leftmost vertical line to the rightmost vertical line.
- If the tree is empty, print `[]`.

---

## Constraints

- $0 \le \text{Number of Nodes } (N) \le 10^4$
- $-10^3 \le \text{Node.val} \le 10^3$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
3 9 20 null null 15 7
```

**Output:**
```text
[[9], [3, 15], [20], [7]]
```

**Explanation:**
The binary tree with coordinates $(\text{col}, \text{row})$ is:
```text
                 3 (0, 0)
               /          \
        9 (-1, 1)        20 (1, 1)
                        /         \
                 15 (0, 2)       7 (2, 2)
```
- Column `-1`: `[9]`
- Column `0`: `[3, 15]` (ordered top-to-bottom: row 0 before row 2)
- Column `1`: `[20]`
- Column `2`: `[7]`

Result: `[[9], [3, 15], [20], [7]]`.

---

### Example 2 (Overlapping Nodes with Tie-Breaking)

**Input:**
```text
1 2 3 4 5 6 7
```

**Output:**
```text
[[4], [2], [1, 5, 6], [3], [7]]
```

**Explanation:**
```text
                 1 (0, 0)
              /            \
        2 (-1, 1)         3 (1, 1)
        /       \         /       \
    4 (-2, 2)  5 (0, 2) 6 (0, 2)  7 (2, 2)
```
- Column `-2`: `[4]`
- Column `-1`: `[2]`
- Column `0`: Node `1` is at `(0, 0)`. Both node `5` and node `6` are at `(0, 2)`.
  - Since `5` and `6` share the same row `2` and column `0`, they are sorted in ascending order: `5 < 6`.
  - Column `0` becomes: `[1, 5, 6]`.
- Column `1`: `[3]`
- Column `2`: `[7]`

Result: `[[4], [2], [1, 5, 6], [3], [7]]`.

---

### Example 3 (Overlapping Nodes with Inverted Insertion Order)

**Input:**
```text
1 2 3 4 6 5 7
```

**Output:**
```text
[[4], [2], [1, 5, 6], [3], [7]]
```

**Explanation:**
Here, node `6` is the right child of `2`, and node `5` is the left child of `3`.
Both are at coordinate `(col = 0, row = 2)`.
Even though node `6` is processed before node `5` during level-order traversal, the value sorting rule requires that `5` precedes `6` because $5 < 6$.
Column `0` produces `[1, 5, 6]`.

---

### Example 4 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
[[42]]
```

**Explanation:**
A single node located at `(0, 0)` produces `[[42]]`.

---

### Example 5 (Empty Tree)

**Input:**
```text
null
```

**Output:**
```text
[]
```

**Explanation:**
An empty binary tree has 0 nodes, producing `[]`.
