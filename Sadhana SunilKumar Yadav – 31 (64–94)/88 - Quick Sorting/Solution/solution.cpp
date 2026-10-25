#include <iostream>
#include <vector>
#include <algorithm>

using namespace std;

class Solution {
private:
    int partition(vector<int>& nums, int low, int high) {
        int pivot = nums[low];
        int i = low;
        int j = high;
        while (i < j) {
            while (nums[i] <= pivot && i <= high - 1) i++;
            while (nums[j] > pivot && j >= low + 1) j--;
            if (i < j) swap(nums[i], nums[j]);
        }
        swap(nums[low], nums[j]);
        return j;
    }

    void quickSortHelper(vector<int>& nums, int low, int high) {
        if (low < high) {
            int pIndex = partition(nums, low, high);
            quickSortHelper(nums, low, pIndex - 1);
            quickSortHelper(nums, pIndex + 1, high);
        }
    }

public:
    vector<int> quickSort(vector<int>& nums) {
        if (!nums.empty()) {
            quickSortHelper(nums, 0, (int)nums.size() - 1);
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
        vector<int> ans = sol.quickSort(nums);
        for (size_t i = 0; i < ans.size(); i++) {
            if (i > 0) cout << " ";
            cout << ans[i];
        }
        cout << "\n";
    }
    return 0;
}
