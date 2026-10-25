#include <iostream>
#include <vector>
#include <stack>
#include <algorithm>

using namespace std;

class MinStack {
private:
    stack<pair<long long, long long>> st;

public:
    void push(long long val) {
        long long current_min = st.empty() ? val : min(val, st.top().second);
        st.push({val, current_min});
    }

    long long pop() {
        if (st.empty()) return -1;
        long long val = st.top().first;
        st.pop();
        return val;
    }

    long long top() {
        if (st.empty()) return -1;
        return st.top().first;
    }

    long long getMin() {
        if (st.empty()) return -1;
        return st.top().second;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int q;
    if (!(cin >> q)) return 0;

    MinStack ms;
    vector<long long> out;

    while (q--) {
        int type;
        cin >> type;
        if (type == 1) {
            long long x;
            cin >> x;
            ms.push(x);
        } else if (type == 2) {
            out.push_back(ms.pop());
        } else if (type == 3) {
            out.push_back(ms.top());
        } else if (type == 4) {
            out.push_back(ms.getMin());
        }
    }

    if (out.empty()) {
        cout << "EMPTY\n";
    } else {
        for (size_t i = 0; i < out.size(); ++i) {
            if (i > 0) cout << " ";
            cout << out[i];
        }
        cout << "\n";
    }

    return 0;
}
