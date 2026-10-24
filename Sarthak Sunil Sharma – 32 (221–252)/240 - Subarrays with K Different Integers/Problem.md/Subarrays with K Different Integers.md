### Problem 240: Subarrays with K Different Integers
**Difficulty:** Hard | **Tags:** Sliding Window, Two Pointers, Hash Map, Arrays

**Problem Statement**

Given an integer array `nums` and an integer `k`, return the number of good subarrays.

A subarray is called **good** if it contains exactly `k` distinct integers.

**Input Format**

The first line contains two integers `n` and `k`.

The second line contains `n` integers representing the array `nums`.

**Output Format**

Print a single integer representing the number of subarrays containing exactly `k` distinct integers.

**Constraints**

- `1 ≤ n ≤ 10^5`
- `1 ≤ nums[i] ≤ 10^5`
- `1 ≤ k ≤ n`

**Example 1**

**Input:**
```text
5 2
1 2 1 2 3
```

**Output:**
```text
7
```

**Explanation:**
The subarrays containing exactly two distinct integers are counted using a sliding-window approach.

**Example 2**

**Input:**
```text
5 1
1 1 1 1 1
```

**Output:**
```text
15
```

**Explanation:**
Every non-empty subarray contains exactly one distinct integer.

**Example 3**

**Input:**
```text
5 3
1 2 1 2 3
```

**Output:**
```text
3
```

**Approach:**

Count subarrays with exactly `k` distinct integers using:

`exactly(k) = atMost(k) - atMost(k - 1)`

For `atMost(x)`, maintain a sliding window containing at most `x` distinct values. For each right endpoint, once the window is valid, there are `right - left + 1` valid subarrays ending at `right`.

**Complexity:**

- Time: `O(n)`
- Space: `O(n)` in the worst case for the frequency map.
