# Morris Preorder Traversal

## Problem Statement

Given the `root` of a binary tree, return the **Preorder** traversal of the binary tree.

**Morris Preorder Traversal** is a tree traversal algorithm aiming to achieve an auxiliary **space complexity of $\mathcal{O}(1)$** without using recursion or an external data structure (such as a stack). It works by establishing temporary threaded links from the inorder predecessor back to the current node, recording node values upon the first encounter, and dismantling those links on the return visit.

---

## Input Format

- A single line representing the level-order serialization of the binary tree.
- The input can be provided as comma-separated values in brackets (e.g., `[1, 4, null, 4, 2]` or `root = [1, 4, null, 4, 2]`) or space-separated values.
- Missing/null children are represented by `null`.

### Example Input Format:
```text
[1, 4, null, 4, 2]
```

---

## Output Format

- Print the Preorder traversal of the binary tree as a comma-separated list enclosed in square brackets `[...]`.

### Example Output Format:
```text
[1, 4, 4, 2]
```

---

## Constraints

- $1 \le \text{Number of Nodes} \le 100$
- $-100 \le \text{Node.val} \le 100$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

```text
Binary Tree:
        1
       /
      4
     / \
    4   2
```

**Input:**
```text
[1, 4, null, 4, 2]
```

**Output:**
```text
[1, 4, 4, 2]
```

**Explanation:**
Preorder traversal (`Root -> Left -> Right`) visits:
1. Root: `1`
2. Left child: `4`
3. Left-left child: `4`
4. Left-right child: `2`
Result: `[1, 4, 4, 2]`.

---

### Example 2

```text
Binary Tree:
        1
```

**Input:**
```text
[1]
```

**Output:**
```text
[1]
```

**Explanation:**
Only the root node is present. Result: `[1]`.

---

### Example 3

```text
Binary Tree:
        1
       / \
      4   2
     /     \
    9       6
```

**Input:**
```text
[1, 4, 2, 9, null, null, 6]
```

**Output:**
```text
[1, 4, 9, 2, 6]
```

**Explanation:**
Preorder traversal visits `1 -> 4 -> 9 -> 2 -> 6`.
Result: `[1, 4, 9, 2, 6]`.
