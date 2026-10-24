### Problem 238: Maximum Points You Can Obtain from Cards
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Arrays

**Problem Statement**

There are several cards arranged in a row. Each card has an associated number of points. You can take exactly `k` cards from either the beginning or the end of the row.

Return the maximum score you can obtain.

**Input Format**

The first line contains two integers `n` and `k`.

The second line contains `n` integers representing the points on each card.

**Output Format**

Print the maximum score obtainable by taking exactly `k` cards from the beginning and/or end.

**Constraints**

- `1 ≤ k ≤ n`
- `1 ≤ n ≤ 10^5`
- `1 ≤ cardPoints[i] ≤ 10^4`

**Example 1**

**Input:**
```text
7 3
1 2 3 4 5 6 1
```

**Output:**
```text
12
```

**Explanation:**
Take `1` and `2` from the beginning and `6` from the end.

**Example 2**

**Input:**
```text
11 4
2 2 2 2 2 2 2 2 2 2 2
```

**Output:**
```text
8
```

**Example 3**

**Input:**
```text
5 3
9 7 7 9 7
```

**Output:**
```text
25
```

**Approach:**

The `k` selected cards leave exactly `n-k` consecutive cards in the middle. Therefore, maximizing the selected score is equivalent to minimizing the sum of a contiguous subarray of length `n-k`. Compute the total sum and use a sliding window to find the minimum window sum.

**Complexity:**

- Time: `O(n)`
- Space: `O(1)`
