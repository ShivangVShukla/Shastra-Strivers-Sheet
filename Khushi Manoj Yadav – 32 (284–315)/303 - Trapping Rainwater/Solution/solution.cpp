#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n;
    if (!(cin >> n)) return 0;

    vector<long long> h(n);
    for (int i = 0; i < n; ++i) {
        cin >> h[i];
    }

    int left = 0, right = n - 1;
    long long left_max = 0, right_max = 0;
    long long total_water = 0;

    while (left <= right) {
        if (h[left] <= h[right]) {
            if (h[left] >= left_max) {
                left_max = h[left];
            } else {
                total_water += left_max - h[left];
            }
            left++;
        } else {
            if (h[right] >= right_max) {
                right_max = h[right];
            } else {
                total_water += right_max - h[right];
            }
            right--;
        }
    }

    cout << total_water << "\n";
    return 0;
}
