# Editorial: Diameter of Binary Tree

## 1. Problem Overview

We are given the root of a binary tree and need to compute the length of its **diameter**.

The **diameter** (or width) of a binary tree is defined as the length of the longest path between any two nodes in the tree. 
- The path length is measured by the number of **edges** traversed.
- A critical observation is that the longest path **does not necessarily pass through the root node** of the entire tree; it may be entirely contained within a subtree.
- For an empty tree or a tree consisting of only a single node, the diameter is $0$.

---

## 2. Mathematical Formulation & Structural Insight

Consider any simple path in a binary tree:
- Any path between two nodes $u$ and $v$ has a unique **highest node** (lowest common ancestor, or turning point) $w$.
- The path consists of:
  1. A path ascending from $u$ up to $w$ through $w$'s left child.
  2. A path descending from $w$ down to $v$ through $w$'s right child.

Therefore, for any node $w$, the longest path that has $w$ as its turning point has length equal to:
$$\text{diameter}(w) = \text{height}(w.\text{left}) + \text{height}(w.\text{right})$$

Here, $\text{height}(x)$ is defined as the maximum number of nodes along any downward path from $x$ to a leaf ($\text{height}(\text{null}) = 0$).

The diameter of the entire binary tree is the maximum over all nodes $w$ in the tree:
$$\text{Diameter} = \max_{w \in V} \big(\text{height}(w.\text{left}) + \text{height}(w.\text{right})\big)$$

---

## 3. Comparison of Approaches

### Approach 1: Naive Top-Down Computation ($\mathcal{O}(N^2)$)
- **Concept:** For every node $u$, compute `height(u.left)` and `height(u.right)` using a separate helper function. Calculate `currentDiameter = height(u.left) + height(u.right)`. Then recursively evaluate the diameter of the left and right subtrees:
  $$\text{diameter}(u) = \max\big(\text{height}(u.\text{left}) + \text{height}(u.\text{right}), \max(\text{diameter}(u.\text{left}), \text{diameter}(u.\text{right}))\big)$$
- **Drawback:** The height calculation is repeated at every ancestor for every descendant node. On a skewed chain of length $N$, this requires:
  $$\sum_{i=1}^N i = \frac{N(N+1)}{2} = \mathcal{O}(N^2) \text{ operations}$$
  For $N = 10^4$, this results in $\approx 5 \times 10^7$ operations, leading to excessive runtime.

### Approach 2: Bottom-Up Recursive DFS ($\mathcal{O}(N)$)
- **Concept:** Instead of recalculating heights from scratch, evaluate heights in a bottom-up post-order traversal (`left -> right -> root`).
- While computing the height of node $u$:
  1. Recursively find $H_L = \text{height}(u.\text{left})$ and $H_R = \text{height}(u.\text{right})$.
  2. Update a running global maximum: $\text{maxDiameter} = \max(\text{maxDiameter}, H_L + H_R)$.
  3. Return the height of $u$ to its parent: $1 + \max(H_L, H_R)$.
- **Time Complexity:** $\mathcal{O}(N)$ — each node is visited once.
- **Space Complexity:** $\mathcal{O}(H)$ — recursion call stack.
- **Limitation (Stack Overflow):** If the tree is deeply skewed ($N = 10^4$), recursive call frames on thread stack can trigger `java.lang.StackOverflowError` in constrained JVM environments.

### Approach 3: Stack-Safe Iterative Post-Order Traversal ($\mathcal{O}(N)$ Optimal)
- **Concept:** Perform the post-order traversal iteratively using an explicit stack (`ArrayDeque`) allocated in **JVM heap memory**.
- **Algorithm:**
  1. Initialize `maxDiameter = 0`, an explicit `stack`, and a lookup table `heights`.
  2. Traverse down the left spine, pushing nodes onto the stack.
  3. When reaching a null left child, inspect the top node:
     - If it has an unvisited right child, pivot to the right child.
     - If its right child is null or already visited, pop the node.
  4. Process the popped node $u$:
     - $H_L = \text{heights.getOrDefault}(u.\text{left}, 0)$
     - $H_R = \text{heights.getOrDefault}(u.\text{right}, 0)$
     - $\text{maxDiameter} = \max(\text{maxDiameter}, H_L + H_R)$
     - Store $\text{heights.put}(u, 1 + \max(H_L, H_R))$
     - Remove $u.\text{left}$ and $u.\text{right}$ from `heights` to maintain $\mathcal{O}(H)$ heap memory.
  5. Return `maxDiameter`.
- **Why It Is Optimal:** 100% stack-safe, zero recursion overhead, and strictly linear $\mathcal{O}(N)$ runtime.

---

## 4. Step-by-Step Dry Runs

### Trace 1: Diameter Passes Through Root (`root = [1, 2, 3, 4, 5]`)

```text
        1
      /   \
     2     3
    / \
   4   5
```

1. **Leaves `4` and `5`:**
   - Both have no children $\to H_L = 0, H_R = 0$.
   - At `4`: path = $0 + 0 = 0$, height = $1$.
   - At `5`: path = $0 + 0 = 0$, height = $1$.
2. **Node `2`:**
   - Left child `4` has height 1, right child `5` has height 1.
   - Longest path turning at `2`: $1 + 1 = 2$ edges ($4 \to 2 \to 5$).
   - Height of `2` = $1 + \max(1, 1) = 2$.
   - `maxDiameter = max(0, 2) = 2`.
3. **Leaf `3`:**
   - Has no children $\to H_L = 0, H_R = 0$, height = $1$.
4. **Root `1`:**
   - Left child `2` has height 2, right child `3` has height 1.
   - Longest path turning at `1`: $2 + 1 = 3$ edges ($4 \to 2 \to 1 \to 3$).
   - `maxDiameter = max(2, 3) = 3`.
5. **Final Result:** `3`.

---

### Trace 2: Diameter Does NOT Pass Through Root

```text
            1
          /
        2
       / \
      3   4
     /     \
    5       6
```

1. **Subtree at `2`:**
   - Left branch $2 \to 3 \to 5$ has height 2.
   - Right branch $2 \to 4 \to 6$ has height 2.
   - Longest path turning at `2`: $2 + 2 = 4$ edges ($5 \to 3 \to 2 \to 4 \to 6$).
   - Height of `2` = $1 + \max(2, 2) = 3$.
   - `maxDiameter = 4`.
2. **Root `1`:**
   - Left child `2` has height 3, right child is null (height 0).
   - Longest path turning at `1`: $3 + 0 = 3$ edges.
   - `maxDiameter = max(4, 3) = 4`.
3. **Final Result:** `4` (the diameter resides entirely inside the left subtree).

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Tree Structure | Diameter | Explanation |
| :--- | :--- | :---: | :--- |
| **Empty Tree** | `null` | `0` | No nodes or edges exist. |
| **Single Node** | `42` | `0` | Path from a node to itself has 0 edges. |
| **Two Nodes** | `1 2` | `1` | Single edge connecting parent and child. |
| **Degenerate Left Skewed** | $N$ nodes in a line | $N - 1$ | Tree is effectively a linear path of $N - 1$ edges. |
| **Degenerate Right Skewed** | $N$ nodes in a line | $N - 1$ | Symmetric linear path. |
| **Full Perfect Tree** | Depth $d$, $N = 2^d - 1$ | $2(d - 1)$ | Longest path connects two deepest leaves across the root. |
| **Off-Center Diameter** | Large deep subtree, short other side | Depends on subtree | Diameter is completely contained in the deeper subtree. |

---

## 6. Complexity Analysis

| Metric | Top-Down Naive | Bottom-Up Recursive DFS | Iterative Post-Order (Optimal) |
| :--- | :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N^2)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ | $\mathcal{O}(H)$ | $\mathcal{O}(1)$ |
| **Heap Memory (Explicit Stack)** | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ | $\mathcal{O}(H)$ |
| **Stack Overflow Risk** | Yes ($N = 10^4$) | Yes ($N = 10^4$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:** Every node is pushed and popped exactly once from the stack. Node height retrieval and comparisons take $\mathcal{O}(1)$ time. Overall time is strictly linear in the number of nodes $N$.
- **Space Complexity $\mathcal{O}(H)$:** The auxiliary stack and height map store at most the nodes along the current path from the root. Since child heights are pruned as soon as their parent finishes processing, auxiliary heap memory is bounded by $\mathcal{O}(H)$ ($\mathcal{O}(\log N)$ for balanced trees, $\mathcal{O}(N)$ for skewed trees).

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
     * Stack-safe iterative post-order traversal to compute the diameter of a binary tree.
     */
    public int diameterOfBinaryTree(TreeNode root) {
        if (root == null) {
            return 0;
        }

        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        TreeNode current = root;
        TreeNode lastVisited = null;
        Map<TreeNode, Integer> heights = new HashMap<>();
        int maxDiameter = 0;

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

                    // Update maximum diameter encountered so far
                    maxDiameter = Math.max(maxDiameter, leftH + rightH);

                    // Store current node's height
                    heights.put(peek, 1 + Math.max(leftH, rightH));

                    // Prune child heights to bound memory to O(H)
                    if (peek.left != null) heights.remove(peek.left);
                    if (peek.right != null) heights.remove(peek.right);

                    lastVisited = peek;
                }
            }
        }

        return maxDiameter;
    }
}
```
