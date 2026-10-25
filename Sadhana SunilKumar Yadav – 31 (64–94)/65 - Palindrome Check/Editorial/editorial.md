# Editorial: Palindrome Check

## Difficulty
Easy

## Prerequisites
- Strings
- Two Pointers Technique

## Problem Statement
Determine if a string reads the same from left to right as from right to left.

## Approach & Algorithm
1. Place one pointer `left = 0` and one pointer `right = s.length() - 1`.
2. Compare `s[left]` and `s[right]`. If they differ, return `false`.
3. Increment `left` and decrement `right`. Repeat until `left >= right`.
4. If no mismatch is encountered, return `true`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ where $N$ is the length of `s`.
- **Space Complexity:** $O(1)$ auxiliary space.
