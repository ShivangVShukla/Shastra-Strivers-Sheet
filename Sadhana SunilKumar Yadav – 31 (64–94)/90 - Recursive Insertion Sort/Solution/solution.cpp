#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
private:
    void insertionSortHelper(vector<int>& nums, int i, int n) {
        if (i >= n) return;
        int j = i;
        while (j > 0 && nums[j - 1] > nums[j]) {
            swap(nums[j - 1], nums[j]);
            j--;
        }
        insertionSortHelper(nums, i + 1, n);
    }
public:
    vector<int> insertionSort(vector<int>& nums) {
        insertionSortHelper(nums, 1, (int)nums.size());
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
