### Problem 236: Count Number of Nice Subarrays
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Prefix Sum, Arrays

**Problem Statement**

Given an array of positive integers and an integer `k`, count the number of contiguous subarrays that contain exactly `k` odd numbers.

A subarray is called **nice** if it contains exactly `k` odd numbers.

**Input Format**

The first line contains two integers `n` and `k`.
The second line contains `n` space-separated integers representing the array.

**Output Format**

Print the number of nice subarrays.

**Constraints**

- `1 ≤ n ≤ 10^5`
- `1 ≤ nums[i] ≤ 10^5`
- `1 ≤ k ≤ n`

**Example 1**
**Input:**
```
5 3
1 1 2 1 1
```
**Output:**
```
2
```
**Explanation:**
The nice subarrays are `[1,1,2,1]` and `[1,2,1,1]`.

**Example 2**
**Input:**
```
5 1
2 4 6 3 8
```
**Output:**
```
8
```
**Explanation:**
Every contiguous subarray containing the only odd element `3` has exactly one odd number.

**Example 3**
**Input:**
```
4 2
2 1 2 1
```
**Output:**
```
3
```

**Approach:**
Count subarrays with exactly `k` odd numbers as `atMost(k) - atMost(k-1)`. For `atMost(x)`, maintain a sliding window containing at most `x` odd numbers. For every right endpoint, after shrinking the window if necessary, all subarrays ending at `right` and starting from `left` through `right` are valid.

**Complexity:**
- Time: `O(n)`
- Space: `O(1)`
