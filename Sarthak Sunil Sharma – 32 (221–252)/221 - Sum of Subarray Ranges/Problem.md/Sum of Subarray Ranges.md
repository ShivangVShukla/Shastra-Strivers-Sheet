### Problem 221: Sum of Subarray Ranges
**Difficulty:** Medium | **Tags:** Stack and Queues, Monotonic Stack
**Problem Statement**
You are given an integer array `nums`. For every non-empty contiguous subarray, define its range as the difference between its maximum element and its minimum element. Return the sum of these ranges over all possible non-empty subarrays.

A subarray must contain consecutive elements and must contain at least one element.

**Input Format**
* The first line contains one integer `N`, the number of elements in the array.
* The second line contains `N` space-separated integers representing `nums`.

**Output Format**
* Print one integer: the sum of the ranges of all non-empty subarrays.

**Constraints**
* `1 <= N <= 1000`
* `-10^9 <= nums[i] <= 10^9`

**Example**
**Input:**
```text
3
1 2 3
```

**Output:**
```text
4
```

**Explanation:**
The six non-empty subarrays are `[1]`, `[2]`, `[3]`, `[1,2]`, `[2,3]`, and `[1,2,3]`. Their ranges are `0, 0, 0, 1, 1, 2`, so the total is `4`.
