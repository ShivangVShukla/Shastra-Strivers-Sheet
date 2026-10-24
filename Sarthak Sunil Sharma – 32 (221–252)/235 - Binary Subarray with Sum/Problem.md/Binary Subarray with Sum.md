### Problem 235: Binary Subarray with Sum
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Prefix Sum, Hashing, Arrays

**Problem Statement**

Given a binary array `nums` and an integer `goal`, return the number of non-empty subarrays whose sum is exactly `goal`.

A subarray is a contiguous part of the array.

**Input Format**

- The first line contains two integers `n` and `goal`.
- The second line contains `n` binary integers `nums[i]`, where each value is `0` or `1`.

**Output Format**

Print one integer: the number of non-empty subarrays whose sum is exactly `goal`.

**Constraints**

- `1 <= n <= 3 * 10^4`
- `0 <= goal <= n`
- `nums[i]` is either `0` or `1`.

**Example 1**
**Input:**
```text
7 3
1 1 0 1 0 0 1
```
**Output:**
```text
4
```
**Explanation:**
There are 4 subarrays with sum exactly 3.

**Example 2**
**Input:**
```text
5 0
0 0 0 0 0
```
**Output:**
```text
15
```
**Explanation:**
Every non-empty subarray has sum 0, so the answer is `5 * 6 / 2 = 15`.

**Approach:**

For a binary array, define `atMost(k)` as the number of non-empty subarrays whose sum is at most `k`. Then:

`answer = atMost(goal) - atMost(goal - 1)`.

Because the array contains only `0` and `1`, a sliding window can count `atMost(k)` in O(n). Expand the right pointer and add the new value to the current sum. Whenever the sum becomes greater than `k`, move the left pointer until the window is valid again. For every valid right endpoint, there are `right - left + 1` valid subarrays ending at `right`.

When `k < 0`, `atMost(k)` is 0 because binary-array subarray sums cannot be negative.

**Complexity:**

- Time: `O(n)`
- Space: `O(1)`
