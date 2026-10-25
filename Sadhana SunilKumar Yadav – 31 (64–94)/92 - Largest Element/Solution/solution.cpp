#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
public:
    int largestElement(vector<int>& nums) {
        int maxElem = nums[0];
        for (size_t i = 1; i < nums.size(); i++) {
            if (nums[i] > maxElem) maxElem = nums[i];
        }
        return maxElem;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n;
    if (cin >> n) {
        vector<int> nums(n);
        for (int i = 0; i < n; i++) cin >> nums[i];
        Solution sol;
        cout << sol.largestElement(nums) << "\n";
    }
    return 0;
}
