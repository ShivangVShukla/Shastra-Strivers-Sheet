# Editorial: Largest Odd Number in a String

## Difficulty
Easy

## Prerequisites
- Strings
- Digit properties

## Problem Statement
Find the largest-valued odd integer that can be formed as a substring of `s`, stripping any leading zeros.

## Approach & Algorithm
1. An integer is odd if and only if its last digit is odd (`1, 3, 5, 7, 9`).
2. To maximize the integer value, the substring should be as long as possible, starting from the earliest non-zero digit and extending to the rightmost odd digit in the string.
3. Traverse backwards from the end of `s` to find the rightmost index `r` where `s[r]` is odd.
4. If no such odd digit exists, return `""`.
5. Find the leftmost non-zero digit `l` in the prefix `s[0...r]`. If all digits up to `r` are `'0'`, return `""`.
6. Return `s.substr(l, r - l + 1)`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ where $N$ is the length of `s`.
- **Space Complexity:** $O(1)$ auxiliary space.
