# Maximum path sum

## Problem Statement

In a binary tree, a **path** is defined as a sequence of nodes where each consecutive pair of nodes in the sequence is connected by an edge. Any node may appear at most once in the sequence (simple path).

The **path sum** is the sum of the node values along the path. Given the root of a binary tree, determine and return the **largest path sum** among all possible non-empty paths.

**Note:** The optimal path does not necessarily have to pass through the root node of the tree.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input line contains `null` or is empty.

---

## Output Format

Print a single integer on a single line representing the **maximum path sum** among all non-empty paths in the binary tree.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 3 \times 10^4$
- $-10^3 \le \text{Node.val} \le 10^3$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
20 9 -10 null null 15 7
```

**Output:**
```text
34
```

**Explanation:**
The binary tree structure is:
```text
          20
        /    \
       9     -10
             /  \
            15   7
```
The optimal path connects node `15` to node `9`:
- Path: $15 \to -10 \to 20 \to 9$
- Sum: $15 + (-10) + 20 + 9 = 34$

This yields the maximum possible path sum in the tree.

---

### Example 2

**Input:**
```text
-10 9 20 null null 15 7
```

**Output:**
```text
42
```

**Explanation:**
The binary tree structure is:
```text
         -10
        /    \
       9      20
             /  \
            15   7
```
The optimal path is contained entirely within the right subtree:
- Path: $15 \to 20 \to 7$
- Sum: $15 + 20 + 7 = 42$

Notice that this path does not include the root node `-10`, as adding it would reduce the path sum.

---

### Example 3

**Input:**
```text
1 2 -3 -4 null 5 8
```

**Output:**
```text
10
```

**Explanation:**
The binary tree structure is:
```text
          1
        /   \
       2     -3
      /      /  \
    -4      5    8
```
The optimal path resides within the right subtree:
- Path: $5 \to -3 \to 8$
- Sum: $5 + (-3) + 8 = 10$

---

### Example 4 (All Negative Nodes)

**Input:**
```text
-3 -2 -5
```

**Output:**
```text
-2
```

**Explanation:**
All node values are negative. The path consisting solely of the single node `-2` yields the maximum sum:
- Sum: $-2$
