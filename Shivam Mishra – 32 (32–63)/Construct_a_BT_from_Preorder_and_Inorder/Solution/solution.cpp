
#include <bits/stdc++.h>
using namespace std;

struct TreeNode {
    int data;
    TreeNode* left;
    TreeNode* right;

    TreeNode(int val) {
        data = val;
        left = right = nullptr;
    }
};

class Solution {
private:
    unordered_map<int, int> inMap;

    TreeNode* build(vector<int>& preorder, int preStart, int preEnd,
                    int inStart, int inEnd) {
        if (preStart > preEnd || inStart > inEnd)
            return nullptr;

        TreeNode* root = new TreeNode(preorder[preStart]);

        int inRoot = inMap[root->data];
        int leftSize = inRoot - inStart;

        root->left = build(preorder,
                           preStart + 1,
                           preStart + leftSize,
                           inStart,
                           inRoot - 1);

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

        for (int i = 0; i < (int)inorder.size(); i++)
            inMap[inorder[i]] = i;

        int n = preorder.size();

        if (n == 0)
            return nullptr;

        return build(preorder, 0, n - 1, 0, n - 1);
    }

    void printLevelOrder(TreeNode* root) {
        if (!root)
            return;

        queue<TreeNode*> q;
        q.push(root);

        vector<string> result;

        while (!q.empty()) {
            TreeNode* curr = q.front();
            q.pop();

            if (curr == nullptr) {
                result.push_back("null");
                continue;
            }

            result.push_back(to_string(curr->data));

            q.push(curr->left);
            q.push(curr->right);
        }

        while (!result.empty() && result.back() == "null")
            result.pop_back();

        for (int i = 0; i < (int)result.size(); i++) {
            if (i > 0)
                cout << " ";

            cout << result[i];
        }

        cout << '\n';
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> preorder(n), inorder(n);

    for (int i = 0; i < n; i++)
        cin >> preorder[i];

    for (int i = 0; i < n; i++)
        cin >> inorder[i];

    Solution sol;

    TreeNode* root = sol.buildTree(preorder, inorder);

    sol.printLevelOrder(root);

    return 0;
}
