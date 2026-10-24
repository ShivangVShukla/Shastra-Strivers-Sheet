#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Solution {
public:
    string longestCommonPrefix(vector<string>& str) {
        if (str.empty()) return "";
        sort(str.begin(), str.end());
        string first = str.front();
        string last = str.back();
        int i = 0;
        while (i < first.size() && i < last.size() && first[i] == last[i]) {
            i++;
        }
        return first.substr(0, i);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n;
    if (cin >> n) {
        vector<string> arr(n);
        for (int i = 0; i < n; i++) cin >> arr[i];
        Solution sol;
        cout << sol.longestCommonPrefix(arr) << "\n";
    }
    return 0;
}
