# Morris Inorder Traversal

## Problem Statement

Given the `root` of a binary tree, return the **Inorder** traversal of the binary tree.

**Morris Inorder Traversal** is a tree traversal algorithm aiming to achieve an auxiliary **space complexity of $\mathcal{O}(1)$** without using recursion or an external data structure (like a stack). It works by establishing temporary threaded links from the inorder predecessor back to the current node, and dismantling those links once the node is visited.

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

- Print the Inorder traversal of the binary tree as a comma-separated list enclosed in square brackets `[...]`.

### Example Output Format:
```text
[4, 4, 2, 1]
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
[4, 4, 2, 1]
```

**Explanation:**
The inorder traversal (`Left -> Root -> Right`) visits:
1. Left child of 4: `4`
2. Node `4`
3. Right child of 4: `2`
4. Root: `1`
Result: `[4, 4, 2, 1]`.

---

### Example 2

```text
Binary Tree:
        1
         \
          2
         /
        3
```

**Input:**
```text
[1, null, 2, 3]
```

**Output:**
```text
[1, 3, 2]
```

**Explanation:**
The inorder traversal visits `1`, then the left child of `2` (`3`), and finally `2`.
Result: `[1, 3, 2]`.

---

### Example 3

```text
Binary Tree:
        5
       / \
      1   2
     /   / \
    8   4   5
     \
      6
```

**Input:**
```text
[5, 1, 2, 8, null, 4, 5, null, 6]
```

**Output:**
```text
[8, 6, 1, 5, 4, 2, 5]
```

**Explanation:**
Inorder traversal visits `8 -> 6 -> 1 -> 5 -> 4 -> 2 -> 5`.
