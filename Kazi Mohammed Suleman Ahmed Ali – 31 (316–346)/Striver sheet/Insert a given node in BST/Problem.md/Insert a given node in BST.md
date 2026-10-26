# Insert a given node in BST

## Problem Statement

Given the `root` node of a **Binary Search Tree (BST)** and an integer `val` to insert into the tree, return the `root` node of the BST after the insertion.

It is guaranteed that the new value does not exist in the original BST.

There may exist multiple valid ways for the insertion, as long as the tree remains a BST after insertion. You can return any valid BST, but standard BST insertion attaches the new node at an available leaf/child position without restructuring the existing tree.

---

## Examples

### Example 1
**Input:**
```text
root = [4, 2, 7, 1, 3], val = 5
```
**Output:**
```text
[4, 2, 7, 1, 3, 5]
```
**Explanation:**
- Traversal starts at root `4`. Since $5 > 4$, move to the right child `7`.
- At node `7`, since $5 < 7$, move to the left child.
- The left child of `7` is `null`, so insert a new node with value `5` as the left child of `7`.

---

### Example 2
**Input:**
```text
root = [40, 20, 60, 10, 30, 50, 70], val = 25
```
**Output:**
```text
[40, 20, 60, 10, 30, 50, 70, null, null, 25]
```
**Explanation:**
- Start at `40`: $25 < 40 \implies$ move left to `20`.
- At `20`: $25 > 20 \implies$ move right to `30`.
- At `30`: $25 < 30 \implies$ move left.
- Left child of `30` is `null`, so insert `25` as the left child of `30`.

---

### Example 3
**Input:**
```text
root = [4, 2, 7, 1, 3], val = 6
```
**Output:**
```text
[4, 2, 7, 1, 3, 6]
```
**Explanation:**
- Start at `4`: $6 > 4 \implies$ move right to `7`.
- At `7`: $6 < 7 \implies$ move left (null), so insert `6` as the left child of `7`.

---

## Constraints

- $1 \le \text{Number of nodes} \le 10^4$
- $-10^8 \le \text{Node.val} \le 10^8$
- $-10^8 \le \text{val} \le 10^8$
- All values in the original BST are **unique**.
- It is guaranteed that `val` does **not** exist in the original BST.

---

## Input Format

- **Line 1:** Level-order serialization of the BST, enclosed in `[...]` or formatted as comma/space-separated values. `null` represents absent child nodes.
- **Line 2:** An integer `val`, the value to be inserted.

## Output Format

- A single line containing the level-order serialization of the BST after inserting `val`, enclosed in square brackets `[...]`.
