# Construct a BT from Postorder and Inorder

## Problem Statement

Given two integer arrays `inorder` and `postorder`, where:
- `inorder` represents the inorder traversal (`Left -> Root -> Right`) of a binary tree.
- `postorder` represents the postorder traversal (`Left -> Right -> Root`) of the same tree.

Construct and return the binary tree using these two traversals.

---

## Input Format

- **Line 1:** An integer array representing `inorder` traversal of the tree.
- **Line 2:** An integer array representing `postorder` traversal of the tree.

Both arrays can be provided either:
- As bracketed comma-separated values (e.g., `[9, 3, 15, 20, 7]` or `inorder = [9, 3, 15, 20, 7]`).
- Or as space-separated integers.

### Example Input Format:
```text
[9, 3, 15, 20, 7]
[9, 15, 7, 20, 3]
```

---

## Output Format

- Print the level-order serialization of the constructed binary tree enclosed in square brackets `[...]`.
- Missing/null nodes are represented by `null`. Trailing `null` values are omitted.

### Example Output Format:
```text
[3, 9, 20, null, null, 15, 7]
```

---

## Constraints

- $1 \le \text{Number of Nodes} \le 3000$
- $-10^4 \le \text{Node.val} \le 10^4$
- All values in the given tree are **unique**.
- Each value of `inorder` also appears in `postorder`.
- `postorder` is guaranteed to be the postorder traversal of the tree.
- `inorder` is guaranteed to be the inorder traversal of the tree.
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

```text
Constructed Binary Tree:
        3
       / \
      9   20
         /  \
        15   7
```

**Input:**
```text
inorder = [9, 3, 15, 20, 7]
postorder = [9, 15, 7, 20, 3]
```

**Output:**
```text
[3, 9, 20, null, null, 15, 7]
```

**Explanation:**
- The last element of `postorder` is `3`, which is the root of the tree.
- In `inorder`, `3` partitions the tree into:
  - Left subtree: elements `[9]`.
  - Right subtree: elements `[15, 20, 7]`.
- Recursively reconstructing each subtree gives the binary tree shown above.

---

### Example 2

```text
Constructed Binary Tree:
        3
       / \
      4   2
     / \   \
    5   6   9
```

**Input:**
```text
inorder = [5, 4, 6, 3, 2, 9]
postorder = [5, 6, 4, 9, 2, 3]
```

**Output:**
```text
[3, 4, 2, 5, 6, null, 9]
```

**Explanation:**
- Root is `3` (last element of postorder).
- In `inorder`, elements before `3` are `[5, 4, 6]` (left subtree).
- Elements after `3` are `[2, 9]` (right subtree).
- Subtrees are recursively constructed.

---

### Example 3

```text
Constructed Binary Tree:
        1
       / \
      2   null
     /
    4
   /
  5
```

**Input:**
```text
inorder = [5, 4, 2, 1]
postorder = [4, 2, 5, 1]
```

**Output:**
```text
[1, 2, null, 4, null, 5]
```

**Explanation:**
- Root is `1` (last element of postorder).
- In `inorder`, `[5, 4, 2]` are in the left subtree, and right subtree is empty.
- Subtrees are recursively reconstructed.
