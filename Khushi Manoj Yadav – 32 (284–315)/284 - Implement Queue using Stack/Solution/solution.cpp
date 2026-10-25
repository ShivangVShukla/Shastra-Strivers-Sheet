#include <iostream>
#include <stack>
#include <vector>

using namespace std;

class MyQueue {
private:
    stack<long long> s1, s2;

    void transfer() {
        if (s2.empty()) {
            while (!s1.empty()) {
                s2.push(s1.top());
                s1.pop();
            }
        }
    }

public:
    void push(long long x) {
        s1.push(x);
    }

    long long pop() {
        transfer();
        if (s2.empty()) return -1;
        long long val = s2.top();
        s2.pop();
        return val;
    }

    long long peek() {
        transfer();
        if (s2.empty()) return -1;
        return s2.top();
    }

    int empty() {
        return (s1.empty() && s2.empty()) ? 1 : 0;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int q;
    if (!(cin >> q)) return 0;

    MyQueue que;
    vector<long long> out;

    while (q--) {
        int type;
        cin >> type;
        if (type == 1) {
            long long x;
            cin >> x;
            que.push(x);
        } else if (type == 2) {
            out.push_back(que.pop());
        } else if (type == 3) {
            out.push_back(que.peek());
        } else if (type == 4) {
            out.push_back(que.empty());
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
