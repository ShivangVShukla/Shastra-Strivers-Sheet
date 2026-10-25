# Editorial: Prefix to Postfix Conversion


## Difficulty

Easy


## Prerequisites
- Stack (LIFO)
- Prefix (Polish) and Postfix (Reverse Polish) notation


## Problem Statement

Given a valid prefix expression (operators `+ - * / ^` before their operands; operands are lowercase letters or digits), convert it into postfix notation, where each operator comes after its operands.


## Explanation & Approach

### Step 1: Read Right to Left

In prefix notation an operator comes before its two operands. If we scan the string from **right to left**, both operands of an operator are always seen before the operator itself, which is exactly the order a stack needs.

### Step 2: Algorithm

Scan the string from the last character to the first, with an empty stack of strings:

- **Operand:** push it as a one-character string.
- **Operator `c`:** pop two strings. The **first** popped is the **left** operand, the **second** popped is the **right** operand. Push `left + right + c` (the operator goes after both operands).

At the end the stack holds exactly one string: the answer.

### Step 3: Why the First Pop Is the Left Operand

Scanning right to left, the right operand is pushed first and the left operand is pushed later, so the left operand sits on top. Swapping the two gives wrong answers for non-commutative operators: `-ab` would become `ba-` instead of `ab-`.

### Step 4: Things to Watch For

- **Operand order.** First pop = left, second pop = right.
- **Operator goes last.** Build `left + right + c`, not `c + left + right`.
- **Same length.** The postfix string has exactly as many characters as the prefix string (no parentheses are added), and operands keep their relative order.


## Example

### Input:

```text
2
+ab
*+ab-cd
```

### Trace:

**Test Case 1:** `+ab`

| Char (right to left) | Action                                      | Stack (bottom → top) |
|----------------------|---------------------------------------------|----------------------|
| b                    | operand, push                               | b                    |
| a                    | operand, push                               | b a                  |
| +                    | pop `a` (left), pop `b` (right), push `ab+` | ab+                  |

Output: **ab+**

**Test Case 2:** `*+ab-cd`

| Char (right to left) | Action                                        | Stack (bottom → top) |
|----------------------|-----------------------------------------------|----------------------|
| d                    | operand, push                                 | d                    |
| c                    | operand, push                                 | d c                  |
| -                    | pop `c` (left), pop `d` (right), push `cd-`   | cd-                  |
| b                    | operand, push                                 | cd- b                |
| a                    | operand, push                                 | cd- b a              |
| +                    | pop `a` (left), pop `b` (right), push `ab+`   | cd- ab+              |
| *                    | pop `ab+` (left), pop `cd-` (right), push     | ab+cd-*              |

Output: **ab+cd-\***


## Java Solution

```java
import java.io.*;

public class Solution {

    static String toPostfix(String s) {
        int n = s.length();
        String[] stack = new String[n];
        int top = 0;

        // Scan from right to left
        for (int i = n - 1; i >= 0; i--) {
            char c = s.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                stack[top++] = String.valueOf(c);
            } else {
                String left = stack[--top];   // first popped is the LEFT operand
                String right = stack[--top];  // second popped is the RIGHT operand
                stack[top++] = left + right + c;
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
            sb.append(toPostfix(s)).append("\n");
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $L$ be the length of the expression and $T$ the number of test cases.

### Total Time Complexity:
$O(L)$ stack operations per test case. Building the combined strings costs extra copying in the worst case, so the total work is at most $O(L^2)$ characters copied per test case, which is tiny for $L \le 10^4$.

### Space Complexity:
$O(L)$ for the stack and the resulting string.
