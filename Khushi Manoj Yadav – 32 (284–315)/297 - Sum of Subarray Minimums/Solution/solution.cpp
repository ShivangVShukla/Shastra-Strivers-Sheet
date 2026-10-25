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

    const long long MOD = 1000000007;
    vector<int> ple(n, -1), nle(n, n);
    stack<int> st;

    for (int i = 0; i < n; ++i) {
        while (!st.empty() && a[st.top()] >= a[i]) {
            st.pop();
        }
        ple[i] = st.empty() ? -1 : st.top();
        st.push(i);
    }

    while (!st.empty()) st.pop();

    for (int i = n - 1; i >= 0; --i) {
        while (!st.empty() && a[st.top()] > a[i]) {
            st.pop();
        }
        nle[i] = st.empty() ? n : st.top();
        st.push(i);
    }

    long long ans = 0;
    for (int i = 0; i < n; ++i) {
        long long left = i - ple[i];
        long long right = nle[i] - i;
        long long contrib = (a[i] % MOD) * ((left * right) % MOD) % MOD;
        ans = (ans + contrib) % MOD;
    }

    cout << ans << "\n";
    return 0;
}
