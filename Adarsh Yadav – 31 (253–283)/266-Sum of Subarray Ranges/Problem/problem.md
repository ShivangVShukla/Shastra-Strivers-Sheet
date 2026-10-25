# Problem: Sum of Subarray Ranges

## Difficulty
Medium

## Tags
Array, Monotonic Stack

## Problem Statement
Given an integer array `nums`, determine the range of a subarray, defined as the difference between the largest and smallest elements within the subarray. Calculate and return the sum of all subarray ranges of `nums`.

A subarray is a contiguous, non-empty sequence of elements within the array.

## Input Format
- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains an integer `N`, the length of the array.
  - The second line contains `N` space-separated integers representing `nums`.

## Output Format
For each test case, print one integer: the sum of the ranges of all non-empty contiguous subarrays.

## Constraints
- `1 <= T <= 1000`
- `1 <= N <= 1000`
- `-10^9 <= nums[i] <= 10^9`

## Example 1

### Input
```text
1
3
1 2 3
```

### Output
```text
4
```

### Explanation
The six subarrays are `[1]`, `[2]`, `[3]`, `[1, 2]`, `[2, 3]`, and `[1, 2, 3]`. Their ranges are `0, 0, 0, 1, 1, 2`, respectively. The total is `4`.

## Example 2

### Input
```text
1
3
1 3 3
```

### Output
```text
4
```

### Explanation
The six subarrays are `[1]`, `[3]`, `[3]`, `[1, 3]`, `[3, 3]`, and `[1, 3, 3]`. Their ranges are `0, 0, 0, 2, 0, 2`, respectively. The total is `4`.

## Notes
The answer may exceed the range of a 32-bit signed integer, so use a 64-bit integer type such as Java `long`.
