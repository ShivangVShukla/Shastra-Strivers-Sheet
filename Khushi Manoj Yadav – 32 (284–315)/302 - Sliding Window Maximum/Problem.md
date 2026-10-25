### Problem 302: Sliding Window Maximum
**Difficulty:** Hard | **Tags:** Monotonic Deque, Sliding Window, Array

**Problem Statement**
You are given an array of integers `nums`, and there is a sliding window of size `k` which is moving from the very left of the array to the very right. You can only see the `k` numbers in the window. Each time the sliding window moves right by one position.
Return the max sliding window.

**Input Format**
- The first line contains two integers `N` and `K`, representing the array length and window size.
- The second line contains `N` space-separated integers representing `nums`.

**Output Format**
- Print `N - K + 1` space-separated integers: the maximum value of each sliding window from left to right.

**Constraints**
- `1 <= K <= N <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

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
