# Editorial: Sum of Digits in a Given Number

## Difficulty
Easy

## Prerequisites
- Recursion, Digital Root

## Approach
- To sum digits of a number recursively:
  `digitSum(n) = (n % 10) + digitSum(n / 10)` with base case `digitSum(0) = 0`.
- To repeatedly sum digits:
  - If `num < 10`, it is already a single digit; return `num`.
  - Otherwise, return `addDigits(digitSum(num))`.

## Complexity Analysis
- **Time Complexity:** $O(\log_{10} N)$ operations.
- **Space Complexity:** $O(\log_{10} N)$ recursion depth.
