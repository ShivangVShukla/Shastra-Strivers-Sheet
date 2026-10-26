# Delete a node in BST

## Problem Statement

Given the `root` node of a **Binary Search Tree (BST)** and an integer `key`, delete the node with the given `key` from the BST and return the `root` node of the BST after deletion.

If the node with value `key` is not found in the BST, return the original tree unchanged.

> **Note:** As there can be multiple valid ways to restructure the tree after deletion, any valid BST that preserves all remaining elements is acceptable. Standard BST deletion replaces a node having two children with its **inorder successor** (the smallest value in its right subtree).

---

## Examples

### Example 1
**Input:**
```text
root = [5, 3, 6, 2, 4, null, 7], key = 3
```
**Output:**
```text
[5, 4, 6, 2, null, null, 7]
```
**Explanation:**
- The node to be deleted is `3`. It has two children: `2` (left) and `4` (right).
- Its inorder successor is `4` (the minimum node in its right subtree).
- Replacing node `3` with `4` and splicing out the successor yields the valid BST:
  ```text
          5
         / \
        4   6
       /     \
      2       7
  ```
- Level-order serialization: `[5, 4, 6, 2, null, null, 7]`.

---

### Example 2
**Input:**
```text
root = [5, 3, 6, 2, 4, null, 7], key = 0
```
**Output:**
```text
[5, 3, 6, 2, 4, null, 7]
```
**Explanation:**
- The value `0` does not exist in the BST, so the tree remains completely unchanged.

---

### Example 3
**Input:**
```text
root = [5, 3, 6, 2, 4, null, 7], key = 5
```
**Output:**
```text
[6, 3, 7, 2, 4]
```
**Explanation:**
- The root node `5` is to be deleted.
- Its inorder successor is `6` (the smallest element in the right subtree).
- Replacing `5` with `6` and attaching the subtrees appropriately yields a valid BST with root `6`.

---

## Constraints

- $0 \le \text{Number of nodes} \le 10^4$
- $-10^8 \le \text{Node.val} \le 10^8$
- All values in the BST are **unique**.
- $-10^8 \le \text{key} \le 10^8$

---

## Input Format

- **Line 1:** Level-order serialization of the BST, enclosed in `[...]` or formatted as comma/space-separated values. `null` represents absent child nodes.
- **Line 2:** An integer `key`, the value of the node to be deleted.

## Output Format

- A single line containing the level-order serialization of the BST after deleting `key`, enclosed in square brackets `[...]`. If the resulting tree is empty, output `[]`.
