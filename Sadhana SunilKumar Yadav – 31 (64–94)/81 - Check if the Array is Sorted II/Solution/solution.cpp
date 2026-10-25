#include <iostream>
#include <vector>

using namespace std;

class Solution {
private:
    bool helper(vector<int>& nums, size_t i) {
        if (i + 1 >= nums.size()) return true;
        if (nums[i] > nums[i + 1]) return false;
        return helper(nums, i + 1);
    }
public:
    bool isSorted(vector<int>& nums) {
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
        cout << (sol.isSorted(nums) ? "true" : "false") << "\n";
    }
    return 0;
}
