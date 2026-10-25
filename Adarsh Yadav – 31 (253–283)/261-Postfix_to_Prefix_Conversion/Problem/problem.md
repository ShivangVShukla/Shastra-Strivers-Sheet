### Problem: Postfix to Prefix Conversion

**Difficulty:** Easy
**Tags:** Stack, String

**Problem Statement**
You are given a valid postfix expression as a string, where:
* Operands are single lowercase English letters (`a` to `z`)
* Operators are binary: `+`, `-`, `*`, `/`
* The expression contains no spaces and is guaranteed to be valid.

Write a function to convert the postfix expression into a prefix expression, also as a string without spaces.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains one postfix expression.

**Output Format**
* For each test case, print the prefix expression on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ |postfix| ≤ 1000
* The postfix expression is valid (no need to validate correctness).
* Operands are lowercase letters (`a`–`z`).
* Operators are binary: `+`, `-`, `*`, `/`.

**Example 1**

**Input:**
1
ab+

**Output:**
+ab

**Explanation:**
Postfix → Prefix

**Example 2**

**Input:**
1
abc*+d-

**Output:**
-+a*bcd
