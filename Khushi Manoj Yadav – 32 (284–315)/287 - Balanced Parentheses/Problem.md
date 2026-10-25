### Problem 287: Balanced Parentheses
**Difficulty:** Easy | **Tags:** Stack, String

**Problem Statement**
Given a string `s` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, determine if the input string is valid.

An input string is valid if:
1. Open brackets must be closed by the same type of brackets.
2. Open brackets must be closed in the correct order.
3. Every close bracket has a corresponding open bracket of the same type.

**Input Format**
- The first line contains the bracket string `s`. (An empty line represents the empty string).

**Output Format**
- Print `true` if `s` is valid, and `false` otherwise.

**Constraints**
- `0 <= s.length <= 10^5`
- `s` consists of parentheses only: `'()[]{}'`.
