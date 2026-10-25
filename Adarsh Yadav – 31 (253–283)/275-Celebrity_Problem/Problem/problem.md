# Celebrity Problem

## Problem Statement

A celebrity is a person who is known by everyone else at a party but does not know anyone in return.

Given an `N x N` matrix `M`, where `M[i][j] = 1` means person `i` knows person `j`, and `M[i][j] = 0` means person `i` does not know person `j`, determine whether a celebrity exists. Return the index of the celebrity, or `-1` if no celebrity exists.

`M[i][i]` is always `0`.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains `N`, the number of people.
  - The next `N` lines each contain `N` space-separated binary values.

## Output Format

For each test case, print the celebrity index, or `-1` if there is no celebrity.

## Examples

### Example 1

Input:
```text
1
4
0 1 1 0
0 0 0 0
1 1 0 0
0 1 1 0
```
Output:
```text
1
```

### Example 2

Input:
```text
1
2
0 1
1 0
```
Output:
```text
-1
```

### Example 3

Input:
```text
1
3
0 1 0
0 0 0
0 1 0
```
Output:
```text
1
```

## Constraints

- `1 <= N <= 3000`
- `M[i][j]` is either `0` or `1`.
- The sum of `N * N` across test cases in a generated input file is at most `9 * 10^6`.
