# Top View of BT

## Problem Statement

Given the root of a binary tree, return the **top view** of the binary tree.

The **top view** of a binary tree consists of the set of nodes visible when the tree is viewed directly from above.
- The returned node values must be ordered from the **leftmost** horizontal position to the **rightmost** horizontal position.
- If multiple nodes share the same horizontal distance from the root, only the node that appears **first** when traversing the tree from top to bottom (and left to right at the same level) is visible and included in the result.

---

### Coordinate System & Rules

Assign a **horizontal distance (HD)** to every node in the binary tree:
- The **root** node has $\text{HD} = 0$.
- For any node with horizontal distance $\text{HD}$:
  - Its **left child** has horizontal distance $\text{HD} - 1$.
  - Its **right child** has horizontal distance $\text{HD} + 1$.

During a **level-order (breadth-first) traversal**:
1. At each horizontal distance $\text{HD}$, the **first node encountered** corresponds to the topmost visible node at that vertical line.
2. Subsequent nodes encountered at the same $\text{HD}$ are obscured by the topmost node and are excluded.
3. The final result consists of the first-seen node for each $\text{HD}$, ordered from the minimum horizontal distance ($\text{HD}_{\min}$) to the maximum horizontal distance ($\text{HD}_{\max}$).

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print the top view as a comma-separated list of integers enclosed in square brackets `[...]`:
- Example: `[4, 2, 1, 3, 7]`
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
1 2 3 4 5 6 7
```

**Output:**
```text
[4, 2, 1, 3, 7]
```

**Explanation:**
The binary tree with horizontal distances ($\text{HD}$) is:
```text
                1 (HD = 0)
              /            \
        2 (HD = -1)     3 (HD = 1)
        /         \     /        \
    4 (HD = -2) 5(0)  6(0)    7 (HD = 2)
```
- $\text{HD} = -2$: First seen is `4`.
- $\text{HD} = -1$: First seen is `2`.
- $\text{HD} = 0$: First seen is `1` (nodes `5` and `6` at HD = 0 are hidden below `1`).
- $\text{HD} = 1$: First seen is `3`.
- $\text{HD} = 2$: First seen is `7`.

Result ordered from leftmost to rightmost: `[4, 2, 1, 3, 7]`.

---

### Example 2

**Input:**
```text
10 20 30 40 60 90 100
```

**Output:**
```text
[40, 20, 10, 30, 100]
```

**Explanation:**
```text
                 10 (HD = 0)
               /             \
        20 (HD = -1)      30 (HD = 1)
        /          \      /         \
    40 (HD = -2) 60(0)  90(0)   100 (HD = 2)
```
Top view nodes:
- $\text{HD} = -2 \to 40$
- $\text{HD} = -1 \to 20$
- $\text{HD} = 0 \to 10$
- $\text{HD} = 1 \to 30$
- $\text{HD} = 2 \to 100$

Result: `[40, 20, 10, 30, 100]`.

---

### Example 3

**Input:**
```text
5 1 2 8 null 4 5 null 6
```

**Output:**
```text
[8, 1, 5, 2, 5]
```

**Explanation:**
The tree structure is:
```text
             5 (HD = 0)
           /            \
     1 (HD = -1)     2 (HD = 1)
     /              /          \
 8 (HD = -2)     4 (HD = 0)  5 (HD = 2)
   \
    6 (HD = -1)
```
- Level 0: Node `5` ($\text{HD} = 0$) $\to$ first at $\text{HD} = 0$.
- Level 1: Node `1` ($\text{HD} = -1$) $\to$ first at $\text{HD} = -1$.
  Node `2` ($\text{HD} = 1$) $\to$ first at $\text{HD} = 1$.
- Level 2: Node `8` ($\text{HD} = -2$) $\to$ first at $\text{HD} = -2$.
  Node `4` ($\text{HD} = 0$) $\to$ already occupied by `5`.
  Node `5` ($\text{HD} = 2$) $\to$ first at $\text{HD} = 2$.
- Level 3: Node `6` ($\text{HD} = -1$) $\to$ already occupied by `1`.

Collecting from $\text{HD} = -2$ to $2$: `[8, 1, 5, 2, 5]`.

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
Only the root node exists at $\text{HD} = 0$. The top view is `[42]`.

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
