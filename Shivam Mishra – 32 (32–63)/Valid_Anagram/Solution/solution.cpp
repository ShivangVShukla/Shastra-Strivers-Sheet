
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    bool anagramStrings(string &s, string &t) {
        if (s.length() != t.length()) {
            return false;
        }

        int freq[26] = {0};

        for (char c : s) {
            freq[c - 'a']++;
        }

        for (char c : t) {
            freq[c - 'a']--;

            if (freq[c - 'a'] < 0) {
                return false;
            }
        }

        return true;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string s, t;
    cin >> s >> t;

    Solution solution;

    cout << (solution.anagramStrings(s, t) ? "true" : "false")
         << '\n';

    return 0;
}
