# Editorial — Number of Substrings Containing All 3 Characters

## Difficulty
Medium

## Prerequisites
- Strings
- Sliding Window / Two Pointers
- Tracking last occurrences
- Counting substrings

## Problem Statement
Given a string containing only `a`, `b`, and `c`, count how many substrings contain at least one occurrence of each of the three characters.

## Explanation & Approach

A direct enumeration of every substring would take `O(n^2)` substrings, which is too slow for large `n`.

Instead, process the string from left to right.

Maintain:

- `last[0]` = latest index of `a`
- `last[1]` = latest index of `b`
- `last[2]` = latest index of `c`

Initially all three values are `-1`.

For every index `right`:

1. Update the last occurrence of `s[right]`.
2. Find `minLast = min(last[0], last[1], last[2])`.
3. If `minLast >= 0`, then all three characters have appeared.
4. Every starting position from `0` through `minLast` produces a valid substring ending at `right`.
5. Therefore add `minLast + 1` to the answer.

### Why does `minLast + 1` work?

Suppose at some `right`:

- last `a` = 5
- last `b` = 7
- last `c` = 6

The earliest of the three latest occurrences is at index `5`.

Any substring ending at `right` and starting at `0, 1, 2, 3, 4, or 5` contains:

- the `a` at index 5,
- the latest `b` at 7,
- the latest `c` at 6.

So there are `6 = 5 + 1` valid substrings ending at `right`.

Starting at index `6` would exclude the `a` at index `5`, so it would not necessarily contain all three characters.

## Example Trace

For `s = "abcabc"`:

| right | char | last(a) | last(b) | last(c) | contribution | total |
|---:|:---:|---:|---:|---:|---:|---:|
| 0 | a | 0 | -1 | -1 | 0 | 0 |
| 1 | b | 0 | 1 | -1 | 0 | 0 |
| 2 | c | 0 | 1 | 2 | 1 | 1 |
| 3 | a | 3 | 1 | 2 | 2 | 3 |
| 4 | b | 3 | 4 | 2 | 3 | 6 |
| 5 | c | 3 | 4 | 5 | 4 | 10 |

Answer = `10`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();

        int[] last = {-1, -1, -1};
        long answer = 0;

        for (int right = 0; right < s.length(); right++) {
            last[s.charAt(right) - 'a'] = right;

            int minLast = Math.min(last[0], Math.min(last[1], last[2]));

            if (minLast >= 0) {
                answer += minLast + 1L;
            }
        }

        System.out.println(answer);
    }
}
```

## Complexity

- Time: `O(n)`
- Space: `O(1)`

## Edge Cases

1. `abc` → exactly `1`.
2. `aaa` → `0`, because `b` and `c` never occur.
3. `aabbcc` → multiple valid substrings after the first `c`.
4. Repeated characters such as `aaabbbccc`.
5. The first valid substring may appear only near the end.
6. The answer can be much larger than `int` for large strings, so `long` is used.

## Why the Approach Works

At each right endpoint, the minimum among the three latest occurrences determines the largest starting index that still guarantees all three characters are inside the substring. Thus exactly `minLast + 1` valid substrings end at the current position. Summing this contribution for every right endpoint counts every valid substring exactly once.
