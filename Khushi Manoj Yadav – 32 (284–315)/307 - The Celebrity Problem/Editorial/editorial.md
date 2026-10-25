# 307. The Celebrity Problem - Editorial

## Approach
Use two pointers to eliminate non-celebrities:
1. Initialize `a = 0, b = N - 1`.
2. While `a < b`:
   - If `M[a][b] == 1`, `a` knows `b`, so `a` cannot be a celebrity. Advance `a++`.
   - Else, `a` does not know `b`, so `b` cannot be a celebrity. Decrement `b--`.
3. The remaining candidate `cand = a` must be verified:
   - Check that `M[cand][i] == 0` for all `i != cand` (knows nobody).
   - Check that `M[i][cand] == 1` for all `i != cand` (known by everybody).
4. If verified, return `cand`; otherwise return `-1`.

## Complexity
- Candidate Elimination: O(N)
- Verification: O(N)
- Total Time: O(N)
- Space Complexity: O(1) beyond input
