# Editorial — Count Number of Nice Subarrays

# Difficulty:
Medium  

# Prerequisites:
- Sliding Window
- Two Pointers
- Prefix Sum / Frequency Counting

## Problem Statement

Count contiguous subarrays containing exactly `k` odd numbers.

## Explanation & Approach

The direct requirement is **exactly `k` odd numbers**. A standard sliding window is naturally suited to counting subarrays with **at most** `k` odd numbers.

Use the identity:

`exactly(k) = atMost(k) - atMost(k - 1)`

For `atMost(x)`:
1. Keep a window `[left, right]`.
2. Increase the odd count whenever `nums[right]` is odd.
3. While the window has more than `x` odd numbers, move `left` forward and remove the contribution of `nums[left]`.
4. Once valid, every subarray ending at `right` and starting anywhere from `left` to `right` has at most `x` odd numbers. Therefore add `right - left + 1`.

The difference between the two at-most counts leaves exactly the subarrays with `k` odd numbers.

## Example / Trace

For `nums = [1,1,2,1,1]`, `k = 3`:

- `atMost(3)` counts every subarray containing at most three odd numbers.
- `atMost(2)` counts every subarray containing at most two odd numbers.
- Their difference is the number containing exactly three odd numbers.

The answer is `2`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static long atMost(int[] nums, int k) {
        if (k < 0) return 0;
        int left = 0;
        int odd = 0;
        long count = 0;

        for (int right = 0; right < nums.length; right++) {
            if ((nums[right] & 1) == 1) odd++;

            while (odd > k) {
                if ((nums[left] & 1) == 1) odd--;
                left++;
            }

            count += right - left + 1L;
        }
        return count;
    }

    public static long countNiceSubarrays(int[] nums, int k) {
        return atMost(nums, k) - atMost(nums, k - 1);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int[] nums = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) nums[i] = Integer.parseInt(st.nextToken());
        System.out.println(countNiceSubarrays(nums, k));
    }
}
```

## Complexity

- **Time:** `O(n)` because each pointer moves only forward.
- **Space:** `O(1)` apart from the input array.

The answer uses `long` because the number of subarrays can be as large as `n(n+1)/2`.

## Edge Cases

- `k = 1`.
- `k = n`.
- All elements are even: answer is `0` for every valid `k`.
- All elements are odd.
- Only one element.
- Very large `n`, where the answer may exceed a 32-bit integer.

## Why the Approach Works

For a fixed right endpoint, once the sliding window has at most `x` odd numbers, every start position from `left` through `right` produces a valid subarray. Thus `atMost(x)` counts every subarray with at most `x` odd numbers exactly once. Subtracting the count for `k-1` from the count for `k` removes all subarrays having fewer than `k` odd numbers and leaves exactly those having `k` odd numbers.
