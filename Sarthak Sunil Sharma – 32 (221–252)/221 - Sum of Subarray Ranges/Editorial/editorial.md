# Editorial: Sum of Subarray Ranges

## Difficulty

Medium

## Prerequisites
- Arrays
- Stack
- Monotonic Stack
- Contribution Technique

## Problem Statement

For every non-empty contiguous subarray, its range is `maximum - minimum`. We need the sum of these ranges for every possible subarray.

## Explanation & Approach

### Step 1: Separate Maximum and Minimum Contributions

For every subarray, `range = maximum - minimum`. Therefore:

`sum of ranges = sum of subarray maximums - sum of subarray minimums`.

Instead of explicitly constructing every subarray, calculate how many subarrays use each element as their maximum and how many use it as their minimum.

### Step 2: Count the Subarrays Where an Element Is the Minimum

For an element at index `i`, find:
- `left`: the nearest index to the left whose value is strictly smaller.
- `right`: the nearest index to the right whose value is smaller or equal.

Then the element can be the chosen minimum for `(i - left) * (right - i)` subarrays.

To handle duplicate values consistently, one side uses a strict comparison and the other uses a non-strict comparison. This prevents the same subarray from being counted more than once.

### Step 3: Count the Subarrays Where an Element Is the Maximum

Similarly, find:
- the nearest strictly greater element on one side, and
- the nearest greater-or-equal element on the other side.

The number of subarrays for which `nums[i]` contributes as the maximum is again the product of the available choices on the left and right.

### Step 4: Use Monotonic Stacks

The nearest smaller and greater boundaries can all be found in linear time using monotonic stacks. Each index is pushed and popped at most once per traversal.

For every index, add its maximum contribution and subtract its minimum contribution from the answer.

### Step 5: Use 64-bit Arithmetic

The answer can be much larger than a 32-bit integer because there can be `N * (N + 1) / 2` subarrays and each element can have magnitude up to `10^9`. Java `long` is therefore required for the accumulated answer and contribution products.

## Example

### Input:

```text
3
1 2 3
```

### Trace:

The subarrays are:
- `[1]` -> range `0`
- `[2]` -> range `0`
- `[3]` -> range `0`
- `[1,2]` -> range `1`
- `[2,3]` -> range `1`
- `[1,2,3]` -> range `2`

Therefore the answer is `0 + 0 + 0 + 1 + 1 + 2 = 4`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static long subArrayRanges(int[] nums) {
        int n = nums.length;
        long answer = 0;
        int[] stack = new int[n];
        int top;

        // Sum of contributions as maximum.
        top = -1;
        for (int i = 0; i <= n; i++) {
            while (top >= 0 && (i == n || nums[stack[top]] <= nums[i])) {
                int mid = stack[top--];
                int left = top >= 0 ? stack[top] : -1;
                int right = i;
                answer += (long) nums[mid] * (mid - left) * (right - mid);
            }
            if (i < n) stack[++top] = i;
        }

        // Sum of contributions as minimum.
        top = -1;
        for (int i = 0; i <= n; i++) {
            while (top >= 0 && (i == n || nums[stack[top]] >= nums[i])) {
                int mid = stack[top--];
                int left = top >= 0 ? stack[top] : -1;
                int right = i;
                answer -= (long) nums[mid] * (mid - left) * (right - mid);
            }
            if (i < n) stack[++top] = i;
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] nums = new int[n];
        int index = 0;
        while (index < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && index < n) {
                nums[index++] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(subArrayRanges(nums));
    }
}
```

## Time Complexity

Each monotonic-stack traversal is `O(N)`, because every index is pushed and popped at most once. The total time complexity is:

`O(N)`

## Space Complexity

The monotonic stack stores at most `N` indices:

`O(N)`

## Important Edge Cases

- A single-element array has answer `0`.
- An array containing equal values has answer `0`.
- Increasing and decreasing arrays exercise opposite stack directions.
- Negative values are allowed.
- Large positive and negative values require `long` arithmetic.
- Duplicate values must be handled consistently by strict/non-strict stack comparisons.
