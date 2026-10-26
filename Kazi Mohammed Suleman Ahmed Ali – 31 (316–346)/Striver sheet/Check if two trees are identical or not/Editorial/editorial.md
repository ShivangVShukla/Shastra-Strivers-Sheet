# Editorial: Check if two trees are identical or not

## 1. Problem Overview

We are given the roots of two binary trees, `p` and `q`. We need to determine whether the two trees are identical. 

Two binary trees are defined to be **identical** if and only if:
1. **Structural Equivalence:** For every node present in `p`, there exists a corresponding node in `q` located at the exact same relative position (same sequence of left and right child transitions from the root).
2. **Value Equivalence:** For every pair of corresponding nodes in `p` and `q`, their stored values are equal (`p.val == q.val`).

If either tree possesses a child where the other tree does not, or if any corresponding values mismatch, the trees are not identical.

---

## 2. Intuition & Mathematical Formulation

A binary tree is defined recursively. Therefore, the equality of two trees `p` and `q` can be decomposed into smaller sub-problems:
- If both trees are empty (`p == null` and `q == null`), they are trivially identical.
- If exactly one of the trees is empty, or if their root values differ (`p.val != q.val`), they cannot be identical.
- If both roots exist and share the same value, then `p` and `q` are identical if and only if:
  1. The left subtree of `p` is identical to the left subtree of `q`.
  2. The right subtree of `p` is identical to the right subtree of `q`.

### Formal Recurrence Relation

$$\text{isSame}(p, q) = \begin{cases} 
\text{true} & \text{if } p = \text{null} \land q = \text{null} \\ 
\text{false} & \text{if } (p = \text{null} \oplus q = \text{null}) \lor (p.\text{val} \ne q.\text{val}) \\ 
\text{isSame}(p.\text{left}, q.\text{left}) \land \text{isSame}(p.\text{right}, q.\text{right}) & \text{otherwise} 
\end{cases}$$

---

## 3. Comparison of Approaches

### Approach 1: Tree Serialization Comparison
- **Concept:** Serialize both trees `p` and `q` into string representations using preorder traversal with explicit markers for `null` nodes (e.g., `1,2,#,#,3,#,#`), and check if `serialize(p).equals(serialize(q))`.
- **Drawbacks:** Requires $\mathcal{O}(N)$ string allocations and full traversals of both trees even if they differ right at the root, wasting memory and missing out on early-exit optimizations.

### Approach 2: Simultaneous Recursive DFS
- **Concept:** Directly evaluate the recurrence relation recursively:
  ```java
  public boolean isSameTree(TreeNode p, TreeNode q) {
      if (p == null && q == null) return true;
      if (p == null || q == null || p.val != q.val) return false;
      return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
  }
  ```
- **Time Complexity:** $\mathcal{O}(\min(N, M))$ where $N$ and $M$ are the node counts of `p` and `q`. It short-circuits immediately upon encountering the first mismatch.
- **Space Complexity:** $\mathcal{O}(H)$ where $H$ is the height of the trees.
- **Limitation:** For deeply skewed trees ($N = 10^4$), recursive call frames can lead to `java.lang.StackOverflowError` in memory-constrained JVM environments.

### Approach 3: Simultaneous Iterative BFS (Optimal & Stack-Safe)
- **Concept:** Traverse both trees level by level in lockstep using two FIFO queues (`ArrayDeque`), pairing up nodes from `p` and `q`.
- **Algorithm:**
  1. If both `p` and `q` are `null`, return `true`. If only one is `null`, return `false`.
  2. Initialize `queueP` with `p` and `queueQ` with `q`.
  3. While both queues are non-empty:
     - Dequeue `nodeP` and `nodeQ`.
     - If `nodeP.val != nodeQ.val`, return `false`.
     - Check left children:
       - If both exist, enqueue both into their respective queues.
       - If only one exists, return `false` (structural mismatch).
     - Check right children:
       - If both exist, enqueue both into their respective queues.
       - If only one exists, return `false` (structural mismatch).
  4. Return `true` if and only if both queues are empty at the end.
- **Why It Is Optimal & Stack-Safe:**
  The queues are allocated on the **JVM heap memory**, which can easily store tens of thousands of references without risking thread call stack exhaustion. Early exit occurs as soon as the first discrepancy in structure or value is detected.

---

## 4. Step-by-Step Dry Runs

### Trace 1: Identical Trees (`p = [1, 2, 3]`, `q = [1, 2, 3]`)

```text
    Tree p        Tree q
      1             1
    /   \         /   \
   2     3       2     3
```

1. **Initialization:**
   - `queueP = [1]`, `queueQ = [1]`.
2. **Iteration 1:**
   - Pop `nodeP = 1`, `nodeQ = 1`.
   - `1 == 1` $\to$ values match.
   - Left children: both exist (`2` and `2`) $\to$ enqueue `2` to `queueP`, `2` to `queueQ`.
   - Right children: both exist (`3` and `3`) $\to$ enqueue `3` to `queueP`, `3` to `queueQ`.
   - Queues: `queueP = [2, 3]`, `queueQ = [2, 3]`.
3. **Iteration 2:**
   - Pop `nodeP = 2`, `nodeQ = 2`.
   - `2 == 2` $\to$ values match.
   - Children: neither node has left or right children.
   - Queues: `queueP = [3]`, `queueQ = [3]`.
4. **Iteration 3:**
   - Pop `nodeP = 3`, `nodeQ = 3`.
   - `3 == 3` $\to$ values match.
   - Children: neither node has left or right children.
   - Queues: `queueP = []`, `queueQ = []`.
5. **Result:** Both queues empty $\to$ return `true`.

---

### Trace 2: Structural Mismatch (`p = [1, 2]`, `q = [1, null, 2]`)

```text
    Tree p        Tree q
      1             1
    /                \
   2                  2
```

1. **Initialization:**
   - `queueP = [1]`, `queueQ = [1]`.
2. **Iteration 1:**
   - Pop `nodeP = 1`, `nodeQ = 1`.
   - Values match (`1 == 1`).
   - Left children: `nodeP` has left child `2`, but `nodeQ` has `left == null`.
   - Structural discrepancy detected $\to$ return `false` immediately.

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Tree p | Tree q | Expected | Rationale |
| :--- | :--- | :--- | :--- | :--- |
| **Both Empty** | `null` | `null` | `true` | Both represent empty trees. |
| **One Empty** | `1 2` | `null` | `false` | One non-empty, one empty. |
| **Identical Single Node** | `5` | `5` | `true` | Single node with identical values. |
| **Different Single Node** | `5` | `10` | `false` | Values mismatch at root. |
| **Negative Node Values** | `-42` | `-42` | `true` | Constraints permit $-10^4 \le \text{val} \le 10^4$. |
| **Symmetric / Mirrored Trees** | `1 2 null` | `1 null 2` | `false` | Mirrored trees are not identical. |
| **Large Identical Trees** | $N = 10^4$ | $N = 10^4$ | `true` | Verifies $\mathcal{O}(N)$ performance and stack safety. |
| **Mismatch at Deepest Leaf** | $N = 10^4$ | $N = 10^4$ (1 leaf differs) | `false` | Requires traversing down to the differing leaf. |

---

## 6. Complexity Analysis

| Metric | Recursive DFS | Iterative BFS (Level Order) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(\min(N, M))$ | $\mathcal{O}(\min(N, M))$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ ($\mathcal{O}(N)$ worst-case) | $\mathcal{O}(1)$ |
| **Heap Memory (Queue)** | $\mathcal{O}(1)$ | $\mathcal{O}(\min(W_p, W_q))$ |
| **Stack Overflow Risk** | Yes (on skewed trees with small stack) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(\min(N, M))$:** In the best case, a mismatch at the root terminates in $\mathcal{O}(1)$ time. In the worst case (identical trees), every node and edge is examined exactly once, giving linear time complexity proportional to tree size.
- **Space Complexity $\mathcal{O}(W)$:** The auxiliary queue holds at most the maximum width of the trees, which is at most $\lceil N/2 \rceil$ for complete binary trees and $\mathcal{O}(1)$ for skewed chains.

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
     * Stack-safe iterative BFS comparison of two binary trees.
     */
    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        ArrayDeque<TreeNode> queueP = new ArrayDeque<>();
        ArrayDeque<TreeNode> queueQ = new ArrayDeque<>();
        queueP.add(p);
        queueQ.add(q);

        while (!queueP.isEmpty() && !queueQ.isEmpty()) {
            TreeNode nodeP = queueP.poll();
            TreeNode nodeQ = queueQ.poll();

            if (nodeP.val != nodeQ.val) {
                return false;
            }

            // Left child structural check
            if (nodeP.left != null && nodeQ.left != null) {
                queueP.add(nodeP.left);
                queueQ.add(nodeQ.left);
            } else if (nodeP.left != null || nodeQ.left != null) {
                return false;
            }

            // Right child structural check
            if (nodeP.right != null && nodeQ.right != null) {
                queueP.add(nodeP.right);
                queueQ.add(nodeQ.right);
            } else if (nodeP.right != null || nodeQ.right != null) {
                return false;
            }
        }

        return queueP.isEmpty() && queueQ.isEmpty();
    }
}
```
