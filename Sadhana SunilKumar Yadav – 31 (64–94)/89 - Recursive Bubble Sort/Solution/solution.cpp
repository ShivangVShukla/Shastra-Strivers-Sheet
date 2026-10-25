#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
private:
    void bubbleSortHelper(vector<int>& nums, int n) {
        if (n <= 1) return;
        bool swapped = false;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                swap(nums[i], nums[i + 1]);
                swapped = true;
            }
        }
        if (!swapped) return;
        bubbleSortHelper(nums, n - 1);
    }
public:
    vector<int> bubbleSort(vector<int>& nums) {
        bubbleSortHelper(nums, (int)nums.size());
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
        vector<int> ans = sol.bubbleSort(nums);
        for (size_t i = 0; i < ans.size(); i++) {
            if (i > 0) cout << " ";
            cout << ans[i];
        }
        cout << "\n";
    }
    return 0;
}
