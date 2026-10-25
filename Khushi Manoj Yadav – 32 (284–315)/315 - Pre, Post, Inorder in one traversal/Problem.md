### Problem 315: Pre, Post, Inorder in one traversal
**Difficulty:** Core | **Tags:** Tree, Binary Tree, Stack

**Problem Statement:**
Given the root of a binary tree, compute and return the **Preorder**, **Inorder**, and **Postorder** traversals of the binary tree using a **single traversal pass**.

You must traverse the tree only once using an iterative approach with a stack that tracks state.

### Input Format
* A single line containing space-separated strings representing the binary tree in level-order traversal format.
* The string `"null"` represents an absent child (null pointer).

### Output Format
* Print exactly 3 lines:
  - **Line 1:** space-separated integers representing the **Preorder** traversal.
  - **Line 2:** space-separated integers representing the **Inorder** traversal.
  - **Line 3:** space-separated integers representing the **Postorder** traversal.
* If the tree is empty, print 3 empty lines.

### Constraints
* $0 \le N \le 10^5$ (where $N$ is the number of nodes in the tree)
* $-10^9 \le \text{Node.val} \le 10^9$

### Example 1
#### Input:
```text
1 2 5 3 4 null null
```

#### Output:
```text
1 2 3 4 5
3 2 4 1 5
3 4 2 5 1
```

#### Explanation:
```text
        1
       / \
      2   5
     / \
    3   4
```
- Preorder (Root, Left, Right): `1 2 3 4 5`
- Inorder (Left, Root, Right): `3 2 4 1 5`
- Postorder (Left, Right, Root): `3 4 2 5 1`

All three traversals are collected in a single traversal pass using a state machine on a stack.
