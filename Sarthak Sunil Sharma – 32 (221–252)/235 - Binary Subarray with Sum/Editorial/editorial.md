# Editorial — Binary Subarray with Sum

## Difficulty
Medium

## Prerequisites
- Arrays
- Sliding Window / Two Pointers
- Prefix sums
- Understanding of subarrays

## Problem Statement

Given a binary array `nums` and an integer `goal`, count the number of non-empty contiguous subarrays whose elements add up to exactly `goal`.

For example, for `nums = [1,1,0,1,0,0,1]` and `goal = 3`, there are 4 valid subarrays.

## Key Observation

Directly counting subarrays with sum exactly `goal` is slightly awkward with a sliding window. Instead, count subarrays with sum **at most** a value.

Let:

`atMost(k) = number of subarrays whose sum <= k`

Then every subarray with sum exactly `goal` is included in `atMost(goal)` but not in `atMost(goal - 1)`.

Therefore:

`exactly(goal) = atMost(goal) - atMost(goal - 1)`

This works especially well because the array contains only 0 and 1, so extending a window never decreases its sum and moving the left pointer never increases it.

## Explanation & Approach

### Step 1: Count subarrays with sum at most k

Maintain a window `[left, right]` and its current sum.

For every `right`:
1. Add `nums[right]` to `sum`.
2. If `sum > k`, move `left` forward until `sum <= k`.
3. Once the window is valid, every subarray ending at `right` and starting between `left` and `right` has sum at most `k`.
4. Add `right - left + 1` to the count.

### Step 2: Convert at-most to exact

Compute:

`atMost(goal) - atMost(goal - 1)`

If `goal == 0`, the second term is automatically 0 because no binary subarray can have a negative sum.

## Example / Trace

Consider:

`nums = [1, 1, 0, 1, 0, 0, 1]`

`goal = 3`

We count all subarrays with sum at most 3 and subtract all subarrays with sum at most 2. The subarrays whose sum changes from 2-or-less to 3-or-less are exactly those having sum 3.

The final result is `4`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static long atMost(int[] nums, int k) {
        if (k < 0) return 0;

        int left = 0;
        int sum = 0;
        long count = 0;

        for (int right = 0; right < nums.length; right++) {
            sum += nums[right];

            while (sum > k) {
                sum -= nums[left++];
            }

            count += right - left + 1L;
        }

        return count;
    }

    static long numberOfSubarraysWithSum(int[] nums, int goal) {
        return atMost(nums, goal) - atMost(nums, goal - 1);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int goal = Integer.parseInt(st.nextToken());

        int[] nums = new int[n];
        st = new StringTokenizer(br.readLine());
        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(numberOfSubarraysWithSum(nums, goal));
    }
}
```

## Complexity

- Time Complexity: `O(n)` because each element enters and leaves the sliding window at most once for each `atMost` calculation.
- Space Complexity: `O(1)` apart from the input array.

## Edge Cases

1. `goal = 0` — all-zero subarrays must be counted correctly.
2. All elements are `1`.
3. All elements are `0`.
4. `goal = n` — only subarrays containing every element equal to 1 can qualify.
5. A single-element array.
6. Alternating zeros and ones.

## Why the Approach Works

For a fixed `right`, once `[left, right]` is the smallest valid window with sum at most `k`, every starting index from `left` through `right` produces a valid subarray ending at `right`. Hence there are exactly `right - left + 1` such subarrays.

The difference between the counts for limits `goal` and `goal - 1` removes every subarray whose sum is below `goal`, leaving precisely the subarrays whose sum equals `goal`.
