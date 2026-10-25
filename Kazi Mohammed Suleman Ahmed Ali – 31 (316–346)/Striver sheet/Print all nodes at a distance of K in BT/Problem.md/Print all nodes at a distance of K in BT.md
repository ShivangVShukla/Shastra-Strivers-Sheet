# Print all nodes at a distance of K in BT

## Problem Statement

Given the **root** of a binary tree, the value of a target node **target**, and an integer **k**. Return an array of the values of all nodes that have a distance **k** from the **target node**.

The distance between two nodes in a binary tree is the number of edges along the shortest path connecting them. You may traverse upwards to parent nodes as well as downwards to left and right child nodes.

The answer can be returned in any order. For deterministic output, values in the result are sorted in ascending order. Both `null` and `N` are supported as indicators of missing child nodes.

---

## Input Format

The input is provided across lines as follows:
- **Line 1:** Space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree.
  - The first token represents the value of the root node.
  - Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
  - The token `null` or `N` indicates that the corresponding child does not exist.
- **Line 2:** An integer representing the value of the **target** node.
- **Line 3:** An integer representing the distance **k**.

*(The solution also flexibly parses inputs where $k$ is on line 1, root is on line 2, and target is on line 3).*

---

## Output Format

Print the values of all nodes at distance $k$ formatted as a comma-separated list enclosed in square brackets `[...]`:
- Example: `[1, 4, 7]`
- If no nodes exist at distance $k$, print `[]`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 10^3$ (empty tree $N = 0$ is also supported)
- $-10^4 \le \text{Node.val} \le 10^4$
- All the values `Node.val` are **unique**.
- `target` is guaranteed to be the value of one of the nodes in the tree.
- $0 \le k \le 10^3$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
3 5 1 6 2 0 8 N N 7 4
5
2
```

**Output:**
```text
[1, 4, 7]
```

**Explanation:**
The binary tree structure is:
```text
          3
       /     \
      5       1
     / \     / \
    6   2   0   8
       / \
      7   4
```
Target is node `5`, and distance $k = 2$:
- Move down via `2`:
  - `5 -> 2 -> 7` (distance 2, node `7`)
  - `5 -> 2 -> 4` (distance 2, node `4`)
- Move up to parent `3`, then to right child `1`:
  - `5 -> 3 -> 1` (distance 2, node `1`)

Nodes at distance 2: `[1, 4, 7]`.

---

### Example 2

**Input:**
```text
3 5 1 6 2 0 8 N N 7 4
5
3
```

**Output:**
```text
[0, 8]
```

**Explanation:**
Target is node `5`, and distance $k = 3$:
- `5 -> 3 -> 1 -> 0` (distance 3, node `0`)
- `5 -> 3 -> 1 -> 8` (distance 3, node `8`)

Nodes at distance 3: `[0, 8]`.

---

### Example 3

**Input:**
```text
1 2 3 4 N 5 6
6
2
```

**Output:**
```text
[1, 5]
```

**Explanation:**
The binary tree structure is:
```text
          1
       /     \
      2       3
     /       / \
    4       5   6
```
Target is node `6`, and distance $k = 2$:
- Move up to parent `3` (dist 1):
  - From `3`, move up to `1` (dist 2, node `1`)
  - From `3`, move down to left child `5` (dist 2, node `5`)

Nodes at distance 2: `[1, 5]`.
