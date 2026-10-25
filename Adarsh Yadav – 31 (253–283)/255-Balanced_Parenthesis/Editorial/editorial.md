# Editorial: Balanced Parenthesis


## Difficulty

Easy


## Prerequisites
- Stack (LIFO)
- Strings


## Problem Statement

Given a string made only of the characters `(`, `)`, `{`, `}`, `[` and `]`, determine whether it is balanced: every opening bracket must be closed by a bracket of the same type, in the correct order.


## Explanation & Approach

### Step 1: The Most Recent Opener Must Close First

When we meet a closing bracket, it must match the **most recent** opening bracket that is still unclosed. "Most recent first" is exactly last-in-first-out, so a stack fits perfectly.

### Step 2: Algorithm

Scan the string left to right with an empty stack:

- **Opening bracket** (`(`, `[`, `{`): push it.
- **Closing bracket:**
  - if the stack is empty, there is nothing to match, so the answer is **False**;
  - otherwise pop the top. If it is not the matching opener, the answer is **False**.

After the scan, the answer is **True** only if the stack is empty (no opener is left unclosed).

### Step 3: Quick Shortcut

A balanced string always has an even length (brackets come in pairs), so an odd-length string can be rejected immediately.

### Step 4: Things to Watch For

- **Pop on an empty stack.** Inputs like `)(` or `())` hit a closer with nothing to match. Check for an empty stack before popping.
- **Leftover openers.** Inputs like `((` or `[()` finish the scan without errors but leave openers on the stack. Always check the stack is empty at the end.
- **Matching types, not just counts.** `([)]` has equal numbers of each type but is invalid, so compare the actual popped bracket with the closer.


## Example

### Input:

```text
3
()[{}()]
[()
{[()]}
```

### Trace:

**Test Case 1:** `()[{}()]`

| Char | Action        | Stack (bottom → top) |
|------|---------------|----------------------|
| (    | push          | (                    |
| )    | pop `(` match | (empty)              |
| [    | push          | [                    |
| {    | push          | [ {                  |
| }    | pop `{` match | [                    |
| (    | push          | [ (                  |
| )    | pop `(` match | [                    |
| ]    | pop `[` match | (empty)              |

Stack is empty → **True**

**Test Case 2:** `[()` has odd length → **False** (a scan also ends with `[` left on the stack)

**Test Case 3:** `{[()]}` opens `{`, `[`, `(`, then closes `)`, `]`, `}` in exact reverse order, stack ends empty → **True**


## Java Solution

```java
import java.io.*;

public class Solution {

    static boolean isBalanced(String s) {
        if (s.length() % 2 != 0) {
            return false;
        }

        char[] stack = new char[s.length()];
        int top = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(' || c == '[' || c == '{') {
                stack[top++] = c;
            } else {
                if (top == 0) {
                    return false;
                }
                char open = stack[--top];
                if ((c == ')' && open != '(')
                        || (c == ']' && open != '[')
                        || (c == '}' && open != '{')) {
                    return false;
                }
            }
        }

        return top == 0;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            String s = br.readLine().trim();
            sb.append(isBalanced(s) ? "True" : "False").append("\n");
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $L$ be the length of the string and $T$ the number of test cases.

### Total Time Complexity:
$O(L)$ per test case (each character is pushed and popped at most once), so $O(T \cdot L)$ overall.

### Space Complexity:
$O(L)$ for the stack in the worst case (a string of only openers).
