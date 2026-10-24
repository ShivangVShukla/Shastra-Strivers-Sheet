# Max Consecutive Ones III — Editorial

## Difficulty
Medium

## Prerequisites
- Arrays
- Sliding Window
- Two Pointers
- Frequency/count tracking

## Problem Statement
Given a binary array and an integer `k`, find the maximum length of a contiguous subarray that can be converted into all `1`s by flipping at most `k` zeroes.

The important phrase is:

> at most `k` zeroes

Therefore, a window is valid exactly when the number of zeroes inside it is less than or equal to `k`.

## Key Observation

Suppose the current window is:

```text
[left ........ right]
```

If this window contains at most `k` zeroes, then we can flip those zeroes and make the entire window consist of `1`s.

So the problem becomes:

> Find the longest subarray containing at most `k` zeroes.

This is a classic sliding-window problem.

## Sliding Window Approach

Maintain:

```text
left = beginning of current window
right = end of current window
zeros = number of zeroes inside the window
```

For every `right`:

1. Add `nums[right]` to the window.
2. If it is `0`, increment `zeros`.
3. If `zeros > k`, move `left` forward.
4. Whenever a zero leaves the window, decrement `zeros`.
5. Update the maximum window length.

The window is valid whenever:

```text
zeros <= k
```

## Example Trace

Consider:

```text
nums = [1,1,1,0,0,0,1,1,1,1,0]
k = 2
```

Start with:

```text
left = 0
zeros = 0
```

Expand the right pointer.

When the window contains:

```text
1 1 1 0 0
```

there are two zeroes, so the window is valid.

When another zero enters:

```text
1 1 1 0 0 0
```

we now have three zeroes.

Since:

```text
3 > k
```

move `left` forward until only two zeroes remain.

The longest valid window eventually has length:

```text
6
```

## Why We Do Not Need to Actually Flip

We never modify the array.

If a window contains `x` zeroes and:

```text
x <= k
```

then all those zeroes can be flipped.

Therefore, the length of the window itself is the answer candidate.

This saves unnecessary work.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    public static int longestOnes(int[] nums, int k) {
        int left = 0;
        int zeros = 0;
        int answer = 0;

        for (int right = 0; right < nums.length; right++) {

            if (nums[right] == 0) {
                zeros++;
            }

            while (zeros > k) {
                if (nums[left] == 0) {
                    zeros--;
                }

                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        return answer;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st =
            new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        int[] nums = new int[n];

        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < n; i++) {
            nums[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(longestOnes(nums, k));
    }
}
```

## Complexity Analysis

Let `n` be the length of the array.

### Time Complexity

The right pointer moves from left to right once.

The left pointer also moves only forward.

Therefore, each element enters and leaves the window at most once.

```text
O(n)
```

### Space Complexity

Only a few integer variables are maintained.

```text
O(1)
```

## Edge Cases

### 1. `k = 0`

No zero can be flipped.

The answer is simply the longest existing sequence of `1`s.

### 2. All Ones

Example:

```text
1 1 1 1 1
```

The answer is `5` for any valid `k`.

### 3. All Zeroes

If:

```text
k = 2
```

then at most two zeroes can be flipped, so the answer is `2`.

### 4. `k` Larger Than the Number of Zeroes

All zeroes can be flipped, so the answer is the entire array length.

### 5. Single Element

For:

```text
[0], k = 0
```

answer is `0`.

For:

```text
[0], k = 1
```

answer is `1`.

## Why the Approach Works

At every step, the sliding window is adjusted until it contains at most `k` zeroes.

Any such window can be converted completely into `1`s by flipping its zeroes.

Therefore, the largest valid window is exactly the maximum number of consecutive ones obtainable using at most `k` flips.

Since both pointers only move forward, the algorithm runs in linear time.
