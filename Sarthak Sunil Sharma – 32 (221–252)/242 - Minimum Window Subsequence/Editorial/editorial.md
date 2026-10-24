# Editorial — Minimum Window Subsequence

## Difficulty
Hard

## Prerequisites
- Strings
- Two Pointers
- Subsequence concept
- Greedy scanning

## Problem Statement

Given `s1` and `s2`, find the shortest contiguous substring of `s1` in which `s2` appears as a subsequence.

The characters of `s2` must occur in the same order, but they do not have to be adjacent.

For example:

`s1 = "abcdebdde"`
`s2 = "bde"`

The answer is:

`"bcde"`

because `b`, `d`, and `e` occur in order.

## Important Difference from Minimum Window Substring

Q241 asks for a window containing all required characters with required frequencies.

Q242 is different:

- order matters,
- characters may have gaps,
- `s2` must be a subsequence.

For example, if `s2 = "abc"`, then a window containing `c`, `a`, and `b` in the wrong order is not valid.

## Explanation & Approach

Use two phases.

### Phase 1 — Forward Scan

Start at a position in `s1` and move forward while trying to match `s2`.

Maintain:

- `i` = position in `s1`
- `j` = position in `s2`

Whenever:

`s1[i] == s2[j]`

advance `j`.

When `j == s2.length()`, the entire `s2` has been matched.

Now `i` is the end of a valid window.

### Phase 2 — Backward Shrinking

Starting from this ending position, move backward.

Set:

`j = s2.length() - 1`

Whenever `s1[i] == s2[j]`, move `j` backward.

When all characters have been matched, the current `i` is the earliest possible start for this particular ending position.

Record the window.

Then continue the forward search from the next position to look for a smaller window.

## Example

`s1 = "abcdebdde"`

`s2 = "bde"`

Forward matching can find:

`b c d e`

So we have:

`"bcde"`

Now scan backward:

- match `e`
- match `d`
- match `b`

The earliest valid start is the `b` at index 1.

Thus the window is `"bcde"`.

## Java Solution

```java
import java.io.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String s1 = br.readLine();
        String s2 = br.readLine();

        int n = s1.length();
        int m = s2.length();

        int bestStart = -1;
        int bestLength = Integer.MAX_VALUE;

        int start = 0;

        while (start < n) {
            int i = start;
            int j = 0;

            while (i < n && j < m) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    j++;
                }
                i++;
            }

            if (j < m) {
                break;
            }

            int end = i - 1;

            j = m - 1;
            i = end;

            while (i >= start) {
                if (s1.charAt(i) == s2.charAt(j)) {
                    j--;
                    if (j < 0) {
                        break;
                    }
                }
                i--;
            }

            int windowStart = i;
            int windowLength = end - windowStart + 1;

            if (windowLength < bestLength) {
                bestLength = windowLength;
                bestStart = windowStart;
            }

            start = windowStart + 1;
        }

        if (bestStart == -1) {
            System.out.println("-1");
        } else {
            System.out.println(s1.substring(bestStart, bestStart + bestLength));
        }
    }
}
```

## Complexity

For each successful forward match, a backward scan is performed.

- Time: `O(|s1| * |s2|)` in the worst case.
- Space: `O(1)` auxiliary space.

## Edge Cases

1. `s2` is not a subsequence of `s1`.
2. `s1 == s2`.
3. `s2` has repeated characters.
4. The answer occurs at the beginning.
5. The answer occurs at the end.
6. Multiple windows have the same minimum length.
7. `s2` contains one character.
8. Large strings.

## Why the Approach Works

The forward scan guarantees that the current ending position contains a complete subsequence match. The backward scan then finds the earliest possible start for that ending position while preserving the order of `s2`. Therefore, every candidate window considered is valid and locally minimal for its ending position. Continuing from `windowStart + 1` ensures later possible windows are also examined.
