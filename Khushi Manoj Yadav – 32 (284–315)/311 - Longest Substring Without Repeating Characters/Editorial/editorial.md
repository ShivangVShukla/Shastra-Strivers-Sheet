# 311. Longest Substring Without Repeating Characters - Editorial

## Approach
Use a sliding window with two pointers `left` and `right`:
1. Maintain an array / hash map `lastSeen` tracking the latest index of each character.
2. For each character `s[right]`:
   - If `s[right]` was seen inside the current window (`lastSeen[s[right]] >= left`), move `left = lastSeen[s[right]] + 1`.
   - Update `lastSeen[s[right]] = right`.
   - The current window length is `right - left + 1`. Track the maximum.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(min(N, Sigma)) where Sigma is the character set size.
