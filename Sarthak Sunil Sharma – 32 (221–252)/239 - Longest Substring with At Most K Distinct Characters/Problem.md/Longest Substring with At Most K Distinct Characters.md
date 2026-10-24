### Problem 239: Longest Substring with At Most K Distinct Characters
**Difficulty:** Hard | **Tags:** Sliding Window, Two Pointers, Hash Map, Strings

**Problem Statement**

Given a string `s` and an integer `k`, return the length of the longest substring of `s` that contains at most `k` distinct characters.

A substring is a contiguous sequence of characters from the string.

**Input Format**

The first line contains two integers `n` and `k`, where `n` is the length of the string.

The second line contains the string `s`.

**Output Format**

Print a single integer representing the maximum length of a substring containing at most `k` distinct characters.

**Constraints**

- `1 ≤ n ≤ 10^5`
- `0 ≤ k ≤ 26`
- `s` consists of lowercase English letters.
- The given value of `n` equals `s.length()`.

**Example 1**

**Input:**
```text
6 2
eceba
```

**Output:**
```text
3
```

**Explanation:**
The longest valid substring is `ece`, which contains only `e` and `c`.

**Example 2**

**Input:**
```text
6 2
aa
```

**Output:**
```text
2
```

**Example 3**

**Input:**
```text
6 1
abcaaa
```

**Output:**
```text
3
```

**Explanation:**
The longest valid substring containing at most one distinct character is `aaa`.

**Approach:**

Use a sliding window `[left, right]` and maintain the frequency of every character in the window. Expand `right` one character at a time. Whenever the number of distinct characters becomes greater than `k`, move `left` forward while removing characters until the window is valid again. Track the maximum valid window length.

**Complexity:**

- Time: `O(n)`
- Space: `O(1)` because there are only 26 lowercase letters.
