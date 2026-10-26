
# Course Schedule II

## Problem Statement

There are a total of `N` tasks, labeled from `0` to `N-1`.

Given an array `arr`, where `arr[i] = [a, b]` indicates that you must take course `b` first if you want to take course `a`, find the order of tasks you should pick to finish all tasks.

If no such ordering exists, return an empty array.

Since multiple valid answers are possible, you can return any answer.

**Note:** The output visible will be `1` if your solution is correct, otherwise `-1`.

## Example 1

**Input:**

```text
N = 4
arr = [[1,0],[2,1],[3,2]]
```

**Output:**

```text
[0, 1, 2, 3]
```

**Explanation:**

First, finish task 0, as it has no prerequisites. Then, finish task 1, since it depends only on task 0. After that, finish task 2, since it depends only on task 1. Finally, finish task 3, since it depends only on task 2.

## Example 2

**Input:**

```text
N = 4
arr = [[0,1],[3,2],[1,3],[3,0]]
```

**Output:**

```text
[]
```

**Explanation:**

It is impossible to finish all the tasks because the prerequisites create a cycle.

For pair `[0,1]`, task 1 must be completed before task 0.

For pair `[3,2]`, task 2 must be completed before task 3.

For pair `[1,3]`, task 3 must be completed before task 1.

These dependencies require the ordering:

`2 -> 3 -> 1 -> 0`

However, pair `[3,0]` requires task 0 to be completed before task 3, creating a cycle. Therefore, no valid ordering exists.

## Approach: Topological Sort (Kahn's Algorithm)

1. Create an adjacency list to represent the prerequisite relationships.
2. Calculate the indegree of every course.
3. Insert all courses with indegree zero into a min-heap.
4. Remove the smallest available course and add it to the answer.
5. Reduce the indegree of its neighboring courses.
6. When a neighboring course reaches indegree zero, insert it into the heap.
7. If the answer contains all `N` courses, return it. Otherwise, return an empty array because a cycle exists.

## Complexity Analysis

Let `N` be the number of courses and `M` be the number of prerequisite pairs.

- **Time Complexity:** `O((N + M) log N)` using a min-heap.
- **Space Complexity:** `O(N + M)` for the adjacency list, indegree array, heap, and answer.

## Local Test Format

Each input file contains:

```text
N M
a1 b1
a2 b2
...
```

Here, `N` is the number of courses and `M` is the number of prerequisite pairs.

Each output file contains the expected course ordering, or `[]` if no valid ordering exists.

