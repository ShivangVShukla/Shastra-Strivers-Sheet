# Stock Span Problem

## Problem Statement

Given an array `arr` of size `N`, where `arr[i]` represents the stock price on day `i`, calculate the span of stock prices for every day.

The span of a day is the maximum number of consecutive previous days, including the current day, for which the stock price was less than or equal to the price on the current day.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains an integer `N`, the number of days.
  - The second line contains `N` space-separated stock prices.

## Output Format

For each test case, print the `N` span values separated by spaces on one line.

## Examples

### Example 1

Input:
```text
1
7
120 100 60 80 90 110 115
```
Output:
```text
1 1 1 2 3 5 6
```

### Example 2

Input:
```text
1
6
15 13 12 14 16 20
```
Output:
```text
1 1 1 3 5 6
```

### Example 3

Input:
```text
1
8
30 20 25 28 27 29 35 40
```
Output:
```text
1 1 2 3 1 5 7 8
```

## Constraints

- `1 <= N <= 10^5`
- `1 <= arr[i] <= 10^9`
- The sum of `N` across test cases in a generated input file is at most `10^5`.
