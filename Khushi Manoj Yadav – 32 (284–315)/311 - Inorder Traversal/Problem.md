### Problem 311: Inorder Traversal
**Difficulty:** Basic | **Tags:** Tree, Binary Tree, Depth-First Search

**Problem Statement:**
Given the root of a binary tree, return the inorder traversal of its nodes' values.

In an inorder traversal, the left subtree is traversed first, followed by the root node, and finally the right subtree:
$$\text{Left} \rightarrow \text{Root} \rightarrow \text{Right}$$

### Input Format
* A single line containing space-separated strings representing the binary tree in level-order traversal format.
* The string `"null"` represents an absent child (null pointer).

### Output Format
* Print a single line containing space-separated integers representing the inorder traversal of the binary tree. If the tree is empty, print nothing.

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
4 4 2 1
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
Traversing Inorder (Left, Root, Right):
1. Left child of 1 is 4.
2. Left child of 4 is 4.
3. Node 4 has no left child, visit 4.
4. Visit parent 4.
5. Right child of 4 is 2. Visit 2.
6. Visit root 1.
Result: `4 4 2 1`.

### Example 2
#### Input:
```text
1 null 2 3
```

#### Output:
```text
1 3 2
```

#### Explanation:
Root 1 has no left child. Visit 1. Right child 2 has left child 3. Visit 3, then 2.
Result: `1 3 2`.
