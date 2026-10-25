### Problem: Prefix to Infix Conversion

**Difficulty:** Easy
**Tags:** Stack, String

**Problem Statement**
You are given a valid arithmetic expression in prefix notation. Your task is to convert it into a fully parenthesized infix expression.

Prefix notation (also known as Polish notation) places the operator before its operands. In contrast, infix notation places the operator between operands.

In the fully parenthesized infix expression, every binary operation is wrapped in its own pair of parentheses, for example `(a+b)`. A lone operand is printed as it is, without parentheses.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains one prefix expression.

**Output Format**
* For each test case, print the fully parenthesized infix expression on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ |expression| ≤ 10^4
* Valid binary operators: `+`, `-`, `*`, `/`, `^`
* Valid operands: lowercase letters (`a`–`z`) or digits (`0`–`9`)
* The input is guaranteed to be a valid prefix expression.

**Example 1**

**Input:**
1
+ab

**Output:**
(a+b)

**Example 2**

**Input:**
1
*+ab-cd

**Output:**
((a+b)*(c-d))
