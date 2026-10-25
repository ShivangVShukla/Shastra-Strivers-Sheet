# Editorial: Factorial of a Given Number

## Difficulty
Easy

## Prerequisites
- Recursion Basics

## Problem Statement
Compute $n! = n \times (n-1) \times \dots \times 1$ recursively with $0! = 1$.

## Approach
- Base Case: If $n == 0$ or $n == 1$, return 1.
- Recursive Step: Return $n \times \text{factorial}(n - 1)$.

## Complexity Analysis
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ auxiliary stack space.
