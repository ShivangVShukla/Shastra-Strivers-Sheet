
# Editorial: Preorder Traversal

## Topic
Binary Tree / Depth-First Search (DFS) / Tree Traversal / Recursion

## Problem Statement

Given the root of a binary tree, return the preorder traversal of the tree.

In preorder traversal, each node is visited in the following order:

1. **Root:** Visit the current node.
2. **Left:** Recursively traverse the left subtree.
3. **Right:** Recursively traverse the right subtree.

The traversal continues until all nodes of the binary tree have been visited.

## Approach: Recursive DFS

### Intuition

Preorder traversal follows the **Root → Left → Right** order.

For every node, we first store its value in the answer array. We then recursively traverse its left subtree, followed by its right subtree.

The recursion stops when the current node is `nullptr`, indicating that there is no node to visit.

A recursive helper function is used to perform the traversal, while the main function initializes the answer vector and returns the final traversal.

### Algorithm

1. Create an empty vector `ans` to store the preorder traversal.
2. Call the helper function `preorder(root, ans)`.
3. If the current node is `nullptr`, return without doing anything.
4. Add the current node's value to `ans`.
5. Recursively traverse the left subtree.
6. Recursively traverse the right subtree.
7. Return `ans` after all nodes have been visited.

## C++ Solution

```cpp
class Solution {
public:
    void preorder(TreeNode* root, vector<int>& ans) {
        // Base case: no node to process
        if (root == nullptr)
            return;

        // Step 1: Visit the root
        ans.push_back(root->data);

        // Step 2: Traverse the left subtree
        preorder(root->left, ans);

        // Step 3: Traverse the right subtree
        preorder(root->right, ans);
    }

    vector<int> preorderTraversal(TreeNode* root) {
        vector<int> ans;

        preorder(root, ans);

        return ans;
    }
};
```

## Dry Run

Consider the following binary tree:

```text
        1
       / \
      2   3
     / \
    4   5
```

Input:

```text
root = [1, 2, 3, 4, 5]
```

The traversal proceeds as follows:

| Step | Current Node | Operation | Result |
|---|---|---|---|
| 1 | 1 | Visit root | [1] |
| 2 | 2 | Visit left subtree | [1, 2] |
| 3 | 4 | Visit left child | [1, 2, 4] |
| 4 | nullptr | Return | [1, 2, 4] |
| 5 | nullptr | Return | [1, 2, 4] |
| 6 | 5 | Visit right child of 2 | [1, 2, 4, 5] |
| 7 | nullptr | Return | [1, 2, 4, 5] |
| 8 | nullptr | Return | [1, 2, 4, 5] |
| 9 | 3 | Visit right subtree of 1 | [1, 2, 4, 5, 3] |

Output:

```text
[1, 2, 4, 5, 3]
```

The final traversal is `[1, 2, 4, 5, 3]` because the root is visited before its left and right subtrees.

## Example 2: Single-Node Tree

Input:

```text
root = [1]
```

The root node is visited first. Both its left and right children are `nullptr`, so the recursive calls return immediately.

Output:

```text
[1]
```

## Example 3: Empty Tree

Input:

```text
root = []
```

The root is `nullptr`, so the base case is reached immediately.

Output:

```text
[]
```

## Complexity Analysis

Let `N` be the total number of nodes in the binary tree and `H` be its height.

### Time Complexity: O(N)

Every node is visited exactly once. Each visit performs constant-time work, excluding the recursive calls.

Therefore, the total time complexity is **O(N)**.

### Auxiliary Space Complexity: O(H)

The recursive calls use the call stack, with a maximum depth equal to the height of the tree.

- For a balanced binary tree, the height is O(log N).
- For a completely skewed binary tree, the height is O(N).

Therefore, the auxiliary space complexity is **O(H)**, excluding the output vector.

The output vector requires an additional **O(N)** space to store all node values.

## Key Takeaways

- Preorder traversal follows **Root → Left → Right**.
- It is a Depth-First Search (DFS) traversal technique.
- The base case prevents recursion from continuing beyond leaf nodes.
- Each node is processed exactly once.
- The recursive approach is simple and naturally follows the structure of a binary tree.
- Preorder traversal can also be implemented iteratively using a stack.

## Implementation

Refer to `Solution/solution.cpp` for the complete executable implementation, including input parsing and output formatting.

The editorial above contains the `Solution` class method implementation, which can be adapted to the function signature required by the coding platform.
