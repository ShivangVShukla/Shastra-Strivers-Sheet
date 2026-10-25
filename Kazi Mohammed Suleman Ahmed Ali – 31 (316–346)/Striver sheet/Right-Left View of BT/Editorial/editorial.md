# Editorial: Right/Left View of Binary Tree

## 1. Problem Overview

Given the root of a binary tree, we want to return the values of the nodes visible when looking at the tree from the **right side**, ordered from **top to bottom**.

- At each depth level $d \in [0, H-1]$ of the binary tree (where $H$ is the tree height), exactly one node is visible from the right side.
- This visible node is the **rightmost** node at depth level $d$.
- Even if a node is in the left subtree, if the right subtree does not extend to that depth level, that left subtree node becomes visible from the right side.

### Left View Equivalent
For the **left view**, the visible node at each depth level $d$ is simply the **leftmost** node at that level.

---

## 2. Approaches

### Approach 1: Level-Order Traversal (BFS) — Optimal & Stack-Safe

#### Intuition:
A level-order traversal visits nodes level by level from top to bottom. Within each level, nodes are visited from left to right.
Therefore, if a level has $K$ nodes:
- The **last** node processed at that level is the **rightmost** node (for the Right View).
- The **first** node processed at that level is the **leftmost** node (for the Left View).

#### Algorithm:
1. If `root == null`, return an empty list `[]`.
2. Initialize an `ArrayDeque<TreeNode> queue` and enqueue `root`.
3. While `queue` is not empty:
   - Determine the number of nodes at the current level: `int levelSize = queue.size()`.
   - Iterate $i$ from $0$ to `levelSize - 1`:
     - Dequeue node `curr`.
     - If $i == \text{levelSize} - 1$, append `curr.val` to the result list (it is the rightmost node of this level).
     - If `curr.left != null`, enqueue `curr.left`.
     - If `curr.right != null`, enqueue `curr.right`.
4. Return the result list.

---

### Approach 2: Recursive Depth-First Search (DFS)

#### Intuition:
We can perform a modified preorder traversal:
- **Right View:** Visit **Root $\to$ Right $\to$ Left**.
- **Left View:** Visit **Root $\to$ Left $\to$ Right**.

By visiting the right child before the left child, the first node encountered at any depth $d$ is guaranteed to be the rightmost node at that level.

#### Algorithm:
1. Maintain a result list `result` and a current `depth` starting at 0.
2. If `depth == result.size()`:
   - We are encountering this depth level for the very first time.
   - Append `node.val` to `result`.
3. Recurse on `node.right` with `depth + 1`.
4. Recurse on `node.left` with `depth + 1`.

> [!NOTE]
> While recursive DFS uses $\mathcal{O}(H)$ memory, in languages with limited call stack sizes (such as Java's default thread stack of 1 MB), a tree with height $N = 10^4$ can cause a `StackOverflowError`. Iterative BFS runs entirely on the JVM heap and is 100% stack-safe for all tree topologies.

---

## 3. Detailed Trace & Dry Run

### Example 3: Left Subtree Extends Deeper
```text
          5         <-- Level 0
       /     \
      1       2     <-- Level 1
     /       / \
    8       4   5   <-- Level 2
     \
      6             <-- Level 3
```

#### BFS Step-by-Step Execution:

| Level | Queue at Start of Level | `levelSize` | Nodes Processed ($i = 0 \dots \text{size}-1$) | Rightmost Node ($i = \text{size}-1$) | Result List |
| :---: | :---: | :---: | :---: | :---: | :---: |
| 0 | `[5]` | 1 | $i = 0$: Node `5` (enqueues `1, 2`) | **`5`** | `[5]` |
| 1 | `[1, 2]` | 2 | $i = 0$: Node `1` (enqueues `8`)<br>$i = 1$: Node `2` (enqueues `4, 5`) | **`2`** | `[5, 2]` |
| 2 | `[8, 4, 5]` | 3 | $i = 0$: Node `8` (enqueues `6`)<br>$i = 1$: Node `4`<br>$i = 2$: Node `5` | **`5`** | `[5, 2, 5]` |
| 3 | `[6]` | 1 | $i = 0$: Node `6` | **`6`** | `[5, 2, 5, 6]` |

**Final Output:** `[5, 2, 5, 6]`

---

## 4. Complexity Analysis

| Metric | BFS Approach | DFS Approach |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ — Each node is enqueued and dequeued once. | $\mathcal{O}(N)$ — Each node is visited once. |
| **Space Complexity** | $\mathcal{O}(W) \le \mathcal{O}(N)$ — At most the maximum width of the tree in the heap queue. | $\mathcal{O}(H)$ — Call stack proportional to tree height ($H = N$ in worst-case skewed trees). |
| **Stack Safety** | **100% Safe** (Heap-allocated queue). | Risk of `StackOverflowError` when $H = 10^4$. |

---

## 5. Clean Reference Implementation (Java)

```java
public List<Integer> rightSideView(TreeNode root) {
    List<Integer> result = new ArrayList<>();
    if (root == null) return result;

    ArrayDeque<TreeNode> queue = new ArrayDeque<>();
    queue.add(root);

    while (!queue.isEmpty()) {
        int levelSize = queue.size();
        for (int i = 0; i < levelSize; i++) {
            TreeNode curr = queue.poll();
            // The last node in this level is visible from the right
            if (i == levelSize - 1) {
                result.add(curr.val);
            }
            if (curr.left != null) queue.add(curr.left);
            if (curr.right != null) queue.add(curr.right);
        }
    }

    return result;
}
```
