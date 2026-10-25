# Construct a BT from Preorder and Inorder

## Problem Statement

Given two integer arrays `preorder` and `inorder`, where:
- `preorder` represents the preorder traversal (`Root -> Left -> Right`) of a binary tree.
- `inorder` represents the inorder traversal (`Left -> Root -> Right`) of the same tree.

Construct and return the binary tree using these two traversals.

---

## Input Format

- **Line 1:** An integer array representing `preorder` traversal of the tree.
- **Line 2:** An integer array representing `inorder` traversal of the tree.

Both arrays can be provided either:
- As bracketed comma-separated values (e.g., `[3, 9, 20, 15, 7]` or `preorder = [3, 9, 20, 15, 7]`).
- Or as space-separated integers.

### Example Input Format:
```text
[3, 9, 20, 15, 7]
[9, 3, 15, 20, 7]
```

---

## Output Format

- Print the level-order serialization of the constructed binary tree enclosed in square brackets `[...]`.
- Missing nodes are represented by `null`. Trailing `null` values are omitted.

### Example Output Format:
```text
[3, 9, 20, null, null, 15, 7]
```

---

## Constraints

- $1 \le \text{Number of Nodes} \le 10^4$
- $-10^4 \le \text{Node.val} \le 10^4$
- All values in the given tree are **unique**.
- Each value of `inorder` also appears in `preorder`.
- `preorder` is guaranteed to be the preorder traversal of the tree.
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
preorder = [3, 9, 20, 15, 7]
inorder = [9, 3, 15, 20, 7]
```

**Output:**
```text
[3, 9, 20, null, null, 15, 7]
```

**Explanation:**
- The first element of `preorder` is `3`, which is the root of the tree.
- In `inorder`, `3` divides the tree into:
  - Left subtree: elements `[9]`.
  - Right subtree: elements `[15, 20, 7]`.
- Recursively repeating this builds the binary tree shown above.

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
preorder = [3, 4, 5, 6, 2, 9]
inorder = [5, 4, 6, 3, 2, 9]
```

**Output:**
```text
[3, 4, 2, 5, 6, null, 9]
```

**Explanation:**
- Root is `3`.
- In `inorder`, elements before `3` are `[5, 4, 6]` (left subtree).
- Elements after `3` are `[2, 9]` (right subtree).
- Subtrees are recursively constructed.

---

### Example 3

```text
Constructed Binary Tree:
        1
       / \
      4   5
       \
        2
```

**Input:**
```text
preorder = [1, 4, 2, 5]
inorder = [4, 2, 1, 5]
```

**Output:**
```text
[1, 4, 5, null, 2]
```

**Explanation:**
- Root is `1`.
- Left subtree inorder is `[4, 2]`, preorder is `[4, 2]`.
- Right subtree inorder is `[5]`, preorder is `[5]`.
- For the left subtree, root is `4`, with right child `2`.
