# Editorial: Clone Graph

## Difficulty
Medium

## Prerequisites
- Graph
- Depth-First Search (DFS)
- Breadth-First Search (BFS)
- Hash Map / Unordered Map
- Deep Copy and Pointer Manipulation

## Problem Statement

Given an undirected, connected graph, return a **deep copy** of the graph.

Each node contains:
- An integer value.
- A list of pointers to its neighboring nodes.

The cloned graph must have the same vertices, edges, and adjacency relationships as the original graph. However, every cloned node must be a newly allocated object, independent of the original graph.

### Input Format

- The first line contains two integers, `n` and `m`, representing the number of vertices and edges.
- The next `m` lines each contain two integers, `u` and `v`, representing an undirected edge between vertices `u` and `v`.
- Vertices are numbered from `1` to `n`.

### Output Format

Print the adjacency list of every cloned node in ascending order of vertex value.

Each line corresponds to a vertex, starting from vertex `1`. Print its neighbors in sorted order.

### Example

**Input**
```text
4 4
1 2
1 4
2 3
3 4
```

**Output**
```text
2 2 4
2 1 3
2 2 4
2 1 3
```

**Explanation**

The graph contains four vertices and four undirected edges:

- Vertex `1` is connected to vertices `2` and `4`.
- Vertex `2` is connected to vertices `1` and `3`.
- Vertex `3` is connected to vertices `2` and `4`.
- Vertex `4` is connected to vertices `1` and `3`.

The cloned graph preserves every vertex value and edge. Its nodes are separate objects from the original graph, so modifying the clone does not modify the original.

## Explanation and Approach

### 1. Understanding Deep Copy

A deep copy creates an entirely separate graph with the same structure as the original.

A shallow copy would copy references to the original neighboring nodes. That is incorrect because the copied graph would still depend on the original graph.

For a valid deep copy:
- Every original node must have exactly one corresponding cloned node.
- Each cloned node must have the same value as its original.
- Every original edge must have a corresponding edge between cloned nodes.
- No cloned node should point to an original node.

### 2. Why DFS Is Suitable

Depth-First Search explores a graph by visiting a node and recursively processing its neighbors.

However, graphs can contain cycles. For example, if vertex `1` is connected to vertex `2`, and vertex `2` is connected back to vertex `1`, a naive recursive implementation could repeatedly visit the same vertices.

To prevent this, maintain a hash map:

`original node → cloned node`

This map serves two purposes:
1. It ensures that each original node is cloned only once.
2. It allows cycles and repeated references to be handled correctly.

### 3. Algorithm

1. If the input node is `nullptr`, return `nullptr`.
2. Check whether the current original node already exists in the map.
3. If it exists, return its previously created clone.
4. Otherwise, create a new node with the same value as the original.
5. Store the original-to-clone mapping **before** visiting any neighbors.
6. Traverse all neighbors of the original node using DFS.
7. Recursively clone each neighbor and append its clone to the current cloned node's neighbor list.
8. Return the cloned node.

**Important:** Store the mapping before processing neighbors. This prevents infinite recursion when the graph contains cycles.

## C++ Solution

**File:** `Solution/solution.cpp`

The implementation below uses a `Node` class and a `Solution` class. The `cloneGraph()` method performs the deep copy using DFS.

```cpp
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
        // Handle an empty graph.
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

    int n, m;
    cin >> n >> m;

    if (n == 0) {
        return 0;
    }
    vector<Node*> nodes(n + 1);

    for (int i = 1; i <= n; i++) {
        nodes[i] = new Node(i);
    }

    for (int i = 0; i < m; i++) {
        int u, v;
        cin >> u >> v;

        nodes[u]->neighbors.push_back(nodes[v]);
        nodes[v]->neighbors.push_back(nodes[u]);
    }

    Solution solution;
    Node* clone = solution.cloneGraph(nodes[1]);
    vector<Node*> clonedNodes(n + 1, nullptr);
    vector<bool> visited(n + 1, false);
    queue<Node*> q;

    q.push(clone);
    visited[clone->val] = true;

    while (!q.empty()) {
        Node* current = q.front();
        q.pop();

        clonedNodes[current->val] = current;

        for (Node* neighbor : current->neighbors) {
            if (!visited[neighbor->val]) {
                visited[neighbor->val] = true;
                q.push(neighbor);
            }
        }
    }

    for (int i = 1; i <= n; i++) {
        vector<int> adjacent;

        for (Node* neighbor : clonedNodes[i]->neighbors) {
            adjacent.push_back(neighbor->val);
        }

        sort(adjacent.begin(), adjacent.end());

        cout << adjacent.size();

        for (int value : adjacent) {
            cout << " " << value;
        }

        cout << '\n';
    }

    return 0;
}
```

## Dry Run

Consider the example graph:

```text
4 4
1 2
1 4
2 3
3 4
```

The original adjacency lists are:

| Vertex | Neighbors |
|---|---|
| 1 | 2, 4 |
| 2 | 1, 3 |
| 3 | 2, 4 |
| 4 | 1, 3 |

Starting DFS from vertex `1`:

| Step | Operation | Mapping created |
|---|---|---|
| 1 | Clone vertex 1 | `1 → clone(1)` |
| 2 | Visit neighbor 2 | `2 → clone(2)` |
| 3 | Visit neighbor 3 | `3 → clone(3)` |
| 4 | Visit neighbor 4 | `4 → clone(4)` |
| 5 | Encounter previously visited nodes | Reuse their clones |

When DFS encounters an already cloned node, the map returns the existing clone instead of creating another one. This also terminates recursion when a cycle is encountered.

The resulting cloned graph has the same adjacency relationships as the original graph.

## Complexity Analysis

Let:
- \(V\) be the number of vertices.
- \(E\) be the number of edges.

### Time Complexity: \(O(V + E)\)

Every vertex is cloned once, and every adjacency-list entry is processed once. For an undirected graph, each edge normally appears in two adjacency lists, which does not change the asymptotic complexity.

Sorting adjacency lists for output adds up to \(O(\sum_{v} d_v \log d_v)\), where \(d_v\) is the degree of vertex \(v\). Therefore, the complete program including sorted output has a worst-case time complexity of \(O(V + E + V\log V)\).

### Auxiliary Space Complexity: \(O(V)\)

The DFS mapping stores one entry per vertex. The recursive call stack can also grow to \(O(V)\).

The cloned graph itself requires \(O(V + E)\) space and is considered output storage rather than auxiliary space.

## Edge Cases

1. **Single vertex with no edges:** The clone contains one vertex with an empty adjacency list.
2. **Cycle:** The mapping prevents infinite recursion.
3. **Multiple neighbors:** Each neighbor is cloned and connected to the corresponding cloned node.
4. **Repeated paths to the same vertex:** The same clone is reused, preserving graph structure.
5. **Empty graph:** The implementation handles `n = 0` by returning without printing any adjacency lists.
6. **Large graph:** The algorithm runs in linear time for cloning, although recursive DFS may exhaust the call stack on extremely deep graphs.

## Conclusion

Graph cloning is a fundamental application of DFS and hash maps. The key idea is to create a cloned node and record its mapping before exploring its neighbors. This ensures that every vertex is copied exactly once and that cycles are handled safely.

The resulting algorithm clones the graph in \(O(V + E)\) time, while preserving vertex values, edges, and adjacency relationships in a fully independent graph.

**Reference:** TakeUForward — Clone Graph: https://takeuforward.org/practice/dsa/clone-graph
