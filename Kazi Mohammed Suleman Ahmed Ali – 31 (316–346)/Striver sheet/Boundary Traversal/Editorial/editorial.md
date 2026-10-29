# Editorial: Boundary Traversal of Binary Tree

## 1. Problem Overview

We are given the root of a binary tree and asked to compute the **boundary traversal** in anti-clockwise order starting from the root.

The anti-clockwise boundary consists of four distinct components:
1. **Root Node:** Included if the tree is non-empty.
2. **Left Boundary Nodes:** Descend along the left perimeter from `root.left`, excluding leaf nodes.
3. **Leaf Nodes:** All leaf nodes across the entire tree, visited in strict left-to-right order.
4. **Right Boundary Nodes (in Reverse):** Ascend along the right perimeter from the lowest non-leaf node back to `root.right`.

---

## 2. Intuition & Deconstructed Traversal Rules

### Rule 1: Avoiding Node Duplication
- The **root** is handled as a special case:
  - If the root is a leaf (single-node tree), we treat it as a leaf and add it only once: `[root.val]`.
  - If the root is not a leaf, it is added once at the very start of the traversal.
- **Leaves are strictly excluded** during the left boundary and right boundary traversals. This guarantees that bottom-corner nodes are not visited twice (once as boundary, once as leaf).

### Rule 2: Left Boundary Path Navigation
Starting at `curr = root.left`:
- While `curr != null`:
  - If `!isLeaf(curr)`, add `curr.val` to result.
  - If `curr.left != null`, move to `curr.left`.
  - Else if `curr.right != null`, move to `curr.right`.
  - If `isLeaf(curr)`, terminate.

### Rule 3: Right Boundary Path Navigation (Bottom-Up)
Starting at `curr = root.right`:
- Collect non-leaf nodes into a temporary list:
  - While `curr != null`:
    - If `!isLeaf(curr)`, add `curr.val` to temporary list.
    - If `curr.right != null`, move to `curr.right`.
    - Else if `curr.left != null`, move to `curr.left`.
    - If `isLeaf(curr)`, terminate.
- Append the temporary list in **reverse order** (from last element down to first).

---

## 3. Stack-Safety: Iterative vs. Recursive Leaf Collection

### The Flaw with Recursive Leaf Collection
Most introductory solutions collect leaves using simple recursive preorder/inorder traversal:
```java
void addLeaves(TreeNode node, List<Integer> res) {
    if (node == null) return;
    if (isLeaf(node)) {
        res.add(node.val);
        return;
    }
    addLeaves(node.left, res);
    addLeaves(node.right, res);
}
```
**Fatal Flaw:** On a skewed binary tree of $N = 10^4$ nodes, the recursion depth reaches $10^4$ frames on the execution call stack. In standard 64-bit Windows JVMs (which allocate 512 KB to 1 MB per thread stack), this triggers a fatal `java.lang.StackOverflowError`.

### The Stack-Safe Solution: Iterative Explicit Heap Stack
We implement leaf collection using an explicit stack (`ArrayDeque<TreeNode>`) allocated on the **JVM heap memory**, which has hundreds of megabytes available:
```java
ArrayDeque<TreeNode> stack = new ArrayDeque<>();
stack.push(root);

while (!stack.isEmpty()) {
    TreeNode curr = stack.pop();

    if (isLeaf(curr)) {
        res.add(curr.val);
    } else {
        // Push right first so left is popped and processed first
        if (curr.right != null) stack.push(curr.right);
        if (curr.left != null) stack.push(curr.left);
    }
}
```
- By pushing `right` before `left`, the left subtree is always popped and processed first.
- This guarantees **strictly left-to-right leaf collection**.
- It is **100% stack-safe** for arbitrary tree depths up to $10^4$ nodes.

---

## 4. Step-by-Step Dry Runs

### Trace on Example 1: `root = [1, 2, 3, 4, 5, 6, 7, null, null, 8, 9]`

```text
           1
         /   \
        2     3
       / \   / \
      4   5 6   7
         / \
        8   9
```

1. **Root:** Not a leaf $\to$ `res = [1]`.
2. **Left Boundary:**
   - Start at `2`. Not a leaf $\to$ `res = [1, 2]`.
   - `2` has left child `4`.
   - Node `4` is a leaf $\to$ stop left boundary.
3. **Leaves Collection (Iterative DFS):**
   - Traverse tree:
     - Node `4` is leaf $\to$ add `4`.
     - Node `8` is leaf $\to$ add `8`.
     - Node `9` is leaf $\to$ add `9`.
     - Node `6` is leaf $\to$ add `6`.
     - Node `7` is leaf $\to$ add `7`.
   - `res = [1, 2, 4, 8, 9, 6, 7]`.
4. **Right Boundary in Reverse:**
   - Start at `3`. Not a leaf $\to$ `rightList = [3]`.
   - `3` has right child `7`.
   - Node `7` is a leaf $\to$ stop right boundary.
   - Reverse `rightList` $\to$ `[3]`.
   - Append to `res` $\to$ `[1, 2, 4, 8, 9, 6, 7, 3]`.

---

### Trace on Single-Node Tree: `root = [42]`

1. `isLeaf(root)` is `true`.
2. Root is NOT added as a non-leaf root.
3. Leaf collection visits `root`, adding `42`.
4. Left boundary (`root.left == null`) and Right boundary (`root.right == null`) loops do not run.
5. Result: `[42]`. Exactly once!

---

## 5. Edge Cases & Boundary Conditions

| Scenario | Input | Expected Output | Purpose / Strategic Objective |
| :--- | :--- | :---: | :--- |
| **Empty Tree** | `null` | `[]` | Null pointer safety ($N = 0$). |
| **Single Node** | `42` | `[42]` | Ensures single node is not duplicated. |
| **Left-Skewed Chain** | `1 2 null 3` | `[1, 2, 3]` | Node `1` root, `2` left boundary, `3` leaf. |
| **Right-Skewed Chain** | `1 null 2 null 3` | `[1, 3, 2]` | Node `1` root, `3` leaf, `2` reverse right boundary. |
| **No Left Child** | `1 null 2 3 4` | `[1, 3, 4, 2]` | Empty left boundary; only right boundary and leaves. |
| **No Right Child** | `1 2 null 3 4` | `[1, 2, 3, 4]` | Empty right boundary; only left boundary and leaves. |
| **Deep Skewed Tree ($N = 10^4$)** | Depth $10^4$ | Boundary list | Verifies 100% stack-safety without StackOverflow. |
| **Complete Tree ($N = 8191$)** | Depth 13 | Boundary + Leaves | Verifies leaf collection across $\approx 4096$ leaves. |

---

## 6. Complexity Analysis

| Metric | Recursive Traversal | Iterative Stack-Safe Traversal (Optimal) |
| :--- | :--- | :--- |
| **Time Complexity** | $\mathcal{O}(N)$ | $\mathcal{O}(N)$ |
| **Auxiliary Call Stack** | $\mathcal{O}(H)$ ($H \le 10^4$) | $\mathcal{O}(1)$ |
| **Heap Memory (Stack)** | $\mathcal{O}(1)$ | $\mathcal{O}(H)$ on JVM heap |
| **Stack Overflow Risk** | **High** on skewed trees ($N = 10^4$) | **Zero (100% stack-safe)** |

- **Time Complexity $\mathcal{O}(N)$:**
  - Left boundary visits at most $\mathcal{O}(H)$ nodes.
  - Right boundary visits at most $\mathcal{O}(H)$ nodes.
  - Leaf collection visits every node in the tree once: $\mathcal{O}(N)$.
  - Total time: $\mathcal{O}(N) + \mathcal{O}(H) + \mathcal{O}(H) = \mathcal{O}(N)$.
- **Space Complexity $\mathcal{O}(H)$:**
  - The explicit stack stores at most the height of the tree at any given moment: $\mathcal{O}(H)$, safely bounded by JVM heap memory.

---

## 7. Java Reference Implementation

```java
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

class Solution {
    static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int val) {
            this.val = val;
        }
    }

    private static boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }

    /**
     * Stack-safe anti-clockwise boundary traversal.
     */
    public List<Integer> boundary(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        // 1. Root node (if not a leaf)
        if (!isLeaf(root)) {
            result.add(root.val);
        }

        // 2. Left boundary (excluding leaves)
        TreeNode curr = root.left;
        while (curr != null) {
            if (!isLeaf(curr)) {
                result.add(curr.val);
            }
            if (curr.left != null) {
                curr = curr.left;
            } else {
                curr = curr.right;
            }
        }

        // 3. Leaves (left-to-right) via iterative explicit-stack DFS
        ArrayDeque<TreeNode> stack = new ArrayDeque<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            if (isLeaf(node)) {
                result.add(node.val);
            } else {
                if (node.right != null) stack.push(node.right);
                if (node.left != null) stack.push(node.left);
            }
        }

        // 4. Right boundary in reverse (excluding leaves)
        ArrayList<Integer> rightBoundary = new ArrayList<>();
        curr = root.right;
        while (curr != null) {
            if (!isLeaf(curr)) {
                rightBoundary.add(curr.val);
            }
            if (curr.right != null) {
                curr = curr.right;
            } else {
                curr = curr.left;
            }
        }
        for (int i = rightBoundary.size() - 1; i >= 0; i--) {
            result.add(rightBoundary.get(i));
        }

        return result;
    }
}
```
