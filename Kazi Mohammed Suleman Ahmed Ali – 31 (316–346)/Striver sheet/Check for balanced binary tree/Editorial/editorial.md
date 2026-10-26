# Editorial: Check for balanced binary tree

## 1. Problem Overview

We are given the root of a binary tree and need to determine whether the tree is **height-balanced**.

A binary tree is defined to be height-balanced if and only if, for **every single node** in the tree:
$$|\text{height}(\text{left}) - \text{height}(\text{right})| \le 1$$

Here, the height of a subtree is defined as the number of edges (or nodes) along the longest path from the subtree's root to any of its leaves. An empty subtree has height $0$.

---

## 2. Structural Intuition & Mathematical Definition

A binary tree is balanced if and only if three conditions hold simultaneously for every node $u$:
1. The left subtree of $u$ is balanced.
2. The right subtree of $u$ is balanced.
3. The absolute difference between their heights is at most $1$:
$$|\text{height}(u.\text{left}) - \text{height}(u.\text{right})| \le 1$$

If any of these conditions fails at any node in the tree, the entire tree is declared **unbalanced**.

---

## 3. Comparison of Approaches

### Approach 1: Naive Top-Down Traversal ($\mathcal{O}(N^2)$)
- **Concept:** For each node, call an auxiliary `height(node)` function that computes the height of the left and right subtrees. If $|H_L - H_R| \le 1$, recursively check `isBalanced(node.left) && isBalanced(node.right)`.
- **Drawback:** Redundant computation. Nodes near the leaves are visited repeatedly by ancestor calls. For a skewed chain of $N$ nodes, the total number of operations is:
$$N + (N - 1) + (N - 2) + \dots + 1 = \frac{N(N + 1)}{2} = \mathcal{O}(N^2)$$
For $N = 10^5$, $\mathcal{O}(N^2)$ requires $10^{10}$ operations, causing Time Limit Exceeded (TLE).

### Approach 2: Bottom-Up Post-Order DFS with Early Exit ($\mathcal{O}(N)$)
- **Concept:** Instead of calculating height top-down, compute heights **bottom-up** using post-order traversal (`left -> right -> root`).
- **Sentinel Flag:** While calculating the height of each subtree:
  - If a subtree is unbalanced, return a sentinel value (e.g., `-1`).
  - If the left child returns `-1`, return `-1` immediately.
  - If the right child returns `-1`, return `-1` immediately.
  - If $|H_L - H_R| > 1$, return `-1` immediately.
  - Otherwise, return the valid height: $1 + \max(H_L, H_R)$.
- **Time Complexity:** $\mathcal{O}(N)$ — each node is visited at most once.
- **Limitation (Stack Overflow Risk):** For degenerate trees with $N = 10^5$, recursive call frames on the thread stack can exceed the JVM's stack size (`-Xss`), leading to `java.lang.StackOverflowError`.

### Approach 3: Stack-Safe Iterative Post-Order Traversal ($\mathcal{O}(N)$ Optimal)
- **Concept:** Perform a post-order traversal using an explicit stack (`ArrayDeque`) allocated in **heap memory**.
- **Mechanism:**
  1. Traverse down the left spine, pushing nodes onto the stack.
  2. Before popping a node, check if its right child has been visited. If not, pivot to the right child.
  3. When both children have been processed, pop the node:
     - Retrieve `height(left)` and `height(right)`.
     - If $|H_L - H_R| > 1$, return `false` immediately.
     - Store the height of the current node: $1 + \max(H_L, H_R)$.
     - Clean up child heights from the lookup table to bound memory usage to $\mathcal{O}(H)$.
  4. If the traversal completes without violating balance, return `true`.
- **Why It Is Optimal:** Zero recursion overhead, $\mathcal{O}(N)$ time complexity, and guaranteed immunity to `StackOverflowError`.

---

## 4. Step-by-Step Dry Runs

### Trace on Example 1: `root = [3, 9, 20, null, null, 15, 7]`

```text
        3
      /   \
     9     20
          /  \
         15   7
```

1. **Leaf `9`:**
   - Left height = 0, right height = 0.
   - $|0 - 0| = 0 \le 1$.
   - Height of `9` = $1 + \max(0, 0) = 1$.
2. **Leaf `15`:**
   - Left height = 0, right height = 0.
   - Height of `15` = $1 + \max(0, 0) = 1$.
3. **Leaf `7`:**
   - Left height = 0, right height = 0.
   - Height of `7` = $1 + \max(0, 0) = 1$.
4. **Node `20`:**
   - Left child `15` has height 1, right child `7` has height 1.
   - $|1 - 1| = 0 \le 1$.
   - Height of `20` = $1 + \max(1, 1) = 2$.
5. **Root `3`:**
   - Left child `9` has height 1, right child `20` has height 2.
   - $|1 - 2| = 1 \le 1$.
   - Balanced! Result: `true`.

---

### Trace on Example 2: `root = [1, 2, null, null, 3]`

```text
        1
       /
      2
       \
        3
```

1. **Leaf `3`:**
   - Left height = 0, right height = 0.
   - Height of `3` = 1.
2. **Node `2`:**
   - Left height = 0, right child `3` has height 1.
   - $|0 - 1| = 1 \le 1$.
   - Height of `2` = $1 + \max(0, 1) = 2$.
3. **Root `1`:**
   - Left child `2` has height 2, right child does not exist (height 0).
   - $|2 - 0| = 2 > 1$.
   - Violation detected at root! Return `false` immediately.

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Rationale |
| :--- | :--- | :--- | :--- |
| **Empty Tree** | `null` | `true` | Empty tree has 0 nodes and is balanced by definition. |
| **Single Node** | `42` | `true` | Left and right heights are both 0 ($|0 - 0| = 0 \le 1$). |
| **Two Nodes (Depth 2)** | `1 2` | `true` | Left height = 1, right height = 0 ($|1 - 0| = 1 \le 1$). |
| **Three Nodes (Chain)** | `1 2 null 3` | `false` | Left height = 2, right height = 0 ($|2 - 0| = 2 > 1$). |
| **Root Balanced, Subtree Unbalanced** | `1 2 3 4 null null null 5` | `false` | Root $|3 - 1| = 2 > 1$ or subtrees unbalanced. |
| **Perfect / Complete Tree** | $N = 2^k - 1$ | `true` | Symmetrically balanced with identical heights everywhere. |
| **Fibonacci / AVL Minimal Tree** | Alternating $H_L = H_R + 1$ | `true` | Exactly satisfies the $\le 1$ boundary condition throughout. |
| **Extreme Maximum Constraint ($N = 10^5$)** | $10^5$ nodes | `true` / `false` | Verifies $\mathcal{O}(N)$ linear scaling and stack safety. |

---

## 6. Complexity Analysis

| Metric | Top-Down Naive | Bottom-Up Recursive DFS | Iterative Post-Order (Optimal) |
| :--- | :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ | $\mathcal{O}(H)$ | $\mathcal{O}(1)$ |
| **Heap Memory (Explicit Stack)** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(H)$ |
| **Stack Overflow Risk** | Yes ($N = 10^5$) | Yes ($N = 10^5$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:** In the post-order traversal, each node is pushed and popped at most once. Height lookups and arithmetic checks take $\mathcal{O}(1)$ per node. Total time is strictly linear in $N$.
- **Space Complexity $\mathcal{O}(H)$:** The explicit stack holds the ancestors on the current path from the root. Since child heights are pruned from the height table as soon as their parent finishes, memory is bounded by tree height $H$ ($\mathcal{O}(\log N)$ for balanced trees, $\mathcal{O}(N)$ in the worst case).

---

## 7. Java Reference Implementation

```java
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;

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
     * Stack-safe iterative post-order traversal to determine if a binary tree is height-balanced.
     */
    public boolean isBalanced(TreeNode root) {
        if (root == null) {
            return true;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, Integer> heights = new HashMap<>();

        while (current != null || !stack.isEmpty()) {
            if (current != null) {
                stack.push(current);
                current = current.left;
            } else {
                TreeNode peek = stack.peek();
                if (peek.right != null && lastVisited != peek.right) {
                    current = peek.right;
                } else {
                    stack.pop();
                    int leftH = peek.left == null ? 0 : heights.getOrDefault(peek.left, 0);
                    int rightH = peek.right == null ? 0 : heights.getOrDefault(peek.right, 0);

                    if (Math.abs(leftH - rightH) > 1) {
                        return false; // Early termination upon detecting unbalance
                    }

                    heights.put(peek, 1 + Math.max(leftH, rightH));

                    // Prune heights of children to bound auxiliary memory to O(H)
                    if (peek.left != null) heights.remove(peek.left);
                    if (peek.right != null) heights.remove(peek.right);

                    lastVisited = peek;
                }
            }
        }

        return true;
    }
}
```
