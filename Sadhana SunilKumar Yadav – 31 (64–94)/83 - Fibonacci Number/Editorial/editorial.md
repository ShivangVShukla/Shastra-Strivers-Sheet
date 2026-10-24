# Editorial: Fibonacci Number

## Difficulty
Easy

## Prerequisites
- Recursion Basics

## Approach
- Base Cases:
  - If $n == 0$, return 0.
  - If $n == 1$, return 1.
- Recursive Step:
  - Return `fib(n - 1) + fib(n - 2)`.
- For efficiency in larger inputs or linear recursion, memoization or iterative / parameterized recursion can be used.

## Complexity Analysis
- **Time Complexity:** $O(2^N)$ for naive branching recursion, or $O(N)$ with memoization/linear recurrence.
- **Space Complexity:** $O(N)$ auxiliary stack space.
