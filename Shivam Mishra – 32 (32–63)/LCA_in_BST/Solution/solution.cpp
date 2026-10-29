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
    TreeNode *r = new TreeNode(stoi(a[0]));
    queue<TreeNode *> q;
    q.push(r);
    size_t i = 1;
    while (!q.empty() && i < a.size())
    {
        auto *c = q.front();
        q.pop();
        if (i < a.size() && a[i] != "null")
        {
            c->left = new TreeNode(stoi(a[i]));
            q.push(c->left);
        }
        ++i;
        if (i < a.size() && a[i] != "null")
        {
            c->right = new TreeNode(stoi(a[i]));
            q.push(c->right);
        }
        ++i;
    }
    return r;
}
class Solution
{
public:
    int lca(TreeNode *root, int p, int q)
    {
        TreeNode *cur = root;
        while (cur)
        {
            if (p < cur->val && q < cur->val)
                cur = cur->left;
            else if (p > cur->val && q > cur->val)
                cur = cur->right;
            else
                return cur->val;  //If return type is TreeNode* then return just curr
        }
        return -1;  //If return type is TreeNode* then return NULL
    }
};
int main()
{
    ios::sync_with_stdio(false);
    cin.tie(nullptr);
    string line;
    getline(cin, line);
    auto sep = line.find('|');
    string treePart = line.substr(0, sep), targets = sep == string::npos ? "" : line.substr(sep + 1);
    stringstream ts(treePart), ps(targets);
    vector<string> a;
    string t;
    while (ts >> t)
        a.push_back(t);
    int p, q;
    if (!(ps >> p >> q))
        return 0;
    TreeNode *r = buildTree(a);
    Solution s;
    cout << s.lca(r, p, q) << '\n';
}
