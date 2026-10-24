#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Solution {
public:
    bool isomorphicString(string s, string t) {
        if (s.length() != t.length()) return false;
        vector<int> m1(256, -1), m2(256, -1);
        for (int i = 0; i < s.length(); i++) {
            unsigned char c1 = s[i], c2 = t[i];
            if (m1[c1] != m2[c2]) return false;
            m1[c1] = i;
            m2[c2] = i;
        }
        return true;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s, t;
    if (cin >> s >> t) {
        Solution sol;
        cout << (sol.isomorphicString(s, t) ? "true" : "false") << "\n";
    }
    return 0;
}
