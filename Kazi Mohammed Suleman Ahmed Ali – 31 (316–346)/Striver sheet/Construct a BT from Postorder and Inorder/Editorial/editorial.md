# Editorial: Construct a Binary Tree from Postorder and Inorder Traversal

## 1. Problem Overview

We are given two integer arrays:
- `inorder`: represents the inorder traversal sequence (`Left Subtree -> Root -> Right Subtree`) of a binary tree.
- `postorder`: represents the postorder traversal sequence (`Left Subtree -> Right Subtree -> Root`) of the same binary tree.

All node values in the tree are **unique**. We must reconstruct the original binary tree and return its root.

---

## 2. Key Insights and Observations

1. **Postorder Traversal identifies the Root:**
   In postorder traversal, children are visited before their parent. Therefore, the **very last element** in any postorder sequence `postorder[postEnd]` is **always the root** of the current subtree.

2. **Inorder Traversal partitions Left and Right Subtrees:**
   Because all node values are unique, finding the position of `rootVal` in `inorder` (say at index `inRoot`) splits the subtree:
   - Elements from `inStart` to `inRoot - 1` form the **Left Subtree**.
   - Elements from `inRoot + 1` to `inEnd` form the **Right Subtree**.

3. **Subtree Size Correspondence:**
   - The size of the left subtree is:
     $$\text{numsLeft} = \text{inRoot} - \text{inStart}$$
   - In `postorder`, the left subtree nodes occupy:
     $$[\text{postStart}, \; \text{postStart} + \text{numsLeft} - 1]$$
   - The right subtree nodes occupy:
     $$[\text{postStart} + \text{numsLeft}, \; \text{postEnd} - 1]$$
   - The root is at `postEnd`.

4. **Fast Lookup via HashMap:**
   Using a HashMap mapping each node value to its index in `inorder` gives $\mathcal{O}(1)$ root lookups, ensuring an overall $\mathcal{O}(N)$ time complexity.

---

## 3. Approaches

### Approach 1: Recursive Divide and Conquer with Hash Map (Standard Optimal)

#### Algorithm
1. Build a hash map `inMap` storing `{inorder[i]: i}` for all $0 \le i < N$.
2. Define a recursive helper function `build(inStart, inEnd, postStart, postEnd)`:
   - **Base Case:** If `inStart > inEnd` or `postStart > postEnd`, return `null`.
   - Current subtree root value is `rootVal = postorder[postEnd]`.
   - Create `TreeNode root = new TreeNode(rootVal)`.
   - Look up `inRoot = inMap.get(rootVal)`.
   - Calculate `numsLeft = inRoot - inStart`.
   - Recursively construct left subtree:
     `root.left = build(inStart, inRoot - 1, postStart, postStart + numsLeft - 1)`
   - Recursively construct right subtree:
     `root.right = build(inRoot + 1, inEnd, postStart + numsLeft, postEnd - 1)`
   - Return `root`.

#### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ — Building the map takes $\mathcal{O}(N)$ and each node is processed in $\mathcal{O}(1)$.
- **Space Complexity:** $\mathcal{O}(N)$ — Hash map takes $\mathcal{O}(N)$ space, recursion stack takes $\mathcal{O}(H)$ space.

---

### Approach 2: Iterative Construction using an Explicit Stack (Optimal & Stack-Safe)

#### Intuition
By reading `postorder` and `inorder` in reverse:
- Reversed `postorder` visits: `Root -> Right -> Left`
- Reversed `inorder` visits: `Right -> Root -> Left`

This is completely symmetric to the preorder-inorder construction, but with the roles of left and right subtrees swapped:
1. Initialize `root = new TreeNode(postorder[postorder.length - 1])`.
2. Push `root` to stack, set `inIndex = inorder.length - 1`.
3. Loop $i$ from `postorder.length - 2` down to 0:
   - Look at `node = stack.peek()`.
   - If `node.val != inorder[inIndex]`:
     The next node in reverse postorder is the **right child** of `node`.
     Set `node.right = new TreeNode(postorder[i])` and push it onto the stack.
   - Else (`node.val == inorder[inIndex]`):
     Pop nodes while `!stack.isEmpty() && stack.peek().val == inorder[inIndex]`, decrementing `inIndex` each time.
     The last popped node has its **left child** set to `new TreeNode(postorder[i])`.
     Push the new left child onto the stack.

#### Complexity
- **Time Complexity:** $\mathcal{O}(N)$ — Each node is pushed and popped at most once.
- **Space Complexity:** $\mathcal{O}(N)$ — Stack is allocated on the heap; zero risk of recursion stack overflow.

---

## 4. Detailed Dry Run (Example 1)

Given:
`inorder   = [9, 3, 15, 20, 7]`
`postorder = [9, 15, 7, 20, 3]`

1. **Step 1:** Root is `postorder[4] = 3`.
   - `inMap[3] = 1`.
   - Inorder left: `[9]` (size 1).
   - Inorder right: `[15, 20, 7]` (size 3).
   - Postorder left: `[9]`.
   - Postorder right: `[15, 20, 7]`.

2. **Step 2 (Right child of 3):**
   - Subtree root is `postorder[3] = 20`.
   - `inMap[20] = 3`.
   - Inorder left of 20: `[15]` (index 2).
   - Inorder right of 20: `[7]` (index 4).
   - Postorder left: `[15]`.
   - Postorder right: `[7]`.
   - `3.right = 20`.

3. **Step 3 (Right child of 20):**
   - Subtree root is `7`.
   - `inMap[7] = 4`. Left and right are empty.
   - `20.right = 7`.

4. **Step 4 (Left child of 20):**
   - Subtree root is `15`.
   - `inMap[15] = 2`. Left and right are empty.
   - `20.left = 15`.

5. **Step 5 (Left child of 3):**
   - Subtree root is `9`.
   - `inMap[9] = 0`. Left and right are empty.
   - `3.left = 9`.

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
| **Auxiliary Space** | $\mathcal{O}(N)$ (Map + Call Stack) | $\mathcal{O}(N)$ (Heap Stack) |
| **Call Stack Risk** | $\mathcal{O}(H)$ depth | **None** (Explicit heap stack) |
| **Traversal Direction** | Forward | Backward (Reverse Postorder) |

---

## 6. Edge Cases Handled

1. **Single Node ($N = 1$):** `inorder = [x]`, `postorder = [x]`. Correctly returns root `x` with `null` children.
2. **Left Skewed Tree:** Inorder and Postorder are identical. Builds chain entirely to the left.
3. **Right Skewed Tree:** Inorder is reverse of Postorder. Builds chain entirely to the right.
4. **Negative and Large Values:** Node values $\in [-10^4, 10^4]$ handled without integer overflow.
5. **Maximum Scale ($N = 3000$):** Runs in $<0.05$s, well within 2.0s time limit and 256 MB memory limit.
