# Editorial: Next Greater Element - 2


## Difficulty

Medium


## Prerequisites
- Stack (LIFO)
- Monotonic Stack
- Circular arrays


## Problem Statement

Given a circular integer array `arr` (after the last element comes the first one again), find for every element the first element **strictly greater** than it met while moving clockwise. If there is none, the answer is `-1`.


## Explanation & Approach

### Step 1: Simulate the Circle by Walking Twice

For an element at index `i`, the elements we meet are `i+1, i+2, ..., n-1, 0, 1, ..., i-1`. That is the same as reading the array written **twice** (`arr` followed by `arr` again) starting from `i+1`, and looking at most `n - 1` steps ahead.

Instead of building the doubled array, we walk over indices `2n-1` down to `0` and use `arr[i % n]` as the value.

### Step 2: Monotonic Stack of Values

Keep a stack of candidate values, going from the right to the left:

- For the current value `cur = arr[i % n]`, pop every value on the stack that is **less than or equal to** `cur`. They can never be a strictly greater answer for `cur` or for anything to its left that `cur` would block.
- If `i < n`, the answer for index `i` is the value now on top of the stack, or `-1` if the stack is empty.
- Push `cur`.

The first `n` iterations (indices `2n-1` down to `n`) only fill the stack with the wrapped-around part; no answers are recorded there.

### Step 3: Why It Works

The stack values are strictly decreasing from bottom to top, and the top is always the nearest greater candidate on the right (including wrapped elements). Each element is pushed and popped at most twice in total (once per pass), so the total work is O(n).

### Step 4: Things to Watch For

- **Strictly greater.** Pop while `top <= cur`, not `<`. Equal values must not become an answer.
- **The maximum has no answer.** Every element equal to the global maximum gets `-1`, since nothing is strictly larger anywhere in the circle.
- **Record answers only for `i < n`.** Indices from `n` to `2n-1` are copies used only to prepare the stack.
- **An element must not be its own answer.** This is automatic because the equal element is popped before the answer is read.
- **Use fast I/O.** `n` goes up to 10^5, so use a tokenizer for input and a `StringBuilder` for output.


## Example

### Input:

```text
1
6
5 7 1 7 6 0
```

### Trace:

Values by index: `arr = [5, 7, 1, 7, 6, 0]`, `n = 6`. The walk goes over `i = 11` down to `0`, with `cur = arr[i % 6]`.

| i  | cur | Pops (<= cur)  | Answer recorded | Stack (bottom → top) |
|----|-----|----------------|-----------------|----------------------|
| 11 | 0   | none           | (i >= n)        | 0                    |
| 10 | 6   | 0              | (i >= n)        | 6                    |
| 9  | 7   | 6              | (i >= n)        | 7                    |
| 8  | 1   | none           | (i >= n)        | 7 1                  |
| 7  | 7   | 1, 7           | (i >= n)        | 7                    |
| 6  | 5   | none           | (i >= n)        | 7 5                  |
| 5  | 0   | none           | res[5] = 5      | 7 5 0                |
| 4  | 6   | 0, 5           | res[4] = 7      | 7 6                  |
| 3  | 7   | 6, 7           | res[3] = -1     | 7                    |
| 2  | 1   | none           | res[2] = 7      | 7 1                  |
| 1  | 7   | 1, 7           | res[1] = -1     | 7                    |
| 0  | 5   | none           | res[0] = 7      | 7 5                  |

Output: **7 -1 7 -1 7 5**


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        st.nextToken();
        int t = (int) st.nval;

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            st.nextToken();
            int n = (int) st.nval;

            long[] arr = new long[n];
            for (int i = 0; i < n; i++) {
                st.nextToken();
                arr[i] = (long) st.nval;
            }

            long[] res = new long[n];

            // Walk the array twice (indices 2n-1 down to 0) to simulate the circle.
            // The stack holds candidate values; from bottom to top they are
            // strictly decreasing.
            long[] stack = new long[n];
            int top = 0;

            for (int i = 2 * n - 1; i >= 0; i--) {
                long cur = arr[i % n];

                while (top > 0 && stack[top - 1] <= cur) {
                    top--;
                }

                if (i < n) {
                    res[i] = top == 0 ? -1 : stack[top - 1];
                }

                stack[top++] = cur;
            }

            for (int i = 0; i < n; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(res[i]);
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $N$ be the size of the array and $T$ the number of test cases.

### Total Time Complexity:
$O(N)$ per test case (the array is walked twice, and each value is pushed and popped at most once per pass), so $O(T \cdot N)$ overall.

### Space Complexity:
$O(N)$ for the stack and the result array.
