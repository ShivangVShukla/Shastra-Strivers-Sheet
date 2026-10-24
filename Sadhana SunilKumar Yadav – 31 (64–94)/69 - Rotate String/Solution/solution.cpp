#include <iostream>
#include <string>

using namespace std;

class Solution {
public:
    bool rotateString(string& s, string& goal) {
        if (s.length() != goal.length()) return false;
        string doubleS = s + s;
        return doubleS.find(goal) != string::npos;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s, goal;
    if (cin >> s >> goal) {
        Solution sol;
        cout << (sol.rotateString(s, goal) ? "true" : "false") << "\n";
    }
    return 0;
}
