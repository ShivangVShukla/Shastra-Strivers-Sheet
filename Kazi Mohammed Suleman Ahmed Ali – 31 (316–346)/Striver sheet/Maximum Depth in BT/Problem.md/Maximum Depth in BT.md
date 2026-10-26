# Maximum Depth in BT

## Problem Statement

Given the root of a binary tree, determine and return its **maximum depth**.

The **maximum depth** of a binary tree is the number of nodes along the longest path from the root node down to the farthest leaf node. A leaf is a node with no children. If the tree is empty (i.e., the root is `null`), its maximum depth is defined to be `0`.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in first-in, first-out (FIFO) order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is completely empty, the input line contains `null` or is empty.

---

## Output Format

Print a single integer on a single line denoting the **maximum depth** of the binary tree.

---

## Constraints

- $0 \le \text{Number of Nodes } (N) \le 10^4$
- $0 \le \text{Node.val} \le 10^4$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3 null null null 6
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
            \
             6
```
The paths from the root to each leaf are:
- Path $1 \to 2$: length = 2 nodes.
- Path $1 \to 3 \to 6$: length = 3 nodes.

The longest path contains $3$ nodes, so the maximum depth is $3$.

---

### Example 2

**Input:**
```text
3 9 20 null null 15 7
```

**Output:**
```text
3
```

**Explanation:**
The binary tree has the following structure:
```text
        3
      /   \
     9     20
          /  \
         15   7
```
The longest paths are $3 \to 20 \to 15$ and $3 \to 20 \to 7$, each containing $3$ nodes. The maximum depth is $3$.

---

### Example 3

**Input:**
```text
5 1 2 8 null null 5 null 4 null null 7
```

**Output:**
```text
5
```

**Explanation:**
The binary tree structure is:
```text
            5
          /   \
         1     2
        /       \
       8         5
        \
         4
          \
           7
```
The longest path traverses $5 \to 1 \to 8 \to 4 \to 7$, comprising $5$ nodes. Hence, the maximum depth is $5$.

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
There are no nodes in the tree, so the maximum depth is $0$.

---

### Example 5 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
1
```

**Explanation:**
The root node is itself the only leaf. The path length is $1$ node.
