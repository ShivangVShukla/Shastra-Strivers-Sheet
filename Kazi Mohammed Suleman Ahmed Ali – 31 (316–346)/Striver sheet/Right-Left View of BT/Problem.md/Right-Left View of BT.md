# Right/Left View of BT

## Problem Statement

Assuming you are standing on the **right side** of a binary tree, return the values of the nodes you can see, ordered from **top to bottom**.

The **right view** of a binary tree is the set of nodes visible when the tree is viewed from the right side:
- At each depth level (from root depth $0$ downwards), exactly one node is visible: the **rightmost** node at that level.
- If a level has only nodes in the left subtree (because the right subtree does not extend to that depth), the rightmost node among those left subtree nodes is visible.

---

### Left View Follow-up
If viewed from the **left side**, the visible nodes are the **leftmost** node at each depth level from top to bottom.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the root node value.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print the right side view as a comma-separated list of integers enclosed in square brackets `[...]`:
- Example: `[1, 3, 4]`
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
1 2 3 null 5 null 4
```

**Output:**
```text
[1, 3, 4]
```

**Explanation:**
The binary tree structure is:
```text
       1            <-- Level 0 (visible: 1)
     /   \
    2     3         <-- Level 1 (visible: 3)
     \     \
      5     4       <-- Level 2 (visible: 4)
```
- At Level 0: Only node `1` is present $\to 1$
- At Level 1: Nodes `2` and `3` are present $\to 3$ is rightmost
- At Level 2: Nodes `5` and `4` are present $\to 4$ is rightmost

Result: `[1, 3, 4]`

---

### Example 2

**Input:**
```text
1 2 3 6 5 8 4
```

**Output:**
```text
[1, 3, 4]
```

**Explanation:**
The binary tree structure is:
```text
          1         <-- Level 0 (visible: 1)
       /     \
      2       3     <-- Level 1 (visible: 3)
     / \     / \
    6   5   8   4   <-- Level 2 (visible: 4)
```
- Level 0: Node `1` $\to 1$
- Level 1: Nodes `2, 3` $\to 3$ is rightmost
- Level 2: Nodes `6, 5, 8, 4` $\to 4$ is rightmost

Result: `[1, 3, 4]`

---

### Example 3 (Left Subtree Extends Deeper)

**Input:**
```text
5 1 2 8 null 4 5 null 6
```

**Output:**
```text
[5, 2, 5, 6]
```

**Explanation:**
The binary tree structure is:
```text
          5         <-- Level 0 (visible: 5)
       /     \
      1       2     <-- Level 1 (visible: 2)
     /       / \
    8       4   5   <-- Level 2 (visible: 5)
     \
      6             <-- Level 3 (visible: 6)
```
- Level 0: Node `5` $\to 5$
- Level 1: Nodes `1, 2` $\to 2$ is rightmost
- Level 2: Nodes `8, 4, 5` $\to 5$ is rightmost
- Level 3: Node `6` (from `8.right`) is the only node at this level $\to 6$ is visible from the right side

Result: `[5, 2, 5, 6]`
