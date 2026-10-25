# 315. Pre, Post, Inorder in one traversal - Editorial

## Problem Statement
Given the root of a binary tree, compute Preorder, Inorder, and Postorder traversals in a **single traversal pass**.

---

## Sample Example

### Sample Input
```text
1 2 5 3 4 null null
```

### Sample Output
```text
1 2 3 4 5
3 2 4 1 5
3 4 2 5 1
```

---

## Intuition & Approach
Instead of traversing the tree three separate times (which would take $3 \times \mathcal{O}(N)$ passes), we can simulate the recursion call stack using an explicit stack storing pairs: `(node, state)`.

Every node goes through 3 states during a full traversal:
1. **State 1 (First visit - Preorder):**
   - Add `node->val` to the **Preorder** list.
   - Increment state to `2`: `it.second = 2`.
   - If the left child exists, push `(node->left, 1)` onto the stack.
2. **State 2 (Second visit - Inorder):**
   - Add `node->val` to the **Inorder** list.
   - Increment state to `3`: `it.second = 3`.
   - If the right child exists, push `(node->right, 1)` onto the stack.
3. **State 3 (Third visit - Postorder):**
   - Add `node->val` to the **Postorder** list.
   - Pop the node from the stack (`st.pop()`).

This simulates the exact DFS traversal order and populates all three sequences simultaneously in a single pass!

---

## Complexity Analysis
* **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of nodes. Each node enters and leaves the stack a constant number of times (at most 3 state transitions).
* **Space Complexity:** $\mathcal{O}(H)$ auxiliary space for the stack, where $H$ is the height of the tree. $\mathcal{O}(N)$ space is used to store the three resulting traversal lists.

---

## Reference Implementation

### C++ Solution
```cpp
#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include <queue>
#include <stack>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
};

class Solution {
public:
    vector<vector<int>> preInPostTraversal(TreeNode* root) {
        vector<int> pre, in, post;
        if (!root) return {pre, in, post};

        stack<pair<TreeNode*, int>> st;
        st.push({root, 1});

        while (!st.empty()) {
            auto& it = st.top();

            if (it.second == 1) {
                pre.push_back(it.first->val);
                it.second = 2;
                if (it.first->left != nullptr) {
                    st.push({it.first->left, 1});
                }
            } else if (it.second == 2) {
                in.push_back(it.first->val);
                it.second = 3;
                if (it.first->right != nullptr) {
                    st.push({it.first->right, 1});
                }
            } else {
                post.push_back(it.first->val);
                st.pop();
            }
        }

        return {pre, in, post};
    }
};

TreeNode* buildTree(const vector<string>& arr) {
    if (arr.empty() || arr[0] == "null") return nullptr;
    TreeNode* root = new TreeNode(stoi(arr[0]));
    queue<TreeNode*> q;
    q.push(root);
    int i = 1;
    while (!q.empty() && i < (int)arr.size()) {
        TreeNode* curr = q.front();
        q.pop();
        if (i < (int)arr.size() && arr[i] != "null") {
            curr->left = new TreeNode(stoi(arr[i]));
            q.push(curr->left);
        }
        i++;
        if (i < (int)arr.size() && arr[i] != "null") {
            curr->right = new TreeNode(stoi(arr[i]));
            q.push(curr->right);
        }
        i++;
    }
    return root;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    string line;
    if (!getline(cin, line)) return 0;

    stringstream ss(line);
    string token;
    vector<string> arr;
    while (ss >> token) arr.push_back(token);

    TreeNode* root = buildTree(arr);

    Solution sol;
    vector<vector<int>> res = sol.preInPostTraversal(root);

    // Line 1: Preorder
    for (int i = 0; i < (int)res[0].size(); i++) {
        if (i > 0) cout << " ";
        cout << res[0][i];
    }
    cout << "\n";

    // Line 2: Inorder
    for (int i = 0; i < (int)res[1].size(); i++) {
        if (i > 0) cout << " ";
        cout << res[1][i];
    }
    cout << "\n";

    // Line 3: Postorder
    for (int i = 0; i < (int)res[2].size(); i++) {
        if (i > 0) cout << " ";
        cout << res[2][i];
    }
    cout << "\n";

    return 0;
}
```
