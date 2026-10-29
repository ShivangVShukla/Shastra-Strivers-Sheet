# Reverse LL in Group of Given Size K

## Problem Statement

Given the head of a singly linked list containing integers and an integer `k`, reverse the nodes of the linked list in consecutive groups of size `k` and return the head of the modified list.

If the number of nodes in the linked list is not a multiple of `k`, the remaining nodes at the end must be kept in their original order and must not be reversed.

**Do not change the values stored in the nodes. Only modify the links between the nodes.**

### Example 1

**Input:**
```text
head -> 1 -> 2 -> 3 -> 4 -> 5
k = 2
```

**Output:**
```text
head -> 2 -> 1 -> 4 -> 3 -> 5
```

**Explanation:**

The linked list is divided into groups of size `2`: `1 -> 2`, `3 -> 4`, and `5`. The first two complete groups are reversed to obtain `2 -> 1` and `4 -> 3`. The final node `5` remains unchanged because it does not form a complete group.

### Example 2

**Input:**
```text
head -> 1 -> 2 -> 3 -> 4 -> 5
k = 3
```

**Output:**
```text
head -> 3 -> 2 -> 1 -> 4 -> 5
```

**Explanation:**

The first group, `1 -> 2 -> 3`, is reversed to become `3 -> 2 -> 1`. The remaining nodes, `4 -> 5`, are fewer than `k = 3`, so they remain in their original order.

### Constraints

- The linked list may contain zero or more nodes.
- Node values are integers.
- `k` is a positive integer.
- Only complete groups of `k` nodes should be reversed.
- The relative order of nodes in an incomplete final group must remain unchanged.
- The values stored in the nodes must not be modified.

### Expected Complexity

- **Time Complexity:** `O(n)`, where `n` is the number of nodes in the linked list.
- **Auxiliary Space Complexity:** `O(1)` for an iterative in-place solution.

### Input Format

- The first line contains the linked-list elements separated by spaces.
- The second line contains the integer `k`.

### Output Format

Print the elements of the modified linked list in order, separated by spaces.

### Additional Examples

**Example 3 — Group size is 1**

Input:
```text
10 20 30 40
1
```

Output:
```text
10 20 30 40
```

**Example 4 — Incomplete group at the end**

Input:
```text
1 2 3 4 5 6 7
3
```

Output:
```text
3 2 1 6 5 4 7
```

**Example 5 — Group size exceeds list length**

Input:
```text
1 2 3
5
```

Output:
```text
1 2 3
```
