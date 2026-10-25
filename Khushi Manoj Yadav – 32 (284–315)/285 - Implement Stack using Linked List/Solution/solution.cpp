#include <iostream>
#include <vector>

using namespace std;

struct Node {
    long long val;
    Node* next;
    Node(long long v, Node* n = nullptr) : val(v), next(n) {}
};

class MyStack {
private:
    Node* head;
    int sz;

public:
    MyStack() : head(nullptr), sz(0) {}

    ~MyStack() {
        while (head) {
            Node* temp = head;
            head = head->next;
            delete temp;
        }
    }

    void push(long long x) {
        head = new Node(x, head);
        sz++;
    }

    long long pop() {
        if (!head) return -1;
        long long val = head->val;
        Node* temp = head;
        head = head->next;
        delete temp;
        sz--;
        return val;
    }

    long long top() {
        if (!head) return -1;
        return head->val;
    }

    int size() {
        return sz;
    }

    int empty() {
        return head == nullptr ? 1 : 0;
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int q;
    if (!(cin >> q)) return 0;

    MyStack st;
    vector<long long> out;

    while (q--) {
        int type;
        cin >> type;
        if (type == 1) {
            long long x;
            cin >> x;
            st.push(x);
        } else if (type == 2) {
            out.push_back(st.pop());
        } else if (type == 3) {
            out.push_back(st.top());
        } else if (type == 4) {
            out.push_back(st.size());
        } else if (type == 5) {
            out.push_back(st.empty());
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
