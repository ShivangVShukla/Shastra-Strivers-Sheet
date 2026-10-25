# Number of Greater Elements to the Right

## Problem Statement

You are given an integer array `arr` of length `N` and an array `indices` containing query positions in `arr`.

For each query index `p`, count the elements strictly greater than `arr[p]` that appear to the right of position `p`. Return the answers in the same order as the queries.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains `N` and `Q`, the array length and number of queries.
  - The second line contains `N` space-separated integers for `arr`.
  - The third line contains `Q` space-separated query indices.

## Output Format

For each test case, print the `Q` answers separated by spaces.

## Examples

### Example 1

Input:
```text
1
8 2
3 4 2 7 5 8 10 6
0 5
```
Output:
```text
6 1
```

### Example 2

Input:
```text
1
5 2
1 2 3 4 1
0 3
```
Output:
```text
3 0
```

### Example 3

Input:
```text
1
4 2
5 5 5 5
0 2
```
Output:
```text
0 0
```

## Constraints

- `1 <= N <= 10^4`
- `1 <= Q <= 100`
- `1 <= arr[i] <= 10^5`
- `0 <= indices[i] < N`
