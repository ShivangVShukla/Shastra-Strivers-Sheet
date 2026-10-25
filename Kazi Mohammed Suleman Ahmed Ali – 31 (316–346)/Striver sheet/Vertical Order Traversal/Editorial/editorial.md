# Editorial: Vertical Order Traversal of Binary Tree

## 1. Problem Overview

We are given the root of a binary tree and asked to compute the **vertical order traversal** of its nodes.

Each node is assigned a 2D Cartesian coordinate $(\text{col}, \text{row})$:
- The **root** is placed at $(0, 0)$.
- Moving to a **left child** shifts coordinates by $(-1, +1) \to (\text{col} - 1, \text{row} + 1)$.
- Moving to a **right child** shifts coordinates by $(+1, +1) \to (\text{col} + 1, \text{row} + 1)$.

### The 3-Tier Ordering Criteria
The final output is grouped into vertical columns, where:
1. **Primary Key:** Vertical column $\text{col}$ in **ascending order** (leftmost to rightmost).
2. **Secondary Key:** Row/depth $\text{row}$ in **ascending order** (top to bottom).
3. **Tertiary Key (Tie-Breaking):** Node value $\text{val}$ in **ascending order** if multiple nodes share the exact same $(\text{col}, \text{row})$ position.

---

## 2. Detailed Comparison of Approaches

### Approach 1: Nested Map & Priority Queue (`TreeMap<col, TreeMap<row, PriorityQueue<val>>>`)
- **Concept:**
  - Traverse the tree level-by-level using BFS.
  - Insert each node's value into a nested map structure:
    `TreeMap<Integer, TreeMap<Integer, PriorityQueue<Integer>>>`
  - The outer `TreeMap` maintains columns in sorted order.
  - The inner `TreeMap` maintains rows in sorted order.
  - The `PriorityQueue` (Min-Heap) automatically orders multiple nodes sharing identical $(\text{col}, \text{row})$ coordinates in ascending order.
- **Analysis:**
  - Highly robust and mathematically direct.
  - Generates multiple nested object wrappers (`TreeMap.Entry`, heap rebalancing on each insertion).

---

### Approach 2: BFS Traversal + Flat Tuple Sorting (Optimal & Cache-Friendly)
- **Concept:**
  1. Define a flat tuple structure:
     ```java
     class Item implements Comparable<Item> {
         int col, row, val;
         Item(int col, int row, int val) {
             this.col = col;
             this.row = row;
             this.val = val;
         }
         public int compareTo(Item o) {
             if (this.col != o.col) return Integer.compare(this.col, o.col);
             if (this.row != o.row) return Integer.compare(this.row, o.row);
             return Integer.compare(this.val, o.val);
         }
     }
     ```
  2. Perform an iterative BFS using `ArrayDeque<QueueEntry>`:
     - For each popped node, record `new Item(col, row, node.val)`.
     - Enqueue left child with `(col - 1, row + 1)`.
     - Enqueue right child with `(col + 1, row + 1)`.
  3. Sort the flat list of items:
     `Collections.sort(items);`
     - Uses TimSort, which has $\mathcal{O}(N \log N)$ worst-case time and optimal cache locality.
  4. Iterate through the sorted list, grouping items by `item.col`:
     - Whenever `item.col` changes, finalize the current column and start a new column list.
- **Why It Is Optimal:**
  - **100% Stack-Safe:** BFS runs on the JVM heap; zero native call stack frames are used.
  - **Minimal Memory Overhead:** Flat `ArrayList<Item>` requires significantly less pointer overhead than nested trees.
  - **Simplicity & Predictability:** The comparator strictly enforces the 3-tier tie-breaking rules.

---

## 3. Step-by-Step Dry Runs

### Trace on Example 2: `root = [1, 2, 3, 4, 5, 6, 7]`

```text
                 1 (0, 0)
              /            \
        2 (-1, 1)         3 (1, 1)
        /       \         /       \
    4 (-2, 2)  5 (0, 2) 6 (0, 2)  7 (2, 2)
```

1. **BFS Coordinate Collection:**
   - `Item(-2, 2, 4)`
   - `Item(-1, 1, 2)`
   - `Item(0, 0, 1)`
   - `Item(0, 2, 5)`
   - `Item(0, 2, 6)`
   - `Item(1, 1, 3)`
   - `Item(2, 2, 7)`

2. **Sorting Phase:**
   - Grouping by `col`:
     - $\text{col} = -2$: `[( -2, 2, 4 )]` $\to$ `[4]`
     - $\text{col} = -1$: `[( -1, 1, 2 )]` $\to$ `[2]`
     - $\text{col} = 0$:
       - `(0, 0, 1)` has row 0 $\to$ placed first.
       - Both `(0, 2, 5)` and `(0, 2, 6)` have row 2.
       - Tie-break by value: $5 < 6 \to$ `5` precedes `6`.
       - Column 0: `[1, 5, 6]`.
     - $\text{col} = 1$: `[( 1, 1, 3 )]` $\to$ `[3]`
     - $\text{col} = 2$: `[( 2, 2, 7 )]` $\to$ `[7]`

3. **Combined Output:**
   `[[4], [2], [1, 5, 6], [3], [7]]`.

---

## 4. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Purpose / Test Objective |
| :--- | :--- | :---: | :--- |
| **Empty Tree** | `null` | `[]` | Boundary condition ($N = 0$). |
| **Single Node** | `42` | `[[42]]` | Single coordinate $(0, 0)$. |
| **Left-Skewed Chain** | `1 2 null 3` | `[[3], [2], [1]]` | Column decreases monotonically: $-2, -1, 0$. |
| **Right-Skewed Chain** | `1 null 2 null 3` | `[[1], [2], [3]]` | Column increases monotonically: $0, 1, 2$. |
| **Overlapping Coordinate Conflict** | `1 2 3 4 6 5 7` | `[[4], [2], [1, 5, 6], [3], [7]]` | Verifies tie-breaking sort ($5 < 6$) even if `6` is discovered before `5`. |
| **Negative Node Values** | `-1 -2 -3` | `[[-2], [-1], [-3]]` | Negative integer preservation. |
| **Deep Skewed Tree ($N = 10^4$)** | Depth $10^4$ | $10^4$ columns of 1 node | Tests 100% stack safety on constrained JVMs. |
| **Complete Binary Tree ($N = 8191$)** | Depth 13 | Overlapping columns | Stress-tests multi-node coordinate collisions and TimSort. |

---

## 5. Complexity Analysis

| Metric | Recursive DFS + Nested Map | BFS + Flat Tuple Sort (Optimal) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N \log N)$ | $\mathcal{O}(N \log N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ ($H \le 10^4$) | $\mathcal{O}(1)$ |
| **Heap Memory** | $\mathcal{O}(N)$ (Heavy nested pointers) | $\mathcal{O}(N)$ (Compact flat array) |
| **Stack Overflow Risk** | **High** on skewed trees ($N = 10^4$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N \log N)$:**
  - BFS visits all $N$ nodes in $\mathcal{O}(N)$ time.
  - Sorting $N$ tuples using Java's Dual-Pivot Quicksort / TimSort requires $\mathcal{O}(N \log N)$ time.
  - Linear scan to group columns takes $\mathcal{O}(N)$ time.
  - Total time: $\mathcal{O}(N \log N)$, completing in $< 0.1\text{s}$ for $N = 10^4$.
- **Space Complexity $\mathcal{O}(N)$:**
  - The queue and the `items` list each hold at most $N$ references.

---

## 6. Java Reference Implementation

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
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

    static class Item implements Comparable<Item> {
        int col;
        int row;
        int val;

        Item(int col, int row, int val) {
            this.col = col;
            this.row = row;
            this.val = val;
        }

        @Override
        public int compareTo(Item other) {
            if (this.col != other.col) {
                return Integer.compare(this.col, other.col);
            }
            if (this.row != other.row) {
                return Integer.compare(this.row, other.row);
            }
            return Integer.compare(this.val, other.val);
        }
    }

    static class QueueNode {
        TreeNode node;
        int col;
        int row;

        QueueNode(TreeNode node, int col, int row) {
            this.node = node;
            this.col = col;
            this.row = row;
        }
    }

    /**
     * Stack-safe Vertical Order Traversal with 3-tier tie-breaking.
     */
    public List<List<Integer>> verticalTraversal(TreeNode root) {
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        List<Item> items = new ArrayList<>();
        ArrayDeque<QueueNode> queue = new ArrayDeque<>();
        queue.add(new QueueNode(root, 0, 0));

        while (!queue.isEmpty()) {
            QueueNode qn = queue.poll();
            items.add(new Item(qn.col, qn.row, qn.node.val));

            if (qn.node.left != null) {
                queue.add(new QueueNode(qn.node.left, qn.col - 1, qn.row + 1));
            }
            if (qn.node.right != null) {
                queue.add(new QueueNode(qn.node.right, qn.col + 1, qn.row + 1));
            }
        }

        Collections.sort(items);

        int prevCol = Integer.MIN_VALUE;
        List<Integer> currentCol = null;

        for (Item item : items) {
            if (currentCol == null || item.col != prevCol) {
                currentCol = new ArrayList<>();
                result.add(currentCol);
                prevCol = item.col;
            }
            currentCol.add(item.val);
        }

        return result;
    }
}
```
