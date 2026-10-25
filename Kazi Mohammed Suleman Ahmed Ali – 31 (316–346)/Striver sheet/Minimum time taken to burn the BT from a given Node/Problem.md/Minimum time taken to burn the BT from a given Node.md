# Minimum time taken to burn the BT from a given Node

## Problem Statement

Given a target node data `start` and the root of a binary tree, determine the shortest amount of **time** (in seconds) needed to **burn** the entire binary tree if the target node is set on fire at time $t = 0$.

It is known that in $1$ second, fire spreads from any burning node to all adjacent connected nodes. That is:
- Its **left child**
- Its **right child**
- Its **parent node**

Once a node catches fire, it continues to burn and can ignite its unburned neighbors in the subsequent second. Both `null` and `N` represent non-existent child nodes.

---

## Input Format

The input is provided across lines as follows:
- **Line 1:** Space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree.
  - The first token represents the value of the root node.
  - Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
  - The token `null` or `N` indicates that the corresponding child does not exist.
- **Line 2:** An integer representing the value of the **target** node `start`.

*(The solution also flexibly parses inputs where target is on line 1 and the tree on line 2).*

---

## Output Format

Print a single integer representing the minimum time in seconds required to burn the entire tree:
- Example: `3`
- If the tree consists of only the target node, the answer is `0`.
- If the tree is empty, print `0`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 10^4$ (empty tree $N = 0$ is also supported)
- $-10^5 \le \text{Node.val} \le 10^5$
- All `Node.val` values are **unique**.
- `start` (or `target`) will always be present in the tree.
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3 4 null 5 6 null 7
1
```

**Output:**
```text
3
```

**Explanation:**
The binary tree structure is:
```text
            1             <-- t = 0: Node 1 set on fire
         /     \
        2       3         <-- t = 1: Nodes 2 and 3 burn
       /       / \
      4       5   6       <-- t = 2: Nodes 4, 5, and 6 burn
       \
        7                 <-- t = 3: Node 7 burns
```
- At $t = 0$: Node `1` is ignited.
- At $t = 1$: Fire spreads to left child `2` and right child `3`.
- At $t = 2$: Fire spreads to `4` (from `2`) and `5, 6` (from `3`).
- At $t = 3$: Fire spreads to `7` (from `4`).

All nodes are burned in $3$ seconds.

---

### Example 2

**Input:**
```text
1 2 3 null 5 null 4
4
```

**Output:**
```text
4
```

**Explanation:**
The binary tree structure is:
```text
       1
     /   \
    2     3
     \     \
      5     4             <-- t = 0: Node 4 set on fire
```
- At $t = 0$: Node `4` is ignited.
- At $t = 1$: Fire spreads up to parent `3`.
- At $t = 2$: Fire spreads up to parent `1`.
- At $t = 3$: Fire spreads down to left child `2`.
- At $t = 4$: Fire spreads down to right child `5`.

Total time required to burn all nodes is $4$ seconds.

---

### Example 3

**Input:**
```text
1 2 3 4 5 6
6
```

**Output:**
```text
4
```

**Explanation:**
The binary tree structure is:
```text
          1
       /     \
      2       3
     / \     /
    4   5   6             <-- t = 0: Node 6 set on fire
```
- At $t = 0$: Node `6` is ignited.
- At $t = 1$: Node `3` catches fire.
- At $t = 2$: Node `1` catches fire.
- At $t = 3$: Node `2` catches fire.
- At $t = 4$: Nodes `4` and `5` catch fire.

Total time required is $4$ seconds.
