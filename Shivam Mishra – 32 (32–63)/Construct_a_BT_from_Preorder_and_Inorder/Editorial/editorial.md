# Editorial: Construct a Binary Tree from Preorder and Inorder

## Topic
Binary Tree, Recursion, Depth-First Search (DFS)

## Problem Statement
Given the preorder and inorder traversals of a binary tree, construct and return the original binary tree.

All node values are distinct, and both traversal arrays represent the same binary tree.

- **Preorder traversal:** Visit the root, then the left subtree, and finally the right subtree.
- **Inorder traversal:** Visit the left subtree, then the root, and finally the right subtree.

The constructed tree can be represented using level-order traversal, with `null` indicating missing children.

## Approach: Recursive Tree Construction

### Key Observation

The main idea is to use the properties of preorder and inorder traversals to identify the root and divide the tree into its left and right subtrees.

1. **Identify the root:** In preorder traversal, the first element is always the root of the current subtree.
2. **Locate the root in inorder:** Find the root's index in the inorder traversal. All elements before this index belong to the left subtree, while all elements after it belong to the right subtree.
3. **Calculate the left subtree size:** If the current inorder segment starts at `inStart` and the root is at index `inRoot`, then:

   `leftSize = inRoot - inStart`

4. **Construct the left subtree:** The elements immediately following the root in preorder represent the left subtree. Recursively construct it using the corresponding preorder and inorder ranges.
5. **Construct the right subtree:** The remaining preorder elements represent the right subtree. Recursively construct it using the remaining ranges.
6. **Base case:** If the preorder or inorder range becomes invalid, return `nullptr`, indicating an empty subtree.

### Optimization Using a Hash Map

Searching for the root in the inorder array during every recursive call would take linear time per search and could result in an overall time complexity of \(O(n^2)\) for a skewed tree.

To optimize this, store each inorder value and its index in an `unordered_map<int, int>`.

This allows the root's inorder position to be retrieved in expected \(O(1)\) time, reducing the overall expected time complexity to \(O(n)\).

## Algorithm

1. Create a hash map that stores each element of the inorder traversal and its index.
2. Call the recursive function with the complete preorder and inorder ranges.
3. If the current range is empty, return `nullptr`.
4. Create a new tree node using the first element of the current preorder range.
5. Find the root's index in the inorder traversal using the hash map.
6. Calculate the number of nodes in the left subtree.
7. Recursively construct the left subtree using:
   - Preorder range: `preStart + 1` to `preStart + leftSize`
   - Inorder range: `inStart` to `inRoot - 1`
8. Recursively construct the right subtree using:
   - Preorder range: `preStart + leftSize + 1` to `preEnd`
   - Inorder range: `inRoot + 1` to `inEnd`
9. Return the root node after both subtrees have been constructed.

## Example

**Input:**

```text
Preorder: [3, 9, 20, 15, 7]
Inorder:  [9, 3, 15, 20, 7]
```

**Step 1: Identify the root**

The first element of preorder is `3`, so `3` is the root.

In inorder, `3` appears at index `1`. Therefore:
- Left subtree: `[9]`
- Right subtree: `[15, 20, 7]`

**Step 2: Construct the left subtree**

The left subtree contains only `9`, so create a node with value `9`. It has no children.

**Step 3: Construct the right subtree**

The next relevant preorder element is `20`, so `20` becomes the root of the right subtree.

In the corresponding inorder segment `[15, 20, 7]`, the elements before `20` form its left subtree, and the elements after `20` form its right subtree.

Thus:
- Left child of `20`: `15`
- Right child of `20`: `7`

**Constructed binary tree:**

```text
        3
       / \
      9   20
         /  \
        15   7
```

**Output (level-order representation):**

```text
3 9 20 null null 15 7
```

## C++ Solution

```cpp
class Solution {
private:
    unordered_map<int, int> inMap;

    TreeNode* build(vector<int>& preorder, int preStart, int preEnd,
                    int inStart, int inEnd) {
        // Base case: no nodes remain in this subtree.
        if (preStart > preEnd || inStart > inEnd)
            return nullptr;

        // The first preorder element is the root.
        TreeNode* root = new TreeNode(preorder[preStart]);

        // Locate the root in inorder traversal.
        int inRoot = inMap[root->data];

        // Number of nodes belonging to the left subtree.
        int leftSize = inRoot - inStart;

        // Recursively construct the left subtree.
        root->left = build(preorder,
                           preStart + 1,
                           preStart + leftSize,
                           inStart,
                           inRoot - 1);

        // Recursively construct the right subtree.
        root->right = build(preorder,
                            preStart + leftSize + 1,
                            preEnd,
                            inRoot + 1,
                            inEnd);

        return root;
    }

public:
    TreeNode* buildTree(vector<int>& preorder, vector<int>& inorder) {
        inMap.clear();

        // Store each inorder value and its index.
        for (int i = 0; i < (int)inorder.size(); i++)
            inMap[inorder[i]] = i;

        int n = preorder.size();

        if (n == 0)
            return nullptr;

        return build(preorder, 0, n - 1, 0, n - 1);
    }
};
```

## Correctness Explanation

The algorithm correctly reconstructs the binary tree because it uses the defining properties of both traversals.

- **Root selection:** The first element of each preorder segment is the root of that subtree.
- **Correct subtree partitioning:** The root's position in the corresponding inorder segment separates the left and right subtrees. The number of elements on the left determines the size of the left subtree in preorder.
- **Recursive construction:** Applying the same process to both subtrees reconstructs their roots and descendants in the correct positions.
- **Termination:** When a subtree has no elements, the function returns `nullptr`, correctly representing an empty subtree.

Since all values are distinct and the traversals describe the same tree, the root positions and subtree boundaries are uniquely determined. Therefore, the algorithm reconstructs the original binary tree.

## Complexity Analysis

Let \(n\) be the number of nodes in the binary tree.

- **Time Complexity: \(O(n)\) expected.** Building the hash map takes \(O(n)\) time. Each node is created exactly once, and each root index is retrieved in expected \(O(1)\) time. Therefore, the total expected time is \(O(n)\). In the worst case, hash-map operations can degrade, so the strict worst-case bound may be higher.
- **Auxiliary Space Complexity: \(O(n)\).** The hash map stores \(n\) elements. The recursive call stack requires \(O(h)\) space, where \(h\) is the tree height. The total auxiliary space is \(O(n+h)=O(n)\). The constructed tree itself additionally occupies \(O(n)\) space.

For a balanced tree, the recursion depth is \(O(\log n)\). For a completely skewed tree, it can reach \(O(n)\).

## Implementation Notes

- The solution assumes all node values are distinct.
- The `TreeNode` definition must match the platform's expected node structure. This implementation uses `root->data`; change it to `root->val` if the provided structure uses `val`.
- The `Solution` class contains only the tree-construction logic, as expected by LeetCode-style and TakeUForward-style submissions.
- Level-order printing and input parsing should be implemented separately in `solution.cpp` if required by your local test harness.
