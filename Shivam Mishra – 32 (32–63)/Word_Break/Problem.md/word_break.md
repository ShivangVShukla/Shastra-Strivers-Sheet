
# Word Break

## Problem Statement

Given a string `s` and a dictionary of strings `wordDict`, determine whether `s` can be segmented into a space-separated sequence of one or more dictionary words.

Return `true` if the entire string can be segmented successfully; otherwise, return `false`.

**Note:** The same word in the dictionary can be used multiple times in the segmentation.

## Examples

### Example 1

**Input:**
```text
s = "takeuforward"
wordDict = ["take", "forward", "you", "u"]
```

**Output:**
```text
true
```

**Explanation:**

The string `"takeuforward"` can be segmented into `"take"`, `"u"`, and `"forward"`. All three words are present in the dictionary.

### Example 2

**Input:**
```text
s = "applepineapple"
wordDict = ["apple"]
```

**Output:**
```text
false
```

**Explanation:**

The string can be segmented as `"apple"`, `"pine"`, and `"apple"`, but `"pine"` is not present in the dictionary. Therefore, the entire string cannot be segmented using the available dictionary words.

## Constraints

- \(1 \leq s.length \leq 300\)
- \(1 \leq wordDict.length \leq 1000\)
- \(1 \leq wordDict[i].length \leq 20\)
- `s` and `wordDict[i]` consist of lowercase English letters.
- All strings in `wordDict` are unique.

## Input Format

- The first line contains the string `s`.
- The second line contains an integer `n`, representing the number of dictionary words.
- The next `n` lines contain one dictionary word each.

## Output Format

Print `true` if the string can be segmented into dictionary words; otherwise, print `false`.


## Complexity

Let `n` be the length of the string and `m` be the maximum length of any dictionary word.

- **Time Complexity:** \(O(nm^2 + D)\) for the substring-based implementation, where `D` is the total number of characters across all dictionary words.
- **Auxiliary Space:** \(O(n + D)\), including the DP array and dictionary storage.
