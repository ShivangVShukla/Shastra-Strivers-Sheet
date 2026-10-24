# Editorial — Minimum Window Substring

## Difficulty
Hard

## Prerequisites
- Strings
- Sliding Window
- Two Pointers
- Frequency Counting

## Problem Statement

Find the shortest substring of `s` that contains every character of `t` with at least the required frequency.

For example, if `t = "AABC"`, a valid window must contain:

- 2 `A`s
- 1 `B`
- 1 `C`

If no valid window exists, the assignment output is `-1`.

## Explanation & Approach

Use a sliding window `[left, right]`.

Maintain:

- `need[c]` = how many times character `c` is required
- `window[c]` = how many times it currently occurs in the window
- `required` = total number of characters required, counting duplicates
- `formed` = number of required character occurrences currently satisfied

### Step 1: Expand the window

Move `right` from left to right.

When adding `s[right]`:

- Increase its window frequency.
- If the character is needed and its frequency does not exceed the required frequency, increase `formed`.

When:

`formed == required`

the current window contains everything needed.

### Step 2: Shrink the window

Move `left` forward while the window remains valid.

For every valid window:

1. Compare its length with the best answer.
2. Save it if it is smaller.
3. Remove `s[left]`.
4. If removing it makes `formed < required`, stop shrinking.

This guarantees that the smallest valid window for each right endpoint is considered.

## Duplicate Characters

Duplicates are important.

For:

`t = "AABC"`

the window must contain two `A`s.

That is why `required` counts character occurrences rather than just distinct characters.

## Example Trace

For:

`s = "ADOBECODEBANC"`
`t = "ABC"`

The first valid window is:

`ADOBEC`

Later, the window is tightened until:

`BANC`

is obtained.

`BANC` has length 4 and is the minimum window.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s = br.readLine();
        String t = br.readLine();

        int[] need = new int[128];
        int[] window = new int[128];

        for (char c : t.toCharArray()) {
            need[c]++;
        }

        int required = t.length();
        int formed = 0;
        int left = 0;

        int bestStart = -1;
        int bestLength = Integer.MAX_VALUE;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            window[c]++;

            if (need[c] > 0 && window[c] <= need[c]) {
                formed++;
            }

            while (formed == required) {
                int currentLength = right - left + 1;

                if (currentLength < bestLength) {
                    bestLength = currentLength;
                    bestStart = left;
                }

                char leftChar = s.charAt(left);
                window[leftChar]--;

                if (need[leftChar] > 0 && window[leftChar] < need[leftChar]) {
                    formed--;
                }

                left++;
            }
        }

        if (bestStart == -1) {
            System.out.println("-1");
        } else {
            System.out.println(s.substring(bestStart, bestStart + bestLength));
        }
    }
}
```

## Complexity

Let `n = s.length()` and `m = t.length()`.

- Time: `O(n + m)`
- Space: `O(C)`

For the ASCII character set used by the solution, `C = 128`, so the auxiliary space is effectively `O(1)`.

## Edge Cases

1. `t` is longer than `s`.
2. No valid window exists.
3. `s` and `t` are identical.
4. `t` contains duplicate characters.
5. The minimum window occurs at the beginning.
6. The minimum window occurs at the end.
7. Multiple windows have the same minimum length.
8. The entire string is the answer.

## Why the Approach Works

The right pointer expands until all required character occurrences are present. Once the window becomes valid, moving the left pointer is the only way to make it smaller. The algorithm records the shortest valid window before each necessary removal. Both pointers move only forward, giving linear time.
