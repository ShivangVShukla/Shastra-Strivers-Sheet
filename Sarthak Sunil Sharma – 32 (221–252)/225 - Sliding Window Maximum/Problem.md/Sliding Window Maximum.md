### Problem 225: Sliding Window Maximum
**Difficulty:** Hard | **Tags:** Array, Deque, Sliding Window, Monotonic Queue

**Problem Statement**
Given an integer array `nums` and an integer `k`, there is a sliding window of size `k` that moves from the left to the right of the array by one position at a time. Return the maximum value in every window.

**Input Format**
* The first line contains two integers `N` and `K`, representing the array length and window size.
* The second line contains `N` space-separated integers representing `nums`.

**Output Format**
* Print `N - K + 1` space-separated integers: the maximum value of each sliding window from left to right.

**Constraints**
* 1 ≤ N ≤ 100000
* 1 ≤ K ≤ N
* -10000 ≤ nums[i] ≤ 10000

**Example**
**Input:**
```text
8 3
1 3 -1 -3 5 3 6 7
```

**Output:**
```text
3 3 5 5 6 7
```

**Explanation:**
The windows are `[1, 3, -1]`, `[3, -1, -3]`, `[-1, -3, 5]`, `[-3, 5, 3]`, `[5, 3, 6]`, and `[3, 6, 7]`. Their maximum values are `3, 3, 5, 5, 6, 7`.

**Expected Complexity**
* Time: O(N)
* Space: O(K)
