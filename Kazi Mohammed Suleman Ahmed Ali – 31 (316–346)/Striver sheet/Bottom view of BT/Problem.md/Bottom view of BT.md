# Bottom view of BT

## Problem Statement

Given the root of a binary tree, return the **bottom view** of the binary tree.

The **bottom view** of a binary tree is the set of nodes visible when the tree is viewed directly from below:
- The returned node values must be ordered from the **leftmost** horizontal position to the **rightmost** horizontal position.
- If multiple nodes share the same horizontal distance ($\text{HD}$) from the root, only the **bottom-most node** (the deepest node along that vertical line) is visible.
- If two or more nodes share the exact same horizontal distance and the same depth level, consider the node that appears **later** in the level-order (left-to-right) traversal.

---

### Coordinate System & Rules

Assign a **horizontal distance (HD)** to every node in the binary tree:
- The **root** node has $\text{HD} = 0$.
- For any node with horizontal distance $\text{HD}$:
  - Its **left child** has horizontal distance $\text{HD} - 1$.
  - Its **right child** has horizontal distance $\text{HD} + 1$.

During a **level-order (breadth-first) traversal**:
1. Nodes are processed level-by-level from top to bottom, and from left to right within each level.
2. For each node encountered at horizontal distance $\text{HD}$, we **update** the entry for that $\text{HD}$.
3. Because BFS naturally encounters deeper nodes later, each update overwrites any shallower node above it.
4. If two nodes share the same depth and horizontal distance, the node processed later from left to right overwrites the earlier one.
5. The final result consists of the last-seen node for each $\text{HD}$, ordered from the minimum horizontal distance ($\text{HD}_{\min}$) to the maximum horizontal distance ($\text{HD}_{\max}$).

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print the bottom view as a comma-separated list of integers enclosed in square brackets `[...]`:
- Example: `[5, 10, 3, 14, 25]`
- If the tree is empty, print `[]`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 10^4$ (empty tree $N = 0$ is also supported)
- $-10^3 \le \text{Node.val} \le 10^3$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
20 8 22 5 3 null 25 null null 10 14
```

**Output:**
```text
[5, 10, 3, 14, 25]
```

**Explanation:**
The binary tree with horizontal distances ($\text{HD}$) is:
```text
                  20 (HD = 0)
                /             \
          8 (HD = -1)      22 (HD = 1)
          /         \                 \
     5 (HD = -2)  3 (HD = 0)       25 (HD = 2)
                  /         \
            10 (HD = -1)  14 (HD = 1)
```
- $\text{HD} = -2$: Only node `5` $\to 5$.
- $\text{HD} = -1$: Node `8` is above node `10`. Viewed from below, only `10` is visible.
- $\text{HD} = 0$: Node `20` is above node `3`. Viewed from below, only `3` is visible.
- $\text{HD} = 1$: Node `22` is above node `14`. Viewed from below, only `14` is visible.
- $\text{HD} = 2$: Only node `25` $\to 25$.

Result ordered from leftmost to rightmost: `[5, 10, 3, 14, 25]`.

---

### Example 2 (Overlapping Nodes at Same Depth)

**Input:**
```text
20 8 22 5 3 4 25 null null 10 14
```

**Output:**
```text
[5, 10, 4, 14, 25]
```

**Explanation:**
```text
                  20 (HD = 0)
                /             \
          8 (HD = -1)      22 (HD = 1)
          /         \      /          \
     5 (HD = -2)  3 (0)  4 (0)     25 (HD = 2)
                  /         \
            10 (HD = -1)  14 (HD = 1)
```
- At $\text{HD} = 0$, both node `3` and node `4` lie at depth 2.
- Since node `4` appears later in the level-order traversal from left to right, node `4` is considered visible.
- At $\text{HD} = -1$, node `10` is below `8` $\to 10$.
- At $\text{HD} = 1$, node `14` is below `22` $\to 14$.

Result: `[5, 10, 4, 14, 25]`.

---

### Example 3

**Input:**
```text
10 20 30 40 60
```

**Output:**
```text
[40, 20, 60, 30]
```

**Explanation:**
```text
               10 (HD = 0)
             /             \
       20 (HD = -1)     30 (HD = 1)
       /          \
  40 (HD = -2)  60 (HD = 0)
```
- $\text{HD} = -2 \to 40$
- $\text{HD} = -1 \to 20$
- $\text{HD} = 0 \to 60$ (node `60` obscures node `10` from below)
- $\text{HD} = 1 \to 30$

Result: `[40, 20, 60, 30]`.

---

### Example 4 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
[42]
```

**Explanation:**
A single node located at $\text{HD} = 0$ produces `[42]`.

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
