# Editorial: Top View of Binary Tree

## 1. Problem Overview

We are given the root of a binary tree and asked to determine its **top view**.

The **top view** consists of the nodes that are visible when looking down at the binary tree from directly above.
- We view each node along vertical columns defined by their **Horizontal Distance (HD)** from the root.
- The root has $\text{HD} = 0$.
- A left child has $\text{HD} - 1$.
- A right child has $\text{HD} + 1$.
- For each vertical line ($\text{HD}$), only the **topmost node** (the node with the smallest level/depth) is visible.
- If two nodes are at the same horizontal distance and the same depth level, the one that appears first from left to right is taken.

The final output is the sequence of visible node values ordered from the minimum horizontal distance ($\text{HD}_{\min}$) to the maximum horizontal distance ($\text{HD}_{\max}$).

---

## 2. Intuition & Approach Analysis

### Why BFS (Level-Order) is Optimal for Top View
1. **Natural Depth Progression:** BFS explores nodes level by level (from depth 0, then depth 1, then depth 2, ...).
2. **First-Come, First-Served Property:**
   - Because higher levels are processed before lower levels, the **very first time** a horizontal distance $\text{HD}$ is encountered in BFS, that node is guaranteed to be the topmost node for that column!
   - Any subsequent node encountered with the same $\text{HD}$ is situated at a deeper level and is thus obscured.
   - Therefore, we simply store the node value in a hash table or tree map on the first encounter:
     ```java
     if (!map.containsKey(hd)) {
         map.put(hd, node.val);
     }
     ```
3. **Comparison with DFS:**
   - In DFS, a deep node in the left subtree could be visited before a shallower node in the right subtree that shares the same horizontal distance.
   - DFS would therefore require storing both `(node.val, depth)` for each $\text{HD}$ and constantly overwriting if a shallower depth is discovered.
   - Furthermore, recursive DFS risks `StackOverflowError` on skewed trees of height $10^4$.
   - Hence, **iterative BFS** is the strictly optimal, stack-safe approach.

---

## 3. Step-by-Step Dry Runs

### Trace on Example 1: `root = [1, 2, 3, 4, 5, 6, 7]`

```text
                1 (HD = 0)
              /            \
        2 (HD = -1)     3 (HD = 1)
        /         \     /        \
    4 (HD = -2) 5(0)  6(0)    7 (HD = 2)
```

1. **Queue Initialization:** `queue = [(1, HD = 0)]`, `map = {}`.
2. **Level 0:**
   - Pop `(1, 0)`. `0` not in `map` $\to$ `map[0] = 1`.
   - Push left `(2, -1)` and right `(3, 1)`.
3. **Level 1:**
   - Pop `(2, -1)`. `-1` not in `map` $\to$ `map[-1] = 2`.
   - Push `(4, -2)` and `(5, 0)`.
   - Pop `(3, 1)`. `1` not in `map` $\to$ `map[1] = 3`.
   - Push `(6, 0)` and `(7, 2)`.
4. **Level 2:**
   - Pop `(4, -2)`. `-2` not in `map` $\to$ `map[-2] = 4`.
   - Pop `(5, 0)`. `0` **already in map** (value `1`) $\to$ ignore `5`.
   - Pop `(6, 0)`. `0` **already in map** (value `1`) $\to$ ignore `6`.
   - Pop `(7, 2)`. `2` not in `map` $\to$ `map[2] = 7`.
5. **Collection from $\text{HD} = -2$ to $2$:**
   - $\text{HD} = -2 \to 4$
   - $\text{HD} = -1 \to 2$
   - $\text{HD} = 0 \to 1$
   - $\text{HD} = 1 \to 3$
   - $\text{HD} = 2 \to 7$
   - Output: `[4, 2, 1, 3, 7]`.

---

## 4. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Purpose / Strategic Objective |
| :--- | :--- | :---: | :--- |
| **Empty Tree** | `null` | `[]` | Null pointer safety ($N = 0$). |
| **Single Node** | `42` | `[42]` | Single horizontal distance base case. |
| **Left-Skewed Chain** | `1 2 null 3` | `[3, 2, 1]` | Each node has $\text{HD} = 0, -1, -2$. All are visible. |
| **Right-Skewed Chain** | `1 null 2 null 3` | `[1, 2, 3]` | Each node has $\text{HD} = 0, 1, 2$. All are visible. |
| **Zig-Zag Tree** | `1 2 null null 3` | `[2, 1]` | Node `3` has $\text{HD} = 0$, obscured by root `1`. |
| **Negative Values** | `-10 -20 -30` | `[-20, -10, -30]` | Verifies signed integer preservation. |
| **Deep Skewed Tree ($N = 10^4$)** | Depth $10^4$ | $10^4$ values | Guarantees zero `StackOverflowError` in constrained JVMs. |
| **Complete Tree ($N = 8191$)** | Depth 13 | Top view of complete tree | Dense overlap stress test for $\mathcal{O}(N)$ BFS. |

---

## 5. Complexity Analysis

| Metric | Recursive DFS | Iterative BFS with Map (Optimal) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N)$ with Min/Max tracking or $\mathcal{O}(N \log N)$ with TreeMap |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ ($H \le 10^4$) | $\mathcal{O}(1)$ |
| **Heap Memory (Queue + Map)** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Stack Overflow Risk** | **High** on skewed trees ($N = 10^4$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:**
  - By tracking `minHd` and `maxHd` alongside a `HashMap<Integer, Integer>`, key insertion and retrieval are $\mathcal{O}(1)$ average.
  - Linear scan from `minHd` to `maxHd` takes $\mathcal{O}(W) \le \mathcal{O}(N)$ time.
  - Overall time: strictly $\mathcal{O}(N)$.
- **Space Complexity $\mathcal{O}(N)$:**
  - The queue holds at most $\mathcal{O}(W) \le \mathcal{O}(N)$ nodes at any level.
  - The map stores at most one node per horizontal distance ($\le N$ entries).

---

## 6. Java Reference Implementation

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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

    static class QueueNode {
        TreeNode node;
        int hd;
        QueueNode(TreeNode node, int hd) {
            this.node = node;
            this.hd = hd;
        }
    }

    /**
     * Optimal 100% stack-safe Top View traversal using BFS.
     */
    public List<Integer> topView(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Map<Integer, Integer> map = new HashMap<>();
        ArrayDeque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode(root, 0));

        int minHd = 0;
        int maxHd = 0;

        while (!queue.isEmpty()) {
            QueueNode qn = queue.poll();
            TreeNode curr = qn.node;
            int hd = qn.hd;

            minHd = Math.min(minHd, hd);
            maxHd = Math.max(maxHd, hd);

            // First node at this horizontal distance is the top view node
            if (!map.containsKey(hd)) {
                map.put(hd, curr.val);
            }

            if (curr.left != null) {
                queue.add(new QueueNode(curr.left, hd - 1));
            }
            if (curr.right != null) {
                queue.add(new QueueNode(curr.right, hd + 1));
            }
        }

        for (int hd = minHd; hd <= maxHd; hd++) {
            if (map.containsKey(hd)) {
                result.add(map.get(hd));
            }
        }

        return result;
    }
}
```
