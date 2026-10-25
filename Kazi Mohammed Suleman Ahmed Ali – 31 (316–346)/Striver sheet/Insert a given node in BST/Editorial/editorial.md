# Editorial: Insert a given node in BST

## 1. Problem Overview

We are given the root of a **Binary Search Tree (BST)** and an integer `val` that is guaranteed not to exist within the tree. Our objective is to insert `val` into the BST such that the resulting tree satisfies all BST properties:
1. For every node, all values in its left subtree are strictly less than its value.
2. For every node, all values in its right subtree are strictly greater than its value.
3. Both left and right subtrees must themselves be BSTs.

The problem guarantees that all node values are unique and that `val` does not exist in the initial BST.

---

## 2. Key Insights

In a Binary Search Tree, locating where a value belongs is guided by simple comparisons:
- If `val < current.val`, the target location must lie in the **left subtree**.
- If `val > current.val`, the target location must lie in the **right subtree**.

Standard BST insertion traverses downward from the root following these comparisons until reaching an empty position (`null`). We simply create a new node with value `val` and attach it as the left or right child of the parent node.

Because we do not need to rebalance the tree (like in AVL or Red-Black trees), insertion can be performed strictly at leaf/null positions without disrupting any existing edges.

---

## 3. Approaches

### Approach 1: Recursive Insertion

#### Algorithm:
1. **Base Case:** If `root == null`, create and return `new TreeNode(val)`.
2. **Recursive Step:**
   - If `val < root.val`, recursively insert into the left subtree:
     $$\text{root.left} = \text{insertIntoBST}(\text{root.left}, \text{val})$$
   - If `val > root.val`, recursively insert into the right subtree:
     $$\text{root.right} = \text{insertIntoBST}(\text{root.right}, \text{val})$$
3. Return `root`.

#### Complexity:
- **Time Complexity:** $\mathcal{O}(H)$, where $H$ is the height of the tree ($\mathcal{O}(\log N)$ for balanced trees, $\mathcal{O}(N)$ for skewed trees).
- **Space Complexity:** $\mathcal{O}(H)$ on the call stack due to recursion. For a deeply skewed tree with $N = 10^4$, recursive depth could risk stack overflow in memory-constrained environments.

---

### Approach 2: Iterative Insertion (Optimal & Stack-Safe)

To achieve true $\mathcal{O}(1)$ auxiliary space and avoid stack overflow on deep trees, we can use an iterative pointer traversal.

#### Algorithm:
1. If `root == null`, return `new TreeNode(val)`.
2. Initialize `curr = root`.
3. Loop indefinitely:
   - If `val < curr.val`:
     - If `curr.left == null`:
       - `curr.left = new TreeNode(val)`
       - Break from the loop.
     - Else:
       - `curr = curr.left`
   - Else (`val > curr.val`):
     - If `curr.right == null`:
       - `curr.right = new TreeNode(val)`
       - Break from the loop.
     - Else:
       - `curr = curr.right`
4. Return `root`.

#### Complexity:
- **Time Complexity:** $\mathcal{O}(H)$, visiting at most one node per level along the search path.
- **Space Complexity:** $\mathcal{O}(1)$ auxiliary space (only a single pointer `curr`).

---

## 4. Step-by-Step Dry Run

Consider the BST:
```text
        4
       / \
      2   7
     / \
    1   3
```
and `val = 5`.

1. **Start at `curr = 4`:**
   - Is $5 < 4$? No, $5 > 4$.
   - Does `curr.right` exist? Yes (`7`).
   - Move: `curr = curr.right` (`7`).

2. **At `curr = 7`:**
   - Is $5 < 7$? Yes.
   - Does `curr.left` exist? No (`null`).
   - Attach: `curr.left = new TreeNode(5)`.
   - Insertion complete! Break.

The resulting tree is:
```text
        4
       / \
      2   7
     / \  /
    1   3 5
```
Level-order output: `[4, 2, 7, 1, 3, 5]`.

---

## 5. Implementations

### Java (Iterative - Optimal)

```java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            return new TreeNode(val);
        }
        
        TreeNode curr = root;
        while (true) {
            if (val < curr.val) {
                if (curr.left == null) {
                    curr.left = new TreeNode(val);
                    break;
                }
                curr = curr.left;
            } else {
                if (curr.right == null) {
                    curr.right = new TreeNode(val);
                    break;
                }
                curr = curr.right;
            }
        }
        
        return root;
    }
}
```

### C++ (Iterative)

```cpp
class Solution {
public:
    TreeNode* insertIntoBST(TreeNode* root, int val) {
        if (!root) return new TreeNode(val);
        
        TreeNode* curr = root;
        while (true) {
            if (val < curr->data) {
                if (!curr->left) {
                    curr->left = new TreeNode(val);
                    break;
                }
                curr = curr->left;
            } else {
                if (!curr->right) {
                    curr->right = new TreeNode(val);
                    break;
                }
                curr = curr->right;
            }
        }
        return root;
    }
};
```

---

## 6. Edge Cases Handled

1. **Empty Tree ($N=0$):**
   - Directly creates and returns a single node `TreeNode(val)`.
2. **Single-Node Tree ($N=1$):**
   - Attaches `val` directly as either the left or right child depending on whether `val < root.val` or `val > root.val`.
3. **Skewed Trees (Worst Case $H = N$):**
   - Left-skewed: Inserting a smaller value attaches it at the lowest leaf; inserting a larger value attaches it as a right child.
   - Iterative approach processes this in $\mathcal{O}(N)$ time with $\mathcal{O}(1)$ memory without stack overflow.
4. **Extreme Integer Values ($-10^8$ and $10^8$):**
   - Comparisons are direct 32-bit signed integer comparisons, avoiding arithmetic overflow.
