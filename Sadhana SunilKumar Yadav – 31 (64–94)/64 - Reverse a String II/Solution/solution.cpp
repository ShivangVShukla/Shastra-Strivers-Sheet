#include <iostream>
#include <vector>
#include <string>
#include <algorithm>

using namespace std;

class Solution {
public:
    void reverseString(vector<char>& s) {
        int left = 0, right = (int)s.size() - 1;
        while (left < right) {
            swap(s[left++], s[right--]);
        }
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string str;
    if (cin >> str) {
        vector<char> s(str.begin(), str.end());
        Solution sol;
        sol.reverseString(s);
        for (char c : s) cout << c;
        cout << "\n";
    }
    return 0;
}
