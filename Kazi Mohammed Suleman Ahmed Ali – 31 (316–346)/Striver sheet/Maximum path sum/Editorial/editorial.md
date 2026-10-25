# Editorial: Maximum path sum

## 1. Problem Overview

We are given the root of a binary tree and need to find the **maximum path sum** among all non-empty paths in the tree.

Key observations:
1. A **path** is a sequence of adjacent nodes in which no node is visited more than once.
2. The path does **not** need to pass through the root of the tree.
3. Node values may be **negative**, **zero**, or **positive**.
4. A valid path must contain **at least one node**.

---

## 2. Mathematical Formulation & Structural Decomposition

In any binary tree path, there is a unique **highest node** (the lowest common ancestor of the path endpoints), which acts as the **turning point** (or apex) of the path.

At any node $u$:
- A path that turns at $u$ can extend downward into its left subtree, downward into its right subtree, or both.
- However, when node $u$ passes its contribution upward to its **parent**, it can only choose **at most one** child branch (either left or right), because extending into both would require revisiting $u$, which violates the simple path constraint.

### Maximum Branch Gain
Let $\text{gain}(u)$ be the maximum path sum starting at node $u$ and extending downward into at most one of its subtrees:
$$\text{gain}(u) = u.\text{val} + \max\big(0, \max(\text{gain}(u.\text{left}), \text{gain}(u.\text{right}))\big)$$

Here, the $\max(0, \dots)$ operation represents **greedy truncation**: if a subtree's maximum gain is negative, taking it would only reduce the total sum, so we simply ignore that branch.

### Maximum Path Turning at $u$
The maximum path sum that has node $u$ as its highest ancestor is:
$$\text{turnSum}(u) = u.\text{val} + \max(0, \text{gain}(u.\text{left})) + \max(0, \text{gain}(u.\text{right}))$$

The overall answer for the tree is the maximum over all nodes $u$:
$$\text{MaxPathSum} = \max_{u \in V} \text{turnSum}(u)$$

---

## 3. Comparison of Approaches

### Approach 1: Naive Top-Down Computation ($\mathcal{O}(N^2)$)
- **Concept:** For every node $u$, compute the maximum branch gain downward through its left and right children using separate recursive calls, compute the turning sum at $u$, and recurse down to child subtrees.
- **Drawback:** Ancestors repeatedly recalculate descendant branch gains. On a degenerate skewed tree of $N$ nodes, this takes $\mathcal{O}(N^2)$ time. For $N = 3 \times 10^4$, this results in $9 \times 10^8$ operations, causing Time Limit Exceeded (TLE).

### Approach 2: Bottom-Up Recursive DFS ($\mathcal{O}(N)$)
- **Concept:** Traverse the tree in post-order (`left -> right -> root`).
  - At each node $u$, receive the maximum gains of the left and right children.
  - Update a global maximum: $\text{maxSum} = \max(\text{maxSum}, u.\text{val} + \max(0, \text{leftGain}) + \max(0, \text{rightGain}))$.
  - Return $u.\text{val} + \max(0, \max(\text{leftGain}, \text{rightGain}))$.
- **Time Complexity:** $\mathcal{O}(N)$ — each node is visited once.
- **Space Complexity:** $\mathcal{O}(H)$ — recursion call stack.
- **Limitation (Stack Overflow):** Deeply skewed trees ($N = 3 \times 10^4$) can exceed JVM thread call stack limits, throwing `java.lang.StackOverflowError`.

### Approach 3: Stack-Safe Iterative Post-Order Traversal ($\mathcal{O}(N)$ Optimal)
- **Concept:** Implement the bottom-up post-order traversal iteratively using an explicit `ArrayDeque` allocated on **heap memory**.
- **Algorithm:**
  1. Initialize `maxSum = Integer.MIN_VALUE`, an explicit `stack`, and a lookup map `maxGains`.
  2. Traverse down the left spine, pushing nodes onto the stack.
  3. When a node's left child is exhausted:
     - If its right child exists and has not been visited, pivot to the right child.
     - Otherwise, pop the node $u$.
  4. Process node $u$:
     - Retrieve $\text{leftGain} = \max(0, \text{maxGains.getOrDefault}(u.\text{left}, 0))$.
     - Retrieve $\text{rightGain} = \max(0, \text{maxGains.getOrDefault}(u.\text{right}, 0))$.
     - $\text{maxSum} = \max(\text{maxSum}, u.\text{val} + \text{leftGain} + \text{rightGain})$.
     - Store $\text{maxGains.put}(u, u.\text{val} + \max(\text{leftGain}, \text{rightGain}))$.
     - Remove $u.\text{left}$ and $u.\text{right}$ from `maxGains` to maintain bounded $\mathcal{O}(H)$ memory.
  5. Return `maxSum`.
- **Why It Is Optimal:** 100% stack-safe, handles negative values correctly, and runs in strictly linear $\mathcal{O}(N)$ time.

---

## 4. Step-by-Step Dry Runs

### Trace on Example 1: `root = [20, 9, -10, null, null, 15, 7]`

```text
          20
        /    \
       9     -10
             /  \
            15   7
```

1. **Leaf `9`:**
   - Left gain = 0, right gain = 0.
   - `turnSum(9) = 9 + 0 + 0 = 9` $\to$ `maxSum = max(MIN, 9) = 9`.
   - `gain(9) = 9 + max(0, 0) = 9`.
2. **Leaf `15`:**
   - Left gain = 0, right gain = 0.
   - `turnSum(15) = 15` $\to$ `maxSum = max(9, 15) = 15`.
   - `gain(15) = 15`.
3. **Leaf `7`:**
   - Left gain = 0, right gain = 0.
   - `turnSum(7) = 7` $\to$ `maxSum = 15`.
   - `gain(7) = 7`.
4. **Node `-10`:**
   - Left gain = 15, right gain = 7.
   - `turnSum(-10) = -10 + 15 + 7 = 12` $\to$ `maxSum = 15`.
   - `gain(-10) = -10 + max(15, 7) = 5`.
5. **Root `20`:**
   - Left gain = 9, right gain = 5.
   - `turnSum(20) = 20 + 9 + 5 = 34` $\to$ `maxSum = max(15, 34) = 34`.
   - `gain(20) = 20 + max(9, 5) = 29`.
6. **Result:** `34` (Path: $15 \to -10 \to 20 \to 9$).

---

### Trace on Example 4: All Negative Nodes (`[-3, -2, -5]`)

```text
       -3
      /  \
    -2   -5
```

1. **Leaf `-2`:**
   - `turnSum(-2) = -2 + 0 + 0 = -2` $\to$ `maxSum = -2`.
   - Upward gain: $\max(0, -2) = 0$ is offered to parent.
2. **Leaf `-5`:**
   - `turnSum(-5) = -5` $\to$ `maxSum = max(-2, -5) = -2`.
   - Upward gain: $\max(0, -5) = 0$ is offered to parent.
3. **Root `-3`:**
   - Left gain = 0 (since $-2 < 0$, truncated to 0).
   - Right gain = 0 (since $-5 < 0$, truncated to 0).
   - `turnSum(-3) = -3 + 0 + 0 = -3` $\to$ `maxSum = max(-2, -3) = -2`.
4. **Result:** `-2` (Single-node path `[-2]`).

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Explanation |
| :--- | :--- | :---: | :--- |
| **All Negative Values** | `-10 -20 -30` | `-10` | Best path is the single maximum negative node. |
| **Single Node (Positive)** | `42` | `42` | Single node is the only valid path. |
| **Single Node (Negative)** | `-100` | `-100` | Non-empty path requirement selects `-100`. |
| **Root Heavily Negative** | `-50 20 30` | `30` | Optimal path does not cross root; optimal is single node `30` or subtree. |
| **Linear Left Chain** | `1 2 null 3` | `6` | Straight sum of all positive nodes along the chain. |
| **Alternating Signs** | `10 -20 30` | `30` | Truncation ignores negative branches where advantageous. |
| **Deep Skewed Tree ($N = 3 \times 10^4$)** | Skewed line | Linear sum | Verifies $\mathcal{O}(N)$ performance and stack safety. |

---

## 6. Complexity Analysis

| Metric | Top-Down Naive | Bottom-Up Recursive DFS | Iterative Post-Order (Optimal) |
| :--- | :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ | $\mathcal{O}(H)$ | $\mathcal{O}(1)$ |
| **Heap Memory (Explicit Stack)** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(H)$ |
| **Stack Overflow Risk** | Yes ($N = 3 \times 10^4$) | Yes ($N = 3 \times 10^4$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:** Each node is pushed onto the stack and popped from the stack exactly once. Lookups and arithmetic updates take $\mathcal{O}(1)$ time per node.
- **Space Complexity $\mathcal{O}(H)$:** Auxiliary memory is bounded by the height of the tree $H$. Finished child gains are removed immediately upon parent completion, ensuring $\mathcal{O}(\log N)$ space on balanced trees and $\mathcal{O}(N)$ worst-case on skewed trees.

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
     * Stack-safe iterative post-order traversal to compute maximum path sum.
     */
    public int maxPathSum(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, Integer> maxGains = new HashMap<>();
        int maxSum = Integer.MIN_VALUE;

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
                    int leftGain = Math.max(0, peek.left == null ? 0 : maxGains.getOrDefault(peek.left, 0));
                    int rightGain = Math.max(0, peek.right == null ? 0 : maxGains.getOrDefault(peek.right, 0));

                    // Maximum path turning at current node
                    int currentTurnSum = peek.val + leftGain + rightGain;
                    maxSum = Math.max(maxSum, currentTurnSum);

                    // Upward branch gain contributed to parent
                    int maxGainUpward = peek.val + Math.max(leftGain, rightGain);
                    maxGains.put(peek, maxGainUpward);

                    // Prune finished child gains to maintain O(H) auxiliary memory
                    if (peek.left != null) maxGains.remove(peek.left);
                    if (peek.right != null) maxGains.remove(peek.right);

                    lastVisited = peek;
                }
            }
        }

        return maxSum;
    }
}
```
