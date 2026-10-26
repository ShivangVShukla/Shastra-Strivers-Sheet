# Editorial: Count Total Nodes in a Complete Binary Tree

## 1. Problem Overview

Given the root of a **complete binary tree**, we want to count the total number of nodes in the tree in less than linear $\mathcal{O}(N)$ time.

### Definitions
- **Complete Binary Tree:** Every level, except possibly the last, is completely filled, and all nodes in the last level are as far left as possible.
- **Perfect Binary Tree:** A complete binary tree where all leaf nodes are at the same depth and every internal node has exactly two children. A perfect binary tree of height $h$ (where root is height 1) has exactly $2^h - 1$ nodes.

---

## 2. Optimal Approach: $\mathcal{O}((\log N)^2)$ Divide and Conquer

### Key Insight
For any subtree rooted at `curr`:
1. Measure the **left height** $lh$: follow left child pointers all the way down (`curr = curr.left`).
2. Measure the **right height** $rh$: follow right child pointers all the way down (`curr = curr.right`).

- **Case 1: $lh == rh$**
  The subtree is a **perfect binary tree**!
  The number of nodes in this subtree is directly:
  $$\text{nodes} = 2^{lh} - 1 = (1 \ll lh) - 1$$
  This calculation takes $\mathcal{O}(lh) = \mathcal{O}(\log N)$ time, skipping the need to visit any of its internal nodes!

- **Case 2: $lh \neq rh$**
  The subtree is complete but not perfect (the last level is only partially filled).
  Recursively count:
  $$\text{countNodes}(curr) = 1 + \text{countNodes}(curr.left) + \text{countNodes}(curr.right)$$

### Why is this $\mathcal{O}((\log N)^2)$?
Because the tree is complete, at each step **at least one** of `curr.left` or `curr.right` is guaranteed to be a perfect binary tree!
- The perfect subtree will be resolved in $\mathcal{O}(\log N)$ time using the formula $(1 \ll h) - 1$.
- Only the remaining non-perfect subtree continues the recursive branching.
- Therefore, there are at most $\mathcal{O}(\log N)$ recursive levels, and at each level we spend $\mathcal{O}(\log N)$ time checking heights.
- Total time complexity: $\mathcal{O}((\log N)^2)$.

For $N = 5 \times 10^4$:
$$\log_2(50000) \approx 16 \implies (\log_2 N)^2 \approx 256 \text{ operations}$$
This is drastically faster than a linear $\mathcal{O}(N)$ traversal.

---

## 3. Step-by-Step Trace (Example 1)

```text
          1
       /     \
      2       3
     / \     /
    4   5   6
```
1. At root `1`:
   - Left height: $1 \to 2 \to 4$ ($lh = 3$)
   - Right height: $1 \to 3$ ($rh = 2$)
   - $lh \neq rh \implies 1 + \text{count}(2) + \text{count}(3)$.
2. At node `2`:
   - Left height: $2 \to 4$ ($lh = 2$)
   - Right height: $2 \to 5$ ($rh = 2$)
   - $lh == rh \implies (1 \ll 2) - 1 = 3$ nodes! (Skipping further traversal of subtree 2).
3. At node `3`:
   - Left height: $3 \to 6$ ($lh = 2$)
   - Right height: $3$ ($rh = 1$)
   - $lh \neq rh \implies 1 + \text{count}(6) + \text{count}(null)$.
4. At node `6`:
   - Left height $= 1$, Right height $= 1 \implies (1 \ll 1) - 1 = 1$.
5. Subtree 3 total: $1 + 1 + 0 = 2$.
6. Root total: $1 + 3 + 2 = \mathbf{6}$.

---

## 4. Complexity Analysis

- **Time Complexity:** $\mathcal{O}((\log N)^2)$
  - Tree height is $H = \lfloor \log_2 N \rfloor + 1 \le 16$.
  - At each level, computing left and right heights takes $\mathcal{O}(H)$ time.
  - At most $\mathcal{O}(H)$ nodes trigger recursion.
  - Overall time: $\mathcal{O}(H^2) = \mathcal{O}((\log N)^2)$, executing in $< 0.005$ seconds.

- **Space Complexity:** $\mathcal{O}(\log N)$
  - Maximum recursion call stack depth equals the height of the complete binary tree, bounded by $\mathcal{O}(\log N) \le 16$ stack frames.
