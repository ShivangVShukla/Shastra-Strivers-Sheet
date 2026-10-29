# Editorial: Print Root to Leaf Path in Binary Tree

## 1. Problem Overview

Given the root of a binary tree, we need to collect and return all **root-to-leaf paths** in the binary tree.
- A **leaf node** is a node with no children (`node.left == null && node.right == null`).
- A **root-to-leaf path** is an ordered sequence of node values starting at the root and ending at a leaf.
- Paths should be returned in standard left-to-right order (preorder: left subtree paths before right subtree paths).

---

## 2. Approach: Depth-First Search (DFS) with Backtracking

### Intuition
DFS is ideally suited for pathfinding problems because it explores a branch fully from root to leaf before backtracking to explore neighboring branches.
By maintaining a dynamic list `currPath`:
1. When visiting a node, append its value to `currPath`.
2. Check if the current node is a leaf:
   - If `node.left == null && node.right == null`, we have reached the end of a valid root-to-leaf path.
   - Make a copy of `currPath` and add it to our list of answers.
3. If not a leaf, recursively explore:
   - Visit `node.left` (if non-null).
   - Visit `node.right` (if non-null).
4. **Backtrack:** Remove `node.val` from `currPath` before returning to the parent, ensuring other branches are not contaminated.

---

### Stack-Safe Iterative DFS

In Java, recursive DFS can lead to `StackOverflowError` on deeply skewed trees (e.g. chains of depth $3 \times 10^3$) if the default call stack limit is reached.
An iterative postorder traversal using an explicit heap-allocated stack `ArrayDeque<TreeNode>` and a `lastVisited` pointer achieves the exact same traversal while being **100% stack-safe**:

```java
public List<List<Integer>> allRootToLeaf(TreeNode root) {
    List<List<Integer>> ans = new ArrayList<>();
    if (root == null) return ans;

    ArrayDeque<TreeNode> stack = new ArrayDeque<>();
    List<Integer> currPath = new ArrayList<>();
    TreeNode curr = root;
    TreeNode lastVisited = null;

    while (curr != null || !stack.isEmpty()) {
        if (curr != null) {
            stack.push(curr);
            currPath.add(curr.val);
            curr = curr.left;
        } else {
            TreeNode peek = stack.peek();
            if (peek.right != null && lastVisited != peek.right) {
                curr = peek.right;
            } else {
                if (peek.left == null && peek.right == null) {
                    ans.add(new ArrayList<>(currPath));
                }
                lastVisited = stack.pop();
                currPath.remove(currPath.size() - 1);
            }
        }
    }
    return ans;
}
```

---

## 3. Step-by-Step Dry Run (Example 1)

**Tree:**
```text
       1
     /   \
    2     3
     \     \
      5     4
```

| Step | Action | Node Visited | `currPath` | Is Leaf? | Action Taken / Path Stored |
| :---: | :---: | :---: | :---: | :---: | :--- |
| 1 | Enter | `1` | `[1]` | No | Recurse to left child `2` |
| 2 | Enter | `2` | `[1, 2]` | No | Left child is null; recurse to right child `5` |
| 3 | Enter | `5` | `[1, 2, 5]` | **Yes** | **Store path:** `[1, 2, 5]` |
| 4 | Backtrack | `5` | `[1, 2]` | - | Pop `5` |
| 5 | Backtrack | `2` | `[1]` | - | Pop `2` |
| 6 | Enter | `3` | `[1, 3]` | No | Left child is null; recurse to right child `4` |
| 7 | Enter | `4` | `[1, 3, 4]` | **Yes** | **Store path:** `[1, 3, 4]` |
| 8 | Backtrack | `4` | `[1, 3]` | - | Pop `4` |
| 9 | Backtrack | `3` | `[1]` | - | Pop `3` |
| 10 | Backtrack | `1` | `[]` | - | Pop `1`, traversal complete |

**Result:** `[ [1, 2, 5] , [1, 3, 4] ]`

---

## 4. Complexity Analysis

- **Time Complexity:**
  - Visiting every node: $\mathcal{O}(N)$.
  - Creating a copy of each path at the leaves: Each path has length at most $H$ (height of tree).
  - In a balanced binary tree, there are $\approx N/2$ leaves with path length $\mathcal{O}(\log N)$, giving $\mathcal{O}(N \log N)$ total time for copying paths.
  - In a skewed tree, there is only $1$ leaf with path length $\mathcal{O}(N)$, giving $\mathcal{O}(N)$ total time.
  - Overall time complexity is $\mathcal{O}(N \cdot H)$ in the worst case, and $\mathcal{O}(N \log N)$ on average. For $N = 3 \times 10^3$, operations finish in $< 0.05$ seconds.

- **Space Complexity:**
  - **Auxiliary Space:** $\mathcal{O}(H)$ for the explicit stack / recursion stack and the `currPath` tracking list.
  - **Output Space:** $\mathcal{O}(L \cdot H)$ where $L$ is the number of leaves, which stores all root-to-leaf paths.
  - Fits comfortably within the 256 MB memory limit.
