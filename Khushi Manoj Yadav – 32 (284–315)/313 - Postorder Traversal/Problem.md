### Problem 313: Postorder Traversal
**Difficulty:** Basic | **Tags:** Tree, Binary Tree, Depth-First Search

**Problem Statement:**
Given the root of a binary tree, return the postorder traversal of its nodes' values.

In a postorder traversal, the left subtree is traversed first, followed by the right subtree, and finally the root node:
$$\text{Left} \rightarrow \text{Right} \rightarrow \text{Root}$$

### Input Format
* A single line containing space-separated strings representing the binary tree in level-order traversal format.
* The string `"null"` represents an absent child (null pointer).

### Output Format
* Print a single line containing space-separated integers representing the postorder traversal of the binary tree. If the tree is empty, print nothing.

### Constraints
* $0 \le N \le 10^5$ (where $N$ is the number of nodes in the tree)
* $-10^9 \le \text{Node.val} \le 10^9$

### Example 1
#### Input:
```text
1 4 null 4 2
```

#### Output:
```text
4 2 4 1
```

#### Explanation:
The tree structure is:
```text
        1
       /
      4
     / \
    4   2
```
Traversing Postorder (Left, Right, Root):
1. Left child of 4 is 4 (leaf). Visit 4.
2. Right child of 4 is 2 (leaf). Visit 2.
3. Visit parent 4.
4. Visit root 1.
Result: `4 2 4 1`.

### Example 2
#### Input:
```text
1 null 2 3
```

#### Output:
```text
3 2 1
```

#### Explanation:
Root 1 has no left child. Right child 2 has left child 3. Visit 3, then 2, then root 1.
Result: `3 2 1`.
