# Boundary Traversal

## Problem Statement

Given the root of a binary tree, perform the **boundary traversal** of the tree in the **anti-clockwise direction**, starting from the root.

The boundary of a binary tree is defined as the concatenation of:
1. **The Root Node:** Included first (if the tree is non-empty).
2. **The Left Boundary:** The sequence of nodes from the root's left child following the boundary down to (but **excluding**) the leaf nodes.
3. **The Leaf Nodes:** All leaf nodes in the tree visited in **left-to-right order**.
4. **The Right Boundary (in Reverse):** The sequence of nodes from the bottom-most non-leaf node up to the root's right child, traversed in **bottom-up (reverse) order**.

---

### Precise Boundary Rules

1. **Left Boundary:**
   - Starts at `root.left`. If `root.left` is `null`, the left boundary is empty.
   - For each node:
     - If the node has a left child, advance to the left child.
     - Else if the node has no left child but has a right child, advance to the right child.
     - If the node is a leaf, stop (leaves are excluded from the left boundary to avoid duplication).

2. **Leaf Nodes:**
   - A node is a leaf if both `left == null` and `right == null`.
   - All leaves across the entire tree are collected in strictly left-to-right order.
   - If the root itself is a leaf (a tree with exactly 1 node), it is collected as a leaf and appears exactly once in the output.

3. **Right Boundary:**
   - Starts at `root.right`. If `root.right` is `null`, the right boundary is empty.
   - For each node:
     - If the node has a right child, advance to the right child.
     - Else if the node has no right child but has a left child, advance to the left child.
     - If the node is a leaf, stop (leaves are excluded from the right boundary).
   - The collected non-leaf right boundary nodes are appended in **reverse order** (from bottom to top).

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The token `null` denotes an absent child node.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print the boundary traversal as a comma-separated list of integers enclosed in square brackets `[...]`:
- Example: `[1, 2, 4, 8, 9, 6, 7, 3]`
- If the tree is empty, print `[]`.

---

## Constraints

- $0 \le \text{Number of Nodes } (N) \le 10^4$
- $-10^3 \le \text{Node.val} \le 10^3$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3 4 5 6 7 null null 8 9
```

**Output:**
```text
[1, 2, 4, 8, 9, 6, 7, 3]
```

**Explanation:**
The binary tree has the following structure:
```text
           1
         /   \
        2     3
       / \   / \
      4   5 6   7
         / \
        8   9
```
1. **Root:** `[1]`
2. **Left Boundary (excluding leaves):**
   - Start at `2`. `2` has left child `4`.
   - Node `4` is a leaf, so left boundary traversal stops.
   - Left boundary nodes: `[2]`.
3. **Leaves (left-to-right):**
   - In left subtree: `4`, `8`, `9`.
   - In right subtree: `6`, `7`.
   - Leaves: `[4, 8, 9, 6, 7]`.
4. **Right Boundary in reverse (excluding leaves):**
   - Start at `3`. `3` has right child `7`.
   - Node `7` is a leaf, so right boundary traversal stops.
   - Right boundary nodes: `[3]`. Reverse: `[3]`.

**Concatenation:**
`[1] + [2] + [4, 8, 9, 6, 7] + [3] = [1, 2, 4, 8, 9, 6, 7, 3]`.

---

### Example 2

**Input:**
```text
1 2 null 4 9 6 5 3 null null null null null 7 8
```

**Output:**
```text
[1, 2, 4, 6, 5, 7, 8]
```

**Explanation:**
- Root: `1`
- Left boundary: `2`, `4` (stop before leaf `6`)
- Leaves left-to-right: `6`, `5`, `7`, `8`
- Right boundary: empty (root has no right child)
- Combined result: `[1, 2, 4, 6, 5, 7, 8]`.

---

### Example 3

**Input:**
```text
5 1 2 8 null 4 5 null 6
```

**Output:**
```text
[5, 1, 8, 6, 4, 5, 2]
```

**Explanation:**
The tree structure is:
```text
          5
        /   \
       1     2
      /     / \
     8     4   5
      \
       6
```
- **Root:** `5`
- **Left boundary:** `1`, `8` (node `6` is a leaf, stop)
- **Leaves:** `6`, `4`, `5`
- **Right boundary in reverse:** `2` (root's right child `2` has children, not a leaf; reverse is `[2]`)
- **Combined:** `[5, 1, 8, 6, 4, 5, 2]`.

---

### Example 4 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
[42]
```

**Explanation:**
A tree consisting of a single node has root `42`. Since it is a leaf, it is output once: `[42]`.

---

### Example 5 (Empty Tree)

**Input:**
```text
null
```

**Output:**
```text
[]
```

**Explanation:**
The tree is empty, resulting in an empty list `[]`.
