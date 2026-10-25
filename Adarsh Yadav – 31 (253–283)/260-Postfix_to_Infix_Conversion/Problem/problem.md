### Problem: Postfix to Infix Conversion

**Difficulty:** Easy
**Tags:** Stack, String

**Problem Statement**
You are given a valid postfix expression as a string, where:
* Operands are single lowercase English letters (`a` to `z`)
* Operators are binary: `+`, `-`, `*`, `/`
* The expression contains no spaces and is guaranteed to be syntactically valid.

Write a function to convert the given postfix expression into a valid infix expression. Use parentheses to clearly represent the evaluation order of the expression: every binary operation is wrapped in its own pair of parentheses, for example `(a+b)`. A lone operand is printed as it is, without parentheses.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains one postfix expression.

**Output Format**
* For each test case, print the infix expression on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ |postExp| ≤ 16000
* `postExp` consists only of lowercase letters (`a`–`z`) as operands and the four binary operators `+ - * /`.
* The given postfix expression is guaranteed valid (every operator has exactly two operands).

**Example 1**

**Input:**
1
ab+

**Output:**
(a+b)

**Explanation:**
```text
postfix : a b +
infix   : (a + b)
```

**Example 2**

**Input:**
1
abc*+

**Output:**
(a+(b*c))
