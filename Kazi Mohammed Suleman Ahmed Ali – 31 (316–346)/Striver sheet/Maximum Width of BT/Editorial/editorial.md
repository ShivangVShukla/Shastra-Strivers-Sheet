# Editorial: Maximum Width of Binary Tree

## 1. Problem Overview

Given the root of a binary tree, we want to find the **maximum width** among all depth levels of the tree.
- The width of a level is the distance between the leftmost and rightmost non-null nodes at that level.
- Any missing or `null` nodes between the leftmost and rightmost nodes are also counted into the width calculation (as if the level was embedded inside a complete binary tree).

---

## 2. Complete Binary Tree Indexing & Level-Wise Normalization

### Standard Heap Indexing
In a complete binary tree:
- If a parent node has index $i$:
  - Its **left child** has index $2 \cdot i + 1$.
  - Its **right child** has index $2 \cdot i + 2$.
- The width of any level is simply:
  $$\text{width} = \text{index}_{\text{rightmost}} - \text{index}_{\text{leftmost}} + 1$$

### Preventing Integer Overflow (Level-Wise Normalization)
In an unbalanced or deep tree with height up to $N = 3000$, indices can grow exponentially up to $2^{3000}$, far exceeding the capacity of standard 64-bit integers (`long`).
To completely eliminate overflow:
- At each level, find the index of the first (leftmost) node: `minIdx = queue.peek().idx`.
- Subtract `minIdx` from all node indices at that level:
  $$\text{normalizedIdx} = \text{curr.idx} - \text{minIdx}$$
- Children's indices are then calculated using the normalized index:
  - Left child: $2 \cdot \text{normalizedIdx} + 1$
  - Right child: $2 \cdot \text{normalizedIdx} + 2$

Because $\text{normalizedIdx}$ resets to $0$ for the leftmost node at every level, the values remain small and are mathematically guaranteed never to overflow.

---

## 3. Algorithm: Level-Order Traversal (BFS)

1. If `root == null`, return $0$.
2. Initialize an `ArrayDeque<Pair>` queue storing `(TreeNode node, long index)`.
3. Enqueue `(root, 0L)` and initialize `maxWidth = 0`.
4. While `queue` is not empty:
   - Record `size = queue.size()`.
   - Record `minIdx = queue.peek().idx`.
   - Track `first = 0` and `last = 0`.
   - Iterate $i$ from $0$ to `size - 1`:
     - Dequeue `curr`.
     - Compute normalized index: `curIdx = curr.idx - minIdx`.
     - If $i == 0$, set `first = curIdx`.
     - If $i == \text{size} - 1$, set `last = curIdx`.
     - If `curr.node.left != null`, enqueue `(curr.node.left, 2 * curIdx + 1)`.
     - If `curr.node.right != null`, enqueue `(curr.node.right, 2 * curIdx + 2)`.
   - Update `maxWidth = Math.max(maxWidth, (int)(last - first + 1))`.
5. Return `maxWidth`.

---

## 4. Step-by-Step Trace (Example 1)

**Tree:**
```text
           1            <-- Level 0
         /   \
        3     2         <-- Level 1
       / \     \
      5   3     9       <-- Level 2
```

| Level | Queue at Start | `minIdx` | Nodes Processed (`curIdx`) | `first` | `last` | Level Width | `maxWidth` |
| :---: | :---: | :---: | :--- | :---: | :---: | :---: | :---: |
| 0 | `[(1, 0)]` | 0 | Node `1`: `curIdx = 0`<br>Enqueues `(3, 1), (2, 2)` | 0 | 0 | $0 - 0 + 1 = \mathbf{1}$ | 1 |
| 1 | `[(3, 1), (2, 2)]` | 1 | Node `3`: `curIdx = 0` (Enqueues `(5, 1), (3, 2)`)<br>Node `2`: `curIdx = 1` (Enqueues `(9, 4)`) | 0 | 1 | $1 - 0 + 1 = \mathbf{2}$ | 2 |
| 2 | `[(5, 1), (3, 2), (9, 4)]` | 1 | Node `5`: `curIdx = 0`<br>Node `3`: `curIdx = 1`<br>Node `9`: `curIdx = 3` | 0 | 3 | $3 - 0 + 1 = \mathbf{4}$ | **4** |

**Final Maximum Width:** `4`

---

## 5. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  - Each of the $N$ nodes is enqueued and dequeued exactly once during the BFS traversal.
  - All arithmetic operations and queue interactions take $\mathcal{O}(1)$ time per node.
  - Overall time is strictly linear in the number of nodes.

- **Space Complexity:** $\mathcal{O}(N)$
  - The queue holds at most the maximum number of nodes at any level, bounded by $\mathcal{O}(N)$ (up to $\approx N/2$ in a full binary tree).
  - Memory consumption is fully heap-allocated and well within the 256 MB limit.
