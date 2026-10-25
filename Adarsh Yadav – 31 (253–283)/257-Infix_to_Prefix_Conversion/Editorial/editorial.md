# Editorial: Infix to Prefix Conversion


## Difficulty

Medium


## Prerequisites
- Stack (LIFO)
- Operator precedence and associativity
- Infix to Postfix (Shunting-Yard algorithm)


## Problem Statement

Convert a valid infix expression (lowercase-letter operands, binary operators `+ - * /`, and parentheses) into prefix (Polish) notation, with no spaces. `* /` have higher precedence than `+ -`, and all operators are left-associative.


## Explanation & Approach

### Step 1: Prefix Is a Mirror of Postfix

In prefix notation the operator comes **before** its operands. If we read the infix expression from right to left, the operands and operators appear in the mirror order of what postfix needs. So we can reuse the postfix algorithm on a reversed expression:

1. Reverse the infix string.
2. Swap every `(` with `)` and vice versa (reversing flips which bracket opens a group).
3. Convert the result to postfix with the stack method.
4. Reverse the postfix result. That is the prefix expression.

### Step 2: The One Rule That Changes

In normal infix-to-postfix, a left-associative operator pops the stack top when `p(top) >= p(c)`. After reversing the string, the order of equal-precedence operators is also reversed, so the rule must become **strictly greater**: pop only when `p(top) > p(c)`.

Check with `a-b-c` (should be `--abc`, meaning `(a-b)-c`):
- Reverse: `c-b-a`
- Postfix with strict `>`: `cba--`
- Reverse: `--abc` ✓

With `>=` we would get `-a-bc`, which means `a-(b-c)`. That is wrong.

### Step 3: The Postfix Pass

Scan the (reversed, swapped) string left to right:

- **Operand:** append to the output.
- **`(`:** push onto the stack.
- **`)`:** pop to the output until `(` is on top, then discard the `(`.
- **Operator `c`:** while the stack top is not `(` and `p(top) > p(c)`, pop it to the output. Then push `c`.

At the end, pop everything left on the stack to the output.

### Step 4: Things to Watch For

- **Use `>` not `>=`** for the pop rule (see Step 2).
- **Swap the parentheses** after reversing. Skipping this leaves unmatched groups.
- **Reverse the output at the end.** The postfix pass alone gives the reversed prefix.
- **Never pop `(` because of an operator.** It is only removed when its `)` arrives.


## Example

### Input:

```text
2
(a+b)*c
a+b*c
```

### Trace:

**Test Case 1:** `(a+b)*c`

Reverse and swap parentheses: `c*(b+a)`

| Char | Action                         | Stack (bottom → top) | Output |
|------|--------------------------------|----------------------|--------|
| c    | operand                        |                      | c      |
| *    | push                           | *                    | c      |
| (    | push                           | * (                  | c      |
| b    | operand                        | * (                  | cb     |
| +    | top is `(`, push               | * ( +                | cb     |
| a    | operand                        | * ( +                | cba    |
| )    | pop `+`, discard `(`           | *                    | cba+   |
| end  | pop all                        |                      | cba+*  |

Reverse `cba+*` → **\*+abc**

**Test Case 2:** `a+b*c`

Reverse: `c*b+a`

| Char | Action                                      | Stack (bottom → top) | Output |
|------|---------------------------------------------|----------------------|--------|
| c    | operand                                     |                      | c      |
| *    | push                                        | *                    | c      |
| b    | operand                                     | *                    | cb     |
| +    | `p(*)=2 > p(+)=1`, pop `*`, then push `+`   | +                    | cb*    |
| a    | operand                                     | +                    | cb*a   |
| end  | pop all                                     |                      | cb*a+  |

Reverse `cb*a+` → **+a\*bc**


## Java Solution

```java
import java.io.*;

public class Solution {

    static int prec(char c) {
        switch (c) {
            case '*':
            case '/':
                return 2;
            case '+':
            case '-':
                return 1;
            default:
                return 0;
        }
    }

    static String toPrefix(String s) {
        int n = s.length();

        // Step 1: reverse the expression and swap the parentheses
        char[] rev = new char[n];
        for (int i = 0; i < n; i++) {
            char c = s.charAt(n - 1 - i);
            if (c == '(') {
                c = ')';
            } else if (c == ')') {
                c = '(';
            }
            rev[i] = c;
        }

        // Step 2: convert to postfix. After the reversal, left-associative
        // operators must pop only strictly higher precedence.
        StringBuilder out = new StringBuilder();
        char[] stack = new char[n];
        int top = 0;

        for (int i = 0; i < n; i++) {
            char c = rev[i];

            if (Character.isLetter(c)) {
                out.append(c);
            } else if (c == '(') {
                stack[top++] = c;
            } else if (c == ')') {
                while (top > 0 && stack[top - 1] != '(') {
                    out.append(stack[--top]);
                }
                top--; // discard '('
            } else {
                while (top > 0 && stack[top - 1] != '(' && prec(stack[top - 1]) > prec(c)) {
                    out.append(stack[--top]);
                }
                stack[top++] = c;
            }
        }

        while (top > 0) {
            out.append(stack[--top]);
        }

        // Step 3: reverse the postfix result to get the prefix expression
        return out.reverse().toString();
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
$O(L)$ per test case (reverse, one stack pass, reverse), so $O(T \cdot L)$ overall.

### Space Complexity:
$O(L)$ for the reversed copy, the operator stack and the output.
