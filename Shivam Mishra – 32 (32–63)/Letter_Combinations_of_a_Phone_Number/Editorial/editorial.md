# Editorial: Letter Combinations of a Phone Number

## Topic
Backtracking / Depth-First Search (DFS)

## Problem Statement
Given a string `digits` containing digits from `2` to `9`, return all possible letter combinations that the digits could represent based on the mapping of digits to letters on a telephone keypad.

Each digit corresponds to a set of letters. For every digit in the input, one letter must be selected from its corresponding set. The goal is to generate every possible combination while preserving the order of the digits.

If the input string is empty, return an empty list.

### Phone Keypad Mapping

| Digit | Corresponding letters |
|---|---|
| 2 | abc |
| 3 | def |
| 4 | ghi |
| 5 | jkl |
| 6 | mno |
| 7 | pqrs |
| 8 | tuv |
| 9 | wxyz |

### Example 1

**Input:**
```text
digits = "23"
```

**Output:**
```text
["ad","ae","af","bd","be","bf","cd","ce","cf"]
```

**Explanation:**

The digit `2` corresponds to `a`, `b`, and `c`, while the digit `3` corresponds to `d`, `e`, and `f`.

Choose one letter from each digit's group to generate all possible combinations:

- Starting with `a`: `ad`, `ae`, `af`
- Starting with `b`: `bd`, `be`, `bf`
- Starting with `c`: `cd`, `ce`, `cf`

Therefore, there are \(3 \times 3 = 9\) possible combinations.

### Example 2

**Input:**
```text
digits = "2"
```

**Output:**
```text
["a","b","c"]
```

**Explanation:**

The digit `2` maps to three letters: `a`, `b`, and `c`. Since there is only one digit, each letter forms a complete combination.

### Example 3

**Input:**
```text
digits = ""
```

**Output:**
```text
[]
```

**Explanation:**

An empty input contains no digits, so there are no combinations to generate.

## Approach

We can solve this problem efficiently using **backtracking**, a technique that explores all possible choices by building a solution incrementally.

For each digit, we consider every letter mapped to that digit. We append one letter to the current combination and recursively process the next digit. After exploring that choice, we remove the appended letter to restore the previous state and try the next available letter.

This process continues until every digit has been processed.

### Algorithm

1. Create a mapping array that stores the letters corresponding to each digit.
2. Initialize an empty result vector to store all valid combinations.
3. If the input string is empty, return the empty result immediately.
4. Start the recursive backtracking function at index `0` with an empty current combination.
5. At each recursive call:
   - If the current index equals the length of the input string, all digits have been processed. Add the current combination to the result and return.
   - Otherwise, retrieve the letters corresponding to the current digit.
   - Iterate through each available letter:
     - Append the letter to the current combination.
     - Recursively process the next digit.
     - Remove the last appended letter to backtrack and explore other choices.
6. Return the result vector containing all possible combinations.

### Dry Run

Consider the input `digits = "23"`.

The recursive process explores the choices as follows:

```text
                         ""
                    /     |     \
                   a      b      c
                 / | \   / | \  / | \
                ad ae af bd be bf cd ce cf
```

**Step-by-step execution:**

1. Initially, `current = ""` and `index = 0`.
2. The first digit is `2`, which maps to `abc`.
3. Choose `a` and move to index `1`. The next digit is `3`, which maps to `def`.
4. Append `d`, producing `ad`. Since all digits have been processed, store `ad`.
5. Backtrack by removing `d`, then append `e` to produce `ae`.
6. Repeat for `f`, producing `af`.
7. Backtrack to the first digit and repeat the same process for `b` and `c`.
8. After all branches have been explored, the result contains nine combinations.

The crucial operation is removing the last character after every recursive call. This ensures that each branch starts with the correct partial combination without retaining letters from previously explored branches.

## Solution

**File:** `Solution/solution.cpp`

```cpp
class Solution {
public:
    vector<string> letterCombinations(string digits) {
        vector<string> result;

        if (digits.empty()) {
            return result;
        }

        vector<string> mapping = {
            "", "", "abc", "def", "ghi",
            "jkl", "mno", "pqrs", "tuv", "wxyz"
        };

        string current;
        backtrack(digits, 0, current, result, mapping);

        return result;
    }

private:
    void backtrack(
        string& digits,
        int index,
        string& current,
        vector<string>& result,
        vector<string>& mapping
    ) {
        if (index == digits.size()) {
            result.push_back(current);
            return;
        }

        string letters = mapping[digits[index] - '0'];

        for (char ch : letters) {
            current.push_back(ch);

            backtrack(
                digits, index + 1,
                current, result, mapping
            );

            current.pop_back();
        }
    }
};
```

### Code Explanation

- **`mapping`** stores the keypad letters for each digit. Its indices correspond directly to the digit values, making lookup straightforward.
- **`result`** stores all completed combinations.
- **`current`** represents the partial combination being constructed during recursion.
- **`backtrack()`** explores every possible letter choice for the current digit.
- **Base case:** When `index == digits.size()`, the current combination is complete and is added to `result`.
- **Recursive case:** For every letter associated with the current digit, append that letter, recursively process the next digit, and then remove the letter using `pop_back()`.
- **`letterCombinations()`** handles empty input, initializes the mapping and current combination, starts recursion, and returns the final result.

## Complexity Analysis

Let \(n\) be the length of the input string, and let \(k_i\) be the number of letters corresponding to the digit at position \(i\).

The total number of combinations is:

\[
K = \prod_{i=0}^{n-1} k_i
\]

Each digit maps to either three or four letters. Therefore, the maximum number of combinations is \(4^n\).

**Time Complexity: \(O(n \cdot 4^n)\)**

There can be up to \(4^n\) combinations, and each completed combination contains \(n\) characters. Copying these combinations into the result requires \(O(n \cdot 4^n)\) time in the worst case.

**Auxiliary Space Complexity: \(O(n)\)**

The recursion depth is at most \(n\), and the current combination requires \(O(n)\) space. The keypad mapping occupies constant space.

**Output Space Complexity: \(O(n \cdot 4^n)\)**

The result vector stores up to \(4^n\) strings, each of length \(n\).

## Key Takeaways

- Backtracking systematically explores all possible combinations of choices.
- DFS is suitable because each combination is constructed one character at a time.
- The base case identifies when a complete combination has been formed.
- The `push_back()` and `pop_back()` operations allow the same string to be reused across different recursive branches.
- The empty-input check prevents an empty string from being incorrectly added as a combination.

## Implementation

See `Solution/solution.cpp`.

The implementation uses the standard `Solution` class interface expected by coding platforms. The input format and execution details for the local C++ runner are documented in `Problem.md`.