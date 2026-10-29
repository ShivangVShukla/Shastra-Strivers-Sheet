#include <bits/stdc++.h>
using namespace std;

class LRUCache {
    struct Node {
        int key, value;
        Node *prev, *next;

        Node(int k = 0, int v = 0)
            : key(k), value(v), prev(nullptr), next(nullptr) {}
    };

    int capacity;
    unordered_map<int, Node*> nodes;
    Node *head, *tail;

    void detach(Node* node) {
        node->prev->next = node->next;
        node->next->prev = node->prev;
    }

    void addAfterHead(Node* node) {
        node->next = head->next;
        node->prev = head;
        head->next->prev = node;
        head->next = node;
    }

public:
    LRUCache(int capacity) {
        this->capacity = capacity;
        head = new Node();
        tail = new Node();

        head->next = tail;
        tail->prev = head;
    }

    int get(int key_) {
        auto it = nodes.find(key_);

        if (it == nodes.end())
            return -1;

        Node* node = it->second;
        detach(node);
        addAfterHead(node);

        return node->value;
    }

    void put(int key_, int value) {
        auto it = nodes.find(key_);

        if (it != nodes.end()) {
            Node* node = it->second;
            node->value = value;
            detach(node);
            addAfterHead(node);
            return;
        }

        Node* node = new Node(key_, value);
        nodes[key_] = node;
        addAfterHead(node);

        if ((int)nodes.size() > capacity) {
            Node* lru = tail->prev;
            detach(lru);
            nodes.erase(lru->key);
            delete lru;
        }
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int capacity, count;
    cin >> capacity >> count;

    LRUCache cache(capacity);

    for (int i = 0; i < count; i++) {
        int type, key;
        cin >> type >> key;

        if (type == 1) {
            int value;
            cin >> value;
            cache.put(key, value);
            cout << "null";
        } else {
            cout << cache.get(key);
        }

        if (i + 1 < count)
            cout << ' ';
    }

    cout << '\n';
    return 0;
}