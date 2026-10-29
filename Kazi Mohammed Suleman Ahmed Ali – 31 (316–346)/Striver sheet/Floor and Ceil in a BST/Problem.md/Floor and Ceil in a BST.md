# Floor and Ceil in a BST

## Problem Statement

Given the `root` of a **Binary Search Tree (BST)** and an integer `key`, find the **floor** and **ceil** value for that particular `key`.

- **Floor Value:** The value of the node with the greatest data that is **less than or equal to** `key` ($\le \text{key}$).
- **Ceil Value:** The value of the node with the smallest data that is **greater than or equal to** `key` ($\ge \text{key}$).

If a particular floor or ceil value does not exist in the BST, output `-1` for that value.

---

## Input Format

- **Line 1:** The level-order serialization of the binary search tree enclosed in square brackets `[...]` (or space-separated values).
- **Line 2:** An integer `key` representing the search key.

### Example Input Format:
```text
[8, 4, 12, 2, 6, 10, 14]
11
```

---

## Output Format

- Print a two-element array `[floor, ceil]` representing the floor and ceil values.

### Example Output Format:
```text
[10, 12]
```

---

## Constraints

- $1 \le \text{Number of Nodes} \le 5000$
- $1 \le \text{Node.val} \le 10^7$
- $1 \le \text{key} \le 10^7$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

```text
Given BST:
          8
        /   \
       4     12
      / \   /  \
     2   6 10  14

Key: 11
```

**Input:**
```text
root = [8, 4, 12, 2, 6, 10, 14]
key = 11
```

**Output:**
```text
[10, 12]
```

**Explanation:**
- Nodes in the tree with values $\le 11$ are `2, 4, 6, 8, 10`. The largest of these is `10` (Floor).
- Nodes in the tree with values $\ge 11$ are `12, 14`. The smallest of these is `12` (Ceil).
- Result: `[10, 12]`.

---

### Example 2

```text
Given BST:
          8
        /   \
       4     12
      / \   /  \
     2   6 10  14

Key: 15
```

**Input:**
```text
root = [8, 4, 12, 2, 6, 10, 14]
key = 15
```

**Output:**
```text
[14, -1]
```

**Explanation:**
- Largest value $\le 15$ is `14` (Floor).
- No value $\ge 15$ exists in the BST, so Ceil is `-1`.
- Result: `[14, -1]`.

---

### Example 3

```text
Given BST:
          8
        /   \
       4     12
      / \   /  \
     2   6 10  14

Key: 1
```

**Input:**
```text
root = [8, 4, 12, 2, 6, 10, 14]
key = 1
```

**Output:**
```text
[-1, 2]
```

**Explanation:**
- No value $\le 1$ exists in the BST, so Floor is `-1`.
- Smallest value $\ge 1$ is `2` (Ceil).
- Result: `[-1, 2]`.
