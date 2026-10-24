### Problem 228: Rotten Oranges
**Difficulty:** Medium | **Tags:** Breadth First Search, Multi-Source BFS, Queue, Matrix, Graph Traversal

**Problem Statement**

Given an `n x m` grid, where each cell has one of the following values:

* `2` - represents a Rotten Orange
* `1` - represents a Fresh Orange
* `0` - represents an Empty Cell

Every minute, if a fresh orange is adjacent to a rotten orange in 4-direction (up, down, right, and left), it becomes rotten.

Return the minimum number of minutes required such that none of the cells has a Fresh Orange. If it is not possible, return `-1`.

**Input Format**

* The first line contains two integers `n` and `m`, representing the number of rows and columns in the grid.
* Each of the next `n` lines contains `m` integers. Each integer is either `0`, `1`, or `2`, representing an empty cell, fresh orange, or rotten orange respectively.

**Output Format**

* Print one integer: the minimum number of minutes required to rot all fresh oranges.
* If at least one fresh orange can never become rotten, print `-1`.

**Constraints**

* `1 ≤ n, m ≤ 500`
* `grid[i][j]` is either `0`, `1`, or `2`

**Example 1**

**Input:**
```text
3 3
2 1 1
0 1 1
1 0 1
```

**Output:**
```text
-1
```

**Explanation:**

The fresh orange at position `(3,1)` cannot be reached by any rotten orange through the allowed four directions. Therefore, it is impossible to rot every fresh orange, so the answer is `-1`.

**Example 2**

**Input:**
```text
3 3
2 1 1
1 1 0
0 1 1
```

**Output:**
```text
4
```

**Explanation:**

The rotten orange spreads to adjacent fresh oranges every minute. Starting from the initially rotten orange, all reachable fresh oranges become rotten after 4 minutes. Therefore, the minimum required time is `4`.

**Approach:**

This problem can be solved using **Multi-Source BFS**.

* Put every initially rotten orange into a queue with time `0`.
* Count the total number of fresh oranges.
* Process the queue level by level.
* For every rotten orange, check its four neighbouring cells.
* If a neighbouring cell contains a fresh orange, make it rotten, decrease the fresh-orange count, and insert it into the queue with time increased by `1`.
* Keep track of the maximum time used.
* If all fresh oranges become rotten, print that maximum time. Otherwise, print `-1`.

Because all initially rotten oranges are inserted into the queue at the beginning, they spread simultaneously. BFS therefore finds the minimum time required.

**Complexity:**

* Time Complexity: `O(n × m)`
* Space Complexity: `O(n × m)`
