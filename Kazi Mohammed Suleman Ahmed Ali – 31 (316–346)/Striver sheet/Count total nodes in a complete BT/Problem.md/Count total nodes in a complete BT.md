# Count total nodes in a complete BT

## Problem Statement

Given the root of a **complete binary tree**, return the total **number of nodes** in the tree.

According to Wikipedia, every level in a complete binary tree, possibly with the exception of the final one, is completely filled, and all nodes in the last level are as far left as possible. It can have between $1$ and $2^h$ nodes inclusive at the last level $h$.

Design an algorithm that runs in less than $\mathcal{O}(N)$ time complexity.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the complete binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` or `N` indicates that the corresponding child does not exist.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print a single integer representing the total number of nodes in the binary tree:
- Example: `6`
- If the tree is empty, print `0`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 5 \times 10^4$ (empty tree $N = 0$ is also supported)
- $-10^5 \le \text{Node.val} \le 10^5$
- The tree is guaranteed to be **complete**.
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3 4 5 6
```

**Output:**
```text
6
```

**Explanation:**
The binary tree structure is:
```text
          1
       /     \
      2       3
     / \     /
    4   5   6
```
There are $6$ nodes in total.

---

### Example 2

**Input:**
```text
1 2 3 4 5 6 7 8 9
```

**Output:**
```text
9
```

**Explanation:**
The binary tree structure is:
```text
             1
          /     \
         2       3
       /   \    /  \
      4     5  6    7
     / \
    8   9
```
There are $9$ nodes in total.

---

### Example 3

**Input:**
```text
1 2 3
```

**Output:**
```text
3
```

**Explanation:**
The binary tree structure is:
```text
      1
    /   \
   2     3
```
This is a perfect binary tree of height 2 with $2^2 - 1 = 3$ nodes.
