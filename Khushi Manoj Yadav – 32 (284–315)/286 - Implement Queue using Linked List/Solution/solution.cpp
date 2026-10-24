#include <iostream>
#include <vector>

using namespace std;

struct Node {
    long long val;
    Node* next;
    Node(long long v) : val(v), next(nullptr) {}
};

class MyQueue {
private:
    Node *head, *tail;
    int sz;

public:
    MyQueue() : head(nullptr), tail(nullptr), sz(0) {}

    ~MyQueue() {
        while (head) {
            Node* temp = head;
            head = head->next;
            delete temp;
        }
    }

    void push(long long x) {
        Node* node = new Node(x);
        if (!tail) {
            head = tail = node;
        } else {
            tail->next = node;
            tail = node;
        }
        sz++;
    }

    long long pop() {
        if (!head) return -1;
        long long val = head->val;
        Node* temp = head;
        head = head->next;
        if (!head) tail = nullptr;
        delete temp;
        sz--;
        return val;
    }

    long long front() {
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

    MyQueue qobj;
    vector<long long> out;

    while (q--) {
        int type;
        cin >> type;
        if (type == 1) {
            long long x;
            cin >> x;
            qobj.push(x);
        } else if (type == 2) {
            out.push_back(qobj.pop());
        } else if (type == 3) {
            out.push_back(qobj.front());
        } else if (type == 4) {
            out.push_back(qobj.size());
        } else if (type == 5) {
            out.push_back(qobj.empty());
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
