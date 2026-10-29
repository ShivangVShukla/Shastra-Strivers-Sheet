# Editorial: Lowest Common Ancestor in Binary Tree

## 1. Problem Overview

Given the root of a binary tree and two target nodes $p$ and $q$, find their **Lowest Common Ancestor (LCA)**.
- The LCA of two nodes $p$ and $q$ is defined as the deepest (lowest) node in the tree that has both $p$ and $q$ as descendants.
- A node is considered a descendant of itself (i.e. if $p$ is an ancestor of $q$, then the LCA is $p$).
- All node values in the tree are unique, and both $p$ and $q$ are guaranteed to exist in the tree.

---

## 2. Approach 1: Recursive DFS (Divide & Conquer)

### Intuition:
We can perform a bottom-up post-order traversal:
1. If the current `root` is `null`, or if `root == p`, or if `root == q`, return `root`.
2. Recursively search in the left subtree: `TreeNode left = lowestCommonAncestor(root.left, p, q)`.
3. Recursively search in the right subtree: `TreeNode right = lowestCommonAncestor(root.right, p, q)`.
4. **Evaluate results:**
   - **Case 1: Both `left` and `right` are non-null.**
     This indicates that $p$ was found in one subtree and $q$ was found in the other. Therefore, the current `root` is the split point and must be the LCA!
   - **Case 2: Only one of `left` or `right` is non-null.**
     Both nodes $p$ and $q$ (or one node containing the other as a descendant) reside entirely in that subtree. Propagate the non-null result upwards.
   - **Case 3: Both `left` and `right` are null.**
     Neither $p$ nor $q$ exists in the subtrees rooted at `root`. Return `null`.

```java
public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
    if (root == null || root == p || root == q) {
        return root;
    }
    TreeNode left = lowestCommonAncestor(root.left, p, q);
    TreeNode right = lowestCommonAncestor(root.right, p, q);
    if (left != null && right != null) {
        return root;
    }
    return left != null ? left : right;
}
```

---

## 3. Approach 2: Iterative Parent Pointers (100% Stack-Safe)

### Why Iterative?
The problem constraint allows up to $N = 10^5$ nodes. On a degenerate linear tree (e.g. a skewed chain of depth $10^5$), recursive DFS will exceed Java's default thread stack limit and throw a `StackOverflowError`.
An iterative BFS using parent pointers operates entirely on the heap and handles trees of any depth safely.

### Algorithm:
1. Use an iterative BFS queue to traverse the tree. Maintain a `Map<TreeNode, TreeNode> parent` mapping each child to its parent node.
2. Terminate the traversal as soon as both $p$ and $q$ have parent entries.
3. Build an ancestor set for $p$:
   - Starting from $p$, repeatedly move to `parent.get(curr)` until reaching the root, adding each node to a `Set<TreeNode> ancestors`.
4. Walk up from $q$:
   - Starting from $q$, check if `curr` is present in `ancestors`.
   - The first node that matches is the Lowest Common Ancestor. Return it immediately.

---

## 4. Detailed Trace (Example 1)

```text
          3
       /     \
      5       1
     / \     / \
    6   2   0   8
       / \
      7   4
```
Target nodes: $p = 5$, $q = 1$.

1. Starting at root `3`:
   - Left subtree of `3` has root `5`: Since `root == p` (5), it returns `5`.
   - Right subtree of `3` has root `1`: Since `root == q` (1), it returns `1`.
2. At root `3`:
   - `left` returned `5` (non-null).
   - `right` returned `1` (non-null).
   - Both are non-null $\implies$ LCA is `3`.

---

## 5. Complexity Analysis

| Metric | Recursive DFS | Iterative Parent Pointers |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ — Each node visited once in worst case. | $\mathcal{O}(N)$ — Traversal stops early once both nodes are found. |
| **Space Complexity** | $\mathcal{O}(H)$ — Recursion stack proportional to height. | $\mathcal{O}(N)$ — Parent map and ancestor set on heap. |
| **Stack Safety** | May overflow stack if $H = 10^5$. | **100% Stack-Safe** on JVM heap. |
