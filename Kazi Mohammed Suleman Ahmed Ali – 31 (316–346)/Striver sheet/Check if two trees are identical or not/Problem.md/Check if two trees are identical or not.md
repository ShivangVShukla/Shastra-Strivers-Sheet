# Check if two trees are identical or not

## Problem Statement

Given the roots of two binary trees `p` and `q`, write a function to check if they are the **same** or not.

Two binary trees are considered the same if and only if:
1. They are **structurally identical** (every node in one tree has the corresponding child structure in the other).
2. All corresponding nodes have the **same value**.

---

## Input Format

The input is provided on two lines:
- The **first line** contains the level-order (breadth-first) traversal of the first binary tree `p` as a space-separated sequence of tokens.
- The **second line** contains the level-order (breadth-first) traversal of the second binary tree `q` as a space-separated sequence of tokens.

Each token is either an integer representing a node's value or the string `null` denoting a missing child. An empty tree is represented by `null` or an empty line.

---

## Output Format

Print `true` if the two binary trees are identical; otherwise, print `false`.

---

## Constraints

- $0 \le \text{Number of Nodes }  \le 100$
- $-10^4 \le \text{Node.val} \le 10^4$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 2 3
1 2 3
```

**Output:**
```text
true
```

**Explanation:**
Both trees have identical structure and identical node values:
```text
      Tree p                Tree q
        1                     1
      /   \                 /   \
     2     3               2     3
```
Both trees are structurally identical and have the same node values everywhere. Hence, the output is `true`.

---

### Example 2

**Input:**
```text
1 2 1
1 1 2
```

**Output:**
```text
false
```

**Explanation:**
The trees are:
```text
      Tree p                Tree q
        1                     1
      /   \                 /   \
     2     1               1     2
```
Although both trees have the same set of values, the values at the left child ($2$ vs $1$) and right child ($1$ vs $2$) do not match. Hence, the output is `false`.

---

### Example 3

**Input:**
```text
1 2
1 null 2
```

**Output:**
```text
false
```

**Explanation:**
Tree `p` has root $1$ with a left child $2$, while tree `q` has root $1$ with a right child $2$.
```text
      Tree p                Tree q
        1                     1
      /                        \
     2                          2
```
Their structures differ, so the output is `false`.

---

### Example 4 (Empty Trees)

**Input:**
```text
null
null
```

**Output:**
```text
true
```

**Explanation:**
Both trees are empty (`null`), so they are identical.

---

### Example 5 (One Empty Tree)

**Input:**
```text
1 2 3
null
```

**Output:**
```text
false
```

**Explanation:**
One tree is non-empty while the other is empty. They are not identical.
