#include <iostream>
#include <string>
#include <stack>
#include <cctype>

using namespace std;

string postfixToInfix(const string& s) {
    stack<string> st;
    for (char c : s) {
        if (isalnum(c)) {
            st.push(string(1, c));
        } else {
            if (st.size() >= 2) {
                string op2 = st.top(); st.pop();
                string op1 = st.top(); st.pop();
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
        cout << postfixToInfix(s) << "\n";
    }
    return 0;
}
