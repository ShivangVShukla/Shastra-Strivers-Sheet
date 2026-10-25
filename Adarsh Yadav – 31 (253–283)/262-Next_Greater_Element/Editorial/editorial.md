# Editorial: Next Greater Element


## Difficulty

Medium


## Prerequisites
- Stack (LIFO)
- Monotonic Stack


## Problem Statement

Given an array `arr` of size `n`, for every element find the nearest element on its right that is **strictly greater** than it. If there is none, the answer for that element is `-1`.


## Explanation & Approach

### Step 1: The Brute Force Is Too Slow

For each element we could scan to the right until we find a larger value. In the worst case (for example a strictly decreasing array) this checks about n²/2 pairs. With n up to 10^5 that is about 5 × 10^9 comparisons, which is far too slow.

### Step 2: Keep the Unanswered Elements on a Stack

Scan the array from left to right and keep a stack of **indices whose next greater element has not been found yet**.

When we reach a new element `arr[i]`:

- While the stack is not empty and the element at the top is **smaller** than `arr[i]`, then `arr[i]` is the next greater element of that top element. Pop it and record `res[top] = arr[i]`.
- Then push `i` onto the stack (its own answer is still unknown).

Elements left on the stack at the end have no greater element to their right, so their answer stays `-1`.

### Step 3: Why the Stack Works

The stack values are always **non-increasing from bottom to top**: before pushing `i`, we have popped everything smaller than `arr[i]`, so everything below is `>= arr[i]`. Therefore, once the top is not smaller than `arr[i]`, nothing below it can be smaller either, and we can stop popping.

Each index is pushed once and popped at most once, so the total work is O(n).

### Step 4: Things to Watch For

- **Strictly greater.** Equal elements do not count. For `[5, 5, 5, 5]` the answer is `-1 -1 -1 -1`. Pop only while `arr[top] < arr[i]`, not `<=`.
- **Default answer is -1.** Initialise the result array with `-1`, so elements never popped keep it.
- **Large values.** Values go up to 10^9, which still fits in an `int`, but use fast input and a `StringBuilder` for output because n is up to 10^5.


## Example

### Input:

```text
1
4
1 3 2 4
```

### Trace:

| i | arr[i] | Pops (index → answer)           | Stack of indices (bottom → top) | res           |
|---|--------|----------------------------------|----------------------------------|---------------|
| 0 | 1      | none                             | 0                                | -1 -1 -1 -1   |
| 1 | 3      | 0 → 3                            | 1                                | 3 -1 -1 -1    |
| 2 | 2      | none (3 is not smaller than 2)   | 1 2                              | 3 -1 -1 -1    |
| 3 | 4      | 2 → 4, 1 → 4                     | 3                                | 3 4 4 -1      |

Output: **3 4 4 -1**


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
            Arrays.fill(res, -1);

            // Stack of indices whose next greater element is not found yet.
            // Their values are non-increasing from bottom to top.
            int[] stack = new int[n];
            int top = 0;

            for (int i = 0; i < n; i++) {
                while (top > 0 && arr[stack[top - 1]] < arr[i]) {
                    res[stack[--top]] = arr[i];
                }
                stack[top++] = i;
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
$O(N)$ per test case, since every index is pushed once and popped at most once, so $O(T \cdot N)$ overall.

### Space Complexity:
$O(N)$ for the stack and the result array.
