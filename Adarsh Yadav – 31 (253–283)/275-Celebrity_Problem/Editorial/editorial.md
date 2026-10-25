# Editorial — Celebrity Problem

## Approach: Candidate Elimination

A celebrity must satisfy both conditions:
- They know nobody else: every value in their row is `0`.
- Everybody else knows them: every other person's value in their column is `1`.

We can find a candidate in `O(N)` time by eliminating people in pairs:

1. Start with candidate `0`.
2. For each person `i` from `1` to `N - 1`:
   - If the current candidate knows `i`, the candidate cannot be a celebrity, so set the candidate to `i`.
   - Otherwise, `i` cannot be a celebrity because the candidate does not know them.
3. Verify the remaining candidate by checking their entire row and column:
   - The candidate must not know anyone.
   - Every other person must know the candidate.
4. If both conditions hold, return the candidate index; otherwise, return `-1`.

## Complexity

- **Time:** `O(N)` — candidate elimination and verification each take linear time.
- **Auxiliary space:** `O(1)` beyond the input matrix.
