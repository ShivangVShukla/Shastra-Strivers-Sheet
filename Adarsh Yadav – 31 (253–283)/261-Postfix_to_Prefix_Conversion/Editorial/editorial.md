# Editorial: Postfix to Prefix Conversion


## Difficulty

Easy


## Prerequisites
- Stack (LIFO)
- Postfix (Reverse Polish) and Prefix (Polish) notation


## Problem Statement

Given a valid postfix expression (lowercase-letter operands, binary operators `+ - * /`, no spaces), convert it into prefix notation, where each operator comes before its operands.


## Explanation & Approach

### Step 1: Read Left to Right

In postfix notation an operator comes **after** its two operands. If we scan the string from **left to right**, both operands of an operator have always been seen by the time we reach it. A stack holds the sub-expressions built so far, each already written in prefix form.

### Step 2: Algorithm

Scan the string from the first character to the last, with an empty stack of strings:

- **Operand:** push it as a one-character string.
- **Operator `c`:** pop two strings. The **first** popped is the **right** operand, the **second** popped is the **left** operand. Push `c + left + right` (the operator goes in front of both operands).

At the end the stack holds exactly one string: the answer.

### Step 3: Why the First Pop Is the Right Operand

Scanning left to right, the left operand is pushed first and the right operand is pushed later, so the right operand sits on top. Swapping the two gives wrong answers for non-commutative operators: `ab-` would become `-ba` instead of `-ab`.

### Step 4: Things to Watch For

- **Operand order.** First pop = right, second pop = left.
- **Operator goes first.** Build `c + left + right`, not `left + right + c`.
- **Same length.** The prefix string has exactly as many characters as the postfix string (no parentheses are added), and operands keep their relative order.


## Example

### Input:

```text
2
ab+
abc*+d-
```

### Trace:

**Test Case 1:** `ab+`

| Char | Action                                       | Stack (bottom → top) |
|------|----------------------------------------------|----------------------|
| a    | operand, push                                | a                    |
| b    | operand, push                                | a b                  |
| +    | pop `b` (right), pop `a` (left), push `+ab`  | +ab                  |

Output: **+ab**

**Test Case 2:** `abc*+d-`

| Char | Action                                             | Stack (bottom → top) |
|------|----------------------------------------------------|----------------------|
| a    | operand, push                                      | a                    |
| b    | operand, push                                      | a b                  |
| c    | operand, push                                      | a b c                |
| *    | pop `c` (right), pop `b` (left), push `*bc`        | a *bc                |
| +    | pop `*bc` (right), pop `a` (left), push `+a*bc`    | +a*bc                |
| d    | operand, push                                      | +a*bc d              |
| -    | pop `d` (right), pop `+a*bc` (left), push          | -+a*bcd              |

Output: **-+a\*bcd**


## Java Solution

```java
import java.io.*;

public class Solution {

    static String toPrefix(String s) {
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
                stack[top++] = c + left + right;
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
            sb.append(toPrefix(s)).append("\n");
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $L$ be the length of the expression and $T$ the number of test cases.

### Total Time Complexity:
$O(L)$ stack operations per test case. Building the combined strings costs extra copying in the worst case, so the total work is at most $O(L^2)$ characters copied per test case, which is tiny for $L \le 1000$.

### Space Complexity:
$O(L)$ for the stack and the resulting string.
