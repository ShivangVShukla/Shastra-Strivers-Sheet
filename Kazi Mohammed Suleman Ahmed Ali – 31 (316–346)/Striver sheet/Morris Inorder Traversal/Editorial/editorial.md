# Editorial: Morris Inorder Traversal

## 1. Problem Overview

We are given the root of a binary tree and must compute its **Inorder Traversal** (`Left Subtree -> Root -> Right Subtree`).
Standard recursive traversal requires $\mathcal{O}(H)$ call stack space, and iterative traversal with an explicit stack requires $\mathcal{O}(N)$ heap space.
**Morris Traversal** achieves the traversal using strictly **$\mathcal{O}(1)$ auxiliary space**, without recursion and without any auxiliary data structure, while fully restoring the original tree structure upon completion.

---

## 2. Core Idea: Threaded Binary Trees

In a standard binary tree, leaf nodes and nodes without right children have `right` pointers set to `null`.
Morris Traversal utilizes these unused `null` pointers to create temporary "threads" pointing back to the inorder successor (the node to visit after the subtree is finished).

For any node `curr`:
1. In an inorder traversal, the node immediately before `curr` is the **inorder predecessor**: the rightmost node of `curr`'s left subtree.
2. If we make `predecessor.right = curr`, we create a return path to `curr` after the entire left subtree has been visited!
3. When returning via this thread, we detect that `predecessor.right == curr`, cut the thread (`predecessor.right = null`), record `curr.val`, and proceed to `curr.right`.

---

## 3. Algorithm

1. Initialize `curr = root` and an empty list `inorder = []`.
2. While `curr != null`:
   - **Case 1: `curr.left == null`**
     - Since there is no left subtree, visit `curr` directly: add `curr.val` to `inorder`.
     - Move to right child: `curr = curr.right`.
   - **Case 2: `curr.left != null`**
     - Find the inorder predecessor:
       ```java
       TreeNode prev = curr.left;
       while (prev.right != null && prev.right != curr) {
           prev = prev.right;
       }
       ```
     - **Subcase 2A: `prev.right == null` (Thread does not exist yet)**
       - Create the temporary thread: `prev.right = curr`.
       - Move to the left child to process the left subtree: `curr = curr.left`.
     - **Subcase 2B: `prev.right == curr` (Thread already exists; left subtree is completely visited)**
       - Remove the temporary thread: `prev.right = null`.
       - Visit `curr`: add `curr.val` to `inorder`.
       - Move to the right child: `curr = curr.right`.
3. Return `inorder`.

---

## 4. Step-by-Step Dry Run (Example 1)

Given Tree:
```text
        1
       /
      4 (A)
     / \
    4   2
   (B)
```

1. **`curr = 1`**:
   - `curr.left = 4(A)` $\ne$ null.
   - Predecessor of 1: start at 4(A), right child is 2. `prev = 2`.
   - `2.right == null` $\implies$ set `2.right = 1`.
   - Move `curr = curr.left` $\implies$ `curr = 4(A)`.

2. **`curr = 4(A)`**:
   - `curr.left = 4(B)` $\ne$ null.
   - Predecessor of 4(A): start at 4(B), `prev = 4(B)`.
   - `4(B).right == null` $\implies$ set `4(B).right = 4(A)`.
   - Move `curr = curr.left` $\implies$ `curr = 4(B)`.

3. **`curr = 4(B)`**:
   - `curr.left == null`.
   - Visit: `inorder.add(4)`. List: `[4]`.
   - Move `curr = curr.right` (follows thread to 4(A)) $\implies$ `curr = 4(A)`.

4. **`curr = 4(A)`**:
   - `curr.left = 4(B)`. Predecessor is 4(B).
   - `4(B).right == 4(A)` (thread exists!).
   - Remove thread: `4(B).right = null`.
   - Visit: `inorder.add(4)`. List: `[4, 4]`.
   - Move `curr = curr.right` $\implies$ `curr = 2`.

5. **`curr = 2`**:
   - `curr.left == null`.
   - Visit: `inorder.add(2)`. List: `[4, 4, 2]`.
   - Move `curr = curr.right` (follows thread to 1) $\implies$ `curr = 1`.

6. **`curr = 1`**:
   - `curr.left = 4(A)`. Predecessor is 2.
   - `2.right == 1` (thread exists!).
   - Remove thread: `2.right = null`.
   - Visit: `inorder.add(1)`. List: `[4, 4, 2, 1]`.
   - Move `curr = curr.right` $\implies$ `curr = null`.

7. Loop terminates. All threads dismantled, tree structure completely restored!
Final Result: `[4, 4, 2, 1]`.

---

## 5. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  Although there is a nested loop to find the predecessor, each edge in the tree is traversed at most **3 times**:
  1. Once when finding predecessor to create the thread.
  2. Once when traversing through the subtree.
  3. Once when finding predecessor to remove the thread.
  Since a tree of $N$ nodes has $N - 1$ edges, total operations are bounded by $3(N - 1) \in \mathcal{O}(N)$.

- **Auxiliary Space Complexity:** $\mathcal{O}(1)$
  Only two pointer variables (`curr` and `prev`) are utilized. Zero call stack frames and zero external stack nodes.

---

## 6. Comparison Summary

| Method | Time Complexity | Auxiliary Space | Tree Structure |
| :--- | :--- | :--- | :--- |
| **Recursive Inorder** | $\mathcal{O}(N)$ | $\mathcal{O}(H)$ stack | Untouched |
| **Iterative with Stack** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ heap | Untouched |
| **Morris Traversal** | $\mathcal{O}(N)$ | $\mathcal{O}(1)$ | Temporarily modified, restored |

---

## 7. Edge Cases Handled

1. **Single Node ($N = 1$):** `curr.left == null`, records node and terminates.
2. **Left Skewed Tree:** Traverses left creating threads at each step, visits from deepest leaf upwards, restoring links.
3. **Right Skewed Tree:** `curr.left` is always null; straightforward linear walk in $\mathcal{O}(N)$ time.
4. **Duplicate Values:** Nodes with identical values are correctly distinguished by pointer identity (`prev.right != curr`).
5. **Negative Values:** All node values $\in [-100, 100]$ recorded accurately.
