### Problem: Infix to Postfix Conversion

**Difficulty:** Medium
**Tags:** Stack, String, Shunting-Yard

**Problem Statement**
You are given a string `expression` representing a valid infix mathematical expression. Your task is to convert this expression into its equivalent postfix notation, also known as Reverse Polish Notation (RPN).

The expression may include:
* Operands: single lowercase English letters (`a` to `z`) or single-digit numbers (`0` to `9`)
* Binary operators: `+`, `-`, `*`, `/`, `^`
* Parentheses: `(` and `)` to indicate grouping and precedence

Operator precedence (from highest to lowest):
* `^` (exponentiation)
* `*`, `/` (multiplication and division)
* `+`, `-` (addition and subtraction)

Associativity:
* `^` is right-associative
* All other operators are left-associative
* Parentheses override standard precedence

Return the corresponding postfix expression as a string. The result must not contain any spaces or separators between characters.

**Input Format**
* The first line contains a single integer `T`, the number of test cases.
* Each of the next `T` lines contains one infix expression.

**Output Format**
* For each test case, print the postfix expression on a new line.

**Constraints**
* 1 ≤ T ≤ 10
* 1 ≤ |expression| ≤ 10^4
* The expression is syntactically valid and properly parenthesized.
* Operands are always single characters.
* No whitespace appears in the input or output.
* All operators are binary.

**Example 1**

**Input:**
1
a+b*c

**Output:**
abc*+

**Explanation:**
Multiplication has higher precedence than addition, so `b * c` is evaluated first, then added to `a`.

**Example 2**

**Input:**
1
(a+b)*c

**Output:**
ab+c*

**Explanation:**
The parentheses ensure that `a + b` is evaluated before multiplying with `c`.
