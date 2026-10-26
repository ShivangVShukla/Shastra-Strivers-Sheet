
#include <bits/stdc++.h>
using namespace std;

class Node {
public:
    int val;
    vector<Node*> neighbors;

    Node(int value) {
        val = value;
    }
};

class Solution {
private:
    unordered_map<Node*, Node*> cloned;

public:
    Node* cloneGraph(Node* node) {
        if (node == nullptr) {
            return nullptr;
        }

        if (cloned.count(node)) {
            return cloned[node];
        }

        Node* copy = new Node(node->val);
        cloned[node] = copy;

        for (Node* neighbor : node->neighbors) {
            copy->neighbors.push_back(cloneGraph(neighbor));
        }

        return copy;
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    if (n == 0) {
        cout << "[]\n";
        return 0;
    }

    vector<Node*> nodes(n + 1);

    for (int i = 1; i <= n; i++) {
        nodes[i] = new Node(i);
    }

    for (int i = 1; i <= n; i++) {
        int degree;
        cin >> degree;

        for (int j = 0; j < degree; j++) {
            int neighbor;
            cin >> neighbor;
            nodes[i]->neighbors.push_back(nodes[neighbor]);
        }
    }

    Solution solution;
    Node* clone = solution.cloneGraph(nodes[1]);

    // Print cloned graph in adjacency-list format.
    vector<Node*> order(n + 1, nullptr);
    vector<bool> visited(n + 1, false);
    queue<Node*> q;

    q.push(clone);
    visited[clone->val] = true;

    while (!q.empty()) {
        Node* current = q.front();
        q.pop();

        order[current->val] = current;

        for (Node* neighbor : current->neighbors) {
            if (!visited[neighbor->val]) {
                visited[neighbor->val] = true;
                q.push(neighbor);
            }
        }
    }

    for (int i = 1; i <= n; i++) {
        Node* current = order[i];

        cout << current->neighbors.size();

        for (Node* neighbor : current->neighbors) {
            cout << " " << neighbor->val;
        }

        cout << '\n';
    }

    return 0;
}
