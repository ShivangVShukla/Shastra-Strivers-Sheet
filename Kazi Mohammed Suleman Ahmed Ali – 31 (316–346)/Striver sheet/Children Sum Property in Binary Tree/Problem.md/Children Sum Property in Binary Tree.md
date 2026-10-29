# Children Sum Property in Binary Tree

## Problem Statement

Given the root of a binary tree, return `true` if and only if **every node’s value is equal to the sum of the values stored in its immediate left and right children**.

### Property Definition:
For every node in the binary tree:
- If a child does not exist (`null`), its value contributes `0` to the sum.
- A **leaf node** (a node with no left or right child) automatically satisfies the property because both children are considered `null` (sum = $0 + 0 = 0$ is not required for leaves; leaves define base values and trivially satisfy the condition).
- For every **internal node** (a node with at least one non-null child):
  $$\text{node.val} = (\text{node.left} \ne \text{null} \ ? \ \text{node.left.val} : 0) + (\text{node.right} \ne \text{null} \ ? \ \text{node.right.val} : 0)$$

An empty tree (`null`) or a single-node tree trivially satisfies the property.

---

## Input Format

The input is provided on a single line as a space-separated sequence of tokens representing the **level-order (breadth-first) traversal** of the binary tree:
- The first token represents the value of the root node.
- Subsequent tokens represent the left and right children of previously created nodes, processed in FIFO order.
- The string `null` denotes an absent child node.
- If the tree is empty, the input contains `null` or is empty.

---

## Output Format

Print `true` if every node in the binary tree satisfies the children sum property; otherwise, print `false`.

---

## Constraints

- $1 \le \text{Number of Nodes } (N) \le 10^4$
- $-10^5 \le \text{Node.val} \le 10^5$
- **Time Limit:** 2.0 seconds
- **Memory Limit:** 256 MB

---

## Examples

### Example 1

**Input:**
```text
1 4 3 5
```

**Output:**
```text
false
```

**Explanation:**
The binary tree structure is:
```text
        1
       / \
      4   3
     /
    5
```
Checking each internal node:
- For node `4`: Left child is `5`, right child is `null` ($0$). Sum = $5 + 0 = 5$. Since $4 \ne 5$, this node violates the property.
- For root `1`: Left child is `4`, right child is `3`. Sum = $4 + 3 = 7$. Since $1 \ne 7$, root also violates the property.

Since at least one node violates the property, the output is `false`.

---

### Example 2

**Input:**
```text
10 4 6 1 3 2 4
```

**Output:**
```text
true
```

**Explanation:**
The binary tree structure is:
```text
          10
        /    \
       4      6
      / \    / \
     1   3  2   4
```
Checking each node:
- Leaves: `1`, `3`, `2`, `4` automatically satisfy the property.
- Internal node `4`: Left child `1` + Right child `3` = $1 + 3 = 4$. Holds ($4 == 4$).
- Internal node `6`: Left child `2` + Right child `4` = $2 + 4 = 6$. Holds ($6 == 6$).
- Root node `10`: Left child `4` + Right child `6` = $4 + 6 = 10$. Holds ($10 == 10$).

Every node satisfies the condition. The output is `true`.

---

### Example 3

**Input:**
```text
35 20 15 15 5 10 5
```

**Output:**
```text
true
```

**Explanation:**
The binary tree structure is:
```text
          35
        /    \
       20     15
      /  \   /  \
     15   5 10   5
```
- Node `20`: $15 + 5 = 20$.
- Node `15`: $10 + 5 = 15$.
- Root `35`: $20 + 15 = 35$.

All internal nodes satisfy the property. The output is `true`.

---

### Example 4 (Single Child)

**Input:**
```text
7 7 null null null
```

**Output:**
```text
true
```

**Explanation:**
```text
    7
   /
  7
```
The root has value `7`, its left child is `7`, and right child is missing ($0$). Sum = $7 + 0 = 7$. Leaf `7` satisfies the property trivially. The output is `true`.

---

### Example 5 (Negative Node Values)

**Input:**
```text
-5 -2 -3
```

**Output:**
```text
true
```

**Explanation:**
```text
     -5
    /   \
  -2     -3
```
Root has value `-5`. Children sum = $(-2) + (-3) = -5$. Since $-5 == -5$, the property holds. The output is `true`.

---

### Example 6 (Empty & Single-Node Trees)

**Input:**
```text
null
```

**Output:**
```text
true
```

**Input:**
```text
42
```

**Output:**
```text
true
```
Both empty trees and single-node trees trivially satisfy the condition.
