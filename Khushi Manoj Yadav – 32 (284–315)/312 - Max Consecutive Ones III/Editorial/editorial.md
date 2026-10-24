# 312. Max Consecutive Ones III - Editorial

## Approach
Use a sliding window with two pointers `left` and `right`:
1. Expand the window by advancing `right` and incrementing `zeroCount` when `nums[right] == 0`.
2. When `zeroCount > k`, advance `left` and decrement `zeroCount` if `nums[left] == 0`.
3. The valid window length is `right - left + 1`. Track the maximum.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(1)
