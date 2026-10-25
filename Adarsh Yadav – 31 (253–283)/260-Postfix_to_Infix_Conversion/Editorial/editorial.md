# Editorial: Postfix to Infix Conversion


## Difficulty

Easy


## Prerequisites
- Stack (LIFO)
- Postfix (Reverse Polish) notation


## Problem Statement

Given a valid postfix expression (lowercase-letter operands, binary operators `+ - * /`, no spaces), convert it into an infix expression where every binary operation is wrapped in its own pair of parentheses. A lone operand stays as it is.


## Explanation & Approach

### Step 1: Read Left to Right

In postfix notation an operator comes **after** its two operands. If we scan the string from **left to right**, both operands of an operator have always been seen by the time we reach it. A stack holds the sub-expressions built so far.

### Step 2: Algorithm

Scan the string from the first character to the last, with an empty stack of strings:

- **Operand:** push it as a one-character string.
- **Operator `c`:** pop two strings. The **first** popped is the **right** operand, the **second** popped is the **left** operand. Push `"(" + left + c + right + ")"`.

At the end the stack holds exactly one string: the answer.

### Step 3: Why the First Pop Is the Right Operand

Scanning left to right, the left operand is pushed first and the right operand is pushed later, so the right operand sits on top. Swapping the two gives wrong answers for non-commutative operators: `ab-` would become `(b-a)` and `ab/` would become `(b/a)`.

(This is the opposite of prefix-to-infix, where the scan runs right to left and the first pop is the left operand.)

### Step 4: Things to Watch For

- **Operand order.** First pop = right, second pop = left.
- **A lone operand gets no parentheses.** For input `a` the answer is `a`, not `(a)`.
- **Parentheses around every operator**, even when precedence would make them unnecessary.


## Example

### Input:

```text
2
ab+
abc*+
```

### Trace:

**Test Case 1:** `ab+`

| Char | Action                                          | Stack (bottom → top) |
|------|-------------------------------------------------|----------------------|
| a    | operand, push                                   | a                    |
| b    | operand, push                                   | a b                  |
| +    | pop `b` (right), pop `a` (left), push `(a+b)`   | (a+b)                |

Output: **(a+b)**

**Test Case 2:** `abc*+`

| Char | Action                                                | Stack (bottom → top) |
|------|-------------------------------------------------------|----------------------|
| a    | operand, push                                         | a                    |
| b    | operand, push                                         | a b                  |
| c    | operand, push                                         | a b c                |
| *    | pop `c` (right), pop `b` (left), push `(b*c)`         | a (b*c)              |
| +    | pop `(b*c)` (right), pop `a` (left), push `(a+(b*c))` | (a+(b*c))            |

Output: **(a+(b\*c))**


## Java Solution

```java
import java.io.*;

public class Solution {

    static String toInfix(String s) {
        int n = s.length();
        String[] stack = new String[n];
        int top = 0;

        // Scan from left to right
        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (Character.isLetter(c)) {
                stack[top++] = String.valueOf(c);
            } else {
                String right = stack[--top];  // first popped is the RIGHT operand
                String left = stack[--top];   // second popped is the LEFT operand
                stack[top++] = "(" + left + c + right + ")";
            }
        }

        return stack[0];
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            sb.append(toInfix(s)).append("\n");
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $L$ be the length of the expression and $T$ the number of test cases.

### Total Time Complexity:
$O(L)$ stack operations per test case. Building the combined strings costs extra copying in the worst case, so the total work is at most $O(L^2)$ characters copied per test case, which is small for $L \le 16000$.

### Space Complexity:
$O(L)$ for the stack and the resulting string (the output has up to about $2L$ characters).
