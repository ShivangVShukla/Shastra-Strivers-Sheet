### Problem 231: Longest Substring Without Repeating Characters
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Hashing, String

**Problem Statement**

Given a string `s`, find the length of the **longest substring without repeating characters**.

A substring is a contiguous sequence of characters from the string.

**Input Format**

* The input contains one line containing the string `s`.
* The string may contain English letters, digits, symbols, and spaces.
* An empty string is also allowed.

**Output Format**

Print one integer representing the length of the longest substring of `s` that contains no repeated character.

**Constraints**

* `0 ≤ s.length ≤ 5 × 10^4`
* `s` consists of English letters, digits, symbols, and spaces.

**Example 1**

**Input:**
```text
abcabcbb
```

**Output:**
```text
3
```

**Explanation:**

The longest substrings without repeating characters include `abc`, `bca`, and `cab`. Their maximum length is `3`.

**Example 2**

**Input:**
```text
bbbbb
```

**Output:**
```text
1
```

**Explanation:**

Every character is the same, so the longest substring containing unique characters has length `1`.

**Example 3**

**Input:**
```text
pwwkew
```

**Output:**
```text
3
```

**Explanation:**

The substring `wke` has length `3` and contains no repeated characters. Therefore, the answer is `3`.

**Approach:**

Use a **sliding window** with two pointers, `left` and `right`.

* Expand the window by moving `right` through the string.
* Store the most recent index at which every character was seen.
* If the current character was previously seen inside the current window, move `left` directly to one position after its previous occurrence.
* Update the character's latest index.
* At every position, calculate the current window length and keep the maximum.

The important condition is:

```text
left = max(left, lastSeen[currentCharacter] + 1)
```

This prevents `left` from ever moving backwards.

**Complexity:**

* Time Complexity: `O(n)`
* Space Complexity: `O(min(n, character-set-size))`, at most `O(n)` in the general case.
