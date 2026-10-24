# Editorial — Maximum Points You Can Obtain from Cards

## Difficulty
Medium

## Prerequisites
- Arrays
- Sliding Window
- Prefix/suffix reasoning
- Basic optimization

## Problem Statement

You have `n` cards in a row and must take exactly `k` cards. At every move, you may take a card from either the left end or the right end.

The goal is to maximize the total points.

## Explanation & Approach

A direct approach could try every possible split:

- take 0 from the left and `k` from the right
- take 1 from the left and `k-1` from the right
- ...
- take `k` from the left and 0 from the right

That works in `O(k)` if prefix sums are used.

An even cleaner sliding-window interpretation is:

If exactly `k` cards are taken, exactly `n-k` cards remain in the middle.

So:

`maximum selected score = total sum - minimum sum of a window of length n-k`

Therefore:

1. Calculate the total sum of all cards.
2. If `k == n`, the answer is the total sum.
3. Find the minimum sum of every contiguous window of length `n-k`.
4. Subtract that minimum window sum from the total.

### Example

For:

`[1, 2, 3, 4, 5, 6, 1]`, `k = 3`

Then `n-k = 4`.

Possible windows of 4 cards include:

- `[1,2,3,4]` → 10
- `[2,3,4,5]` → 14
- `[3,4,5,6]` → 18
- `[4,5,6,1]` → 16

The minimum remaining sum is `10`.

Total sum = `22`.

Answer = `22 - 10 = 12`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        long total = 0;
        int[] cards = new int[n];

        for (int i = 0; i < n; i++) {
            cards[i] = Integer.parseInt(st.nextToken());
            total += cards[i];
        }

        int windowSize = n - k;

        if (windowSize == 0) {
            System.out.println(total);
            return;
        }

        long windowSum = 0;

        for (int i = 0; i < windowSize; i++) {
            windowSum += cards[i];
        }

        long minWindow = windowSum;

        for (int right = windowSize; right < n; right++) {
            windowSum += cards[right];
            windowSum -= cards[right - windowSize];
            minWindow = Math.min(minWindow, windowSum);
        }

        System.out.println(total - minWindow);
    }
}
```

## Complexity

- Time: `O(n)`
- Space: `O(n)` for the input array.

The sliding-window calculation itself uses `O(1)` extra space.

## Edge Cases

1. `k = n` → take every card.
2. `k = 1` → choose the larger endpoint.
3. `n = 1` → the only card must be taken.
4. All card values are equal.
5. The best answer takes all cards from one side.
6. The best answer requires a mixture of left and right cards.
7. Large values require `long` for the total and answer.
