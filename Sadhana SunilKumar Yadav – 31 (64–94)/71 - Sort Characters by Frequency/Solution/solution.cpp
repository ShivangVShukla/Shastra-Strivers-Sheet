#include <iostream>
#include <string>
#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;

class Solution {
public:
    vector<char> frequencySort(string& s) {
        unordered_map<char, int> freq;
        for (char c : s) freq[c]++;
        vector<pair<char, int>> arr(freq.begin(), freq.end());
        sort(arr.begin(), arr.end(), [](const pair<char, int>& a, const pair<char, int>& b) {
            if (a.second != b.second) return a.second > b.second;
            return a.first < b.first;
        });
        vector<char> result;
        for (auto& p : arr) result.push_back(p.first);
        return result;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s;
    if (cin >> s) {
        Solution sol;
        vector<char> ans = sol.frequencySort(s);
        for (size_t i = 0; i < ans.size(); i++) {
            if (i > 0) cout << " ";
            cout << ans[i];
        }
        cout << "\n";
    }
    return 0;
}
