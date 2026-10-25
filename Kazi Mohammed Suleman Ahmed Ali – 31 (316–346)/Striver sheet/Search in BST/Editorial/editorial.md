# Editorial: Search in a Binary Search Tree (BST)

## 1. Problem Overview

We are given the root of a **Binary Search Tree (BST)** and a target integer `val`.
Our task is to find the node whose value equals `val` and return the complete subtree rooted at that node. If no such node exists in the tree, we return `null`.

---

## 2. Key Insights and Observations

A Binary Search Tree (BST) possesses an ordering invariant for every node `curr`:
- Every node in `curr`'s left subtree has a value strictly **less than** `curr.val`.
- Every node in `curr`'s right subtree has a value strictly **greater than** `curr.val`.

Because of this property:
1. If `val == curr.val`, we have found the target node! Return `curr`.
2. If `val < curr.val`, the target value **cannot possibly exist** in the right subtree. We only need to search the left subtree.
3. If `val > curr.val`, the target value **cannot possibly exist** in the left subtree. We only need to search the right subtree.
4. If we reach `null`, the value is not present in the BST. Return `null`.

Unlike a generic binary tree (which requires searching both subtrees in $\mathcal{O}(N)$ time), a BST allows us to eliminate half of the remaining search space at each step in balanced trees, reducing the search time to $\mathcal{O}(H)$ where $H$ is the height of the tree.

---

## 3. Approaches

### Approach 1: Recursive BST Search

#### Algorithm
```java
public TreeNode searchBST(TreeNode root, int val) {
    if (root == null || root.val == val) {
        return root;
    }
    if (val < root.val) {
        return searchBST(root.left, val);
    } else {
        return searchBST(root.right, val);
    }
}
```

#### Complexity
- **Time Complexity:** $\mathcal{O}(H)$ — At most $H$ nodes visited along a single path from root to leaf.
  - Balanced BST: $\mathcal{O}(\log N)$
  - Skewed BST: $\mathcal{O}(N)$
- **Space Complexity:** $\mathcal{O}(H)$ — Recursion call stack up to tree height $H$.

---

### Approach 2: Iterative BST Search (Optimal & Stack-Safe)

#### Algorithm
```java
public TreeNode searchBST(TreeNode root, int val) {
    TreeNode curr = root;
    while (curr != null && curr.val != val) {
        if (val < curr.val) {
            curr = curr.left;
        } else {
            curr = curr.right;
        }
    }
    return curr;
}
```

#### Complexity
- **Time Complexity:** $\mathcal{O}(H)$ — Visits at most $H$ nodes.
- **Auxiliary Space Complexity:** $\mathcal{O}(1)$ — Uses a single pointer `curr`. Zero heap allocation and zero call stack frames, making it 100% stack-safe even for deeply skewed trees of size $N = 5000$.

---

## 4. Step-by-Step Dry Run

### Dry Run 1: `val = 2` (Found)
Tree:
```text
        4
       / \
      2   7
     / \
    1   3
```

1. `curr = 4`:
   - `val (2) != 4`. Since `2 < 4`, move `curr = curr.left` $\implies$ `curr = 2`.
2. `curr = 2`:
   - `val (2) == 2`! Match found.
3. Return `curr` (node 2).
The subtree rooted at 2 contains `[2, 1, 3]`.

---

### Dry Run 2: `val = 5` (Not Found)
1. `curr = 4`:
   - `5 > 4` $\implies$ move `curr = curr.right` $\implies$ `curr = 7`.
2. `curr = 7`:
   - `5 < 7` $\implies$ move `curr = curr.left` $\implies$ `curr = null`.
3. `curr == null` $\implies$ loop terminates. Return `null` (`[]`).

---

## 5. Comparison Summary

| Metric | Recursive Search | Iterative Search |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(H)$ | $\mathcal{O}(H)$ |
| **Auxiliary Space** | $\mathcal{O}(H)$ call stack | **$\mathcal{O}(1)$** (constant) |
| **Stack Safety** | Risks StackOverflow on skewed trees | **100% stack-safe** |
| **Code Length** | ~6 lines | ~6 lines |

---

## 6. Edge Cases Handled

1. **Root is Target:** `root.val == val` $\implies$ immediately returns entire tree.
2. **Target is a Leaf:** Traverses to a leaf node and returns a subtree with `null` children.
3. **Target Not in Tree:** Correctly returns `null` (`[]`).
4. **Single Node Tree ($N = 1$):** Returns node if matched, else `null`.
5. **Completely Skewed BST (Left or Right):** Traverses linearly without stack overflow in $\mathcal{O}(1)$ extra space.
6. **Large Values ($1 \le \text{val} \le 10^7$):** Fits standard 32-bit signed integer without overflow.
