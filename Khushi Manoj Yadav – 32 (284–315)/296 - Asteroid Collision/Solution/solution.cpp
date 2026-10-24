#include <iostream>
#include <vector>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int n;
    if (!(cin >> n)) return 0;

    vector<int> a(n);
    for (int i = 0; i < n; ++i) {
        cin >> a[i];
    }

    vector<int> st;
    for (int x : a) {
        bool alive = true;
        while (alive && x < 0 && !st.empty() && st.back() > 0) {
            if (st.back() < -x) {
                st.pop_back();
            } else if (st.back() == -x) {
                st.pop_back();
                alive = false;
            } else {
                alive = false;
            }
        }
        if (alive) {
            st.push_back(x);
        }
    }

    if (st.empty()) {
        cout << "EMPTY\n";
    } else {
        for (size_t i = 0; i < st.size(); ++i) {
            if (i > 0) cout << " ";
            cout << st[i];
        }
        cout << "\n";
    }

    return 0;
}
