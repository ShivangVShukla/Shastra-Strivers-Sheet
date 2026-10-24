### Problem 242: Minimum Window Subsequence
**Difficulty:** Hard | **Tags:** Sliding Window, Two Pointers, Strings, Subsequence

**Problem Statement**

Given two strings `s1` and `s2`, return the minimum contiguous substring of `s1` such that `s2` is a subsequence of that substring.

If multiple minimum windows exist, return the one with the smallest starting index.

If no such substring exists, print `-1`.

**Input Format**

The first line contains string `s1`.

The second line contains string `s2`.

**Output Format**

Print the minimum window substring of `s1` containing `s2` as a subsequence. If no such window exists, print `-1`.

**Constraints**

- `1 ≤ s1.length, s2.length ≤ 10^4`
- `s1` and `s2` consist of English letters.
- Character matching is case-sensitive.

**Example 1**

**Input:**
```text
abcdebdde
bde
```

**Output:**
```text
bcde
```

**Explanation:**
`bde` is a subsequence of `bcde`, and no shorter valid window exists.

**Example 2**

**Input:**
```text
abc
d
```

**Output:**
```text
-1
```

**Explanation:**
`d` does not occur in `s1`.

**Example 3**

**Input:**
```text
jmeqksfrsdcmsiwvaovkjb
u
```

**Output:**
```text
-1
```

**Approach:**

Scan `s1` from left to right to find an ending position where all characters of `s2` can be matched in order. Once a complete match is found, move backward through `s1` to find the earliest possible start of that subsequence. Record the window and continue searching for a smaller one.

**Complexity:**

- Time: `O(|s1| * |s2|)` in the worst case.
- Space: `O(1)` auxiliary space.
