# 314. Level Order Traversal - Editorial

## Problem Statement
Given the root of a binary tree, return its level order traversal (breadth-first traversal), level by level.

---

## Sample Example

### Sample Input
```text
3 9 20 null null 15 7
```

### Sample Output
```text
3
9 20
15 7
```

---

## Intuition & Approach
Level order traversal visits all nodes at depth $0$ first, then all nodes at depth $1$, and so forth.
This naturally maps to a **Breadth-First Search (BFS)** utilizing a First-In-First-Out **Queue**.

### Algorithm Steps:
1. If the root is `nullptr`, return an empty result.
2. Initialize a queue `q` and push `root` into it.
3. While `!q.empty()`:
   - Determine the number of nodes currently at this level: `levelSize = q.size()`.
   - Create a list `currentLevel` for the current depth.
   - Loop `levelSize` times:
     - Pop front node `node = q.front()`.
     - Append `node->val` to `currentLevel`.
     - If `node->left != nullptr`, push `node->left` to `q`.
     - If `node->right != nullptr`, push `node->right` to `q`.
   - Append `currentLevel` to the final result list.
4. Print each level on a new line.

---

## Complexity Analysis
* **Time Complexity:** $\mathcal{O}(N)$ where $N$ is the number of nodes in the tree. Each node is enqueued and dequeued exactly once.
* **Space Complexity:** $\mathcal{O}(W)$ auxiliary space where $W$ is the maximum width (maximum number of nodes at any level) of the binary tree. For a full binary tree, $W \approx N / 2 = \mathcal{O}(N)$.

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
public:
    vector<vector<int>> levelOrder(TreeNode* root) {
        vector<vector<int>> result;
        if (!root) return result;

        queue<TreeNode*> q;
        q.push(root);

        while (!q.empty()) {
            int levelSize = q.size();
            vector<int> currentLevel;

            for (int i = 0; i < levelSize; i++) {
                TreeNode* node = q.front();
                q.pop();

                currentLevel.push_back(node->val);

                if (node->left) q.push(node->left);
                if (node->right) q.push(node->right);
            }

            result.push_back(currentLevel);
        }

        return result;
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
    vector<vector<int>> result = sol.levelOrder(root);

    for (const auto& level : result) {
        for (int i = 0; i < (int)level.size(); i++) {
            if (i > 0) cout << " ";
            cout << level[i];
        }
        cout << "\n";
    }

    return 0;
}
```
