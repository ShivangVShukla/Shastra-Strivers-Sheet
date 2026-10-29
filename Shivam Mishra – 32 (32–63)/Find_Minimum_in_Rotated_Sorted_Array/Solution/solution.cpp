
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int findMin(vector<int>& nums) {
        int low = 0;
        int high = nums.size() - 1;

        while (low < high) {
            // If the current range is sorted
            if (nums[low] < nums[high]) {
                return nums[low];
            }

            int mid = low + (high - low) / 2;

            if (nums[mid] > nums[high]) {
                // Minimum lies in the right half
                low = mid + 1;
            } else {
                // Minimum is at mid or in the left half
                high = mid;
            }
        }

        return nums[low];
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> nums(n);
    for (int i = 0; i < n; i++) {
        cin >> nums[i];
    }

    Solution obj;
    cout << obj.findMin(nums) << '\n';

    return 0;
}
