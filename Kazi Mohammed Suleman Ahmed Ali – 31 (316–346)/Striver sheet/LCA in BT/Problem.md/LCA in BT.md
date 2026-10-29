# LCA in BT

## Problem Statement

Given the root of a binary tree, find the **lowest common ancestor (LCA)** of two given nodes, $p$ and $q$, in the tree.

According to the definition of LCA on Wikipedia:
> The **lowest common ancestor** is defined between two nodes $p$ and $q$ as the lowest node in $T$ that has both $p$ and $q$ as descendants (where we allow a node to be a descendant of itself).

**Note:** The function should return the `TreeNode` reference itself, not just its value.

---

## Input Format

The input is provided across lines as follows:
- **Line 1:** Space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree.
  - The first token represents the value of the root node.
  - Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
  - The token `null` indicates that the corresponding child does not exist.
- **Line 2:** An integer representing the value of node $p$.
- **Line 3:** An integer representing the value of node $q$.

*(The solution also flexibly parses inputs where $p$ and $q$ are specified on lines 1 and 2 and the tree on line 3, or where $p$ and $q$ are provided on a single line).*

---

## Output Format

Print a single integer representing the value of the lowest common ancestor node.
- Example: `3`

---

## Constraints

- $2 \le \text{Number of Nodes } (N) \le 10^5$
- $-10^6 \le \text{node.val} \le 10^6$
- All values in the tree are **unique**.
- $p \neq q$
- Both $p$ and $q$ are guaranteed to exist in the tree.
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
3 5 1 6 2 0 8 null null 7 4
5
1
```

**Output:**
```text
3
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
- Node `5` is in the left subtree of root `3`.
- Node `1` is in the right subtree of root `3`.
- The lowest node that has both `5` and `1` as descendants is `3`.

---

### Example 2

**Input:**
```text
3 5 1 6 2 0 8 null null 7 4
5
4
```

**Output:**
```text
5
```

**Explanation:**
- Node `5` is the ancestor of node `4` (`5 -> 2 -> 4`).
- By definition, a node is allowed to be a descendant of itself.
- Thus, the LCA of `5` and `4` is `5`.

---

### Example 3

**Input:**
```text
7 1 2 8 10 4 5 null 6
6
10
```

**Output:**
```text
1
```

**Explanation:**
The binary tree structure is:
```text
            7
         /     \
        1       2
       / \     / \
      8   10  4   5
       \
        6
```
- Node `6` is a descendant of `8`, which is a descendant of `1`.
- Node `10` is a direct right child of `1`.
- The lowest common ancestor of `6` and `10` is `1`.
