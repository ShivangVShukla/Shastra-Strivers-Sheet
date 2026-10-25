# Trapping Rainwater

## Problem Statement

Given an array of non-negative integers `height`, where each element represents the elevation of a ground bar, calculate the amount of water that can be trapped after raining.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains an integer `N`, the length of the array.
  - The second line contains `N` space-separated integers representing `height`.

## Output Format

For each test case, print one integer: the total amount of trapped water.

## Examples

### Example 1

Input:
```text
1
12
0 1 0 2 1 0 1 3 2 1 2 1
```

Output:
```text
6
```

### Example 2

Input:
```text
1
6
4 2 0 3 2 5
```

Output:
```text
9
```

### Example 3

Input:
```text
1
4
7 4 0 9
```

Output:
```text
10
```

## Constraints

- `1 <= N <= 10^5`
- `0 <= height[i] <= 10^5`

The sum of `N` across test cases in a single generated file is at most `10^5`.
