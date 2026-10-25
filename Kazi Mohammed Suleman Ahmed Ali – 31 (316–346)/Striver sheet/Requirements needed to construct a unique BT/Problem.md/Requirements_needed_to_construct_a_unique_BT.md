# 885. Requirements needed to construct a unique BT

## Problem Statement

Given a pair of tree traversals, return **true** if a unique binary tree can be constructed from them, otherwise return **false**.

Each traversal is represented by an integer:

| Value | Traversal |
|-------|-----------|
| 1     | Preorder  |
| 2     | Inorder   |
| 3     | Postorder |

## Input Format

A single line containing two space-separated integers `a` and `b`, the two traversal types that are given.

## Output Format

Print `true` if a unique binary tree can be constructed from the given pair of traversals, otherwise print `false`.

## Examples

### Example 1

**Input:**
```
1 2
```

**Output:**
```
true
```

**Explanation:** A unique binary tree can be constructed. The preorder traversal provides the root of the tree, and the inorder traversal helps determine the left and right subtrees.

### Example 2

**Input:**
```
2 2
```

**Output:**
```
false
```

**Explanation:** Two inorder traversals are insufficient to uniquely determine a binary tree.

## Constraints

- `1 <= a, b <= 3`
