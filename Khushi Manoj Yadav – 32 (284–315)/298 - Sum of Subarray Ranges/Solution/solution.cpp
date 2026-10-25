#include <iostream>
#include <vector>
#include <stack>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n;
    if (!(cin >> n)) return 0;

    vector<long long> a(n);
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }

    vector<int> pge(n, -1), nge(n, n);
    vector<int> ple(n, -1), nle(n, n);
    stack<int> st;

    // Maximum boundaries
    for (int i = 0; i < n; ++i) {
        while (!st.empty() && a[st.top()] <= a[i]) st.pop();
        pge[i] = st.empty() ? -1 : st.top();
        st.push(i);
    }
    while (!st.empty()) st.pop();

    for (int i = n - 1; i >= 0; --i) {
        while (!st.empty() && a[st.top()] < a[i]) st.pop();
        nge[i] = st.empty() ? n : st.top();
        st.push(i);
    }
    while (!st.empty()) st.pop();

    // Minimum boundaries
    for (int i = 0; i < n; ++i) {
        while (!st.empty() && a[st.top()] >= a[i]) st.pop();
        ple[i] = st.empty() ? -1 : st.top();
        st.push(i);
    }
    while (!st.empty()) st.pop();

    for (int i = n - 1; i >= 0; --i) {
        while (!st.empty() && a[st.top()] > a[i]) st.pop();
        nle[i] = st.empty() ? n : st.top();
        st.push(i);
    }

    long long ans = 0;
    for (int i = 0; i < n; ++i) {
        long long max_cnt = (long long)(i - pge[i]) * (nge[i] - i);
        long long min_cnt = (long long)(i - ple[i]) * (nle[i] - i);
        ans += a[i] * (max_cnt - min_cnt);
    }

    cout << ans << "\n";
    return 0;
}
