#include <iostream>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    string s;
    int k;
    if (!(cin >> s >> k)) return 0;

    vector<int> count(26, 0);
    int left = 0, max_freq = 0, max_len = 0;

    for (int right = 0; right < (int)s.size(); ++right) {
        int idx = s[right] - 'A';
        count[idx]++;
        max_freq = max(max_freq, count[idx]);

        while ((right - left + 1) - max_freq > k) {
            count[s[left] - 'A']--;
            left++;
        }

        max_len = max(max_len, right - left + 1);
    }

    cout << max_len << "\n";
    return 0;
}
