### Problem 315: Binary Subarrays with Sum
**Difficulty:** Medium | **Tags:** Sliding Window, Prefix Sum, Array

**Problem Statement**
Given a binary array `nums` and an integer `goal`, return the number of non-empty subarrays with a sum equal to `goal`.

A subarray is a contiguous part of the array.

**Input Format**
- The first line contains two integers `N` and `goal`.
- The second line contains `N` space-separated binary integers (`0` or `1`).

**Output Format**
- Print a single integer representing the number of subarrays with sum equal to `goal`.

**Constraints**
- `1 <= N <= 10^5`
- `0 <= goal <= N`
- `nums[i]` is either `0` or `1`.

**Example**
**Input:**
```text
5 2
1 0 1 0 1
```
**Output:**
```text
4
```
