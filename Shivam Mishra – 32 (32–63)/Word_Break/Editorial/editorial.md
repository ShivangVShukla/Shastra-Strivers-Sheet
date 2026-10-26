
# Editorial: Word Break

## Topic
Dynamic Programming, Hashing, String Processing

## Problem Statement

Given a string `s` and a dictionary of strings `wordDict`, determine whether `s` can be segmented into a sequence of one or more dictionary words.

Each word in the dictionary can be used multiple times.

Return `true` if the entire string can be segmented successfully; otherwise, return `false`.

### Example 1

**Input:**
```text
s = "leetcode"
wordDict = ["leet", "code"]
```

**Output:**
```text
true
```

**Explanation:**

The string can be segmented as `"leet" + "code"`.
Both `"leet"` and `"code"` are present in the dictionary.

### Example 2

**Input:**
```text
s = "applepenapple"
wordDict = ["apple", "pen"]
```

**Output:**
```text
true
```

**Explanation:**

The string can be segmented as `"apple" + "pen" + "apple"`.
The word `"apple"` is used twice, which is allowed.

### Example 3

**Input:**
```text
s = "catsandog"
wordDict = ["cats", "dog", "sand", "and", "cat"]
```

**Output:**
```text
false
```

**Explanation:**

Although `"cats"` and `"cat"` are valid dictionary words, the entire string cannot be segmented into dictionary words. The remaining characters cannot be completely matched.

---

## Approach: Dynamic Programming with Hashing

### 1. Intuition

We need to determine whether the entire string can be divided into valid dictionary words.

Checking every possible segmentation independently would be inefficient because many substrings and prefixes would be examined repeatedly.

Instead, we use **Dynamic Programming (DP)** to store whether each prefix of the string can be segmented successfully.

We also use an `unordered_set` to store the dictionary words. This allows us to check whether a substring belongs to the dictionary in expected constant time, excluding the cost of creating and hashing the substring.

### 2. DP State Definition

Let:

`dp[i] = true`

if and only if the prefix `s[0...i-1]`, containing the first `i` characters, can be segmented into valid dictionary words.

Otherwise:

`dp[i] = false`

The DP array has size `n + 1`, where `n` is the length of the string.

The state `dp[0]` represents the empty prefix. It is initialized to `true` because an empty prefix requires no words and provides a valid starting point for building longer prefixes.

Therefore:

```cpp
dp[0] = true;
```

All other states are initially `false`.

### 3. Transition

To determine whether the prefix ending at index `i - 1` can be segmented, consider every possible final word length `len`.

The corresponding substring is:

```cpp
s.substr(i - len, len)
```

This substring represents the last `len` characters of the prefix of length `i`.

For this prefix to be valid, both of the following conditions must hold:

1. The preceding prefix can be segmented: `dp[i - len] == true`.
2. The final substring exists in the dictionary.

If both conditions hold, then:

```cpp
dp[i] = true;
```

The recurrence can be written as:

\[
dp[i] =
\bigvee_{j=0}^{i-1}
\left(
dp[j] \land
(s[j\ldots i-1] \in wordDict)
\right)
\]

Here, `∨` means logical OR and `∧` means logical AND.

In other words, a prefix is valid if there is at least one position where a valid preceding prefix is followed by a dictionary word.

### 4. Optimization Using Maximum Word Length

Let `maxLen` be the maximum length of any word in the dictionary.

A dictionary word cannot be longer than `maxLen`. Therefore, when checking the prefix ending at position `i`, we only need to consider:

```cpp
len <= maxLen && len <= i
```

This avoids checking substrings that cannot possibly match any dictionary word.

If a valid segmentation is found for a particular prefix, we can stop checking further possibilities for that prefix.

### 5. Final Answer

After processing every prefix, the answer is stored in:

```cpp
dp[n]
```

- If `dp[n]` is `true`, the entire string can be segmented.
- If `dp[n]` is `false`, no valid segmentation exists.

---

## Algorithm

1. Create an `unordered_set` containing all dictionary words.
2. Find the maximum length of any word in the dictionary.
3. Initialize a boolean DP array of size `n + 1` with all values set to `false`.
4. Set `dp[0] = true`.
5. Iterate over every prefix length `i` from `1` to `n`.
6. For each prefix, try every possible final word length from `1` to `min(i, maxLen)`.
7. If `dp[i - len]` is `true` and the corresponding substring exists in the dictionary, set `dp[i] = true` and stop checking that prefix.
8. Return `dp[n]`.

---

## Dry Run

Consider the following input:

```text
s = "leetcode"
wordDict = ["leet", "code"]
```

The string has length `8`.

Initially:

```text
Index:  0      1      2      3      4      5      6      7      8
dp:    true   false  false  false  false  false  false  false  false
```

The dictionary contains `"leet"` and `"code"`, and the maximum word length is `4`.

| Prefix length `i` | Prefix | Valid segmentation found | `dp[i]` |
|---:|---|---|---|
| 1 | `"l"` | No | false |
| 2 | `"le"` | No | false |
| 3 | `"lee"` | No | false |
| 4 | `"leet"` | `"leet"` | true |
| 5 | `"leetc"` | No | false |
| 6 | `"leetco"` | No | false |
| 7 | `"leetcod"` | No | false |
| 8 | `"leetcode"` | `"leet" + "code"` | true |

At `i = 4`, the substring `"leet"` is found in the dictionary, and `dp[0]` is true. Therefore, `dp[4]` becomes true.

At `i = 8`, the substring `"code"` is found in the dictionary, and `dp[4]` is true. Therefore, `dp[8]` becomes true.

The final answer is:

```text
true
```

---

## C++ Solution

```cpp
class Solution {
public:
    bool wordBreak(string s, vector<string>& wordDict) {
        int n = s.size();

        unordered_set<string> dict(
            wordDict.begin(), wordDict.end()
        );

        vector<bool> dp(n + 1, false);
        dp[0] = true;

        int maxLen = 0;

        for (const string& word : wordDict) {
            maxLen = max(maxLen, (int)word.size());
        }

        for (int i = 1; i <= n; i++) {
            for (int len = 1;
                 len <= maxLen && len <= i;
                 len++) {

                if (!dp[i - len]) {
                    continue;
                }

                string part = s.substr(i - len, len);

                if (dict.count(part)) {
                    dp[i] = true;
                    break;
                }
            }
        }

        return dp[n];
    }
};
```

---

## Correctness Proof

We prove that `dp[i]` correctly indicates whether the first `i` characters can be segmented into dictionary words.

### Base Case

The empty prefix is represented by `dp[0]`. Since no characters need to be segmented, `dp[0] = true` is correct.

### Inductive Step

Assume all DP states for prefixes shorter than `i` are correct.

The algorithm examines every possible final word length `len` up to `min(i, maxLen)`.

If `dp[i - len]` is true and `s.substr(i - len, len)` is in the dictionary, the prefix of length `i` consists of a valid preceding segmentation followed by a valid dictionary word. Hence, setting `dp[i]` to true is correct.

Conversely, if the prefix of length `i` has a valid segmentation, its final word must be a dictionary word of length at most `maxLen`. The preceding prefix must also have a valid segmentation. The algorithm considers this word length and will therefore discover the valid segmentation.

Thus, `dp[i]` is correct.

### Conclusion

By induction, all DP states are correct. Therefore, `dp[n]` correctly determines whether the entire string can be segmented into dictionary words.

---

## Complexity Analysis

Let:

- `n` be the length of the input string.
- `D` be the total number of characters across all dictionary words.
- `M` be the maximum dictionary word length.

### Time Complexity

Building the hash set takes expected \(O(D)\) time.

The algorithm examines at most `n × min(n, M)` candidate substrings. Creating and hashing a substring of length `len` takes expected \(O(len)\) time.

Therefore, a safe upper bound for the implementation is:

\[
O(D+nM^2)
\]

Since `M <= n`, the worst-case time complexity is:

\[
\boxed{O(D+n^3)}
\]

This bound accounts for substring creation and hashing. The DP array itself requires only \(O(nM)\) candidate checks.

### Auxiliary Space Complexity

The DP array uses \(O(n)\) space, and the dictionary hash set uses \(O(D)\) space.

Temporary substring storage can use up to \(O(M)\) additional space.

Thus, the auxiliary space complexity is:

\[
\boxed{O(n+D+M)}
\]

Since \(M \le n\), this simplifies to:

\[
\boxed{O(n+D)}
\]

---

## Key Takeaways

- Dynamic Programming avoids repeatedly solving the same prefix-segmentation subproblems.
- `dp[i]` represents whether the first `i` characters can be segmented.
- `dp[0] = true` provides the base case for forming dictionary words from the beginning.
- Hashing enables efficient expected dictionary membership checks.
- Restricting candidate word lengths to `maxLen` reduces unnecessary work.
- The final answer is stored in `dp[n]`.

## Implementation

The complete executable solution, including input parsing and `main()`, is available in `Solution/solution.cpp`.

The editorial uses the LeetCode/TakeUForward-style `Solution` class and method, without a `main()` function.
