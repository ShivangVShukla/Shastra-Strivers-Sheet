#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    string s;
    if (!getline(cin, s)) {
        cout << 0 << "\n";
        return 0;
    }
    if (!s.empty() && s.back() == '\r') s.pop_back();

    vector<int> last(256, -1);
    int left = 0;
    int max_len = 0;

    for (int right = 0; right < (int)s.size(); ++right) {
        unsigned char c = (unsigned char)s[right];
        if (last[c] >= left) {
            left = last[c] + 1;
        }
        last[c] = right;
        max_len = max(max_len, right - left + 1);
    }

    cout << max_len << "\n";
    return 0;
}
