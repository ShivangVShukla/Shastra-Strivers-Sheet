### Problem 241: Minimum Window Substring
**Difficulty:** Hard | **Tags:** Sliding Window, Two Pointers, Hash Map, Strings

**Problem Statement**

Given two strings `s` and `t`, return the minimum window substring of `s` that contains every character of `t`, including duplicate characters.

If there is no such substring, print `-1`.

If multiple minimum windows have the same length, return the one with the smallest starting index.

**Input Format**

The first line contains the string `s`.

The second line contains the string `t`.

**Output Format**

Print the minimum window substring. If no valid window exists, print `-1`.

**Constraints**

- `1 ≤ s.length, t.length ≤ 10^5`
- `s` and `t` consist of English letters.
- Character matching is case-sensitive.

**Example 1**

**Input:**
```text
ADOBECODEBANC
ABC
```

**Output:**
```text
BANC
```

**Explanation:**
`BANC` is the smallest substring of `s` containing `A`, `B`, and `C`.

**Example 2**

**Input:**
```text
a
aa
```

**Output:**
```text
-1
```

**Explanation:**
`s` does not contain two occurrences of `a`.

**Example 3**

**Input:**
```text
a
a
```

**Output:**
```text
a
```

**Approach:**

Use a sliding window. Store the required frequency of every character from `t`. Expand the right side of the window until it contains all required characters. Then shrink the left side while the window remains valid, recording the shortest valid window.

**Complexity:**

- Time: `O(|s| + |t|)`
- Space: `O(C)`, where `C` is the character set size.
