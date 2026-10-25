# Flatten Binary Tree to Linked List

## Problem Statement

You are given the `root` of a binary tree.

Re-arrange the tree **in-place** so that it becomes a singly linked list following the order of a **preorder traversal**:

- Each node's `right` pointer must point to the next node in the preorder sequence.
- Each node's `left` pointer must be set to `null`.
- The relative order of nodes must be exactly the preorder traversal sequence of the original tree.
- The transformation must be done on the original tree structure; **do not create any new nodes**.

---

## Input Format

- The input consists of a single line representing the level-order serialization of the binary tree.
- The sequence is enclosed in square brackets `[...]` with comma-separated values (or space-separated values).
- An empty tree is represented by `[]`.
- Missing/null nodes are indicated by `null`.

### Example Input Format:
```text
[1,2,5,3,4,null,6]
```

---

## Output Format

- Print the level-order serialization of the flattened binary tree enclosed in square brackets `[...]`.
- If the tree is empty, print `[]`.

### Example Output Format:
```text
[1,null,2,null,3,null,4,null,5,null,6]
```

---

## Constraints

- $0 \le \text{number of nodes} \le 2000$
- $-100 \le \text{Node.val} \le 100$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

```text
Original Tree:
        1
       / \
      2   5
     / \   \
    3   4   6

Preorder Traversal: 1 -> 2 -> 3 -> 4 -> 5 -> 6

Flattened Tree:
    1
     \
      2
       \
        3
         \
          4
           \
            5
             \
              6
```

**Input:**
```text
[1,2,5,3,4,null,6]
```

**Output:**
```text
[1,null,2,null,3,null,4,null,5,null,6]
```

**Explanation:**
The preorder traversal of the original tree is `1 -> 2 -> 3 -> 4 -> 5 -> 6`. Each node's `left` pointer is set to `null` and `right` pointer points to the next node in the preorder sequence.

---

### Example 2

**Input:**
```text
[]
```

**Output:**
```text
[]
```

**Explanation:**
An empty tree has $0$ nodes, so the result remains empty `[]`.

---

### Example 3

```text
Original Tree:
        0
```

**Input:**
```text
[0]
```

**Output:**
```text
[0]
```

**Explanation:**
A single node tree has only root node `0`. Both `left` and `right` are `null`.

---

### Example 4

```text
Original Tree:
        1
       / \
      2   3
     /     \
    4       5

Preorder Traversal: 1 -> 2 -> 4 -> 3 -> 5
```

**Input:**
```text
[1,2,3,4,null,null,5]
```

**Output:**
```text
[1,null,2,null,4,null,3,null,5]
```

**Explanation:**
The preorder order is `1, 2, 4, 3, 5`. The flattened tree forms a right-skewed spine `1 -> 2 -> 4 -> 3 -> 5`.
