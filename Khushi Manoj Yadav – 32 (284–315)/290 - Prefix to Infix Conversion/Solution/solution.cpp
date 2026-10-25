#include <iostream>
#include <string>
#include <stack>
#include <cctype>

using namespace std;

string prefixToInfix(const string& s) {
    stack<string> st;
    for (int i = (int)s.size() - 1; i >= 0; --i) {
        char c = s[i];
        if (isalnum(c)) {
            st.push(string(1, c));
        } else {
            if (st.size() >= 2) {
                string op1 = st.top(); st.pop();
                string op2 = st.top(); st.pop();
                st.push("(" + op1 + c + op2 + ")");
            }
        }
    }
    return st.empty() ? "" : st.top();
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s;
    if (cin >> s) {
        cout << prefixToInfix(s) << "\n";
    }
    return 0;
}
