#include <iostream>
#include <vector>
#include <stack>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n;
    if (!(cin >> n)) return 0;

    vector<long long> price(n);
    for (int i = 0; i < n; ++i) {
        cin >> price[i];
    }

    stack<int> st;
    vector<int> span(n);

    for (int i = 0; i < n; ++i) {
        while (!st.empty() && price[st.top()] <= price[i]) {
            st.pop();
        }
        span[i] = st.empty() ? (i + 1) : (i - st.top());
        st.push(i);
    }

    for (int i = 0; i < n; ++i) {
        if (i > 0) cout << " ";
        cout << span[i];
    }
    cout << "\n";

    return 0;
}
