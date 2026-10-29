# Editorial: Zig Zag or Spiral Traversal

## 1. Problem Overview

We are given the root of a binary tree and required to return the **zigzag level order traversal** of its node values.

In standard level-order traversal, every level is processed from left to right. In zigzag traversal:
- **Level 0 (Root):** Traverse from **left to right**.
- **Level 1:** Traverse from **right to left**.
- **Level 2:** Traverse from **left to right**.
- **Level $k$:** Traverse left-to-right if $k$ is even, and right-to-left if $k$ is odd.

---

## 2. Intuition & Architectural Comparison

### Approach 1: BFS with Post-Reversal (`Collections.reverse`)
- **Concept:** Perform standard BFS level by level. Maintain a boolean flag `leftToRight`. After collecting all node values for a level, if `!leftToRight`, call `Collections.reverse(levelList)`.
- **Analysis:**
  - Easy to implement.
  - Reversing a list requires an additional pass over every element on odd levels.
  - While still $\mathcal{O}(N)$, it does redundant work and allocates extra intermediate memory.

---

### Approach 2: Two Stacks (Classical Approach)
- **Concept:** Maintain two stacks: `currentLevel` and `nextLevel`.
  - On left-to-right levels, push children to `nextLevel` as `left` then `right`.
  - On right-to-left levels, push children to `nextLevel` as `right` then `left`.
  - Swap stacks when `currentLevel` is empty.
- **Analysis:**
  - Correct and $\mathcal{O}(N)$, but managing two alternating stacks introduces branching and bookkeeping complexity compared to a single FIFO queue.

---

### Approach 3: BFS with Direct Index Placement (Optimal & Stack-Safe)
- **Concept:**
  - Use a single standard FIFO queue (`ArrayDeque<TreeNode>`).
  - At each level, determine `int size = queue.size()`.
  - Allocate an array of length `size`: `Integer[] levelValues = new Integer[size]`.
  - For each node $i \in [0, \text{size} - 1]$ popped from the queue:
    $$\text{index} = \begin{cases} i & \text{if } \text{leftToRight} \\ \text{size} - 1 - i & \text{if } \neg\text{leftToRight} \end{cases}$$
    $$\text{levelValues}[\text{index}] = \text{node.val}$$
  - Enqueue children in natural order (`left` first, then `right`) regardless of the direction flag.
  - Invert the flag `leftToRight = !leftToRight`.
- **Why It Is Optimal:**
  - **Zero Reversals:** Values are written directly into their correct target slot in $\mathcal{O}(1)$ time.
  - **Consistent Invariant:** Children are always enqueued in natural `left` $\to$ `right` order, making queue operations clean and uniform.
  - **100% Stack-Safe:** The traversal is iterative and queue allocations reside on the JVM heap.

---

## 3. Step-by-Step Dry Runs

### Trace on Example 1: `root = [1, 2, 3, null, 4, 8, 5]`

```text
Level 0 (L -> R):        1
                       /   \
Level 1 (R -> L):     2     3
                       \   / \
Level 2 (L -> R):       4 8   5
```

1. **Initialization:**
   - `queue = [1]`
   - `leftToRight = true`
   - `result = []`

2. **Level 0:**
   - `size = 1`, `levelValues = [ _ ]`
   - $i = 0$: Pop `1`. `index = 0`. `levelValues[0] = 1`.
   - Enqueue children of `1`: `2`, `3`. `queue = [2, 3]`.
   - Append `[1]` to `result`.
   - `leftToRight` becomes `false`.

3. **Level 1:**
   - `size = 2`, `levelValues = [ _, _ ]`
   - $i = 0$: Pop `2`. Since $\neg\text{leftToRight}$, $\text{index} = 2 - 1 - 0 = 1$. `levelValues[1] = 2`.
     - Enqueue child of `2`: `4`. `queue = [3, 4]`.
   - $i = 1$: Pop `3`. $\text{index} = 2 - 1 - 1 = 0$. `levelValues[0] = 3`.
     - Enqueue children of `3`: `8`, `5`. `queue = [4, 8, 5]`.
   - `levelValues = [3, 2]`.
   - Append `[3, 2]` to `result`.
   - `leftToRight` becomes `true`.

4. **Level 2:**
   - `size = 3`, `levelValues = [ _, _, _ ]`
   - $i = 0$: Pop `4`. `index = 0`. `levelValues[0] = 4`. (No children).
   - $i = 1$: Pop `8`. `index = 1`. `levelValues[1] = 8`. (No children).
   - $i = 2$: Pop `5`. `index = 2`. `levelValues[2] = 5`. (No children).
   - `levelValues = [4, 8, 5]`.
   - Append `[4, 8, 5]` to `result`.
   - `leftToRight` becomes `false`.

5. **Result:**
   - `queue` is empty.
   - Return `[[1], [3, 2], [4, 8, 5]]`.

---

## 4. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Strategic Objective |
| :--- | :--- | :---: | :--- |
| **Empty Tree** | `null` | `[]` | Boundary condition ($N = 0$). |
| **Single Node** | `42` | `[[42]]` | Single level base case ($N = 1$). |
| **Left-Skewed Chain** | `1 2 null 3 null` | `[[1], [2], [3]]` | Each level has size 1; direction does not alter singletons. |
| **Right-Skewed Chain** | `1 null 2 null 3` | `[[1], [2], [3]]` | Single node per level, tests alternating singletons. |
| **Zig-Zag Tree** | `1 2 null null 3` | `[[1], [2], [3]]` | Alternating single branches. |
| **Negative Node Values** | `-1 -2 -3` | `[[-1], [-3, -2]]` | Correct sign preservation. |
| **Deep Skewed Tree ($N = 10^4$)** | Depth $10^4$ | $10^4$ levels of size 1 | Guarantees zero `StackOverflowError` in constrained JVMs. |
| **Complete Binary Tree ($N = 8191$)** | Depth 13 | 13 levels alternating | Verifies maximum queue width performance ($\approx 4096$ nodes). |

---

## 5. Complexity Analysis

| Metric | Recursive DFS | BFS with Post-Reversal | BFS with Direct Indexing (Optimal) |
| :--- | :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ ($H \le 10^4$) | $\mathcal{O}(1)$ | $\mathcal{O}(1)$ |
| **Heap Memory (Queue)** | $\mathcal{O}(1)$ | $\mathcal{O}(W)$ | $\mathcal{O}(W)$ |
| **Extra Reversals** | None | $\mathcal{O}(N/2)$ operations | **None ($\mathcal{O}(1)$ index math)** |
| **Stack Overflow Risk** | **High** on skewed trees | **Zero (100% stack-safe)** | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:** Every node is pushed and polled from the queue exactly once. Each node's value is written to an array slot in $\mathcal{O}(1)$ time.
- **Space Complexity $\mathcal{O}(W)$:** The FIFO queue holds at most the maximum number of nodes at any level, which is $W \le \lceil N/2 \rceil$ for balanced trees and $\mathcal{O}(1)$ for skewed trees.

---

## 6. Java Reference Implementation

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
     * Returns the zigzag level order traversal of a binary tree.
     * Uses 100% stack-safe iterative BFS with direct index placement.
     */
    public List<List<Integer>> zigzagLevelOrder(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        ArrayDeque<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        boolean leftToRight = true;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            Integer[] levelValues = new Integer[levelSize];

            for (int i = 0; i < levelSize; i++) {
                TreeNode curr = queue.poll();

                int index = leftToRight ? i : (levelSize - 1 - i);
                levelValues[index] = curr.val;

                if (curr.left != null) {
                    queue.add(curr.left);
                }
                if (curr.right != null) {
                    queue.add(curr.right);
                }
            }

            result.add(Arrays.asList(levelValues));
            leftToRight = !leftToRight;
        }

        return result;
    }
}
```
