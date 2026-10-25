#include <iostream>
#include <string>
#include <vector>

using namespace std;

class Solution {
public:
    bool anagramStrings(string &s, string &t) {
        if (s.length() != t.length()) return false;
        vector<int> count(256, 0);
        for (char c : s) count[(unsigned char)c]++;
        for (char c : t) count[(unsigned char)c]--;
        for (int x : count) if (x != 0) return false;
        return true;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s, t;
    if (cin >> s >> t) {
        Solution sol;
        cout << (sol.anagramStrings(s, t) ? "true" : "false") << "\n";
    }
    return 0;
}
