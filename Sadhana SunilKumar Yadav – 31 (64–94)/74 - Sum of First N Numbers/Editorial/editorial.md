# Editorial: Sum of First N Numbers

## Difficulty
Easy

## Prerequisites
- Recursion Basics

## Problem Statement
Compute the sum $1 + 2 + \dots + N$ recursively.

## Approach
- Base Case: When $N = 0$, return 0.
- Recursive Case: Return $N + \text{NnumbersSum}(N - 1)$.

## Complexity Analysis
- **Time Complexity:** $O(N)$ recursive calls.
- **Space Complexity:** $O(N)$ stack frames.
