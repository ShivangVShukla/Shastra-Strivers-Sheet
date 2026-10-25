### Problem: Balanced Parenthesis

**Difficulty:** Easy
**Tags:** Stack, String

**Problem Statement**
Given a string `str` containing just the characters `'('`, `')'`, `'{'`, `'}'`, `'['` and `']'`, check if the input string is valid and return `true` if the string is balanced, otherwise return `false`.

A string is balanced if every opening bracket is closed by a bracket of the same type, and brackets are closed in the correct order.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains one string `str`.

**Output Format**
* For each test case, print `True` if the string is balanced, and `False` otherwise, on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ |str| ≤ 10^4
* `str` consists only of the characters `()[]{}`.

**Example 1**

**Input:**
1
()[{}()]

**Output:**
True

**Explanation:**
Every open bracket has its corresponding close bracket, and the brackets are matched in the correct order, so the string is balanced.

**Example 2**

**Input:**
1
[()

**Output:**
False

**Explanation:**
`[` does not have a matching `]`, so the string is not valid.
