
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    vector<vector<int>> insertNewInterval(
        vector<vector<int>>& Intervals,
        vector<int>& newInterval
    ) {
        vector<vector<int>> ans;
        int i = 0;
        int n = Intervals.size();

        while (i < n && Intervals[i][1] < newInterval[0]) {
            ans.push_back(Intervals[i]);
            i++;
        }

        while (i < n && Intervals[i][0] <= newInterval[1]) {
            newInterval[0] = min(newInterval[0], Intervals[i][0]);
            newInterval[1] = max(newInterval[1], Intervals[i][1]);
            i++;
        }

        ans.push_back(newInterval);

        while (i < n) {
            ans.push_back(Intervals[i]);
            i++;
        }

        return ans;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<vector<int>> intervals(n, vector<int>(2));

    for (int i = 0; i < n; i++) {
        cin >> intervals[i][0] >> intervals[i][1];
    }

    vector<int> newInterval(2);
    cin >> newInterval[0] >> newInterval[1];

    Solution sol;
    vector<vector<int>> ans =
        sol.insertNewInterval(intervals, newInterval);

    for (int i = 0; i < (int)ans.size(); i++) {
        if (i > 0) cout << " ";
        cout << "[" << ans[i][0] << "," << ans[i][1] << "]";
    }

    cout << '\n';
    return 0;
}
