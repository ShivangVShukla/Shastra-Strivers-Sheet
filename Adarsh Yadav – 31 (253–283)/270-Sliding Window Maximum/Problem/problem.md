# Problem: Sliding Window Maximum

## Difficulty
Hard

## Tags
Array, Queue, Sliding Window, Monotonic Queue

## Problem Statement
Given an array of integers `arr`, there is a sliding window of size `k` moving from the left of the array to the right. At each position, only the `k` elements inside the window are considered. Return the maximum element for every window.

## Input Format
- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains two integers `N` and `K`, the array length and sliding-window size.
  - The second line contains `N` space-separated integers representing `arr`.

## Output Format
For each test case, print the maximum of each window in order, separated by spaces.

## Constraints
- `1 <= T <= 120`
- `1 <= N <= 10^5`
- `-10^4 <= arr[i] <= 10^4`
- `1 <= K <= N`

## Example 1

### Input
```text
1
8 3
4 0 -1 3 5 3 6 8
```

### Output
```text
4 3 5 5 6 8
```

### Explanation
The windows are `[4, 0, -1]`, `[0, -1, 3]`, `[-1, 3, 5]`, `[3, 5, 3]`, `[5, 3, 6]`, and `[3, 6, 8]`. Their maximums are `4, 3, 5, 5, 6, 8`.

## Example 2

### Input
```text
1
2 2
20 25
```

### Output
```text
25
```
