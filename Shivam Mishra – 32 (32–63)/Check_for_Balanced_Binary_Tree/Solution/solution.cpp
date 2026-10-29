#include <bits/stdc++.h>
using namespace std;
struct TreeNode
{
    int val;
    TreeNode *left, *right;
    explicit TreeNode(int x) : val(x), left(nullptr), right(nullptr) {}
};
TreeNode *buildTree(const vector<string> &a)
{
    if (a.empty() || a[0] == "null")
        return nullptr;
    TreeNode *root = new TreeNode(stoi(a[0]));
    queue<TreeNode *> q;
    q.push(root);
    size_t i = 1;
    while (!q.empty() && i < a.size())
    {
        TreeNode *cur = q.front();
        q.pop();
        if (i < a.size() && a[i] != "null")
        {
            cur->left = new TreeNode(stoi(a[i]));
            q.push(cur->left);
        }
        ++i;
        if (i < a.size() && a[i] != "null")
        {
            cur->right = new TreeNode(stoi(a[i]));
            q.push(cur->right);
        }
        ++i;
    }
    return root;
}
class Solution
{
    int heightOrBad(TreeNode *node)
    {
        if (!node)
            return 0;
        int lh = heightOrBad(node->left);
        if (lh == -1)
            return -1;
        int rh = heightOrBad(node->right);
        if (rh == -1)
            return -1;
        if (abs(lh - rh) > 1)
            return -1;
        return 1 + max(lh, rh);
    }

public:
    bool isBalanced(TreeNode *root) { 
        return heightOrBad(root) != -1; 
    }
};
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    string line;
    getline(cin, line);
    stringstream ss(line);
    vector<string> a;
    string t;
    while (ss >> t)
        a.push_back(t);
    TreeNode *root = buildTree(a);
    Solution s;
    cout << (s.isBalanced(root) ? "Yes" : "No") << '\n';
}
