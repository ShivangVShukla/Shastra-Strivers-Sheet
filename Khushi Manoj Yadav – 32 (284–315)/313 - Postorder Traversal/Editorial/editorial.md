# 313. Postorder Traversal - Editorial

## Problem Statement
Given the root of a binary tree, return the postorder traversal of its nodes' values.

---

## Sample Example

### Sample Input
```text
1 4 null 4 2
```

### Sample Output
```text
4 2 4 1
```

---

## Intuition & Approach
Postorder traversal visits nodes in the following order:
1. Traverse the entire left subtree recursively.
2. Traverse the entire right subtree recursively.
3. Visit the current node (add its value to the output list).

### Recursive Depth-First Search (DFS)
- Base Case: If the current node is `nullptr`, return immediately.
- Recursive Step:
  1. `postorder(root->left, ans)`
  2. `postorder(root->right, ans)`
  3. `ans.push_back(root->val)`

### Iterative Approach (Using Two Stacks or Modified One Stack)
1. Using 2 stacks:
   - Push `root` to stack 1.
   - Pop `curr` from stack 1 and push to stack 2.
   - Push `curr->left` then `curr->right` to stack 1.
   - When stack 1 is empty, pop all elements from stack 2 to get postorder traversal.

---

## Complexity Analysis
* **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of nodes in the tree. Each node is visited a constant number of times.
* **Space Complexity:** $\mathcal{O}(H)$ auxiliary space for recursion stack where $H$ is tree height (or $\mathcal{O}(N)$ for iterative 2-stack approach).

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
    void postorder(TreeNode* root, vector<int>& ans) {
        if (!root) return;
        postorder(root->left, ans);
        postorder(root->right, ans);
        ans.push_back(root->val);
    }

public:
    vector<int> postorderTraversal(TreeNode* root) {
        vector<int> ans;
        postorder(root, ans);
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
    vector<int> ans = sol.postorderTraversal(root);

    for (int i = 0; i < (int)ans.size(); i++) {
        if (i > 0) cout << " ";
        cout << ans[i];
    }
    cout << "\n";

    return 0;
}
```
