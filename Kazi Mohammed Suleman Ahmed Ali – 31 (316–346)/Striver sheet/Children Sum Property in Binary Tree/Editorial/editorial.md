# Editorial: Children Sum Property in Binary Tree

## 1. Problem Overview

We are given the root of a binary tree and asked to determine whether **every node’s value equals the sum of the values of its immediate children**.

### Key Rules:
1. A **leaf node** (both `left == null` and `right == null`) trivially satisfies the property.
2. For an **internal node**, any missing child is treated as having value `0`:
   $$\text{childSum} = (\text{node.left} \ne \text{null} \ ? \ \text{node.left.val} : 0) + (\text{node.right} \ne \text{null} \ ? \ \text{node.right.val} : 0)$$
3. The node satisfies the property if and only if:
   $$\text{node.val} == \text{childSum}$$
4. If **every** node in the tree satisfies this condition, we return `true`; otherwise, if any node violates it, we return `false`.

---

## 2. Distinction: Verification vs. Transformation

In competitive programming and interview preparation, two variants of this problem exist:
- **Variant A (This Problem):** *Check / Verify* whether the given tree already satisfies the children sum property (returns `boolean`).
- **Variant B:** *Modify / Convert* the given tree in-place so that it satisfies the children sum property (nodes can only be incremented).

In this problem, we strictly perform **verification**. We must not mutate the tree; we only inspect each node.

---

## 3. Comparison of Approaches

### Approach 1: Recursive Traversal (DFS)

#### Concept:
At each node:
- If `root == null` or `(root.left == null && root.right == null)`, return `true`.
- Compute `childSum = (root.left != null ? root.left.val : 0) + (root.right != null ? root.right.val : 0)`.
- If `root.val != childSum`, return `false`.
- Recursively check `check(root.left) && check(root.right)`.

```java
boolean checkChildrenSum(TreeNode root) {
    if (root == null || (root.left == null && root.right == null)) {
        return true;
    }
    int sum = (root.left != null ? root.left.val : 0) + (root.right != null ? root.right.val : 0);
    if (root.val != sum) {
        return false;
    }
    return checkChildrenSum(root.left) && checkChildrenSum(root.right);
}
```

#### Drawback:
In Java, default JVM thread call stacks are constrained (typically 512 KB to 1 MB). For a skewed binary tree with $N = 10^4$ nodes, this recursive approach creates $10^4$ consecutive stack frames on the native execution stack, resulting in a fatal `java.lang.StackOverflowError`.

---

### Approach 2: Iterative BFS (Level-Order Traversal) — Optimal & 100% Stack-Safe

#### Concept:
We use a standard FIFO queue (`ArrayDeque<TreeNode>`) allocated on the **JVM heap memory**, which has hundreds of megabytes available.

#### Algorithm:
1. If `root == null`, return `true`.
2. Initialize a queue `ArrayDeque<TreeNode>` and push `root`.
3. While the queue is non-empty:
   - Pop `curr = queue.poll()`.
   - If `curr` is a leaf (`curr.left == null && curr.right == null`), continue.
   - Compute:
     $$\text{childSum} = (\text{curr.left} \ne \text{null} \ ? \ \text{curr.left.val} : 0) + (\text{curr.right} \ne \text{null} \ ? \ \text{curr.right.val} : 0)$$
   - If `curr.val != childSum`, return `false` immediately (**early exit**).
   - If `curr.left != null`, push `curr.left` to the queue.
   - If `curr.right != null`, push `curr.right` to the queue.
4. If the loop completes without finding any mismatch, return `true`.

#### Advantages:
- **100% Stack-Safe:** The recursion call stack depth is $\mathcal{O}(1)$.
- **Optimal Short-Circuiting:** As soon as an invalid node is encountered (even at the root), it immediately exits in $\mathcal{O}(1)$ time.
- **Cache-Friendly & Simple:** Simple linear iteration.

---

## 4. Step-by-Step Dry Runs

### Trace 1: Valid Complete Tree (`root = [10, 4, 6, 1, 3, 2, 4]`)

```text
          10
        /    \
       4      6
      / \    / \
     1   3  2   4
```

1. **Initialization:** `queue = [10]`.
2. **Pop `10`:**
   - Children: left=`4`, right=`6`.
   - `childSum = 4 + 6 = 10`.
   - `10 == 10` $\to$ valid.
   - Push `4`, `6`. `queue = [4, 6]`.
3. **Pop `4`:**
   - Children: left=`1`, right=`3`.
   - `childSum = 1 + 3 = 4`.
   - `4 == 4` $\to$ valid.
   - Push `1`, `3`. `queue = [6, 1, 3]`.
4. **Pop `6`:**
   - Children: left=`2`, right=`4`.
   - `childSum = 2 + 4 = 6`.
   - `6 == 6` $\to$ valid.
   - Push `2`, `4`. `queue = [1, 3, 2, 4]`.
5. **Pop `1`, `3`, `2`, `4`:**
   - Each node is a leaf (both children `null`).
   - Leaves automatically satisfy the condition.
6. **Result:** Queue is empty, return `true`.

---

### Trace 2: Violation (`root = [1, 4, 3, 5]`)

```text
        1
       / \
      4   3
     /
    5
```

1. **Initialization:** `queue = [1]`.
2. **Pop `1`:**
   - Children: left=`4`, right=`3`.
   - `childSum = 4 + 3 = 7`.
   - `1 != 7` $\to$ VIOLATION!
3. **Result:** Immediately return `false` on step 1 without traversing the remaining subtree.

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Input Tree | Expected | Key Test Objective |
| :--- | :--- | :---: | :--- |
| **Empty Tree** | `null` | `true` | Valid boundary base case ($N = 0$). |
| **Single Node** | `42` | `true` | Single node is a leaf; satisfies property. |
| **Only Left Child** | `7 7 null` | `true` | Missing right child contributes $0$ ($7 + 0 = 7$). |
| **Only Right Child** | `9 null 9` | `true` | Missing left child contributes $0$ ($0 + 9 = 9$). |
| **Single Child Mismatch** | `7 5 null` | `false` | $5 + 0 \ne 7$. |
| **Negative Values** | `-10 -4 -6` | `true` | $(-4) + (-6) = -10$. Handles negative arithmetic. |
| **Zero Sum** | `0 5 -5` | `true` | $5 + (-5) = 0$. Node value is zero with cancelling children. |
| **Deep Skewed Tree ($N = 10^4$)** | Valid chain | `true` | Tests stack-safety: zero recursion stack consumption. |
| **Deep Skewed Mismatch ($N = 10^4$)** | Leaf parent differs | `false` | Verifies traversal reaches depth $10^4$ without StackOverflow. |
| **Extreme Values ($\pm 10^5$)** | Large integers | `true` | Sum up to $2 \cdot 10^5$, safely fits in standard 32-bit signed integer. |

---

## 6. Complexity Analysis

| Metric | Recursive DFS | Iterative BFS (Optimal) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ ($H \le 10^4$) | $\mathcal{O}(1)$ |
| **Heap Memory (Queue)** | $\mathcal{O}(1)$ | $\mathcal{O}(W)$ ($W \le \lceil N/2 \rceil$) |
| **Stack Overflow Risk** | **High** on skewed trees ($N = 10^4$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:** In the worst case (valid tree), every node is visited exactly once. If a violation exists near the top, it short-circuits in $\mathcal{O}(1)$ to $\mathcal{O}(k)$ steps.
- **Space Complexity $\mathcal{O}(W)$:** The queue holds at most the maximum width of the tree at any level (at most $\approx N/2$ for balanced trees, and $\mathcal{O}(1)$ for skewed trees).

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
     * Stack-safe iterative BFS check for Children Sum Property.
     *
     * @param root The root of the binary tree.
     * @return true if every node satisfies the property, false otherwise.
     */
    public boolean checkChildrenSum(TreeNode root) {
        if (root == null) {
            return true;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            TreeNode curr = queue.poll();

            // Leaf nodes trivially satisfy the property
            if (curr.left == null && curr.right == null) {
                continue;
            }

            int childSum = (curr.left != null ? curr.left.val : 0) +
                           (curr.right != null ? curr.right.val : 0);

            if (curr.val != childSum) {
                return false;
            }

            if (curr.left != null) {
                queue.add(curr.left);
            }
            if (curr.right != null) {
                queue.add(curr.right);
            }
        }

        return true;
    }
}
```
