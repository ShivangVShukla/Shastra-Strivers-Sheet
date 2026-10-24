# Editorial: Check if String is Palindrome or Not

## Difficulty
Easy

## Prerequisites
- Recursion on Strings

## Approach
- Base Case: If `left >= right`, all matching pairs have verified successfully, return `true`.
- If `s[left] != s[right]`, return `false`.
- Recursive Step: Return `helper(s, left + 1, right - 1)`.

## Complexity Analysis
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ stack space.
