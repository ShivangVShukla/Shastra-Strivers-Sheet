### Problem 232: Max Consecutive Ones III
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Arrays

**Problem Statement**

Given a binary array `nums` and an integer `k`, return the maximum number of consecutive `1`s in the array if you can flip at most `k` `0`s.

A flip changes one `0` into a `1`.

**Input Format**

* The first line contains two integers `n` and `k`.
* The second line contains `n` integers, each either `0` or `1`.

**Output Format**

Print one integer representing the maximum number of consecutive `1`s obtainable after flipping at most `k` zeroes.

**Constraints**

* `1 ≤ n ≤ 10^5`
* `nums[i]` is either `0` or `1`
* `0 ≤ k ≤ n`

**Example 1**

**Input:**
```text
11 2
1 1 1 0 0 0 1 1 1 1 0
```

**Output:**
```text
6
```

**Explanation:**

By flipping two zeroes in the suitable window, we can obtain six consecutive `1`s.

**Example 2**

**Input:**
```text
19 3
0 0 1 1 0 0 1 1 1 0 1 1 0 0 1 1 1 1 0
```

**Output:**
```text
10
```

**Explanation:**

The longest valid window contains at most three zeroes. Flipping those zeroes gives ten consecutive `1`s.

**Approach:**

Use a sliding window with two pointers `left` and `right`.

* Expand the window by moving `right`.
* Count how many zeroes are currently inside the window.
* If the number of zeroes becomes greater than `k`, move `left` forward until the window contains at most `k` zeroes.
* At every step, update the maximum window length.

The window is always maintained so that it contains at most `k` zeroes. Therefore, every valid window represents a segment that can be converted entirely into `1`s using at most `k` flips.

**Complexity:**

* Time Complexity: `O(n)`
* Space Complexity: `O(1)`
