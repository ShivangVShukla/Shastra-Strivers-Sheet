### Problem 234: Longest Repeating Character Replacement
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Hashing, Strings

**Problem Statement**

You are given a string `s` containing only uppercase English letters and an integer `k`.

You may choose any character in the string and replace it with another uppercase English letter. You can perform this replacement at most `k` times.

Return the length of the longest substring that can be made to contain only one distinct character after at most `k` replacements.

**Input Format**

- The first line contains the string `s`.
- The second line contains the integer `k`.

**Output Format**

Print one integer: the maximum possible length of a substring that can be converted into a string containing only one repeated character using at most `k` replacements.

**Constraints**

- `1 ≤ s.length() ≤ 10^5`
- `s` consists only of uppercase English letters `A-Z`.
- `0 ≤ k ≤ s.length()`

**Example 1**
**Input:**
```text
ABAB
2
```
**Output:**
```text
4
```
**Explanation:**
Replace the two `B`s with `A`s to obtain `AAAA`.

**Example 2**
**Input:**
```text
AABABBA
1
```
**Output:**
```text
4
```
**Explanation:**
The substring `AABA` can be changed into `AAAA` using one replacement.

**Example 3**
**Input:**
```text
AAAA
0
```
**Output:**
```text
4
```
**Explanation:**
The entire string already contains the same character, so no replacement is required.

**Approach:**

Use a sliding window. Maintain the frequency of each character inside the current window and the frequency of the most common character, `maxFreq`.

For a window of length `L`, the number of replacements needed to make the whole window equal to its most frequent character is:

`L - maxFreq`

If this value is greater than `k`, move the left pointer forward until the window becomes valid again. Track the maximum valid window length.

**Complexity:**

- Time: `O(n)` because each character enters and leaves the sliding window at most once.
- Space: `O(1)` because there are only 26 uppercase English letters.
