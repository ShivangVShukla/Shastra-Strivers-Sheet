#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    long long maxProfit(const vector<int>& prices) {
        if (prices.empty()) return 0;
        int minPrice = prices[0];
        long long best = 0;
        for (size_t i = 1; i < prices.size(); ++i) {
            best = max(best, (long long)prices[i] - minPrice);
            minPrice = min(minPrice, prices[i]);
        }
        return best;
    }
};
int main() {
    ios::sync_with_stdio(false); cin.tie(nullptr);
    int n; if (!(cin >> n)) return 0;
    vector<int> prices(n); for (int &x : prices) cin >> x;
    Solution solution; cout << solution.maxProfit(prices) << '\n';
    return 0;
}
