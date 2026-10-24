### Problem 310: Number of Greater Elements to the Right
**Difficulty:** Medium | **Tags:** Fenwick Tree, Coordinate Compression, Array

**Problem Statement**
Given an array `arr` of size `N` and `Q` queries, each specifying an index `idx` (0-indexed), return the number of elements strictly to the right of `idx` that are strictly greater than `arr[idx]`.

**Input Format**
- The first line contains an integer `N`, the size of the array.
- The second line contains `N` space-separated integers representing `arr`.
- The third line contains an integer `Q`, the number of queries.
- The fourth line contains `Q` space-separated query indices.

**Output Format**
- Print `Q` space-separated integers representing the answer for each query.

**Constraints**
- `1 <= N, Q <= 10^5`
- `-10^9 <= arr[i] <= 10^9`
- `0 <= query[i] < N`

**Example**
**Input:**
```text
8
3 4 2 7 5 8 10 6
2
0 5
```
**Output:**
```text
6 1
```
