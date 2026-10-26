#include <bits/stdc++.h>
using namespace std;

struct TreeNode {
    int data;
    TreeNode *left;
    TreeNode *right;

    TreeNode(int val) : data(val), left(nullptr), right(nullptr) {}
};

class Solution {
public:
    vector<int> rightSideView(TreeNode* root) {
        vector<int> rightView;

        if (root == nullptr)
            return rightView;

        queue<TreeNode*> q;
        q.push(root);

        while (!q.empty()) {
            int count = q.size();

            for (int i = 0; i < count; ++i) {
                TreeNode* cur = q.front();
                q.pop();

                if (i == count - 1)
                    rightView.push_back(cur->data);

                if (cur->left)
                    q.push(cur->left);

                if (cur->right)
                    q.push(cur->right);
            }
        }

        return rightView;
    }
};

TreeNode* buildTree(const vector<string>& a) {
    if (a.empty() || a[0] == "null")
        return nullptr;

    TreeNode* root = new TreeNode(stoi(a[0]));
    queue<TreeNode*> q;
    q.push(root);

    size_t i = 1;

    while (!q.empty() && i < a.size()) {
        TreeNode* cur = q.front();
        q.pop();

        if (i < a.size() && a[i] != "null") {
            cur->left = new TreeNode(stoi(a[i]));
            q.push(cur->left);
        }
        ++i;

        if (i < a.size() && a[i] != "null") {
            cur->right = new TreeNode(stoi(a[i]));
            q.push(cur->right);
        }
        ++i;
    }

    return root;
}

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string line;
    getline(cin, line);

    stringstream ss(line);
    vector<string> a;
    string token;

    while (ss >> token)
        a.push_back(token);

    TreeNode* root = buildTree(a);

    Solution sol;
    vector<int> result = sol.rightSideView(root);

    for (int i = 0; i < (int)result.size(); ++i) {
        if (i > 0)
            cout << ' ';
        cout << result[i];
    }

    cout << '\n';

    return 0;
}