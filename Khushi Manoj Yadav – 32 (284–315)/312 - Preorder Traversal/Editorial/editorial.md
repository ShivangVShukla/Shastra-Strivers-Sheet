# 312. Preorder Traversal - Editorial

## Problem Statement
Given the root of a binary tree, return the preorder traversal of its nodes' values.

---

## Sample Example

### Sample Input
```text
1 4 null 4 2
```

### Sample Output
```text
1 4 4 2
```

---

## Intuition & Approach
Preorder traversal visits nodes in the following sequence:
1. Visit the current node (add its value to the output list).
2. Traverse the entire left subtree recursively.
3. Traverse the entire right subtree recursively.

### Recursive Depth-First Search (DFS)
- Base Case: If the current node is `nullptr`, return immediately.
- Recursive Step:
  1. `ans.push_back(root->val)`
  2. `preorder(root->left, ans)`
  3. `preorder(root->right, ans)`

### Iterative Approach (Using Explicit Stack)
1. Initialize an empty stack `st` and push `root` if it is not null.
2. While `!st.empty()`:
   - Pop the top node `curr = st.top()`.
   - Record its value: `ans.push_back(curr->val)`.
   - Push `curr->right` first (if not null) so that the left child is processed first.
   - Push `curr->left` (if not null).

---

## Complexity Analysis
* **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of nodes in the tree. Each node is visited once.
* **Space Complexity:** $\mathcal{O}(H)$ auxiliary space for the recursion stack, where $H$ is the height of the tree. $\mathcal{O}(\log N)$ for a balanced tree, and $\mathcal{O}(N)$ in the worst case for a skewed tree.

---

## Reference Implementation

### C++ Solution
```cpp
#include <iostream>
#include <vector>
#include <string>
#include <sstream>
#include <queue>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
};

class Solution {
private:
    void preorder(TreeNode* root, vector<int>& ans) {
        if (!root) return;
        ans.push_back(root->val);
        preorder(root->left, ans);
        preorder(root->right, ans);
    }

public:
    vector<int> preorderTraversal(TreeNode* root) {
        vector<int> ans;
        preorder(root, ans);
        return ans;
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
    vector<int> ans = sol.preorderTraversal(root);

    for (int i = 0; i < (int)ans.size(); i++) {
        if (i > 0) cout << " ";
        cout << ans[i];
    }
    cout << "\n";

    return 0;
}
```
