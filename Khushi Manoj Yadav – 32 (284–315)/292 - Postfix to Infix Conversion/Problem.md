### Problem 292: Postfix to Infix Conversion
**Difficulty:** Medium | **Tags:** Stack, String, Parsing

**Problem Statement**
Given a valid postfix expression string, convert it to its equivalent infix expression.

Supported operators include:
- `^` (exponentiation, highest precedence, right-associative)
- `*`, `/` (multiplication, division, precedence 2, left-associative)
- `+`, `-` (addition, subtraction, precedence 1, left-associative)

Operands are single alphanumeric characters (`a-z`, `A-Z`, `0-9`).

**Input Format**
- A single line containing the postfix expression string.

**Output Format**
- Print the converted infix expression string.

**Constraints**
- `1 <= length <= 10^5`
- The expression is syntactically valid.
