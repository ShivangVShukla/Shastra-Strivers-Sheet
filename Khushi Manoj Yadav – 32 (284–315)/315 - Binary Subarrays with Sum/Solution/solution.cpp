#include <iostream>
#include <vector>

using namespace std;

long long atMost(const vector<int>& a, int k) {
    if (k < 0) return 0;
    int left = 0, sum = 0;
    long long cnt = 0;
    for (int right = 0; right < (int)a.size(); ++right) {
        sum += a[right];
        while (sum > k) {
            sum -= a[left];
            left++;
        }
        cnt += (right - left + 1);
    }
    return cnt;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n, goal;
    if (!(cin >> n >> goal)) return 0;

    vector<int> a(n);
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }

    long long ans = atMost(a, goal) - atMost(a, goal - 1);
    cout << ans << "\n";
    return 0;
}
