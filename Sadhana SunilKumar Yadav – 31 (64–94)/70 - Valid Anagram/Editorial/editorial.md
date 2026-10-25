# Editorial: Valid Anagram

## Difficulty
Easy

## Prerequisites
- Strings
- Frequency Array / Hash Table

## Problem Statement
Check whether two strings contain identical character counts for all characters.

## Approach & Algorithm
1. If lengths of `s` and `t` are different, return `false` immediately.
2. Maintain a frequency table of size 26 (or 256 for ASCII).
3. Increment the frequency count for every character in `s`.
4. Decrement the frequency count for every character in `t`.
5. Iterate through the frequency table. If any count is non-zero, return `false`.
6. If all counts are zero, return `true`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ where $N$ is string length.
- **Space Complexity:** $O(1)$ since character alphabet size is constant (26/256).
