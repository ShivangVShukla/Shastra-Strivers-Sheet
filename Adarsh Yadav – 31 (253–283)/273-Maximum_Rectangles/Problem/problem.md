# Maximum Rectangles

## Problem Statement

Given an `M x N` binary matrix filled with `0`s and `1`s, find the largest rectangle containing only `1`s and return its area.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains two integers `M` and `N`, the number of rows and columns.
  - The next `M` lines each contain `N` space-separated binary values (`0` or `1`).

## Output Format

For each test case, print the area of the largest rectangle containing only `1`s.

## Examples

### Example 1

Input:
```text
1
4 5
1 0 1 0 0
1 0 1 1 1
1 1 1 1 1
1 0 0 1 0
```
Output:
```text
6
```

### Example 2

Input:
```text
1
1 1
1
```
Output:
```text
1
```

### Example 3

Input:
```text
1
2 5
1 0 1 0 0
1 0 1 1 1
```
Output:
```text
3
```

## Constraints

- `1 <= M, N <= 1000`
- `matrix[i][j]` is either `0` or `1`.
- The sum of `M * N` across test cases in a generated input file is at most `10^6`.
