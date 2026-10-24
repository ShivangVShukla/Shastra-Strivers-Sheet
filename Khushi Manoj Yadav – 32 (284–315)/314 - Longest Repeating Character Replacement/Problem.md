### Problem 314: Longest Repeating Character Replacement
**Difficulty:** Medium | **Tags:** Sliding Window, String

**Problem Statement**
You are given a string `s` consisting of uppercase English letters and an integer `k`. You can choose any character of the string and change it to any other uppercase English letter. You can perform this operation at most `k` times.
Return the length of the longest substring containing the same letter you can get after performing the above operations.

**Input Format**
- The first line contains the string `s`.
- The second line contains the integer `k`.

**Output Format**
- Print a single integer representing the maximum length of repeating character substring.

**Constraints**
- `1 <= s.length <= 10^5`
- `s` consists of only uppercase English letters.
- `0 <= k <= s.length`

**Example**
**Input:**
```text
ABAB
2
```
**Output:**
```text
4
```
