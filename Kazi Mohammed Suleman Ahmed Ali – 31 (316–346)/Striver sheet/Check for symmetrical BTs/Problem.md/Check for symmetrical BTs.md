# Check for symmetrical BTs

## Problem Statement

Given the root of a binary tree, check whether it is a **mirror of itself** (i.e., symmetric around its center).

A binary tree is defined to be **symmetric** if and only if:
1. The root's left subtree is a mirror reflection of its right subtree.
2. For every pair of mirrored nodes:
   - Their values are equal ($L.\text{val} == R.\text{val}$).
   - The left child of $L$ mirrors the right child of $R$.
   - The right child of $L$ mirrors the left child of $R$.

An empty tree (`null`) or a single-node tree is considered symmetric by definition.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input line contains `null` or is empty.

---

## Output Format

Print `true` if the binary tree is symmetric around its vertical axis; otherwise, print `false`.

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
1 2 2 3 4 4 3
```

**Output:**
```text
true
```

**Explanation:**
The binary tree has the following structure:
```text
        1
      /   \
     2     2
    / \   / \
   3   4 4   3
```
Drawing a vertical mirror axis through the root `1`:
- Left child `2` mirrors right child `2` ($2 == 2$).
- Outer pair: left-left `3` mirrors right-right `3` ($3 == 3$).
- Inner pair: left-right `4` mirrors right-left `4` ($4 == 4$).

The tree is completely symmetric. The output is `true`.

---

### Example 2

**Input:**
```text
1 2 2 null 3 null 3
```

**Output:**
```text
false
```

**Explanation:**
The binary tree structure is:
```text
        1
      /   \
     2     2
      \     \
       3     3
```
- In the left subtree, node `2` has a right child `3`, but no left child.
- In the right subtree, node `2` has a right child `3`, but no left child.
- Under mirror reflection around the root, the right child of the left node should correspond to the **left child** of the right node. Here, the right child has a right child instead.

The symmetry is broken. The output is `false`.

---

### Example 3

**Input:**
```text
1 2 3
```

**Output:**
```text
false
```

**Explanation:**
The left child has value `2` while the right child has value `3`. Since $2 \ne 3$, the tree is not symmetric.

---

### Example 4 (Empty Tree)

**Input:**
```text
null
```

**Output:**
```text
true
```

**Explanation:**
An empty binary tree has 0 nodes and is symmetric by definition.

---

### Example 5 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
true
```

**Explanation:**
A single node with no children is symmetric around itself.
