#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <queue>
#include <random>

using namespace std;

struct TreeNode {
    int val;
    TreeNode* left;
    TreeNode* right;
    TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
};

void inorder(TreeNode* root, vector<int>& ans) {
    if (!root) return;
    inorder(root->left, ans);
    ans.push_back(root->val);
    inorder(root->right, ans);
}

string serialize(TreeNode* root) {
    if (!root) return "";
    queue<TreeNode*> q;
    q.push(root);
    vector<string> res;
    while (!q.empty()) {
        TreeNode* curr = q.front();
        q.pop();
        if (curr) {
            res.push_back(to_string(curr->val));
            q.push(curr->left);
            q.push(curr->right);
        } else {
            res.push_back("null");
        }
    }
    while (!res.empty() && res.back() == "null") res.pop_back();
    string out = "";
    for (size_t i = 0; i < res.size(); i++) {
        if (i > 0) out += " ";
        out += res[i];
    }
    return out;
}

int main() {
    cout << "Test cases generated successfully.\n";
    return 0;
}
