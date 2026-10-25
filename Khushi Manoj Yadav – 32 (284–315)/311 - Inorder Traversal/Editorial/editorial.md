# 311. Inorder Traversal - Editorial

## Problem Statement
Given the root of a binary tree, return the inorder traversal of its nodes' values.

---

## Sample Example

### Sample Input
```text
1 4 null 4 2
```

### Sample Output
```text
4 4 2 1
```

---

## Intuition & Approach
Inorder traversal visits nodes in the following order:
1. Traverse the entire left subtree recursively.
2. Visit the current node (add its value to the output list).
3. Traverse the entire right subtree recursively.

### Recursive Depth-First Search (DFS)
- Base Case: If the current node is `nullptr`, return immediately.
- Recursive Step:
  1. `inorder(root->left, ans)`
  2. `ans.push_back(root->val)`
  3. `inorder(root->right, ans)`

### Iterative Approach (Using Explicit Stack)
We can also perform inorder traversal iteratively:
1. Initialize an empty stack `st` and pointer `curr = root`.
2. While `curr != nullptr` or `!st.empty()`:
   - Traverse as far left as possible, pushing each node onto `st`: `curr = curr->left`.
   - Pop the top node from `st`, record its value: `ans.push_back(top->val)`.
   - Move to the right child: `curr = top->right`.

---

## Complexity Analysis
* **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of nodes in the binary tree. Every node is visited a constant number of times.
* **Space Complexity:** $\mathcal{O}(H)$ auxiliary space for the recursion stack (or explicit stack), where $H$ is the height of the binary tree. In the worst case (skewed tree), $H = N$; for a balanced tree, $H = \mathcal{O}(\log N)$.

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
    void inorder(TreeNode* root, vector<int>& ans) {
        if (!root) return;
        inorder(root->left, ans);
        ans.push_back(root->val);
        inorder(root->right, ans);
    }

public:
    vector<int> inorderTraversal(TreeNode* root) {
        vector<int> ans;
        inorder(root, ans);
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
    vector<int> ans = sol.inorderTraversal(root);

    for (int i = 0; i < (int)ans.size(); i++) {
        if (i > 0) cout << " ";
        cout << ans[i];
    }
    cout << "\n";

    return 0;
}
```
