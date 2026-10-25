# Editorial: Construct a Binary Tree from Preorder and Inorder Traversal

## 1. Problem Overview

We are given two integer arrays:
- `preorder`: represents the preorder traversal sequence of a binary tree (`Root -> Left Subtree -> Right Subtree`).
- `inorder`: represents the inorder traversal sequence of the same binary tree (`Left Subtree -> Root -> Right Subtree`).

All node values in the tree are **unique**. We must reconstruct the original binary tree and return its root.

---

## 2. Key Insights and Observations

1. **Preorder Traversal identifies the Root:**
   The very first element in any preorder sequence `preorder[preStart]` is **always the root** of the current subtree.

2. **Inorder Traversal partitions Left and Right Subtrees:**
   Because all values are unique, finding the index of the root value in `inorder` (say at index `inRoot`) splits the elements:
   - Elements from index `inStart` to `inRoot - 1` belong strictly to the **Left Subtree**.
   - Elements from index `inRoot + 1` to `inEnd` belong strictly to the **Right Subtree**.

3. **Subtree Size Correspondence:**
   - The number of nodes in the left subtree is:
     $$\text{numsLeft} = \text{inRoot} - \text{inStart}$$
   - In `preorder`, the nodes for the left subtree occupy the range:
     $$[\text{preStart} + 1, \; \text{preStart} + \text{numsLeft}]$$
   - The nodes for the right subtree occupy the remaining range:
     $$[\text{preStart} + \text{numsLeft} + 1, \; \text{preEnd}]$$

4. **Fast Lookup via HashMap:**
   Searching for `rootVal` in `inorder` naively takes $\mathcal{O}(N)$ per level, leading to an $\mathcal{O}(N^2)$ worst-case time complexity.
   By precomputing a hash map mapping `value -> index in inorder`, each lookup takes $\mathcal{O}(1)$, reducing overall time complexity to $\mathcal{O}(N)$.

---

## 3. Approaches

### Approach 1: Recursive Divide and Conquer with Hash Map (Standard Optimal)

#### Algorithm
1. Build a hash map `inMap` storing `{inorder[i]: i}` for all $0 \le i < N$.
2. Define a recursive helper function `build(preStart, preEnd, inStart, inEnd)`:
   - **Base Case:** If `preStart > preEnd` or `inStart > inEnd`, return `null`.
   - The current subtree's root value is `rootVal = preorder[preStart]`.
   - Create `TreeNode root = new TreeNode(rootVal)`.
   - Look up `inRoot = inMap.get(rootVal)`.
   - Calculate `numsLeft = inRoot - inStart`.
   - Recursively construct left subtree:
     `root.left = build(preStart + 1, preStart + numsLeft, inStart, inRoot - 1)`
   - Recursively construct right subtree:
     `root.right = build(preStart + numsLeft + 1, preEnd, inRoot + 1, inEnd)`
   - Return `root`.

#### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ — Building the map takes $\mathcal{O}(N)$. Each node is created exactly once in $\mathcal{O}(1)$ time.
- **Space Complexity:** $\mathcal{O}(N)$ — $\mathcal{O}(N)$ for the hash map plus $\mathcal{O}(H)$ call stack space (where $H$ is the height of the tree).

---

### Approach 2: Iterative Construction using an Explicit Stack (Optimal & Stack-Safe)

#### Intuition
When constructing trees of sizes up to $N = 10^4$, a completely skewed tree could produce a call stack depth of $10,000$, risking stack overflow in restricted environments.
An iterative approach using an explicit stack eliminates call stack risk entirely:

1. Maintain an index pointer `inIndex = 0` for `inorder`.
2. Push `root = new TreeNode(preorder[0])` onto the stack.
3. Iterate $i$ from $1$ to $N - 1$:
   - Look at the top node on the stack `node = stack.peek()`.
   - If `node.val != inorder[inIndex]`:
     The next node in preorder must be the **left child** of `node`.
     Set `node.left = new TreeNode(preorder[i])` and push it onto the stack.
   - Else (`node.val == inorder[inIndex]`):
     We have reached the end of left descendants.
     Pop nodes from the stack while `!stack.isEmpty() && stack.peek().val == inorder[inIndex]`, incrementing `inIndex` each time.
     The last popped node is the ancestor whose **right child** must be `new TreeNode(preorder[i])`.
     Set that node's right child and push the new right child onto the stack.

#### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ — Each node is pushed and popped at most once.
- **Space Complexity:** $\mathcal{O}(N)$ — Explicit stack on the heap. Completely stack-safe.

---

## 4. Detailed Dry Run (Example 1)

Given:
`preorder = [3, 9, 20, 15, 7]`
`inorder  = [9, 3, 15, 20, 7]`

1. **Step 1:** `root = 3`.
   - `inMap[3] = 1`.
   - Inorder left: `[9]` (size 1). Inorder right: `[15, 20, 7]` (size 3).
   - Preorder left: `[9]`. Preorder right: `[20, 15, 7]`.

2. **Step 2 (Left child of 3):**
   - Subtree root is `9`.
   - `inMap[9] = 0`. Left is empty, right is empty.
   - `9` is a leaf node. `3.left = 9`.

3. **Step 3 (Right child of 3):**
   - Subtree root is `20`.
   - `inMap[20] = 3`.
   - Inorder left of 20: `[15]` (index 2).
   - Inorder right of 20: `[7]` (index 4).
   - Preorder left: `[15]`. Preorder right: `[7]`.

4. **Step 4 (Left child of 20):**
   - Subtree root is `15`.
   - `inMap[15] = 2`. Left and right are empty.
   - `20.left = 15`.

5. **Step 5 (Right child of 20):**
   - Subtree root is `7`.
   - `inMap[7] = 4`. Left and right are empty.
   - `20.right = 7`.

Final Tree:
```text
        3
       / \
      9   20
         /  \
        15   7
```
Level order: `[3, 9, 20, null, null, 15, 7]`.

---

## 5. Comparison Summary

| Metric | Recursive with Hash Map | Iterative with Stack |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Space** | $\mathcal{O}(N)$ (Map + Call Stack) | $\mathcal{O}(N)$ (Explicit Heap Stack) |
| **Call Stack Risk** | Yes ($H \le 10^4$ on skewed trees) | **None** (Stack is on heap) |
| **Implementation** | Divide and conquer | Simulation |

---

## 6. Edge Cases Handled

1. **Single Node ($N = 1$):** `preorder = [x]`, `inorder = [x]`. Correctly returns root `x` with `null` children.
2. **Left Skewed Tree:** `inorder` is the exact reverse of `preorder`. Recursively or iteratively chains nodes entirely to the left.
3. **Right Skewed Tree:** `inorder` and `preorder` are identical. Chains nodes entirely to the right.
4. **Negative and Large Values:** Node values $\in [-10^4, 10^4]$ handled without integer overflow.
5. **Maximum Scale ($N = 10^4$):** Completes well within the 2.0s time limit and 256 MB memory limit.
