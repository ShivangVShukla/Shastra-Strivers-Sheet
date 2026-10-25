#include <iostream>
#include <string>
#include <vector>

using namespace std;

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    string num;
    int k;
    if (!(cin >> num >> k)) return 0;

    string st = "";
    for (char c : num) {
        while (k > 0 && !st.empty() && st.back() > c) {
            st.pop_back();
            k--;
        }
        st.push_back(c);
    }

    while (k > 0 && !st.empty()) {
        st.pop_back();
        k--;
    }

    // Strip leading zeroes
    int start = 0;
    while (start < (int)st.size() && st[start] == '0') {
        start++;
    }

    string res = st.substr(start);
    if (res.empty()) res = "0";

    cout << res << "\n";
    return 0;
}
