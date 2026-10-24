# Editorial — Longest Repeating Character Replacement

## Difficulty
Medium

## Prerequisites
- Strings
- Frequency counting
- Sliding Window
- Two Pointers

## Problem Statement

Given an uppercase English string `s` and an integer `k`, find the length of the longest substring that can be transformed into a substring containing only one repeated character by replacing at most `k` characters.

For a chosen window, if its most frequent character occurs `maxFreq` times and the window length is `windowLength`, then every other character must be replaced. Therefore, replacements required are:

`windowLength - maxFreq`

The window is valid when this value is at most `k`.

## Explanation & Approach

A brute-force solution would examine every substring and count its character frequencies. That can take `O(n^2)` time, which is too slow for large strings.

Instead, maintain a sliding window `[left, right]`.

1. Expand the window by moving `right`.
2. Increase the frequency of the newly added character.
3. Maintain `maxFreq`, the highest frequency of any character in the current window.
4. If `windowLength - maxFreq > k`, the current window needs too many replacements, so move `left` forward and decrease the frequency of the removed character.
5. Whenever the window is valid, update the answer with its length.

### Why `windowLength - maxFreq`?

Suppose the current window is:

`A A B A B`

The window length is `5`, and `A` appears `3` times. If we want every character to become `A`, only the two `B`s need replacement.

So replacements required:

`5 - 3 = 2`

If `k >= 2`, this window is valid.

### Important Observation About `maxFreq`

When the left side of the window moves, we do not decrease `maxFreq` immediately even if the removed character was the most frequent one.

This is safe for this algorithm. An old, slightly larger `maxFreq` can make a window appear valid for a little longer, but it cannot cause the final answer to become incorrect. The window expansion and contraction still produce the correct maximum length in `O(n)` time.

## Example / Trace

For:

`ABAB`, `k = 2`

- Window `A`: length `1`, max frequency `1`, replacements `0` → valid.
- Window `AB`: length `2`, max frequency `1`, replacements `1` → valid.
- Window `ABA`: length `3`, max frequency `2`, replacements `1` → valid.
- Window `ABAB`: length `4`, max frequency `2`, replacements `2` → valid.

Therefore the answer is `4`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static int longestRepeatingCharacterReplacement(String s, int k) {
        int[] freq = new int[26];
        int left = 0, maxFreq = 0, ans = 0;

        for (int right = 0; right < s.length(); right++) {
            int idx = s.charAt(right) - 'A';
            freq[idx]++;
            maxFreq = Math.max(maxFreq, freq[idx]);

            while (right - left + 1 - maxFreq > k) {
                freq[s.charAt(left) - 'A']--;
                left++;
            }

            ans = Math.max(ans, right - left + 1);
        }
        return ans;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String s = br.readLine();
        String kLine = br.readLine();
        if (s == null) s = "";
        int k = Integer.parseInt(kLine.trim());
        System.out.println(longestRepeatingCharacterReplacement(s, k));
    }
}
```

## Complexity

- Time: `O(n)`
- Space: `O(1)` because the frequency array contains only 26 entries.

## Edge Cases

1. `k = 0`: the answer is the longest run of the same character.
2. All characters are identical: the answer is the complete string length.
3. `k >= n`: the complete string can be converted into one repeated character, so the answer is `n`.
4. String contains only two characters and `k` is small.
5. The optimal window changes as the right pointer expands.
6. Large strings require an `O(n)` solution.

## Why the Approach Works

Every window is evaluated using the minimum number of replacements required to make all its characters equal. The most frequent character is always the best target because keeping those occurrences unchanged minimizes replacements.

The sliding window keeps expanding while the required replacements are within `k`. When the requirement exceeds `k`, the left pointer removes characters until the window becomes valid again. Thus every candidate window is handled efficiently, and the maximum valid window length is the required answer.
