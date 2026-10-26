
# Construct a BT from Preorder and Inorder

## Problem Statement

Given two integer arrays `preorder` and `inorder`, where `preorder` represents the preorder traversal of a binary tree and `inorder` represents the inorder traversal of the same binary tree, construct and return the original binary tree.

All node values are distinct.

- **Preorder Traversal:** Root → Left Subtree → Right Subtree
- **Inorder Traversal:** Left Subtree → Root → Right Subtree

The constructed binary tree should be represented using level-order traversal, with `null` indicating missing children.

## Examples

### Example 1

**Input:**
```text
preorder = [3, 9, 20, 15, 7]
inorder  = [9, 3, 15, 20, 7]
```

**Output:**
```text
[3, 9, 20, null, null, 15, 7]
```

**Explanation:**

The first element of preorder, `3`, is the root. In the inorder traversal, `3` separates the left subtree `[9]` from the right subtree `[15, 20, 7]`.

The left child of `3` is `9`, and the right child is `20`. Node `20` has left child `15` and right child `7`.

The resulting binary tree is:

```text
        3
       / \
      9   20
         /  \
        15   7
```

### Example 2

**Input:**
```text
preorder = [3, 4, 5, 6, 2, 9]
inorder  = [5, 4, 6, 3, 2, 9]
```

**Output:**
```text
[3, 4, 2, 5, 6, null, 9]
```

**Explanation:**

The first element of preorder, `3`, is the root. In the inorder traversal, the elements `[5, 4, 6]` belong to the left subtree, while `[2, 9]` belong to the right subtree.

The left subtree is rooted at `4`, with left child `5` and right child `6`. The right subtree is rooted at `2`, with right child `9`.

The resulting binary tree is:

```text
        3
       / \
      4   2
     / \   \
    5   6   9
```

## Constraints

- `1 <= preorder.length <= 100`
- `inorder.length == preorder.length`
- `-1000 <= preorder[i] <= 1000`
- All values in `preorder` and `inorder` are distinct.
- Both arrays represent traversals of the same binary tree.

## Expected Complexity

- **Time Complexity:** `O(n)` expected, where `n` is the number of nodes.
- **Auxiliary Space Complexity:** `O(n)` for the hash map and recursion stack in the worst case.
