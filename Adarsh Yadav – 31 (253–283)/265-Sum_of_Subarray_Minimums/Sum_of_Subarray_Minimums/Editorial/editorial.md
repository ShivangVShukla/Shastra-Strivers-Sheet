# Editorial: Sum of Subarray Minimums


## Difficulty

Medium


## Prerequisites
- Stack (LIFO)
- Monotonic Stack
- Modular arithmetic


## Problem Statement

Given an array `arr` of size `n`, find the sum of the minimum element of every contiguous subarray, modulo `10^9 + 7`.


## Explanation & Approach

### Step 1: Brute Force Is Too Slow

There are n(n+1)/2 subarrays. Even with a running minimum this is O(n²), about 5 × 10^9 steps for n = 10^5, which is far too slow.

### Step 2: Count How Many Subarrays Each Element Is the Minimum Of

Instead of looking at subarrays, look at each element. If `arr[i]` is the minimum of exactly `c_i` subarrays, then

```
answer = sum of arr[i] * c_i   (mod 10^9 + 7)
```

For `arr[i]` to be the minimum of a subarray `[l, r]`, the subarray must stay inside the region where nothing is smaller than `arr[i]`:

- `left`  = index of the previous element that is **strictly smaller** than `arr[i]` (or `-1` if none)
- `right` = index of the next element that is **smaller or equal** to `arr[i]` (or `n` if none)

Then `l` can be any of `left + 1 .. i` (that is `i - left` choices) and `r` any of `i .. right - 1` (that is `right - i` choices), so

```
c_i = (i - left) * (right - i)
```

### Step 3: Why One Side Is Strict and the Other Is Not

With equal values, a subarray may have several minimums, but it must be counted **once**. We give the credit to the **rightmost** minimum: equal elements on the left are allowed inside the range (strict `<` on the left), equal elements on the right are not (`<=` stops the range on the right). Every subarray then has exactly one rightmost minimum, so nothing is double counted or missed.

### Step 4: Finding left and right in One Pass

Scan with a stack of indices whose values are strictly increasing. For each new index `i` (and a sentinel at `i = n` with value `0`, since all values are at least 1):

- While the top `mid` has `arr[mid] >= arr[i]`, pop it. Then `right = i` (the first element `<=` it) and `left` is the new top of the stack (or `-1`), which is the previous strictly smaller element.
- Add `arr[mid] * (mid - left) * (right - mid)` to the answer.
- Push `i`.

Each index is pushed and popped once, so the total work is O(n).

### Step 5: Things to Watch For

- **Tie-breaking.** Use `>=` when popping (strict on the left, non-strict on the right). Using the same comparison on both sides double counts subarrays with repeated minimums.
- **Overflow.** `(mid - left) * (right - mid)` can reach about 2.5 × 10^9, so use `long`. Reduce it modulo `10^9 + 7` before multiplying by `arr[mid]` (up to 10^6) to stay below the `long` limit.
- **Take the modulo as you go**, not only at the end.


## Example

### Input:

```text
1
4
3 1 2 5
```

### Trace:

| Index | arr[i] | left | right | (i - left) * (right - i) | Contribution |
|-------|--------|------|-------|---------------------------|--------------|
| 0     | 3      | -1   | 1     | 1 * 1 = 1                 | 3            |
| 1     | 1      | -1   | 4     | 2 * 3 = 6                 | 6            |
| 2     | 2      | 1    | 4     | 1 * 2 = 2                 | 4            |
| 3     | 5      | 2    | 4     | 1 * 1 = 1                 | 5            |

Sum = 3 + 6 + 4 + 5 = **18**


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static final long MOD = 1_000_000_007L;

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

            // Each element is counted for the subarrays in which it is the
            // rightmost minimum:
            //   left  = index of the previous element STRICTLY smaller (or -1)
            //   right = index of the next element SMALLER OR EQUAL (or n)
            // The stack holds indices with strictly increasing values.
            int[] stack = new int[n];
            int top = 0;
            long ans = 0;

            for (int i = 0; i <= n; i++) {
                long cur = (i == n) ? 0 : arr[i]; // 0 acts as a sentinel (all values >= 1)

                while (top > 0 && arr[stack[top - 1]] >= cur) {
                    int mid = stack[--top];
                    int left = top == 0 ? -1 : stack[top - 1];
                    long count = (long) (mid - left) * (i - mid);
                    ans = (ans + (count % MOD) * arr[mid]) % MOD;
                }

                stack[top++] = i;
            }

            sb.append(ans).append('\n');
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $N$ be the size of the array and $T$ the number of test cases.

### Total Time Complexity:
$O(N)$ per test case (each index is pushed and popped once), so $O(T \cdot N)$ overall.

### Space Complexity:
$O(N)$ for the stack.
