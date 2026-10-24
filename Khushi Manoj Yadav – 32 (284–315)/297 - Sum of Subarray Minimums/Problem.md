### Problem 297: Sum of Subarray Minimums
**Difficulty:** Medium | **Tags:** Monotonic Stack, Array

**Problem Statement**
Given an array of integers `arr`, find the sum of `min(b)`, where `b` ranges over every contiguous subarray of `arr`.
Since the answer may be large, return the answer modulo `10^9 + 7`.

**Input Format**
- The first line contains an integer `N`, the size of the array.
- The second line contains `N` space-separated integers representing `arr`.

**Output Format**
- Print a single integer representing the sum of subarray minimums modulo `10^9 + 7`.

**Constraints**
- `1 <= N <= 10^5`
- `1 <= arr[i] <= 10^5`

**Example**
**Input:**
```text
4
3 1 2 4
```
**Output:**
```text
17
```
