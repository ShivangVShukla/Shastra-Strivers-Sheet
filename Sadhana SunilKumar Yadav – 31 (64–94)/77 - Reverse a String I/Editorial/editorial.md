# Editorial: Reverse a String I

## Difficulty
Easy

## Prerequisites
- Recursion on Strings

## Approach
- Base Case: If `left >= right`, reversal of the subarray is complete.
- Recursive Step: Swap `s[left]` and `s[right]`, then recursively call with `left + 1` and `right - 1`.

## Complexity Analysis
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ stack space.
