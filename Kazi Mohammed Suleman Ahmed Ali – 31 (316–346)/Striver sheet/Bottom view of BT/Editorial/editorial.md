# Editorial: Bottom View of Binary Tree

## 1. Problem Overview

Given the root of a binary tree, we need to return its **bottom view**.
The bottom view of a binary tree is the sequence of nodes visible when looking at the tree from underneath.
- The nodes must be ordered from the **leftmost** horizontal distance to the **rightmost** horizontal distance.
- Along any given vertical column (horizontal distance $\text{HD}$), only the **bottom-most node** is visible.
- If multiple nodes share both the same horizontal distance and the same depth (vertical level), the tie-breaker dictates that the node visited **later** in the level-order (left-to-right) traversal takes precedence.

---

## 2. Coordinate System & Observations

To uniquely position each node in 2D space:
- Let the root node be located at coordinate $(x, y) = (0, 0)$, where $x$ represents the **horizontal distance ($\text{HD}$)** and $y$ represents the **vertical level (depth)**.
- For any node at $(x, y)$:
  - Its **left child** is located at $(x - 1, y + 1)$.
  - Its **right child** is located at $(x + 1, y + 1)$.

### Key Insight
When viewed from below:
1. For each horizontal line $x = \text{HD}$, we want the node that has the **maximum depth** $y$.
2. If two nodes have identical $(x, y)$, the problem specifies taking the node that appears later in the level-order traversal (i.e. the rightmost one).
3. In a standard **Level-Order Traversal (Breadth-First Search, BFS)**:
   - Nodes are processed in non-decreasing order of depth $y$.
   - Within each level $y$, nodes are processed strictly from left to right.
   - Therefore, whenever we visit a node at horizontal distance $x$, we can simply **unconditionally update** our recorded value for $x$:
     $$\text{bottomView}[x] = \text{node.val}$$
   - Any node encountered later will either be at a strictly greater depth $y$ or at the same depth $y$ but further to the right. Both cases mean the later node should overwrite the earlier node!

---

## 3. Algorithm: Breadth-First Search (BFS)

### Step-by-Step Procedure:

1. **Base Case:**
   - If the root is `null`, return an empty list `[]`.

2. **Initialize Data Structures:**
   - A queue to perform BFS: `Queue<Pair<TreeNode, Integer>>`, where each pair stores a tree node and its horizontal distance $\text{HD}$.
   - A map `Map<Integer, Integer>` (or array/`HashMap`) to store the latest node value seen at each $\text{HD}$.
   - Two integers, `minHd = 0` and `maxHd = 0`, to keep track of the minimum and maximum horizontal distances encountered during traversal. This allows extracting the keys in sorted order in $\mathcal{O}(W)$ time without needing a `TreeMap`.

3. **BFS Traversal:**
   - Enqueue `(root, 0)`.
   - While the queue is not empty:
     - Dequeue the front element `(currNode, hd)`.
     - Update the map: `map.put(hd, currNode.val)`.
     - Update bounds: `minHd = Math.min(minHd, hd)` and `maxHd = Math.max(maxHd, hd)`.
     - If `currNode.left != null`, enqueue `(currNode.left, hd - 1)`.
     - If `currNode.right != null`, enqueue `(currNode.right, hd + 1)`.

4. **Construct the Result:**
   - Iterate $i$ from `minHd` to `maxHd`:
     - Append `map.get(i)` to the result list.
   - Return the result list.

---

## 4. Trace & Dry Run

### Example 1:
```text
                  20 (HD = 0)
                /             \
          8 (HD = -1)      22 (HD = 1)
          /         \                 \
     5 (HD = -2)  3 (HD = 0)       25 (HD = 2)
                   /         \
             10 (HD = -1)  14 (HD = 1)
```

| Step | Dequeued Node | HD | Map Updates | `[minHd, maxHd]` |
| :---: | :---: | :---: | :--- | :---: |
| 1 | `20` | `0` | `{0: 20}` | `[0, 0]` |
| 2 | `8` | `-1` | `{0: 20, -1: 8}` | `[-1, 0]` |
| 3 | `22` | `1` | `{0: 20, -1: 8, 1: 22}` | `[-1, 1]` |
| 4 | `5` | `-2` | `{0: 20, -1: 8, 1: 22, -2: 5}` | `[-2, 1]` |
| 5 | `3` | `0` | `{0: 3, -1: 8, 1: 22, -2: 5}` *(20 overwritten by 3)* | `[-2, 1]` |
| 6 | `25` | `2` | `{0: 3, -1: 8, 1: 22, -2: 5, 2: 25}` | `[-2, 2]` |
| 7 | `10` | `-1` | `{0: 3, -1: 10, 1: 22, -2: 5, 2: 25}` *(8 overwritten by 10)* | `[-2, 2]` |
| 8 | `14` | `1` | `{0: 3, -1: 10, 1: 14, -2: 5, 2: 25}` *(22 overwritten by 14)* | `[-2, 2]` |

Iterating from `minHd = -2` to `maxHd = 2`:
- $\text{HD} = -2 \implies 5$
- $\text{HD} = -1 \implies 10$
- $\text{HD} = 0 \implies 3$
- $\text{HD} = 1 \implies 14$
- $\text{HD} = 2 \implies 25$

**Result:** `[5, 10, 3, 14, 25]`

---

### Example 2 (Overlapping Nodes at Same Depth):
```text
                  20 (HD = 0)
                /             \
          8 (HD = -1)      22 (HD = 1)
          /         \       /         \
     5 (HD = -2)  3 (0)   4 (0)    25 (2)
                  /   \
             10 (-1) 14 (1)
```
- At level 2, both `3` (from `8.right`) and `4` (from `22.left`) have $\text{HD} = 0$.
- In BFS, `3` is processed first (`map[0] = 3`), then `4` is processed second (`map[0] = 4`).
- Since neither node has a child at $\text{HD} = 0$ on level 3, the final value at $\text{HD} = 0$ remains `4`.
- **Result:** `[5, 10, 4, 14, 25]`.

---

## 5. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  - Each node is enqueued and dequeued exactly once during BFS ($\mathcal{O}(N)$).
  - Map lookups and updates take $\mathcal{O}(1)$ average time.
  - Iterating from `minHd` to `maxHd` takes $\mathcal{O}(W)$ where $W \le N$ is the horizontal width of the tree.
  - Overall time is strictly linear, $\mathcal{O}(N)$, comfortably running in $< 0.15\text{s}$ for $N = 10^4$.

- **Space Complexity:** $\mathcal{O}(N)$
  - The BFS queue holds at most one full level of nodes, bounded by $\mathcal{O}(N)$ (specifically up to $\approx N/2$ in a balanced binary tree).
  - The hash map stores one entry per distinct horizontal distance, bounded by $W \le N$.
  - Auxiliary memory is $\mathcal{O}(N)$, well within the 256 MB limit.
