# Largest Rectangle in a Histogram

## Problem Statement

Given an array of integers `heights` representing the heights of histogram bars, where each bar has width `1`, return the area of the largest rectangle that can be formed within the histogram.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains an integer `N`, the number of bars.
  - The second line contains `N` space-separated integers representing `heights`.

## Output Format

For each test case, print the largest rectangle area on its own line.

## Examples

### Example 1

Input:
```text
1
6
2 1 5 6 2 3
```
Output:
```text
10
```

### Example 2

Input:
```text
1
6
3 5 1 7 5 9
```
Output:
```text
15
```

### Example 3

Input:
```text
1
2
2 4
```
Output:
```text
4
```

## Constraints

- `1 <= heights.length <= 10^5`
- `0 <= heights[i] <= 10^4`
- The sum of `N` across test cases in a generated input file is at most `10^5`.
