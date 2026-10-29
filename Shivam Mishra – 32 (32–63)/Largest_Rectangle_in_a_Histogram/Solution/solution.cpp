
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int largestRectangleArea(vector<int>& heights) {
        int n = heights.size();
        stack<int> st;
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (!st.empty() &&
                   heights[st.top()] > currentHeight) {
                int height = heights[st.top()];
                st.pop();

                int width = st.empty()
                    ? i
                    : i - st.top() - 1;

                maxArea = max(maxArea, height * width);
            }

            if (i < n) {
                st.push(i);
            }
        }

        return maxArea;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    string line;
    vector<int> heights;

    // Accepts input like [2, 1, 5, 6, 2, 3]
    while (getline(cin, line)) {
        for (char& c : line) {
            if (!(isdigit(static_cast<unsigned char>(c)) ||
                  c == '-')) {
                c = ' ';
            }
        }

        stringstream ss(line);
        int value;

        while (ss >> value) {
            heights.push_back(value);
        }
    }

    if (heights.empty()) {
        return 0;
    }

    Solution solution;
    cout << solution.largestRectangleArea(heights) << '\n';

    return 0;
}
