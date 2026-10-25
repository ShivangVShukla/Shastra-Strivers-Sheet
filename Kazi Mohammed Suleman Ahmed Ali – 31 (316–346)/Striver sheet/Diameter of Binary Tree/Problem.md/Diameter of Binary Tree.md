# Diameter of Binary Tree

## Problem Statement

Given the root of a binary tree, return the length of the **diameter** of the tree.

The **diameter** of a binary tree is defined as the length of the longest path between any two nodes in the tree. This path may or may not pass through the root.

The length of a path between two nodes is defined as the number of **edges** connecting them (i.e., one less than the number of nodes along the path). For an empty tree or a single-node tree, the diameter is $0$.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input line contains `null` or is empty.

---

## Output Format

Print a single integer on a single line representing the length (number of edges) of the **diameter** of the binary tree.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 10^4$
- $-100 \le \text{Node.val} \le 100$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3 4 5
```

**Output:**
```text
3
```

**Explanation:**
The binary tree has the following structure:
```text
        1
      /   \
     2     3
    / \
   4   5
```
The longest path connects node `4` (or `5`) to node `3`:
- Path: $4 \to 2 \to 1 \to 3$
- Number of edges: $3$

Therefore, the diameter of the tree is $3$.

---

### Example 2

**Input:**
```text
1 2 3 null 4 null 5
```

**Output:**
```text
4
```

**Explanation:**
The binary tree has the following structure:
```text
        1
      /   \
     2     3
      \     \
       4     5
```
The longest path connects node `4` to node `5`:
- Path: $4 \to 2 \to 1 \to 3 \to 5$
- Number of edges: $4$

Therefore, the diameter of the tree is $4$.

---

### Example 3 (Diameter Does Not Pass Through Root)

**Input:**
```text
1 2 null 3 4 5 null null 6
```

**Output:**
```text
4
```

**Explanation:**
The longest path resides entirely within the left subtree rooted at `2`:
```text
            1
          /
        2
       / \
      3   4
     /     \
    5       6
```
The longest path is $5 \to 3 \to 2 \to 4 \to 6$, which has length $4$ edges. It does not pass through the root node `1`.

---

### Example 4 (Empty Tree)

**Input:**
```text
null
```

**Output:**
```text
0
```

**Explanation:**
An empty tree has no edges, so the diameter is $0$.

---

### Example 5 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
0
```

**Explanation:**
There is only one node. The path length between a node and itself is $0$ edges.
