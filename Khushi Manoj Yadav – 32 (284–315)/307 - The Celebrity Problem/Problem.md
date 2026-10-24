### Problem 307: The Celebrity Problem
**Difficulty:** Medium | **Tags:** Two Pointers, Stack, Matrix

**Problem Statement**
A celebrity is a person who is known to everyone else at a party, but does not know anyone.
You are given an `N x N` binary matrix `M` where `M[i][j] = 1` means person `i` knows person `j`, and `0` means person `i` does not know person `j`.
Find the celebrity (0-indexed). If no celebrity exists, return `-1`.

**Input Format**
- The first line contains an integer `N`, the number of people.
- The next `N` lines each contain `N` space-separated integers representing matrix `M`.

**Output Format**
- Print the 0-indexed integer ID of the celebrity, or `-1` if none exists.

**Constraints**
- `1 <= N <= 1000`
- `M[i][j]` is either `0` or `1`.
- `M[i][i] = 0`.

**Example**
**Input:**
```text
3
0 1 0
0 0 0
0 1 0
```
**Output:**
```text
1
```
