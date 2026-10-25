# Editorial: Maximum Depth in BT (Maximum Depth of Binary Tree)

## 1. Problem Overview

We are given the root of a binary tree and asked to compute its **maximum depth**. The maximum depth is defined as the total number of nodes along the longest simple path beginning at the root node and terminating at any leaf node (a node with no children). For an empty tree (`root == null`), the depth is defined as `0`.

---

## 2. Structural Intuition & Recurrence

A binary tree possesses a naturally recursive structure:
- Any non-empty binary tree consists of a single **root node** and two independent subtrees: the **left subtree** and the **right subtree**.
- The longest path starting from the root must proceed through either its left child or its right child (whichever child contains the deeper subtree).
- Therefore, if we know the maximum depth of the left subtree, say $H_L$, and the maximum depth of the right subtree, say $H_R$, the maximum depth of the entire tree rooted at the current node is:

$$\text{depth}(u) = 1 + \max(H_L, H_R)$$

### Base Case
When $u$ is `null` (representing an absent child or empty tree), there are no nodes along the path:
$$\text{depth}(\text{null}) = 0$$

This divide-and-conquer formulation leads directly to a post-order traversal: compute the depths of the left and right children first, then combine them at the root.

---

## 3. Comparison of Approaches

### Approach 1: Naive Path Enumeration (Brute Force)
- **Concept:** Enumerate every path from the root to every leaf using a backtracking DFS, maintaining the current path length, and update a global maximum `max_depth = max(max_depth, current_len)`.
- **Drawbacks:** While this visits every node, tracking paths explicitly or passing state down without combining results from subproblems introduces unnecessary bookkeeping and does not leverage the optimal substructure cleanly.

### Approach 2: Recursive Post-Order DFS (Divide & Conquer)
- **Concept:** Directly evaluate the recurrence relation:
  ```java
  public int maxDepth(TreeNode root) {
      if (root == null) return 0;
      int left = maxDepth(root.left);
      int right = maxDepth(root.right);
      return 1 + Math.max(left, right);
  }
  ```
- **Time Complexity:** $O(N)$ — every node is visited exactly once.
- **Space Complexity:** $O(H)$ — where $H$ is the height of the tree.
- **Limitation (Stack Overflow Risk):**
  In Java, recursion relies on the execution thread call stack. If the tree is heavily skewed (for example, a straight chain of $N = 10,000$ nodes), the call stack requires $10,000$ active stack frames. Under constrained JVM environments (e.g., `-Xss256k`), this can trigger a fatal `java.lang.StackOverflowError`.

### Approach 3: Iterative Breadth-First Search (BFS) / Level-Order Traversal (Optimal & Stack-Safe)
- **Concept:** Process the tree level by level using an iterative FIFO Queue (`ArrayDeque`).
- **Mechanism:**
  1. If `root == null`, return `0`.
  2. Enqueue the `root` into a queue. Initialize `depth = 0`.
  3. While the queue is not empty:
     - Record the number of nodes at the current level: `int levelSize = queue.size()`.
     - Increment `depth++`.
     - Dequeue all `levelSize` nodes. For each node, if it has a left child, enqueue it; if it has a right child, enqueue it.
  4. When the queue becomes empty, every level of the tree has been processed. Return `depth`.
- **Why It Is Stack-Safe:**
  The queue is allocated on the **JVM heap memory**, which easily accommodates hundreds of thousands of elements without overflowing the thread execution stack. It is robust against all pathological tree shapes (skewed chains, zig-zags, etc.).

---

## 4. Dry Run Examples

### Trace on Example 1: `root = [1, 2, 3, null, null, null, 6]`

```text
        1
      /   \
     2     3
            \
             6
```

1. **Initialization:**
   - `queue = [1]`, `depth = 0`.

2. **Level 1:**
   - `levelSize = 1`.
   - Increment `depth = 1`.
   - Pop `1`:
     - Enqueue left child `2`.
     - Enqueue right child `3`.
   - End of level 1: `queue = [2, 3]`.

3. **Level 2:**
   - `levelSize = 2`.
   - Increment `depth = 2`.
   - Pop `2`: has no children.
   - Pop `3`: has no left child, has right child `6` $\to$ enqueue `6`.
   - End of level 2: `queue = [6]`.

4. **Level 3:**
   - `levelSize = 1`.
   - Increment `depth = 3`.
   - Pop `6`: has no children.
   - End of level 3: `queue = []`.

5. **Termination:**
   - `queue` is empty.
   - Final result: `depth = 3`.

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Rationale |
| :--- | :--- | :--- | :--- |
| **Empty Tree** | `null` | `0` | Tree has 0 nodes; base case handled immediately. |
| **Single Node** | `42` | `1` | Root is its own leaf; path length is 1. |
| **Full Degenerate Left Chain** | `1 2 null 3 null ...` | $N$ | Tree behaves like a linked list; depth equals node count $N$. |
| **Full Degenerate Right Chain** | `1 null 2 null 3 ...` | $N$ | Symmetric degenerate linked list. |
| **Zig-Zag Tree** | `1 2 null null 3 4 null ...` | $N$ | Alternating left and right edges; depth equals $N$. |
| **Perfect / Complete Tree** | $N = 2^k - 1$ | $k = \log_2(N + 1)$ | Symmetrically balanced tree with minimal height. |
| **Maximum Constraint ($N = 10^4$)** | 10,000 nodes | $14 \le \text{depth} \le 10,000$ | Iterative implementation prevents JVM stack overflow. |

---

## 6. Complexity Analysis

| Metric | Recursive DFS | Iterative BFS (Level Order) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ ($\mathcal{O}(N)$ worst-case) | $\mathcal{O}(1)$ |
| **Heap Memory (Queue)** | $\mathcal{O}(1)$ | $\mathcal{O}(W)$ ($W \le \lceil N/2 \rceil$) |
| **Stack Overflow Risk** | Yes (on skewed trees with small stack) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:** Every node is pushed into the queue and popped from the queue exactly once. For each node, pointer operations take $\mathcal{O}(1)$ time. Overall time is linear in the number of nodes.
- **Space Complexity $\mathcal{O}(W)$:** The queue holds at most the maximum number of nodes in any single level (the tree width $W$). In a complete binary tree, the last level contains at most $\lceil N / 2 \rceil$ nodes, requiring $\mathcal{O}(N)$ auxiliary memory. In a skewed tree, $W = 1$, taking $\mathcal{O}(1)$ extra memory.

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
     * Stack-safe optimal BFS level-order traversal.
     * Computes the maximum depth of the binary tree.
     */
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        int depth = 0;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode current = queue.poll();
                if (current.left != null) {
                    queue.add(current.left);
                }
                if (current.right != null) {
                    queue.add(current.right);
                }
            }
            depth++;
        }

        return depth;
    }
}
```
