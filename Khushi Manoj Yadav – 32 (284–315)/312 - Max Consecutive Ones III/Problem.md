### Problem 312: Max Consecutive Ones III
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Array

**Problem Statement**
Given a binary array `nums` and an integer `k`, return the maximum number of consecutive `1`'s in the array if you can flip at most `k` `0`'s.

**Input Format**
- The first line contains two integers `N` and `K`, representing the size of the array and the maximum allowed flips.
- The second line contains `N` space-separated integers (`0` or `1`).

**Output Format**
- Print a single integer representing the maximum consecutive 1's obtainable.

**Constraints**
- `1 <= N <= 10^5`
- `0 <= K <= N`
- `nums[i]` is either `0` or `1`.

**Example**
**Input:**
```text
11 2
1 1 1 0 0 0 1 1 1 1 0
```
**Output:**
```text
6
```
