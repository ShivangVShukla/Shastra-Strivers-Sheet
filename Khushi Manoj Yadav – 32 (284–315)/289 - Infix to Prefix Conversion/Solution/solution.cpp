#include <iostream>
#include <string>
#include <stack>
#include <algorithm>
#include <cctype>

using namespace std;

int prec(char c) {
    if (c == '^') return 3;
    if (c == '*' || c == '/') return 2;
    if (c == '+' || c == '-') return 1;
    return -1;
}

string infixToPrefix(string s) {
    reverse(s.begin(), s.end());
    for (char& c : s) {
        if (c == '(') c = ')';
        else if (c == ')') c = '(';
    }

    string res = "";
    stack<char> st;
    for (char c : s) {
        if (isalnum(c)) {
            res += c;
        } else if (c == '(') {
            st.push(c);
        } else if (c == ')') {
            while (!st.empty() && st.top() != '(') {
                res += st.top();
                st.pop();
            }
            if (!st.empty() && st.top() == '(') st.pop();
        } else {
            while (!st.empty() && (prec(st.top()) > prec(c) || (prec(st.top()) == prec(c) && c == '^'))) {
                res += st.top();
                st.pop();
            }
            st.push(c);
        }
    }
    while (!st.empty()) {
        res += st.top();
        st.pop();
    }
    reverse(res.begin(), res.end());
    return res;
}

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);
    string s;
    if (cin >> s) {
        cout << infixToPrefix(s) << "\n";
    }
    return 0;
}
