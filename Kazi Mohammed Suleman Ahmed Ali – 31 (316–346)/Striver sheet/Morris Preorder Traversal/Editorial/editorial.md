# Editorial: Morris Preorder Traversal

## 1. Problem Overview

We are given the root of a binary tree and must compute its **Preorder Traversal** (`Root -> Left Subtree -> Right Subtree`).
Standard recursion requires $\mathcal{O}(H)$ call stack space, and iterative methods require $\mathcal{O}(N)$ heap space.
**Morris Preorder Traversal** achieves the traversal with strictly **$\mathcal{O}(1)$ auxiliary space**, using temporary threaded links from the inorder predecessor back to the current node, without modifying the tree permanently.

---

## 2. Core Idea: When to Record in Preorder

In Morris Inorder traversal, we record the node's value **after** its left subtree has been traversed (when we revisit the node via the thread).
In **Preorder traversal** (`Root -> Left -> Right`), the root must be processed **before** its left subtree!
Therefore:
1. When a node has no left child, we process it and move to its right child.
2. When a node has a left child:
   - If the thread to `curr` does **not yet exist**, this is our **first time** visiting `curr`! We record `curr.val` immediately, establish the thread, and descend into the left subtree.
   - If the thread **already exists**, we are returning from the left subtree. We remove the thread and move into the right subtree (without recording `curr.val` again).

---

## 3. Algorithm

1. Initialize `curr = root` and an empty list `preorder = []`.
2. While `curr != null`:
   - **Case 1: `curr.left == null`**
     - Record `curr.val` in `preorder`.
     - Move to right child: `curr = curr.right`.
   - **Case 2: `curr.left != null`**
     - Find the inorder predecessor (rightmost node in `curr`'s left subtree):
       ```java
       TreeNode prev = curr.left;
       while (prev.right != null && prev.right != curr) {
           prev = prev.right;
       }
       ```
     - **Subcase 2A: `prev.right == null` (First time visiting `curr`)**
       - **Record `curr.val` in `preorder`** (Preorder: visit root before left subtree).
       - Create the return thread: `prev.right = curr`.
       - Move into the left subtree: `curr = curr.left`.
     - **Subcase 2B: `prev.right == curr` (Left subtree finished)**
       - Dismantle the thread: `prev.right = null`.
       - Move into the right subtree: `curr = curr.right`.
3. Return `preorder`.

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
   - `2.right == null`:
     - **Record `1`**: `preorder = [1]`.
     - Thread: `2.right = 1`.
     - Move: `curr = 4(A)`.

2. **`curr = 4(A)`**:
   - `curr.left = 4(B)` $\ne$ null.
   - Predecessor of 4(A): `prev = 4(B)`.
   - `4(B).right == null`:
     - **Record `4`**: `preorder = [1, 4]`.
     - Thread: `4(B).right = 4(A)`.
     - Move: `curr = 4(B)`.

3. **`curr = 4(B)`**:
   - `curr.left == null`:
     - **Record `4`**: `preorder = [1, 4, 4]`.
     - Move: `curr = curr.right` (follows thread to 4(A)).

4. **`curr = 4(A)`**:
   - `curr.left = 4(B)`. Predecessor is 4(B).
   - `4(B).right == 4(A)` (thread exists):
     - Remove thread: `4(B).right = null`.
     - Move: `curr = curr.right` $\implies$ `curr = 2`.

5. **`curr = 2`**:
   - `curr.left == null`:
     - **Record `2`**: `preorder = [1, 4, 4, 2]`.
     - Move: `curr = curr.right` (follows thread to 1).

6. **`curr = 1`**:
   - `curr.left = 4(A)`. Predecessor is 2.
   - `2.right == 1` (thread exists):
     - Remove thread: `2.right = null`.
     - Move: `curr = curr.right` $\implies$ `curr = null`.

7. Loop terminates. All threads dismantled, tree structure completely restored!
Final Result: `[1, 4, 4, 2]`.

---

## 5. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  Each of the $N - 1$ edges is traversed at most 3 times (finding predecessor twice, traversing once). Total steps are bounded by $\le 3N \in \mathcal{O}(N)$.

- **Auxiliary Space Complexity:** $\mathcal{O}(1)$
  Operates entirely using pointers (`curr`, `prev`). Zero recursion call stack frames and zero external stack structures.

---

## 6. Key Difference: Morris Preorder vs Morris Inorder

```text
+-----------------------+-----------------------------------+-----------------------------------+
| Action                | Morris Preorder                   | Morris Inorder                    |
+-----------------------+-----------------------------------+-----------------------------------+
| If curr.left == null  | Record curr.val, move right       | Record curr.val, move right       |
| If prev.right == null | Record curr.val, thread, go left  | Thread, go left                   |
| If prev.right == curr | Unthread, go right                | Record curr.val, unthread, right  |
+-----------------------+-----------------------------------+-----------------------------------+
```

---

## 7. Edge Cases Handled

1. **Single Node ($N = 1$):** `curr.left == null`, records node and terminates.
2. **Left Skewed Tree:** Records each node on the way down, establishing threads, then unthreads on the way up.
3. **Right Skewed Tree:** `curr.left` is always null; straightforward linear walk in $\mathcal{O}(N)$ time.
4. **Duplicate Values:** Nodes with identical values are correctly distinguished by reference identity (`prev.right != curr`).
5. **Negative Values:** All node values $\in [-100, 100]$ recorded accurately.
