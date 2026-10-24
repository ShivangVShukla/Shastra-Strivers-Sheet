# Editorial: Rotate String

## Difficulty
Easy

## Prerequisites
- Strings
- Substring Search / Double String Trick

## Problem Statement
Determine if string `goal` is a circular rotation of string `s`.

## Approach & Algorithm
1. Check if lengths differ: if `s.length() != goal.length()`, return `false`.
2. Concatenate `s` with itself: `doubleS = s + s`.
3. Any possible rotation of `s` is a contiguous substring of length `s.length()` within `doubleS`.
4. Therefore, check if `goal` is a substring of `doubleS`.
5. Return `true` if found, `false` otherwise.

## Complexity Analysis
- **Time Complexity:** $O(N)$ using efficient string search (e.g. KMP or Boyer-Moore).
- **Space Complexity:** $O(N)$ to store the concatenated string.
