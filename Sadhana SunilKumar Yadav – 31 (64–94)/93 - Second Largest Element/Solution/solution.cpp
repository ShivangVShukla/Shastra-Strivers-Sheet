#include <iostream>
#include <vector>
#include <climits>

using namespace std;

class Solution {
public:
    int secondLargestElement(vector<int>& nums) {
        if (nums.size() < 2) return -1;
        long long largest = -2e18;
        long long secondLargest = -2e18;
        for (int x : nums) {
            if (x > largest) {
                secondLargest = largest;
                largest = x;
            } else if (x > secondLargest && x != largest) {
                secondLargest = x;
            }
        }
        if (secondLargest == (long long)-2e18) return -1;
        return (int)secondLargest;
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
        cout << sol.secondLargestElement(nums) << "\n";
    }
    return 0;
}
