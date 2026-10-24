#include <iostream>
#include <vector>
#include <unordered_map>
#include <algorithm>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n;
    if (!(cin >> n)) return 0;

    vector<long long> f(n);
    for (int i = 0; i < n; ++i) {
        cin >> f[i];
    }

    unordered_map<long long, int> cnt;
    int left = 0, max_fruits = 0;

    for (int right = 0; right < n; ++right) {
        cnt[f[right]]++;
        while ((int)cnt.size() > 2) {
            cnt[f[left]]--;
            if (cnt[f[left]] == 0) {
                cnt.erase(f[left]);
            }
            left++;
        }
        max_fruits = max(max_fruits, right - left + 1);
    }

    cout << max_fruits << "\n";
    return 0;
}
