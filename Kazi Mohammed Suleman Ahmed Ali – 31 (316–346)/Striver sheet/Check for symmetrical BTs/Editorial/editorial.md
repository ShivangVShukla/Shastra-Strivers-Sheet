# Editorial: Check for symmetrical BTs

## 1. Problem Overview

We are given the root of a binary tree and asked to determine whether the tree is **symmetric** (a mirror reflection of itself across its vertical center axis).

A tree is symmetric if and only if its left subtree and right subtree are **mirror reflections** of each other:
1. The root values of the mirrored subtrees must be identical.
2. The **left** child of the left subtree must mirror the **right** child of the right subtree.
3. The **right** child of the left subtree must mirror the **left** child of the right subtree.

---

## 2. Intuition: Mirroring vs. Identity

It is important to contrast this with the "Identical Trees" problem:
- **Identical Trees:** Compares corresponding branches directly ($L.\text{left} \leftrightarrow R.\text{left}$ and $L.\text{right} \leftrightarrow R.\text{right}$).
- **Symmetric Trees:** Compares mirrored branches ($L.\text{left} \leftrightarrow R.\text{right}$ and $L.\text{right} \leftrightarrow R.\text{left}$).

### Formal Recurrence Relation

$$\text{isMirror}(L, R) = \begin{cases} 
\text{true} & \text{if } L = \text{null} \land R = \text{null} \\ 
\text{false} & \text{if } (L = \text{null} \oplus R = \text{null}) \lor (L.\text{val} \ne R.\text{val}) \\ 
\text{isMirror}(L.\text{left}, R.\text{right}) \land \text{isMirror}(L.\text{right}, R.\text{left}) & \text{otherwise} 
\end{cases}$$

For the whole tree, we evaluate $\text{isMirror}(\text{root.left}, \text{root.right})$.

---

## 3. Comparison of Approaches

### Approach 1: Inorder Traversal & Palindrome Check (Flawed if unpadded)
- **Concept:** Perform an inorder traversal of the tree and check if the resulting array of node values is a palindrome.
- **Why It Fails Without Null Markers:** Two structurally distinct trees can yield the exact same inorder traversal. For example:
  - Tree A: `root = 1`, left child `2` (inorder: `[2, 1]`)
  - Tree B: `root = 1`, right child `2` (inorder: `[1, 2]`)
  - Combining them: `root = 1`, left = `[2, null]`, right = `[null, 2]` gives inorder `[2, 1, 2]`, which is a palindrome, but the tree is NOT symmetric!
  - Therefore, level-based or simultaneous structural comparison is strictly required.

### Approach 2: Simultaneous Recursive DFS ($\mathcal{O}(N)$)
- **Concept:** Directly evaluate `isMirror(L, R)` recursively.
  ```java
  boolean isMirror(TreeNode L, TreeNode R) {
      if (L == null && R == null) return true;
      if (L == null || R == null || L.val != R.val) return false;
      return isMirror(L.left, R.right) && isMirror(L.right, R.left);
  }
  ```
- **Time Complexity:** $\mathcal{O}(N)$ — visits each node at most once.
- **Space Complexity:** $\mathcal{O}(H)$ — recursion call stack.
- **Limitation:** Deeply skewed symmetric trees (e.g. large V-trees with $N = 10^4$) risk `java.lang.StackOverflowError` in constrained JVM environments.

### Approach 3: Simultaneous Iterative BFS (Optimal & Stack-Safe)
- **Concept:** Traverse both subtrees in lockstep using two FIFO queues (`ArrayDeque`) allocated in **JVM heap memory**.
- **Algorithm:**
  1. If `root == null` or both `root.left` and `root.right` are null, return `true`.
  2. If exactly one of `root.left` or `root.right` is null, return `false`.
  3. Initialize `q1` with `root.left` and `q2` with `root.right`.
  4. While both queues are non-empty:
     - Pop `n1` from `q1` and `n2` from `q2`.
     - Check values: if `n1.val != n2.val`, return `false`.
     - **Outer pair ($n_1.\text{left}, n_2.\text{right}$):**
       - If both exist, push `n1.left` to `q1`, `n2.right` to `q2`.
       - If only one exists, return `false` (structural mismatch).
     - **Inner pair ($n_1.\text{right}, n_2.\text{left}$):**
       - If both exist, push `n1.right` to `q1`, `n2.left` to `q2`.
       - If only one exists, return `false` (structural mismatch).
  5. Return `true` if and only if both queues are empty.
- **Why It Is Optimal:** 100% stack-safe, zero recursion overhead, handles all edge cases, and short-circuits immediately upon the earliest mismatch.

---

## 4. Step-by-Step Dry Runs

### Trace on Example 1: `root = [1, 2, 2, 3, 4, 4, 3]`

```text
        1
      /   \
     2     2
    / \   / \
   3   4 4   3
```

1. **Initialization:**
   - `q1 = [2 (left)]`, `q2 = [2 (right)]`.
2. **Iteration 1:**
   - Pop `n1 = 2`, `n2 = 2` $\to 2 == 2$ (match).
   - Outer pair: `n1.left (3)` and `n2.right (3)` both exist $\to$ push `3` to `q1`, `3` to `q2`.
   - Inner pair: `n1.right (4)` and `n2.left (4)` both exist $\to$ push `4` to `q1`, `4` to `q2`.
   - Queues: `q1 = [3, 4]`, `q2 = [3, 4]`.
3. **Iteration 2:**
   - Pop `n1 = 3`, `n2 = 3` $\to 3 == 3$ (match).
   - Neither node has children.
   - Queues: `q1 = [4]`, `q2 = [4]`.
4. **Iteration 3:**
   - Pop `n1 = 4`, `n2 = 4` $\to 4 == 4$ (match).
   - Neither node has children.
   - Queues: `q1 = []`, `q2 = []`.
5. **Result:** Both queues empty $\to$ return `true`.

---

### Trace on Example 2: `root = [1, 2, 2, null, 3, null, 3]`

```text
        1
      /   \
     2     2
      \     \
       3     3
```

1. **Initialization:**
   - `q1 = [2]`, `q2 = [2]`.
2. **Iteration 1:**
   - Pop `n1 = 2`, `n2 = 2` $\to$ values match.
   - Outer pair: `n1.left` is null, but `n2.right` is `3`.
   - Structural asymmetry detected $\to$ return `false` immediately.

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Explanation |
| :--- | :--- | :---: | :--- |
| **Empty Tree** | `null` | `true` | Trivially symmetric. |
| **Single Node** | `42` | `true` | Single node is symmetric around itself. |
| **Two Nodes** | `1 2` | `false` | Root has left child but no right child. |
| **Symmetric V-Tree** | `1 2 2 3 null null 3` | `true` | Left and right outer arms mirror each other. |
| **Inner Asymmetry** | `1 2 2 null 3 3 null` | `true` | Inner children mirror each other ($L.\text{right} == R.\text{left}$). |
| **Negative Values** | `1 -2 -2 -3 -4 -4 -3` | `true` | Negative values mirror correctly. |
| **Large Symmetric Tree ($N = 10^4$)** | Symmetrical | `true` | Verifies $\mathcal{O}(N)$ performance and stack safety. |
| **Mismatch at Deepest Leaf ($N = 10^4$)** | 1 leaf differs | `false` | Verifies traversal reaches deepest levels without stack overflow. |

---

## 6. Complexity Analysis

| Metric | Simultaneous Recursive DFS | Simultaneous Iterative BFS (Optimal) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ | $\mathcal{O}(1)$ |
| **Heap Memory (Queues)** | $\mathcal{O}(1)$ | $\mathcal{O}(W)$ ($W \le \lceil N/2 \rceil$) |
| **Stack Overflow Risk** | Yes ($N = 10^4$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:** In the worst case (symmetric tree), every node is enqueued and dequeued once. For asymmetric trees, it terminates in $\mathcal{O}(1)$ to $\mathcal{O}(k)$ operations at the first mismatch.
- **Space Complexity $\mathcal{O}(W)$:** The queues store at most the width of the tree at any level, bounded by $\mathcal{O}(N)$ in complete trees and $\mathcal{O}(1)$ in skewed trees.

---

## 7. Java Reference Implementation

```java
import java.util.ArrayDeque;

class Solution {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * Stack-safe iterative BFS check for tree symmetry.
     */
    public boolean isSymmetric(TreeNode root) {
        if (root == null) {
            return true;
        }
        if (root.left == null && root.right == null) {
            return true;
        }
        if (root.left == null || root.right == null) {
            return false;
        }

        ArrayDeque<TreeNode> q1 = new ArrayDeque<>();
        ArrayDeque<TreeNode> q2 = new ArrayDeque<>();
        q1.add(root.left);
        q2.add(root.right);

        while (!q1.isEmpty() && !q2.isEmpty()) {
            TreeNode n1 = q1.poll();
            TreeNode n2 = q2.poll();

            if (n1.val != n2.val) {
                return false;
            }

            // Outer pair: n1.left and n2.right
            if (n1.left != null && n2.right != null) {
                q1.add(n1.left);
                q2.add(n2.right);
            } else if (n1.left != null || n2.right != null) {
                return false;
            }

            // Inner pair: n1.right and n2.left
            if (n1.right != null && n2.left != null) {
                q1.add(n1.right);
                q2.add(n2.left);
            } else if (n1.right != null || n2.left != null) {
                return false;
            }
        }

        return q1.isEmpty() && q2.isEmpty();
    }
}
```
