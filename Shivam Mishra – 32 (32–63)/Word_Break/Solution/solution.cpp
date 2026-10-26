
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    bool wordBreak(string s, vector<string>& wordDict) {
        int n = s.size();

        unordered_set<string> dict(
            wordDict.begin(), wordDict.end()
        );

        vector<bool> dp(n + 1, false);
        dp[0] = true;

        int maxLen = 0;
        for (const string& word : wordDict) {
            maxLen = max(maxLen, (int)word.size());
        }

        for (int i = 1; i <= n; i++) {
            for (int len = 1; len <= maxLen && len <= i; len++) {
                if (!dp[i - len]) {
                    continue;
                }

                string part = s.substr(i - len, len);

                if (dict.count(part)) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
};

int main() {
    string s;
    cin >> s;

    int n;
    cin >> n;

    vector<string> wordDict(n);
    for (int i = 0; i < n; i++) {
        cin >> wordDict[i];
    }

    Solution sol;
    cout << (sol.wordBreak(s, wordDict) ? "true" : "false")
         << '\n';

    return 0;
}