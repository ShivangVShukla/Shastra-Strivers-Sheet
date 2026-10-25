#include <iostream>
#include <vector>

using namespace std;

class Solution {
private:
    int helper(vector<int>& nums, size_t i) {
        if (i >= nums.size()) return 0;
        return nums[i] + helper(nums, i + 1);
    }
public:
    int arraySum(vector<int>& nums) {
        return helper(nums, 0);
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
        cout << sol.arraySum(nums) << "\n";
    }
    return 0;
}
