# Editorial — Subarrays with K Different Integers

## Difficulty
Hard

## Prerequisites
- Sliding Window
- Two Pointers
- Hash Map / Frequency Counting
- Counting subarrays

## Problem Statement

Given an integer array `nums` and an integer `k`, count the number of contiguous subarrays containing exactly `k` distinct integers.

## Key Observation

Counting subarrays with **exactly** `k` distinct values directly is awkward.

Instead, use:

`exactly(k) = atMost(k) - atMost(k - 1)`

Why?

- `atMost(k)` counts subarrays having 0, 1, ..., `k` distinct values.
- `atMost(k - 1)` counts subarrays having 0, 1, ..., `k-1` distinct values.
- Subtracting removes every case below `k`, leaving exactly `k`.

## Counting At Most K

Use a sliding window `[left, right]`.

Maintain a frequency map for the values inside the window.

For every `right`:

1. Add `nums[right]`.
2. If a new distinct value appears, increase `distinct`.
3. While `distinct > k`, move `left` forward and remove frequencies.
4. Now the window contains at most `k` distinct values.
5. Every subarray ending at `right` and starting between `left` and `right` is valid.

Therefore, add:

`right - left + 1`

to the count.

## Example Trace

For:

`nums = [1,2,1,2,3]`, `k = 2`

`atMost(2)` counts all subarrays with at most two distinct values.

`atMost(1)` counts all subarrays containing only one distinct value.

Their difference is:

`12 - 5 = 7`

So the answer is `7`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static long atMostK(int[] nums, int k) {
        if (k <= 0) {
            return 0;
        }

        HashMap<Integer, Integer> freq = new HashMap<>();
        int left = 0;
        int distinct = 0;
        long count = 0;

        for (int right = 0; right < nums.length; right++) {
            int value = nums[right];

            if (!freq.containsKey(value) || freq.get(value) == 0) {
                distinct++;
            }

            freq.put(value, freq.getOrDefault(value, 0) + 1);

            while (distinct > k) {
                int leftValue = nums[left++];
                int newFreq = freq.get(leftValue) - 1;

                if (newFreq == 0) {
                    freq.remove(leftValue);
                    distinct--;
                } else {
                    freq.put(leftValue, newFreq);
                }
            }

            count += right - left + 1L;
        }

        return count;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int[] nums = new int[n];

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        long answer = atMostK(nums, k) - atMostK(nums, k - 1);
        System.out.println(answer);
    }
}
```

## Complexity

Each element enters and leaves the sliding window at most once for each `atMostK` call.

- Time: `O(n)` expected with a HashMap
- Space: `O(n)` in the worst case

## Edge Cases

1. `k = 1` → count all subarrays containing one distinct value.
2. `k = n` → only subarrays containing all distinct values qualify.
3. All elements are identical.
4. All elements are different.
5. Repeated patterns such as `1 2 1 2 3`.
6. Large answers require `long`.

## Why the Approach Works

The `atMostK` sliding window maintains the invariant that the current window contains no more than `k` distinct values. For every right endpoint, all starting positions from `left` through `right` form valid subarrays, giving exactly `right-left+1` possibilities. Taking the difference between the two at-most counts isolates subarrays with exactly `k` distinct values.
