
# Preorder Traversal

Given the root of a binary tree, return the **preorder traversal** of the binary tree.

In preorder traversal, visit nodes in the following order:

1. Root
2. Left subtree
3. Right subtree

### Example 1

**Input:**
```text
root = [1, 4, null, 4, 2]
```

**Output:**
```text
[1, 4, 4, 2]
```

**Explanation:**

The root node `1` is visited first, followed by its left child `4`. Then, the left child of that node (`4`) and its right child (`2`) are visited.

### Example 2

**Input:**
```text
root = [1]
```

**Output:**
```text
[1]
```

**Explanation:**

Only the root node is present, so the preorder traversal contains only `1`.

### Constraints

- The number of nodes in the binary tree is between `0` and `100`.
- `-100 <= Node.val <= 100`.
- The tree may be empty.

### Expected Complexity

- **Time Complexity:** `O(N)`, where `N` is the number of nodes.
- **Space Complexity:** `O(N)` for the traversal result and recursion stack in the worst case.
