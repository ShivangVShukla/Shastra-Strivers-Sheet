#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    int linearSearch(vector<int>& nums, int target) {
        for (int i = 0; i < (int)nums.size(); i++) {
            if (nums[i] == target) return i;
        }
        return -1;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n, target;
    if (cin >> n >> target) {
        vector<int> nums(n);
        for (int i = 0; i < n; i++) cin >> nums[i];
        Solution sol;
        cout << sol.linearSearch(nums, target) << "\n";
    }
    return 0;
}
