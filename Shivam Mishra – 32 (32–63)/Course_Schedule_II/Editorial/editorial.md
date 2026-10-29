
# Editorial: Course Schedule II

## 1. Problem Overview

We are given `N` courses numbered from `0` to `N-1` and a list of prerequisite pairs `arr`.

Each pair `[a, b]` means that course `b` must be completed before course `a`.

Our task is to find a valid ordering in which all courses can be completed.

If no valid ordering exists because of cyclic dependencies, we must return an empty array.

This problem can be solved using **Topological Sorting** on a directed graph.

## 2. Approach

### Step 1: Build the Directed Graph

Represent each course as a vertex in a directed graph.

For every prerequisite pair `[a, b]`, create a directed edge:

`b -> a`

This edge indicates that course `b` must be completed before course `a`.

Also, maintain an `indegree` array, where `indegree[i]` represents the number of prerequisites that must be completed before course `i`.

For every edge `b -> a`, increment `indegree[a]`.

### Step 2: Initialize the Queue

Kahn's algorithm starts by identifying all vertices with indegree zero.

A vertex with indegree zero has no remaining prerequisites and can be completed immediately.

Insert all such vertices into a queue.

### Step 3: Perform BFS-Based Topological Sorting

While the queue is not empty:

1. Remove a course from the front of the queue.
2. Add that course to the topological ordering.
3. Visit all its neighboring courses.
4. Decrease the indegree of each neighboring course by one.
5. If a neighbor's indegree becomes zero, add it to the queue.

This process simulates completing courses and gradually removing their prerequisite dependencies.

### Step 4: Detect Cycles

After processing the graph, check the number of courses in the resulting ordering.

- If the ordering contains all `N` courses, return the ordering.
- If fewer than `N` courses were processed, the graph contains a cycle. Return an empty array.

A cycle makes it impossible to satisfy every prerequisite.

## 3. Algorithm

1. Create an adjacency list of size `N`.
2. Initialize an indegree array with zeros.
3. For every pair `[a, b]`, add the edge `b -> a` and increment `indegree[a]`.
4. Push all courses with indegree zero into a queue.
5. Initialize an empty answer array.
6. Process the queue using Kahn's algorithm.
7. If the answer contains all `N` courses, return it; otherwise, return an empty array.

## 4. Example Walkthrough

**Input:**

```text
N = 4
arr = [[1,0], [2,1], [3,2]]
```

The graph contains these edges:

```text
0 -> 1
1 -> 2
2 -> 3
```

Initially, the indegree array is:

```text
indegree = [0, 1, 1, 1]
```

Only course `0` has indegree zero, so the initial queue is `[0]`.

| Operation | Queue | Result |
|---|---|---|
| Remove course 0 | `[1]` | `[0]` |
| Remove course 1 | `[2]` | `[0, 1]` |
| Remove course 2 | `[3]` | `[0, 1, 2]` |
| Remove course 3 | `[]` | `[0, 1, 2, 3]` |

All four courses are processed, so the final answer is:

```text
[0, 1, 2, 3]
```

## 5. Cycle Detection Example

Consider the following prerequisite pairs:

```text
N = 2
arr = [[1,0], [0,1]]
```

The graph contains:

```text
0 -> 1
1 -> 0
```

Both courses have indegree one.

Therefore, the initial queue is empty. No course can be completed first.

Since fewer than `N` courses are processed, the graph contains a cycle, and the answer is:

```text
[]
```

## 6. Complexity Analysis

Let:

- `V` be the number of vertices (courses), equal to `N`.
- `E` be the number of prerequisite pairs.

### Time Complexity: O(V + E)

- Building the adjacency list takes `O(V + E)` time.
- Initializing the queue takes `O(V)` time.
- Each vertex is processed at most once.
- Each directed edge is examined once.

Therefore, the total time complexity is **O(V + E)**.

### Space Complexity: O(V + E)

- The adjacency list requires `O(V + E)` space.
- The indegree array requires `O(V)` space.
- The queue and answer array each require up to `O(V)` space.

Therefore, the total auxiliary space complexity is **O(V + E)**.

## 7. C++ Implementation

The reference implementation is located at:

`Solution/solution.cpp`

```cpp
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    vector<int> findOrder(int N, vector<vector<int>>& arr) {
        vector<vector<int>> adj(N);
        vector<int> indegree(N, 0);

        // Build the graph and calculate indegrees.
        for (const auto& edge : arr) {
            int a = edge[0];
            int b = edge[1];

            adj[b].push_back(a);
            indegree[a]++;
        }

        queue<int> q;

        // Add courses with no prerequisites.
        for (int i = 0; i < N; i++) {
            if (indegree[i] == 0) {
                q.push(i);
            }
        }

        vector<int> order;

        // Kahn's BFS topological sort.
        while (!q.empty()) {
            int course = q.front();
            q.pop();

            order.push_back(course);

            for (int next : adj[course]) {
                indegree[next]--;

                if (indegree[next] == 0) {
                    q.push(next);
                }
            }
        }

        // If some courses remain, a cycle exists.
        if ((int)order.size() != N) {
            return {};
        }

        return order;
    }
};
```

## 8. Implementation Notes

- The graph must contain an edge `b -> a` for every pair `[a, b]`.
- The indegree of course `a` increases because of its prerequisite `b`.
- Use a queue to implement Kahn's BFS-based topological sort.
- An empty queue before all courses are processed does not always mean a cycle; the definitive check is whether fewer than `N` courses were processed.
- Multiple valid topological orderings may exist. Any ordering satisfying every prerequisite is correct.
- If the platform uses a provided driver, submit the `Solution` class according to its expected function signature rather than adding an independent `main()` function.

## 9. Input and Output Format

The local test files follow the format documented in:

`Problem.md/course_schedule_ii.md`

Each input file contains:

```text
N M
a1 b1
a2 b2
...
aM bM
```

Here, `N` is the number of courses and `M` is the number of prerequisite pairs.

Each output file contains a valid course ordering or `[]` when no valid ordering exists.

## 10. Conclusion

Course Schedule II is a classic directed graph problem that can be solved using topological sorting.

Kahn's algorithm repeatedly processes courses whose prerequisites have already been satisfied. If every course is processed, a valid ordering exists; otherwise, a cycle prevents completion.

The solution runs in **O(V + E) time and O(V + E) space**, making it efficient for large graphs.
```

