#include <iostream>
#include <string>

using namespace std;

class Solution {
public:
    string largeOddNum(string& s) {
        int r = (int)s.size() - 1;
        while (r >= 0 && (s[r] - '0') % 2 == 0) {
            r--;
        }
        if (r < 0) return "";
        int l = 0;
        while (l <= r && s[l] == '0') {
            l++;
        }
        if (l > r) return "";
        return s.substr(l, r - l + 1);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s;
    if (cin >> s) {
        Solution sol;
        cout << sol.largeOddNum(s) << "\n";
    }
    return 0;
}
