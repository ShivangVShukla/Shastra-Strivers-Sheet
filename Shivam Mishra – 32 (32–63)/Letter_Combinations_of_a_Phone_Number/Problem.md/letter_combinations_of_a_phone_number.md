
# Letter Combinations of a Phone Number

Given a string consisting of digits from **2 to 9 (inclusive)**, return all possible letter combinations that the number can represent.

The mapping of digits to letters is based on the standard telephone keypad.

## Examples

### Example 1

**Input:**
```text
digits = "34"
```

**Output:**
```text
["dg", "dh", "di", "eg", "eh", "ei", "fg", "fh", "fi"]
```

**Explanation:**

The digit `3` maps to `"def"` and the digit `4` maps to `"ghi"`.

By replacing each digit with its corresponding letters, we obtain all possible combinations:

- Starting with `d`: `dg`, `dh`, `di`
- Starting with `e`: `eg`, `eh`, `ei`
- Starting with `f`: `fg`, `fh`, `fi`

Therefore, there are 9 possible combinations.

### Example 2

**Input:**
```text
digits = "3"
```

**Output:**
```text
["d", "e", "f"]
```

**Explanation:**

The digit `3` maps to `"def"`. Since the input contains only one digit, each mapped letter forms a separate combination.

## Constraints

- The input string contains digits from `2` to `9`, inclusive.
- The input may be empty, in which case return an empty list.
- Each digit maps to either three or four letters.

## Expected Approach

**Backtracking (Depth-First Search)**

For each digit, iterate through its corresponding letters and append one letter to the current combination. Recursively process the next digit, then remove the last letter to explore other possibilities.

When all digits have been processed, add the completed combination to the result.

## Complexity Analysis

Let `n` be the length of the input string.

- **Time Complexity:** `O(n * 4^n)` in the worst case, because there can be up to `4^n` combinations, each containing `n` characters.
- **Auxiliary Space Complexity:** `O(n)` for the recursion stack and the current combination, excluding the output.

## Implementation

See `Solution/solution.cpp`.
