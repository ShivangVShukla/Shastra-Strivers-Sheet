# Editorial: Delete a node in BST

## 1. Problem Overview

We are given the root of a **Binary Search Tree (BST)** and an integer `key`. We need to delete the node containing `key` (if it exists) such that the remaining tree preserves all BST properties:
1. For every node, all values in its left subtree are strictly less than the node's value.
2. For every node, all values in its right subtree are strictly greater than the node's value.
3. Both left and right subtrees must themselves be valid BSTs.

If `key` does not exist in the BST, the original tree should be returned unmodified.

---

## 2. Key Insights & Deletion Cases

Deleting a node from a BST involves two main steps:
1. **Search:** Traverse down the tree using BST comparisons (`key < curr.val` $\implies$ move left, `key > curr.val` $\implies$ move right) to locate the target node.
2. **Deletion & Reconnection:** Once the target node is found, handle one of three fundamental structural cases:

### Case 1: Target Node is a Leaf (0 Children)
- The node has no children (`node.left == null` and `node.right == null`).
- Simply remove the node by returning `null` to its parent.

### Case 2: Target Node has Exactly 1 Child
- Either `node.left == null` or `node.right == null`.
- Bypass the node by returning its non-null child directly to its parent.

### Case 3: Target Node has 2 Children
- Both `node.left != null` and `node.right != null`.
- To preserve the BST order, we can replace the node's value with its **inorder successor** (the smallest value in its right subtree) or its **inorder predecessor** (the largest value in its left subtree).
- Since the inorder successor lies in the right subtree and has no left child, we copy its value into the current node, then recursively delete the inorder successor from the right subtree (which reduces to Case 1 or Case 2).

Alternatively (Striver's pointer-based method), we can attach the target node's right subtree to the extreme right of its left subtree, then return `node.left`. Both strategies produce a valid BST.

---

## 3. Algorithm (Inorder Successor Approach)

1. **Base Case:**
   - If `root == null`, return `null`.

2. **Recursive Search:**
   - If `key < root.val`:
     $$\text{root.left} = \text{deleteNode}(\text{root.left}, \text{key})$$
   - Else if `key > root.val`:
     $$\text{root.right} = \text{deleteNode}(\text{root.right}, \text{key})$$

3. **Node Found (`key == root.val`):**
   - If `root.left == null`, return `root.right`.
   - If `root.right == null`, return `root.left`.
   - If both children exist:
     - Find the inorder successor:
       $$\text{successor} = \text{findMin}(\text{root.right})$$
     - Copy `successor.val` to `root.val`.
     - Delete the successor from the right subtree:
       $$\text{root.right} = \text{deleteNode}(\text{root.right}, \text{successor.val})$$

4. Return `root`.

---

## 4. Step-by-Step Dry Run

### Example 1: `root = [5, 3, 6, 2, 4, null, 7], key = 3`

Original Tree:
```text
        5
       / \
      3   6
     / \   \
    2   4   7
```

1. **Search:**
   - Start at root `5`. Since $3 < 5$, recurse left: `root.left = deleteNode(root.left, 3)`.
2. **At node `3`:**
   - Value matches `key = 3`. Both children `2` and `4` exist.
3. **Inorder Successor:**
   - Minimum in right subtree of `3` is node `4`.
   - Copy value `4` into node `3`: node becomes `4`.
4. **Delete Successor:**
   - Recurse right: `node.right = deleteNode(node.right, 4)`.
   - At node `4`: has no left child, so return `null`.
   - `node.right` becomes `null`.
5. **Resulting Subtree:**
   - Root of subtree is `4`, with left child `2` and right child `null`.
   - Reconnected to parent `5`: `5.left = 4`.

Final Tree:
```text
        5
       / \
      4   6
     /     \
    2       7
```
Level-order output: `[5, 4, 6, 2, null, null, 7]`.

---

## 5. Implementations

### Java (Optimal & Clean)

```java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) return null;

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            // Case 1 & 2: 0 or 1 child
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            // Case 3: 2 children
            TreeNode successor = findMin(root.right);
            root.val = successor.val;
            root.data = successor.val;
            root.right = deleteNode(root.right, successor.val);
        }

        return root;
    }

    private TreeNode findMin(TreeNode node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}
```

### C++

```cpp
class Solution {
public:
    TreeNode* deleteNode(TreeNode* root, int key) {
        if (!root) return nullptr;

        if (key < root->data) {
            root->left = deleteNode(root->left, key);
        } else if (key > root->data) {
            root->right = deleteNode(root->right, key);
        } else {
            if (!root->left) {
                TreeNode* temp = root->right;
                delete root;
                return temp;
            } else if (!root->right) {
                TreeNode* temp = root->left;
                delete root;
                return temp;
            }

            TreeNode* successor = findMin(root->right);
            root->data = successor->data;
            root->right = deleteNode(root->right, successor->data);
        }
        return root;
    }

private:
    TreeNode* findMin(TreeNode* node) {
        while (node->left) {
            node = node->left;
        }
        return node;
    }
};
```

---

## 6. Complexity Analysis

- **Time Complexity:** $\mathcal{O}(H)$, where $H$ is the height of the BST.
  - In a balanced BST: $\mathcal{O}(\log N)$.
  - In a skewed BST: $\mathcal{O}(N)$ in the worst case.
  - Finding the node takes $\mathcal{O}(H)$, and finding/splicing the successor also traverses along a downward branch bounded by $H$.
- **Space Complexity:** $\mathcal{O}(H)$ auxiliary stack space due to recursion. Can be reduced to $\mathcal{O}(1)$ auxiliary space with an iterative implementation.

---

## 7. Edge Cases Handled

1. **Key Not Present:** Tree remains unmodified without error.
2. **Single-Node Tree ($N=1$):**
   - Deleting the only node returns `null` (`[]`).
   - Non-matching key returns the single node.
3. **Deleting the Root Node:**
   - Handled seamlessly; returns the new root node.
4. **Skewed Trees (Left or Right Heavy):**
   - Correctly deletes nodes along deep chains up to $N = 10^4$.
5. **Extreme Integer Values ($-10^8$ and $10^8$):**
   - Direct 32-bit signed comparisons prevent integer overflow.
