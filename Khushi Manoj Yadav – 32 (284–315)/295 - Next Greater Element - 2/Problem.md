### Problem 295: Next Greater Element - 2
**Difficulty:** Medium | **Tags:** Monotonic Stack, Array

**Problem Statement**
Given a circular integer array `nums` (i.e., the next element of `nums[n-1]` is `nums[0]`), return the next greater number for every element in `nums`.
The next greater number of a number `x` is the first greater number to its traversing-order next in the array, which means you could search circularly to find its next greater number. If it doesn't exist, return `-1` for this number.

**Input Format**
- The first line contains an integer `N`, the size of the array.
- The second line contains `N` space-separated integers representing `nums`.

**Output Format**
- Print `N` space-separated integers representing the circular Next Greater Element for each position.

**Constraints**
- `1 <= N <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

**Example**
**Input:**
```text
3
1 2 1
```
**Output:**
```text
2 -1 2
```
