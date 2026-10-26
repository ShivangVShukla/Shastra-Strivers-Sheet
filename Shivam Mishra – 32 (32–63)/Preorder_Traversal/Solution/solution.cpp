
#include <bits/stdc++.h>
using namespace std;

class TreeNode {
public:
    int data;
    TreeNode* left;
    TreeNode* right;

    TreeNode(int val) {
        data = val;
        left = nullptr;
        right = nullptr;
    }
};

class Solution {
public:
    TreeNode* buildTree(vector<string>& nodes) {
        if (nodes.empty() || nodes[0] == "null")
            return nullptr;

        TreeNode* root = new TreeNode(stoi(nodes[0]));
        queue<TreeNode*> q;
        q.push(root);

        int i = 1;

        while (!q.empty() && i < (int)nodes.size()) {
            TreeNode* curr = q.front();
            q.pop();

            if (i < (int)nodes.size() && nodes[i] != "null") {
                curr->left = new TreeNode(stoi(nodes[i]));
                q.push(curr->left);
            }
            i++;

            if (i < (int)nodes.size() && nodes[i] != "null") {
                curr->right = new TreeNode(stoi(nodes[i]));
                q.push(curr->right);
            }
            i++;
        }

        return root;
    }

    void preorder(TreeNode* root, vector<int>& ans) {
        if (root == nullptr)
            return;

        ans.push_back(root->data);
        preorder(root->left, ans);
        preorder(root->right, ans);
    }

    vector<int> preorderTraversal(TreeNode* root) {
        vector<int> ans;
        preorder(root, ans);
        return ans;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string line;
    getline(cin, line);

    for (char& c : line) {
        if (c == '[' || c == ']' || c == ',')
            c = ' ';
    }

    stringstream ss(line);
    vector<string> nodes;
    string token;

    while (ss >> token)
        nodes.push_back(token);

    Solution sol;

    TreeNode* root = sol.buildTree(nodes);
    vector<int> ans = sol.preorderTraversal(root);

    cout << "[";
    for (int i = 0; i < (int)ans.size(); i++) {
        if (i > 0)
            cout << ", ";

        cout << ans[i];
    }
    cout << "]\n";

    return 0;
}
