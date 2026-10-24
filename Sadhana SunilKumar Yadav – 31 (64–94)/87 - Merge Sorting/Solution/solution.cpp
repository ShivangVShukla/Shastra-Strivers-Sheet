#include <iostream>
#include <vector>

using namespace std;

class Solution {
private:
    void merge(vector<int>& nums, int low, int mid, int high) {
        vector<int> temp;
        temp.reserve(high - low + 1);
        int left = low, right = mid + 1;
        while (left <= mid && right <= high) {
            if (nums[left] <= nums[right]) {
                temp.push_back(nums[left++]);
            } else {
                temp.push_back(nums[right++]);
            }
        }
        while (left <= mid) temp.push_back(nums[left++]);
        while (right <= high) temp.push_back(nums[right++]);
        for (int i = low; i <= high; i++) {
            nums[i] = temp[i - low];
        }
    }

    void mergeSortHelper(vector<int>& nums, int low, int high) {
        if (low >= high) return;
        int mid = low + (high - low) / 2;
        mergeSortHelper(nums, low, mid);
        mergeSortHelper(nums, mid + 1, high);
        merge(nums, low, mid, high);
    }

public:
    vector<int> mergeSort(vector<int>& nums) {
        if (!nums.empty()) {
            mergeSortHelper(nums, 0, (int)nums.size() - 1);
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
        vector<int> ans = sol.mergeSort(nums);
        for (size_t i = 0; i < ans.size(); i++) {
            if (i > 0) cout << " ";
            cout << ans[i];
        }
        cout << "\n";
    }
    return 0;
}
