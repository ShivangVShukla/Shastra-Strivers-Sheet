#include <iostream>
#include <vector>

using namespace std;

class Solution {
public:
    vector<int> insertionSort(vector<int>& nums) {
        int n = (int)nums.size();
        for (int i = 1; i < n; i++) {
            int key = nums[i];
            int j = i - 1;
            while (j >= 0 && nums[j] > key) {
                nums[j + 1] = nums[j];
                j--;
            }
            nums[j + 1] = key;
        }
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
        vector<int> ans = sol.insertionSort(nums);
        for (size_t i = 0; i < ans.size(); i++) {
            if (i > 0) cout << " ";
            cout << ans[i];
        }
        cout << "\n";
    }
    return 0;
}
