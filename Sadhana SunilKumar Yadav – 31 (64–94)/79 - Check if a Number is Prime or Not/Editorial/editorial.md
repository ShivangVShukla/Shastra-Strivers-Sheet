# Editorial: Check if a Number is Prime or Not

## Difficulty
Easy

## Prerequisites
- Prime Factorization & Recursion

## Approach
- If $num \le 1$, return `false`.
- Helper function with divisor $d$ starting at 2:
  - If $d \times d > num$, return `true` (no factors found up to $\sqrt{num}$).
  - If $num \% d == 0$, return `false` (composite).
  - Recursively check $d + 1$.

## Complexity Analysis
- **Time Complexity:** $O(\sqrt{num})$.
- **Space Complexity:** $O(\sqrt{num})$ recursion depth.
