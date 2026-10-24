#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int R, C;
    if (!(cin >> R >> C)) return 0;

    vector<int> heights(C, 0);
    int max_area = 0;

    for (int r = 0; r < R; ++r) {
        for (int c = 0; c < C; ++c) {
            int val;
            cin >> val;
            if (val == 1) heights[c]++;
            else heights[c] = 0;
        }

        vector<int> h = heights;
        h.push_back(0);
        stack<int> st;
        for (int i = 0; i <= C; ++i) {
            while (!st.empty() && h[st.top()] >= h[i]) {
                int height = h[st.top()];
                st.pop();
                int width = st.empty() ? i : (i - st.top() - 1);
                max_area = max(max_area, height * width);
            }
            st.push(i);
        }
    }

    cout << max_area << "\n";
    return 0;
}
