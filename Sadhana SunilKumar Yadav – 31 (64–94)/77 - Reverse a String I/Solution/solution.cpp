#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Solution {
private:
    void helper(vector<char>& s, int l, int r) {
        if (l >= r) return;
        swap(s[l], s[r]);
        helper(s, l + 1, r - 1);
    }
public:
    vector<char> reverseString(vector<char>& s) {
        helper(s, 0, (int)s.size() - 1);
        return s;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string str;
    if (cin >> str) {
        vector<char> s(str.begin(), str.end());
        Solution sol;
        vector<char> ans = sol.reverseString(s);
        for (char c : ans) cout << c;
        cout << "\n";
    }
    return 0;
}
