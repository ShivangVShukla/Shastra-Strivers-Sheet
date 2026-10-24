#include <iostream>
#include <vector>

using namespace std;

class Solution {
private:
    int helper(int n, vector<int>& memo) {
        if (n <= 0) return 0;
        if (n == 1) return 1;
        if (memo[n] != -1) return memo[n];
        return memo[n] = helper(n - 1, memo) + helper(n - 2, memo);
    }
public:
    int fib(int n) {
        vector<int> memo(n + 2, -1);
        return helper(n, memo);
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    int n;
    if (cin >> n) {
        Solution sol;
        cout << sol.fib(n) << "\n";
    }
    return 0;
}
