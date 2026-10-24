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

    vector<long long> res(n, -1);
    stack<long long> st;

    for (int i = n - 1; i >= 0; --i) {
        while (!st.empty() && st.top() <= a[i]) {
            st.pop();
        }
        if (!st.empty()) {
            res[i] = st.top();
        }
        st.push(a[i]);
    }

    for (int i = 0; i < n; ++i) {
        if (i > 0) cout << " ";
        cout << res[i];
    }
    cout << "\n";

    return 0;
}
