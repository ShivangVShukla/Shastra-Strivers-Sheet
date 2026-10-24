# 314. Longest Repeating Character Replacement - Editorial

## Approach
Use a sliding window `[left, right]`:
1. Maintain character frequency array for the current window and track `maxFreq` (frequency of the most common character).
2. For window length `L = right - left + 1`, the number of characters that need replacement is `L - maxFreq`.
3. If `(right - left + 1) - maxFreq > k`, shrink window from `left`: decrement `freq[s[left]]` and advance `left++`.
4. The maximum valid window size is the answer.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(1) (26 uppercase characters)
