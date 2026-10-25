#include <iostream>
#include <string>

using namespace std;

class Solution {
private:
    bool helper(const string& s, int l, int r) {
        if (l >= r) return true;
        if (s[l] != s[r]) return false;
        return helper(s, l + 1, r - 1);
    }
public:
    bool palindromeCheck(string& s) {
        return helper(s, 0, (int)s.size() - 1);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s;
    if (cin >> s) {
        Solution sol;
        cout << (sol.palindromeCheck(s) ? "true" : "false") << "\n";
    }
    return 0;
}
