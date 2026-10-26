# Check for balanced binary tree

## Problem Statement

Given the root of a binary tree, determine whether it is **height-balanced** or not.

A binary tree is defined to be **height-balanced** if and only if:
- For **every node** in the tree, the absolute difference between the heights of its left subtree and right subtree is **at most 1**:

$$|\text{height}(\text{left}) - \text{height}(\text{right})| \le 1$$

An empty tree (`null`) is considered height-balanced by definition.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` indicates that the corresponding child does not exist.
- If the tree is empty, the input line contains `null` or is empty.

---

## Output Format

Print `true` if the binary tree is height-balanced; otherwise, print `false`.

---

## Constraints

- $0 \le \text{Number of Nodes } (N) \le 10^5$
- $1 \le \text{Node.val} \le 10^5$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
3 9 20 null null 15 7
```

**Output:**
```text
true
```

**Explanation:**
The binary tree structure is:
```text
        3
      /   \
     9     20
          /  \
         15   7
```
Checking heights at each node:
- At node `15`: left height = 0, right height = 0 $\to |0 - 0| = 0 \le 1$.
- At node `7`: left height = 0, right height = 0 $\to |0 - 0| = 0 \le 1$.
- At node `20`: left height = 1, right height = 1 $\to |1 - 1| = 0 \le 1$.
- At node `9`: left height = 0, right height = 0 $\to |0 - 0| = 0 \le 1$.
- At root `3`: left height = 1, right height = 2 $\to |1 - 2| = 1 \le 1$.

Every node satisfies the balance condition. The output is `true`.

---

### Example 2

**Input:**
```text
1 2 null null 3
```

**Output:**
```text
false
```

**Explanation:**
The binary tree structure is:
```text
        1
       /
      2
       \
        3
```
- At node `1`: the left subtree has height 2 (path $1 \to 2 \to 3$), while the right subtree does not exist (height 0).
- The difference is $|2 - 0| = 2 > 1$.

The condition is violated at the root. The output is `false`.

---

### Example 3

**Input:**
```text
1 2 3 4 5 6
```

**Output:**
```text
true
```

**Explanation:**
The binary tree structure is:
```text
        1
      /   \
     2     3
    / \   /
   4   5 6
```
- At root `1`: left height = 2, right height = 2 $\to |2 - 2| = 0 \le 1$.
- At node `2`: left height = 1, right height = 1 $\to |1 - 1| = 0 \le 1$.
- At node `3`: left height = 1, right height = 0 $\to |1 - 0| = 1 \le 1$.
- All leaves have height 0.

Every node is height-balanced, so the output is `true`.

---

### Example 4 (Empty Tree)

**Input:**
```text
null
```

**Output:**
```text
true
```

**Explanation:**
An empty binary tree has 0 nodes and is height-balanced by definition.

---

### Example 5 (Single Node)

**Input:**
```text
42
```

**Output:**
```text
true
```

**Explanation:**
The root has no children. Left height = 0, right height = 0 $\to |0 - 0| = 0 \le 1$.
