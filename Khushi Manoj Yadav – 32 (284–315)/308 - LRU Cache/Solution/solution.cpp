#include <iostream>
#include <unordered_map>
#include <vector>

using namespace std;

struct Node {
    int key;
    long long val;
    Node *prev, *next;
    Node(int k, long long v) : key(k), val(v), prev(nullptr), next(nullptr) {}
};

class LRUCache {
private:
    int cap;
    unordered_map<int, Node*> mp;
    Node *head, *tail;

    void remove(Node* node) {
        node->prev->next = node->next;
        node->next->prev = node->prev;
    }

    void insertFront(Node* node) {
        node->next = head->next;
        node->prev = head;
        head->next->prev = node;
        head->next = node;
    }

public:
    LRUCache(int capacity) : cap(capacity) {
        head = new Node(-1, -1);
        tail = new Node(-1, -1);
        head->next = tail;
        tail->prev = head;
    }

    ~LRUCache() {
        Node* cur = head;
        while (cur) {
            Node* temp = cur;
            cur = cur->next;
            delete temp;
        }
    }

    long long get(int key) {
        if (mp.find(key) == mp.end()) return -1;
        Node* node = mp[key];
        remove(node);
        insertFront(node);
        return node->val;
    }

    void put(int key, long long val) {
        if (mp.find(key) != mp.end()) {
            Node* node = mp[key];
            node->val = val;
            remove(node);
            insertFront(node);
        } else {
            if ((int)mp.size() >= cap) {
                Node* lru = tail->prev;
                remove(lru);
                mp.erase(lru->key);
                delete lru;
            }
            Node* node = new Node(key, val);
            insertFront(node);
            mp[key] = node;
        }
    }
};

int main() {
    ios_base::sync_with_stdio(false);
    cin.tie(NULL);

    int cap, q;
    if (!(cin >> cap >> q)) return 0;

    LRUCache cache(cap);
    vector<long long> out;

    while (q--) {
        int type;
        cin >> type;
        if (type == 1) {
            int k;
            long long v;
            cin >> k >> v;
            cache.put(k, v);
        } else if (type == 2) {
            int k;
            cin >> k;
            out.push_back(cache.get(k));
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
