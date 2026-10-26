# Editorial: Minimum Time Taken to Burn the BT from a Given Node

## 1. Problem Overview

Given the root of a binary tree and a target node `start`, we want to find the minimum time (in seconds) required for fire to consume the entire tree.
- At time $t = 0$, the target node is set on fire.
- In each subsequent second, fire spreads from every currently burning node to its immediate neighbors (left child, right child, and parent).
- Once burned, a node remains burned and cannot be reignited.
- The minimum time to burn the entire tree is equivalent to the **maximum shortest-path distance** from the target node to any other node in the tree:
  $$\text{Burn Time} = \max_{v \in \text{Tree}} \text{distance}(\text{target}, v)$$

---

## 2. Approach: Graph Conversion & Radial BFS

In a standard binary tree, node references only point downwards (`left` and `right`). Because fire propagates in all directions (including upwards toward the root), we can model the tree as an undirected graph.

### Step-by-Step Procedure:

### Step 1: Establish Parent Pointers & Locate Target
- Run a standard BFS starting from `root`.
- Store each node's parent in a map: `parentTrack.put(child, parent)`.
- Simultaneously locate the target node instance (`curr.val == start`).

### Step 2: Simulate Fire Spread using Radial BFS
- Initialize a queue with the target node: `queue.add(target)`.
- Maintain a `Set<TreeNode> visited` containing `target`.
- Initialize `time = 0`.
- While `queue` is not empty:
  - Record the current queue size: `size = queue.size()`.
  - Maintain a boolean flag `burnedAny = false` for the current second.
  - Process all `size` nodes burning at the current time:
    - Dequeue `curr`.
    - Check the **left child**: if non-null and not visited, ignite and enqueue it, set `burnedAny = true`.
    - Check the **right child**: if non-null and not visited, ignite and enqueue it, set `burnedAny = true`.
    - Check the **parent node**: if non-null and not visited, ignite and enqueue it, set `burnedAny = true`.
  - If at least one new node was ignited (`burnedAny == true`), increment `time++`.

### Step 3: Return Result
- When the queue becomes empty, all reachable nodes in the tree have burned.
- Return `time`.

---

## 3. Step-by-Step Trace (Example 1)

```text
            1
         /     \
        2       3
       /       / \
      4       5   6
       \
        7
```
`start = 1`.

| Time Step ($t$) | Burning Nodes in Queue | Neighbors Ignited | `burnedAny` | Total Time |
| :---: | :---: | :--- | :---: | :---: |
| **0** | `[1]` | Left: `2`<br>Right: `3` | Yes | 1 |
| **1** | `[2, 3]` | From `2`: `4`<br>From `3`: `5, 6` | Yes | 2 |
| **2** | `[4, 5, 6]` | From `4`: `7`<br>From `5, 6`: None | Yes | 3 |
| **3** | `[7]` | From `7`: None (parent `4` already burned) | No | 3 |

**Result:** `3` seconds.

---

## 4. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(N)$
  - Mapping parent pointers via BFS visits every node once: $\mathcal{O}(N)$.
  - The radial BFS visits each node and each edge at most once: $\mathcal{O}(N)$.
  - Total time is strictly linear in the number of nodes, running in $< 0.1$ seconds for $N = 10^4$.

- **Space Complexity:** $\mathcal{O}(N)$
  - `parentTrack` map stores $N - 1$ entries.
  - `visited` set stores at most $N$ nodes.
  - `queue` stores at most $N$ nodes at any point.
  - Total auxiliary memory is $\mathcal{O}(N)$, comfortably within the 256 MB memory limit.
