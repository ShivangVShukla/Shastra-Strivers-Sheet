# Problem: Next Smaller Element

## Difficulty
Medium

## Tags
Array, Stack, Monotonic Stack

## Problem Statement
Given an array of integers `arr`, find the Next Smaller Element (NSE) for every element.

The Next Smaller Element for an element `x` is the first element to the right of `x` that is strictly smaller than `x`. If no such element exists, the NSE is `-1`.

## Input Format
- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains an integer `N`, the length of the array.
  - The second line contains `N` space-separated integers representing `arr`.

## Output Format
For each test case, print `N` space-separated integers. The `i`-th integer is the next smaller element to the right of `arr[i]`, or `-1` if no such element exists.

## Constraints
- `1 <= T <= 1000`
- `1 <= N <= 10^5`
- `-10^9 <= arr[i] <= 10^9`
- The sum of `N` across test cases should be suitable for the runtime environment.

## Example 1

### Input
```text
1
5
4 8 5 2 25
```

### Output
```text
2 5 2 -1 -1
```

### Explanation
- For `4`, the first smaller element to its right is `2`.
- For `8`, it is `5`.
- For `5`, it is `2`.
- `2` and `25` have no smaller element to their right.

## Example 2

### Input
```text
1
4
10 9 8 7
```

### Output
```text
9 8 7 -1
```

## Example 3

### Input
```text
1
5
1 2 3 4 5
```

### Output
```text
-1 -1 -1 -1 -1
```

### Explanation
The array is strictly increasing, so no element has a smaller element to its right.
