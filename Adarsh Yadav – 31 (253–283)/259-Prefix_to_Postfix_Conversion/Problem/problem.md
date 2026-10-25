### Problem: Prefix to Postfix Conversion

**Difficulty:** Easy
**Tags:** Stack, String

**Problem Statement**
You are given a valid prefix expression consisting of binary operators and single-character operands. Your task is to convert it into a valid postfix expression.

* Prefix (Polish) notation places the operator before its operands.
* Postfix (Reverse Polish) notation places the operator after its operands.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains one prefix expression.

**Output Format**
* For each test case, print the postfix expression on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ |expression| ≤ 10^4
* The expression contains only the characters `a`–`z`, `0`–`9`, `+`, `-`, `*`, `/`, `^`.
* The expression is guaranteed to be a valid prefix expression.

**Example 1**

**Input:**
1
+ab

**Output:**
ab+

**Example 2**

**Input:**
1
*+ab-cd

**Output:**
ab+cd-*
