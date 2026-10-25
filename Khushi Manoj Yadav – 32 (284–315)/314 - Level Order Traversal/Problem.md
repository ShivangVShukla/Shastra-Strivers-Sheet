### Problem 314: Level Order Traversal
**Difficulty:** Core | **Tags:** Tree, Binary Tree, Breadth-First Search, Queue

**Problem Statement:**
Given the root of a binary tree, return the level order traversal of its nodes' values (i.e., from left to right, level by level).

Each level must be printed on a separate line with its values separated by a space.

### Input Format
* A single line containing space-separated strings representing the binary tree in level-order traversal format.
* The string `"null"` represents an absent child (null pointer).

### Output Format
* Print the nodes level by level. Each level should be printed on a new line with space-separated values.
* If the tree is empty, print nothing.

### Constraints
* $0 \le N \le 10^5$ (where $N$ is the number of nodes in the tree)
* $-10^9 \le \text{Node.val} \le 10^9$

### Example 1
#### Input:
```text
3 9 20 null null 15 7
```

#### Output:
```text
3
9 20
15 7
```

#### Explanation:
```text
    3
   / \
  9  20
    /  \
   15   7
```
- Level 0: 3
- Level 1: 9 20
- Level 2: 15 7

### Example 2
#### Input:
```text
1
```

#### Output:
```text
1
```

#### Explanation:
The tree consists of a single node at level 0.
