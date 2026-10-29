# Minimum and Maximum in BST

## Problem Statement

You are given the `root` of a **Binary Search Tree (BST)** containing integer values.

Your task is to find the **minimum** and **maximum** values present in the BST.

In a Binary Search Tree:
- All values in the left subtree of a node are strictly smaller than the node's value.
- All values in the right subtree of a node are strictly greater than the node's value.

Return the minimum and maximum values present in the BST as a 2-element array/list `[min, max]`, where the first element is the minimum value and the second element is the maximum value.

---

## Input Format

- A single line containing the level-order serialization of the binary search tree enclosed in square brackets `[...]` (or space-separated values).
- Missing/null children are represented by `null`.

### Example Input Format:
```text
[8, 3, 12, 1, 6, 10, 14]
```

---

## Output Format

- Print a two-element array `[min, max]` representing the minimum and maximum values in the BST.

### Example Output Format:
```text
[1, 14]
```

---

## Constraints

- $1 \le \text{number of nodes} \le 10^5$
- $-10^9 \le \text{Node.val} \le 10^9$
- All node values are **unique**.
- The given tree is a valid Binary Search Tree.
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

```text
Given BST:
          8
        /   \
       3     12
      / \   /  \
     1   6 10  14
```

**Input:**
```text
root = [8, 3, 12, 1, 6, 10, 14]
```

**Output:**
```text
[1, 14]
```

**Explanation:**
The smallest value in the BST is `1` (leftmost node), and the largest value is `14` (rightmost node). Therefore, the answer is `[1, 14]`.

---

### Example 2

```text
Given BST:
            20
          /    \
        10      30
       /  \    /  \
      5   15  25  40
       \
        7
```

**Input:**
```text
root = [20, 10, 30, 5, 15, 25, 40, null, 7]
```

**Output:**
```text
[5, 40]
```

**Explanation:**
Following the leftmost path from the root leads to `5`. Following the rightmost path from the root leads to `40`. The answer is `[5, 40]`.

---

### Example 3

```text
Given BST:
            15
          /    \
         8      22
        / \    /  \
       4  11  18  27
        \
         6
```

**Input:**
```text
root = [15, 8, 22, 4, 11, 18, 27, null, 6]
```

**Output:**
```text
[4, 27]
```

**Explanation:**
The minimum element in the BST is `4`, and the maximum element is `27`. The answer is `[4, 27]`.
