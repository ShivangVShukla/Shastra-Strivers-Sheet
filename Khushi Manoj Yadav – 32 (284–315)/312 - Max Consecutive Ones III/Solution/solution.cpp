#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n, k;
    if (!(cin >> n >> k)) return 0;

    vector<int> a(n);
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }

    int left = 0, zeros = 0, max_len = 0;
    for (int right = 0; right < n; ++right) {
        if (a[right] == 0) zeros++;
        while (zeros > k) {
            if (a[left] == 0) zeros--;
            left++;
        }
        max_len = max(max_len, right - left + 1);
    }

    cout << max_len << "\n";
    return 0;
}
