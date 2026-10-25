#include <iostream>
#include <string>

using namespace std;

class Solution {
public:
    bool palindromeCheck(string& s) {
        int left = 0, right = (int)s.size() - 1;
        while (left < right) {
            if (s[left++] != s[right--]) return false;
        }
        return true;
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
