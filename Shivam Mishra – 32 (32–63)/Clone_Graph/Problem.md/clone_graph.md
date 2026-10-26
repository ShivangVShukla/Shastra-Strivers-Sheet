
# Clone Graph

## Difficulty
Medium

## Prerequisites
- Graph
- Depth-First Search (DFS)
- Breadth-First Search (BFS)
- Hash Map
- Deep Copy

## Problem Statement

You are given a reference to a node in a connected undirected graph. Each node has an integer value `val` and a list of its neighboring nodes.

Return a **deep copy (clone)** of the graph.

The cloned graph must have the same structure and values as the original graph, but all nodes must be newly created objects. No node in the cloned graph should reference a node from the original graph.

Each node is defined as:

```java
class Node {
    public int val;
    public List<Node> neighbors;
}
```

The value of each node is unique and lies between `1` and `100`.

## Input Format

The graph is represented by an adjacency list `adjList`, where:

- `adjList[i]` contains the neighbors of node `i + 1`.
- Each neighbor is represented by its node value.
- The graph is undirected and connected.
- An empty adjacency list represents a node with no neighbors.

## Output Format

Return the adjacency list representation of the cloned graph.

The cloned graph must preserve all node values, edges, and neighbor relationships.

## Examples

### Example 1

**Input:**
```text
adjList = [[2,4],[1,3],[2,4],[1,3]]
```

**Output:**
```text
[[2,4],[1,3],[2,4],[1,3]]
```

**Explanation:**

There are four nodes in the graph:

- Node 1 connects to nodes 2 and 4.
- Node 2 connects to nodes 1 and 3.
- Node 3 connects to nodes 2 and 4.
- Node 4 connects to nodes 1 and 3.

The cloned graph contains the same four nodes and edges, but each node is a new object independent of the original graph.

### Example 2

**Input:**
```text
adjList = [[]]
```

**Output:**
```text
[[]]
```

**Explanation:**

The graph contains a single node with no neighbors. The cloned graph also contains one node with the same value and an empty neighbor list.

### Example 3

**Input:**
```text
adjList = [[2],[1]]
```

**Output:**
```text
[[2],[1]]
```

**Explanation:**

The graph contains two nodes connected by a single undirected edge. The clone preserves this connection using two newly created nodes.

## Constraints

- The number of nodes is between `1` and `100`.
- `1 <= Node.val <= 100`.
- Every node has a unique value.
- The graph is undirected and connected.
- There are no repeated edges or self-loops.
- Each node's neighbors are represented by a list of node references.

## Approach: Depth-First Search (DFS)

Use DFS to traverse the original graph and create a deep copy.

Maintain a hash map that maps each original node to its corresponding cloned node.

### Algorithm

1. If the input node is `null`, return `null`.
2. Check whether the original node already exists in the map.
3. If it exists, return the previously created clone.
4. Otherwise, create a new node with the same value.
5. Store the original node and its clone in the map **before traversing its neighbors**.
6. Recursively clone each neighbor.
7. Add each cloned neighbor to the current cloned node's neighbor list.
8. Return the cloned node.

### Why Is the Hash Map Necessary?

An undirected graph can contain cycles. For example, node 1 connects to node 2, and node 2 connects back to node 1.

Without a hash map, DFS could repeatedly create new copies of the same nodes and recurse indefinitely.

The map ensures that:
- Each original node is cloned exactly once.
- Cycles are handled correctly.
- Connections between cloned nodes are preserved.
- The cloned graph does not reference original nodes.

## Complexity Analysis

Let `V` be the number of vertices and `E` be the number of edges.

- **Time Complexity:** `O(V + E)`, because each vertex is cloned once and each edge is processed a constant number of times.
- **Auxiliary Space Complexity:** `O(V)` for the hash map and recursive DFS call stack.
- **Space Required for the Cloned Graph:** `O(V + E)`.

## Important Notes

- Create the cloned node before recursively processing its neighbors.
- Always reuse an existing clone when the original node is already present in the map.
- A deep copy must allocate new nodes rather than reuse the original nodes.
- The output adjacency list can look identical to the input even though the underlying node objects are different.

## Expected Result

Return a completely independent copy of the original connected undirected graph, preserving all node values and edges.
