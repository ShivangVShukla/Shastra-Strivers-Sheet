# Editorial: Infix to Postfix Conversion


## Difficulty

Medium


## Prerequisites
- Stack (LIFO)
- Operator precedence and associativity
- Shunting-Yard algorithm


## Problem Statement

Convert a valid infix expression (single-character operands, binary operators `+ - * / ^`, and parentheses) into postfix notation (Reverse Polish Notation), with no spaces. `^` has the highest precedence and is right-associative; `* /` come next; `+ -` are lowest; all except `^` are left-associative.


## Explanation & Approach

### Step 1: Operands Keep Their Order

In postfix, operands appear in the same relative order as in the infix string, so every operand can be written to the output as soon as it is read. Only the operators need to be delayed.

### Step 2: Delay Operators on a Stack

An operator must be written after both of its operands. So we hold operators on a stack and release them when we know no later operator should be applied before them. This is the **Shunting-Yard algorithm**.

Scan the expression left to right:

- **Operand:** append to the output.
- **`(`:** push onto the stack.
- **`)`:** pop operators to the output until `(` is on top, then discard the `(`.
- **Operator `c`:** while the stack top is not `(` and the top operator should run before `c`, pop it to the output. Then push `c`.

After the scan, pop everything left on the stack to the output.

### Step 3: When Should the Top Run Before `c`?

Let `p(x)` be the precedence of `x` (`^` = 3, `* /` = 2, `+ -` = 1). The top operator runs first if:

- `p(top) > p(c)`, or
- `p(top) == p(c)` and `c` is **left-associative** (`c != '^'`).

For `^` (right-associative), equal precedence does not pop, so `a^b^c` becomes `abc^^` (meaning `a^(b^c)`). For `-` (left-associative), `a-b-c` becomes `ab-c-` (meaning `(a-b)-c`).

### Step 4: Things to Watch For

- **Associativity of `^`.** Using `>=` for every operator wrongly turns `a^b^c` into `ab^c^`.
- **Never pop `(` because of an operator.** A `(` is only removed when its matching `)` arrives, so stop popping when `(` is on top.
- **Flush the stack at the end.** Operators left on the stack must be appended after the scan.
- **Parentheses never appear in the output.**


## Example

### Input:

```text
2
a+b*c
(a+b)*c
```

### Trace:

**Test Case 1:** `a+b*c`

| Char | Action                                  | Stack (bottom → top) | Output |
|------|-----------------------------------------|----------------------|--------|
| a    | operand                                 |                      | a      |
| +    | push                                    | +                    | a      |
| b    | operand                                 | +                    | ab     |
| *    | `p(+)=1 < p(*)=2`, no pop, push         | + *                  | ab     |
| c    | operand                                 | + *                  | abc    |
| end  | pop all                                 |                      | abc*+  |

Output: **abc\*+**

**Test Case 2:** `(a+b)*c`

| Char | Action                                  | Stack (bottom → top) | Output |
|------|-----------------------------------------|----------------------|--------|
| (    | push                                    | (                    |        |
| a    | operand                                 | (                    | a      |
| +    | top is `(`, push                        | ( +                  | a      |
| b    | operand                                 | ( +                  | ab     |
| )    | pop `+`, discard `(`                    |                      | ab+    |
| *    | push                                    | *                    | ab+    |
| c    | operand                                 | *                    | ab+c   |
| end  | pop all                                 |                      | ab+c*  |

Output: **ab+c\***


## Java Solution

```java
import java.io.*;

public class Solution {

    static int prec(char c) {
        switch (c) {
            case '^':
                return 3;
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

    static String toPostfix(String s) {
        StringBuilder out = new StringBuilder();
        char[] stack = new char[s.length()];
        int top = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            if (Character.isLetterOrDigit(c)) {
                out.append(c);
            } else if (c == '(') {
                stack[top++] = c;
            } else if (c == ')') {
                while (top > 0 && stack[top - 1] != '(') {
                    out.append(stack[--top]);
                }
                top--; // discard '('
            } else {
                // '^' is right-associative: pop only strictly higher precedence.
                // Other operators are left-associative: pop higher or equal.
                while (top > 0 && stack[top - 1] != '('
                        && (prec(stack[top - 1]) > prec(c)
                        || (prec(stack[top - 1]) == prec(c) && c != '^'))) {
                    out.append(stack[--top]);
                }
                stack[top++] = c;
            }
        }

        while (top > 0) {
            out.append(stack[--top]);
        }

        return out.toString();
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
$O(L)$ per test case, since each character is pushed and popped at most once, so $O(T \cdot L)$ overall.

### Space Complexity:
$O(L)$ for the operator stack and the output string.
