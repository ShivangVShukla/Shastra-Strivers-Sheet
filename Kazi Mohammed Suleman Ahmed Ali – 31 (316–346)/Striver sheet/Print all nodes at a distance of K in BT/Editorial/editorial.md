# Editorial: Print All Nodes at a Distance of K in Binary Tree

## 1. Problem Overview

Given the root of a binary tree, a target node, and an integer $k$, we want to find all nodes in the tree that are at a distance of exactly $k$ edges from the target node.
- Distance is defined in an undirected sense: edges can be traversed downwards (to left/right children) or upwards (to the parent).
- All node values in the tree are unique.
- $k \ge 0$, and if $k = 0$, the only node at distance 0 is the target node itself.

---

## 2. Core Intuition: Converting Tree to an Undirected Graph

In a standard binary tree, edges are directed from parent to child. However, distance-$k$ nodes can lie:
1. In the subtree of the target node (moving downwards).
2. In ancestor nodes or other subtrees connected through ancestors (moving upwards then downwards).

To enable two-way traversal without modifying the original tree data structure, we can:
1. **Record Parent Pointers:** Perform a traversal (BFS or DFS) starting from the root to map every child node to its parent:
   $$\text{parentTrack}[\text{child}] = \text{parent}$$
2. **Radial BFS from Target:** Treat the tree as an undirected graph where each node has up to 3 neighbors:
   - Left child (`curr.left`)
   - Right child (`curr.right`)
   - Parent (`parentTrack.get(curr)`)

Using Breadth-First Search starting at `target`, we advance level-by-level (radius by radius) until reaching radius $k$.

---

## 3. Step-by-Step Algorithm

### Step 1: Map Parents & Locate Target
- Perform a BFS from `root` using an `ArrayDeque<TreeNode>`.
- For each node:
  - If `curr.left != null`, set `parentTrack.put(curr.left, curr)`.
  - If `curr.right != null`, set `parentTrack.put(curr.right, curr)`.
  - Also identify the reference to `target` (where `curr.val == targetVal`).

### Step 2: Radial Level-by-Level BFS
- Initialize a queue with `target` and a `Set<TreeNode> visited` containing `target`.
- Maintain `currentDistance = 0`.
- While the queue is not empty:
  - If `currentDistance == k`, break out of the loop.
  - Determine `size = queue.size()`.
  - For each of the `size` nodes at the current distance:
    - Dequeue `curr`.
    - Check all 3 potential neighbors:
      1. **Left Child:** If `curr.left != null` and not in `visited`, mark visited and enqueue.
      2. **Right Child:** If `curr.right != null` and not in `visited`, mark visited and enqueue.
      3. **Parent:** If `parentTrack.containsKey(curr)` and `parentTrack.get(curr)` is not in `visited`, mark visited and enqueue.
  - Increment `currentDistance++`.

### Step 3: Collect Result
- When the loop terminates at `currentDistance == k`, every node remaining in the queue is at distance exactly $k$ from `target`.
- Extract all values, sort them for clean deterministic presentation, and return.

---

## 4. Detailed Trace (Example 1)

```text
          3
       /     \
      5       1
     / \     / \
    6   2   0   8
       / \
      7   4
```
Target $= 5$, $k = 2$.

| Radius (Distance) | Nodes in Queue at Start | Neighbors Explored | Added to Queue |
| :---: | :---: | :--- | :--- |
| **0** | `[5]` | `5.left = 6`<br>`5.right = 2`<br>`parent(5) = 3` | `[6, 2, 3]` |
| **1** | `[6, 2, 3]` | From `6`: `parent(6)=5` (visited), children null.<br>From `2`: `2.left = 7`, `2.right = 4`, `parent(2)=5` (visited).<br>From `3`: `3.left = 5` (visited), `3.right = 1`, `parent(3) = null`. | `[7, 4, 1]` |
| **2** | `[7, 4, 1]` | `currentDistance == 2 == k` $\implies$ **STOP** | - |

**Nodes at Distance 2:** `[1, 4, 7]`

---

## 5. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  - Building the parent map visits all $N$ nodes: $\mathcal{O}(N)$.
  - Radial BFS visits each node at most once since every node is marked in `visited`: $\mathcal{O}(N)$.
  - Sorting the at-most $N$ nodes in the final answer takes $\mathcal{O}(K \log K) \le \mathcal{O}(N \log N)$ where $K \le N$.
  - Total time is $\mathcal{O}(N)$, completing in $< 0.05$ seconds for $N = 1000$.

- **Space Complexity:** $\mathcal{O}(N)$
  - `parentTrack` map stores $N$ entries.
  - `visited` set stores at most $N$ nodes.
  - BFS queue holds at most $N$ nodes.
  - Total auxiliary memory is $\mathcal{O}(N)$, well within the 256 MB limit.
