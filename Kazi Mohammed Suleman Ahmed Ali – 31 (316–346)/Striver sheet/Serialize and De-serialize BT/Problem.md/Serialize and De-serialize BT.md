# Serialize and De-serialize BT

## Problem Statement

Serialization is the process of converting a data structure or object into a sequence of bits or a string so that it can be stored in a file or memory buffer, or transmitted across a network connection link to be reconstructed later in the same or another computer environment.

Design an algorithm to **serialize** and **deserialize** a binary tree. There is no restriction on how your serialization/deserialization algorithm should work. You just need to ensure that a binary tree can be serialized to a string, and this string can be deserialized to the original tree structure.

The encoded string should be as compact as possible.

---

## Input Format

- A single line containing the level-order representation of the binary tree.
- Format can be comma-separated in square brackets (e.g., `[2, 1, 3]` or `[7, 3, 15, null, null, 9, 20]`) or space-separated values.
- Empty tree is represented by `[]`.
- Missing nodes are represented by `null`.

### Example Input Format:
```text
[7, 3, 15, null, null, 9, 20]
```

---

## Output Format

- Print the level-order serialization of the reconstructed binary tree enclosed in square brackets `[...]`.
- Missing children before trailing nodes are represented by `null`. Trailing `null` values are omitted.

### Example Output Format:
```text
[7, 3, 15, null, null, 9, 20]
```

---

## Constraints

- $1 \le \text{Number of Nodes} \le 10^4$
- $0 \le \text{Node.val} \le 10^4$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

```text
Binary Tree:
        2
       / \
      1   3
```

**Input:**
```text
[2, 1, 3]
```

**Output:**
```text
[2, 1, 3]
```

**Explanation:**
The binary tree has root `2` with left child `1` and right child `3`. Serialization and subsequent deserialization completely preserves this exact tree structure.

---

### Example 2

```text
Binary Tree:
        7
       / \
      3   15
         /  \
        9   20
```

**Input:**
```text
[7, 3, 15, null, null, 9, 20]
```

**Output:**
```text
[7, 3, 15, null, null, 9, 20]
```

**Explanation:**
The node `3` has no children (represented by `null, null`). Node `15` has children `9` and `20`. The structure is faithfully preserved.

---

### Example 3

```text
Binary Tree:
        10
       /  \
      20  30
     /  \   \
    40  50  60
```

**Input:**
```text
[10, 20, 30, 40, 50, null, 60]
```

**Output:**
```text
[10, 20, 30, 40, 50, null, 60]
```

**Explanation:**
All parent-child relationships and null markers are accurately restored after serialization and deserialization.
