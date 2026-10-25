# Editorial: Remove K Digits


## Difficulty

Medium


## Prerequisites
- Stack (LIFO)
- Greedy algorithms
- Monotonic Stack


## Problem Statement

Given a string `nums` of digits representing a non-negative integer and an integer `k`, remove exactly `k` digits so that the remaining number is as small as possible. The result must not have leading zeros, and if nothing is left, the answer is `"0"`.


## Explanation & Approach

### Step 1: What Makes a Number Smaller?

All results have the same length (`n - k` digits), so the number is smaller when its **leftmost** digits are smaller. Therefore we want the most significant digits to be as small as possible.

### Step 2: The Greedy Rule

Look at two neighbouring digits `a b`. If `a > b`, then deleting `a` puts the smaller digit `b` in its position, which makes the number smaller. So we should delete a digit whenever the digit after it is smaller.

### Step 3: Monotonic Stack

Scan the digits from left to right with a stack and `remaining = k`:

- While `remaining > 0`, the stack is not empty, and the top digit is **greater** than the current digit: pop the top and decrease `remaining`.
- Push the current digit.

After the scan, if `remaining > 0`, the stack digits are non-decreasing, so removing the largest ones means dropping the last `remaining` digits.

Finally, strip leading zeros. If nothing is left, print `0`.

### Step 4: Why It Works

The stack always holds the best possible prefix: every time a smaller digit arrives, bigger digits before it that can still be removed are discarded, since keeping them would make an earlier position larger. Each digit is pushed and popped at most once, so the total work is O(n).

### Step 5: Things to Watch For

- **Leading zeros.** `"10200"` with `k = 1` leaves `"0200"`, and the answer is `"200"`. Strip zeros after building the result.
- **Empty result.** If everything is removed, or only zeros remain, print `"0"`.
- **Leftover removals.** For `"12345"` with `k = 2` the loop never pops. Remove from the **end**, giving `"123"`.
- **Equal digits are not removed by the loop.** Only a strictly greater top is popped (`"112"` with `k = 1` gives `"11"`).
- **k can equal the length.** The answer is `"0"`.


## Example

### Input:

```text
1
1002991 3
```

### Trace:

| Digit | What happens                                   | Stack (bottom → top) | k left |
|-------|------------------------------------------------|----------------------|--------|
| 1     | push                                           | 1                    | 3      |
| 0     | top 1 > 0, pop 1; push 0                       | 0                    | 2      |
| 0     | top 0 is not > 0; push                         | 0 0                  | 2      |
| 2     | top 0 is not > 2; push                         | 0 0 2                | 2      |
| 9     | push                                           | 0 0 2 9              | 2      |
| 9     | push                                           | 0 0 2 9 9            | 2      |
| 1     | top 9 > 1, pop; top 9 > 1, pop; push 1        | 0 0 2 1              | 0      |

The stack is `0021`; strip leading zeros → **21**


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static String removeKDigits(String s, int k) {
        int n = s.length();
        char[] stack = new char[n];
        int top = 0;
        int remaining = k;

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            // A bigger digit followed by a smaller one should be removed
            while (remaining > 0 && top > 0 && stack[top - 1] > c) {
                top--;
                remaining--;
            }
            stack[top++] = c;
        }

        // If removals are left, the digits are non-decreasing: drop from the end
        top -= remaining;

        // Strip leading zeros
        int start = 0;
        while (start < top && stack[start] == '0') {
            start++;
        }

        return start == top ? "0" : new String(stack, start, top - start);
    }

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine().trim());

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            String s = st.nextToken();
            int k = Integer.parseInt(st.nextToken());
            sb.append(removeKDigits(s, k)).append('\n');
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $N$ be the length of `nums` and $T$ the number of test cases.

### Total Time Complexity:
$O(N)$ per test case (each digit is pushed and popped at most once), so $O(T \cdot N)$ overall.

### Space Complexity:
$O(N)$ for the stack.
