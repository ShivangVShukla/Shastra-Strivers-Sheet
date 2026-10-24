# Editorial — Longest Substring with At Most K Distinct Characters

## Difficulty
Hard

## Prerequisites
- Strings
- Sliding Window
- Two Pointers
- Hash Map / Frequency Array

## Problem Statement

Given a string `s` and an integer `k`, find the longest contiguous substring containing at most `k` distinct characters.

## Explanation & Approach

We need to find a longest window satisfying:

`number of distinct characters <= k`

A brute-force approach would examine every substring and count its distinct characters, which can take `O(n^2)` or worse.

Instead, use a sliding window.

Maintain:

- `left` = left boundary of the current window
- `right` = right boundary
- `freq[c]` = number of occurrences of character `c` in the window
- `distinct` = number of characters whose frequency is greater than zero

For every `right`:

1. Add `s[right]` to the window.
2. If its frequency changes from `0` to `1`, increment `distinct`.
3. If `distinct > k`, move `left` forward.
4. Whenever a character's frequency becomes `0`, decrement `distinct`.
5. The window is now valid, so update the maximum length.

### Why does sliding window work?

If a window contains more than `k` distinct characters, extending it further cannot make it valid. The only way to restore validity is to remove characters from the left.

Once a window is valid, every shorter window ending at the same `right` is also valid. Therefore, keeping the largest valid window is sufficient.

## Example Trace

For `s = "eceba"` and `k = 2`:

- `e` → valid, length 1
- `ec` → valid, length 2
- `ece` → valid, length 3
- `eceb` → 3 distinct characters, shrink from left
- after shrinking → `ceb`, valid, length 3
- `ceba` → 3 distinct characters, shrink
- final maximum = 3

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
        String s = br.readLine();

        int[] freq = new int[26];
        int left = 0;
        int distinct = 0;
        int answer = 0;

        for (int right = 0; right < n; right++) {
            int idx = s.charAt(right) - 'a';

            if (freq[idx] == 0) {
                distinct++;
            }
            freq[idx]++;

            while (distinct > k) {
                int leftIdx = s.charAt(left) - 'a';
                freq[leftIdx]--;

                if (freq[leftIdx] == 0) {
                    distinct--;
                }
                left++;
            }

            answer = Math.max(answer, right - left + 1);
        }

        System.out.println(answer);
    }
}
```

## Complexity

- Time: `O(n)`
- Space: `O(1)` because the alphabet contains only 26 lowercase letters.

## Edge Cases

1. `k = 0` → no non-empty substring is valid, so answer is `0`.
2. `k = 1` → find the longest run of one character.
3. `k >= number of distinct characters in s` → answer is `n`.
4. All characters are identical → answer is `n` for any `k >= 1`.
5. Every character is different → the window continuously shrinks when it exceeds `k`.
6. `n = 1`.
7. Large strings require efficient `O(n)` processing.

## Why the Approach Works

The sliding window always restores the invariant `distinct <= k` after shrinking. Every right endpoint is processed once, and the left pointer only moves forward. Thus all candidate maximal windows are considered without enumerating every substring.
