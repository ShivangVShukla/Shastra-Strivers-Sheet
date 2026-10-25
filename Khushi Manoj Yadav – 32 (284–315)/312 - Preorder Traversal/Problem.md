### Problem 312: Preorder Traversal
**Difficulty:** Basic | **Tags:** Tree, Binary Tree, Depth-First Search

**Problem Statement:**
Given the root of a binary tree, return the preorder traversal of its nodes' values.

In a preorder traversal, the root node is visited first, followed by the left subtree, and finally the right subtree:
$$\text{Root} \rightarrow \text{Left} \rightarrow \text{Right}$$

### Input Format
* A single line containing space-separated strings representing the binary tree in level-order traversal format.
* The string `"null"` represents an absent child (null pointer).

### Output Format
* Print a single line containing space-separated integers representing the preorder traversal of the binary tree. If the tree is empty, print nothing.

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
1 4 4 2
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
Traversing Preorder (Root, Left, Right):
1. Visit root 1.
2. Traverse left subtree: Visit node 4.
3. Left child of 4 is 4. Visit node 4.
4. Right child of 4 is 2. Visit node 2.
Result: `1 4 4 2`.

### Example 2
#### Input:
```text
1 null 2 3
```

#### Output:
```text
1 2 3
```

#### Explanation:
Visit root 1. Root has no left child. Traverse right child 2 (visit 2). Node 2 has left child 3 (visit 3).
Result: `1 2 3`.
