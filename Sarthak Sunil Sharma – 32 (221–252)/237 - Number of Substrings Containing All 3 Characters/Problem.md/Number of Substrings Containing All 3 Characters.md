### Problem 237: Number of Substrings Containing All 3 Characters
**Difficulty:** Medium | **Tags:** Sliding Window, Two Pointers, Strings

**Problem Statement**

Given a string `s` consisting only of the characters `a`, `b`, and `c`, return the number of substrings containing at least one occurrence of all three characters.

A substring is a contiguous sequence of characters from the string.

**Input Format**

A single line containing the string `s`.

**Output Format**

Print a single integer representing the number of substrings that contain at least one `a`, one `b`, and one `c`.

**Constraints**

- `3 ≤ s.length ≤ 5 * 10^4`
- `s` consists only of the characters `a`, `b`, and `c`.

**Example 1**

**Input:**
```text
abcabc
```

**Output:**
```text
10
```

**Explanation:**
The valid substrings are those whose range contains all three characters.

**Example 2**

**Input:**
```text
aaacb
```

**Output:**
```text
3
```

**Explanation:**
The valid substrings are `aaacb`, `aacb`, and `acb`.

**Example 3**

**Input:**
```text
abc
```

**Output:**
```text
1
```

**Explanation:**
Only the complete string contains all three characters.

**Approach:**

Use a sliding window and store the latest index of `a`, `b`, and `c`. At every position `right`, let `m` be the minimum of the three latest indices. If all three characters have appeared, every starting index from `0` through `m` creates a valid substring ending at `right`. Therefore, add `m + 1` to the answer.

**Complexity:**

- Time: `O(n)`
- Space: `O(1)`
