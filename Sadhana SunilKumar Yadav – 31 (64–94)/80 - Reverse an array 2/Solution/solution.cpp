#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
private:
    void helper(vector<int>& nums, int l, int r) {
        if (l >= r) return;
        swap(nums[l], nums[r]);
        helper(nums, l + 1, r - 1);
    }
public:
    vector<int> reverseArray(vector<int>& nums) {
        helper(nums, 0, (int)nums.size() - 1);
        return nums;
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
        vector<int> ans = sol.reverseArray(nums);
        for (int i = 0; i < n; i++) {
            if (i > 0) cout << " ";
            cout << ans[i];
        }
        cout << "\n";
    }
    return 0;
}
