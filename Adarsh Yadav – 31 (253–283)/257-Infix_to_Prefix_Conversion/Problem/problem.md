### Problem: Infix to Prefix Conversion

**Difficulty:** Medium
**Tags:** Stack, String, Shunting-Yard

**Problem Statement**
Given a valid arithmetic expression in infix notation, return its equivalent prefix (Polish) notation.

The expression can contain:
* lowercase letters `a`–`z` as operands
* the four binary operators `+`, `-`, `*`, `/`
* round parentheses `(` `)` that enforce evaluation order

`*` and `/` have higher precedence than `+` and `-`. All operators are left-associative. No whitespace appears in the input or the output. The input is guaranteed to be syntactically correct (parentheses are balanced, every operator has two operands, etc.).

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains one infix expression.

**Output Format**
* For each test case, print the prefix expression on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ |infix| ≤ 1000
* `infix` contains only characters `{a–z, +, -, *, /, (, )}`.
* The expression is valid.

**Example 1**

**Input:**
1
(a+b)*c

**Output:**
*+abc

**Explanation:**
Infix  : (a + b) * c
Prefix : * + a b c

**Example 2**

**Input:**
1
a+b*c

**Output:**
+a*bc

**Explanation:**
Infix  : a + (b * c)
Prefix : + a * b c
