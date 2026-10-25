# Editorial: Reverse a String II

## Difficulty
Easy

## Prerequisites
- Strings
- Two Pointers Technique

## Problem Statement
Reverse a given character array `s` in place using $O(1)$ extra memory.

## Approach & Algorithm
1. Initialize two pointers: `left = 0` at the beginning of the string and `right = s.size() - 1` at the end.
2. While `left < right`:
   - Swap the elements at `s[left]` and `s[right]`.
   - Increment `left` by 1 and decrement `right` by 1.
3. This reverses the entire array in a single linear pass.

## Complexity Analysis
- **Time Complexity:** $O(N)$ where $N$ is the length of the string, since each character is visited once and swapped.
- **Space Complexity:** $O(1)$ auxiliary space as the reversal is performed in place.
