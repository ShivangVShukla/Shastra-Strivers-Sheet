# Search in BST

## Problem Statement

Given the `root` of a **Binary Search Tree (BST)** and an integer `val`.

Find the node in the BST whose value equals `val` and return the subtree rooted with that node. If such a node does not exist in the BST, return `null`.

A **Binary Search Tree** satisfies the property that for every node:
- All values in its left subtree are strictly **less than** the node's value.
- All values in its right subtree are strictly **greater than** the node's value.
- Both left and right subtrees must also be binary search trees.

---

## Input Format

- **Line 1:** The level-order serialization of the binary search tree enclosed in square brackets `[...]` (or space-separated values).
- **Line 2:** An integer `val` representing the search key.

### Example Input Format:
```text
[4, 2, 7, 1, 3]
2
```

---

## Output Format

- Print the level-order serialization of the subtree rooted at the found node enclosed in square brackets `[...]`.
- If the node is not found (`null`), print `[]`.

### Example Output Format:
```text
[2, 1, 3]
```

---

## Constraints

- $1 \le \text{Number of Nodes} \le 5000$
- $1 \le \text{Node.val} \le 10^7$
- $1 \le \text{val} \le 10^7$
- All node values in the BST are **unique**.
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

```text
Given BST:
        4
       / \
      2   7
     / \
    1   3

Search Value: val = 2

Subtree Rooted at 2:
      2
     / \
    1   3
```

**Input:**
```text
[4, 2, 7, 1, 3]
2
```

**Output:**
```text
[2, 1, 3]
```

**Explanation:**
Node with value `2` exists in the BST. Its left child is `1` and right child is `3`. Returning the subtree rooted at `2` gives `[2, 1, 3]`.

---

### Example 2

```text
Given BST:
        4
       / \
      2   7
     / \
    1   3

Search Value: val = 5
```

**Input:**
```text
[4, 2, 7, 1, 3]
5
```

**Output:**
```text
[]
```

**Explanation:**
Value `5` is not present in the BST, so `null` is returned (represented as `[]`).

---

### Example 3

```text
Given BST:
         10
        /  \
       2    12
      / \
     1   4
        /
       3

Search Value: val = 2
```

**Input:**
```text
[10, 2, 12, 1, 4, null, null, null, null, 3]
2
```

**Output:**
```text
[2, 1, 4, null, null, 3]
```

**Explanation:**
Node `2` is found. Its left child is `1`, its right child is `4`, and `4` has left child `3`. The level-order serialization of this subtree is `[2, 1, 4, null, null, 3]`.
