# Editorial: Remove K Digits

## Difficulty

Medium

## Prerequisites
- Strings
- Stack
- Monotonic Stack / Greedy

## Problem Statement

Given a non-negative integer represented by a string `num`, remove exactly `k` digits so that the remaining number is as small as possible.

## Explanation & Approach

### Step 1: Greedy Observation

To make the number as small as possible, whenever the current digit is smaller than the digit immediately before it, the larger previous digit should be removed. This gives the number a smaller value at the earliest possible position.

### Step 2: Use a Monotonic Stack

Maintain the digits that are currently kept in a stack-like structure.

For every digit `c`:
- While there are digits in the stack, `k > 0`, and the last kept digit is greater than `c`, remove that last digit.
- Push `c` onto the stack.

This keeps the remaining prefix as small as possible.

### Step 3: Remove Remaining Digits

If the number is non-decreasing, the loop above may finish while `k > 0`. In that case, remove the last `k` digits because they have the least effect on the earlier prefix.

### Step 4: Remove Leading Zeros

After all removals, skip leading zeros. If nothing remains, return `0`.

### Step 5: Complexity

Each digit is pushed once and popped at most once.

- Time Complexity: `O(n)`
- Space Complexity: `O(n)`

## Example

### Input:
```text
1432219
3
```

### Trace:

- Keep `1`.
- `4` is larger than `1`, so keep `4` initially.
- When `3` arrives, remove `4` because `3 < 4`.
- Continue applying the same greedy rule until three digits have been removed.
- The remaining number is `1219`.

### Output:
```text
1219
```

## Java Solution

```java
import java.io.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String num = br.readLine().trim();
        int k = Integer.parseInt(br.readLine().trim());

        System.out.println(removeKdigits(num, k));
    }

    static String removeKdigits(String num, int k) {
        char[] stack = new char[num.length()];
        int top = 0;

        for (int i = 0; i < num.length(); i++) {
            char c = num.charAt(i);
            while (top > 0 && k > 0 && stack[top - 1] > c) {
                top--;
                k--;
            }
            stack[top++] = c;
        }

        top -= k;

        int start = 0;
        while (start < top && stack[start] == '0') {
            start++;
        }

        if (start == top) {
            return "0";
        }
        return new String(stack, start, top - start);
    }
}
```
