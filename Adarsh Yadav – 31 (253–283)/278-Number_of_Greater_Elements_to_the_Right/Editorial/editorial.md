# Editorial — Number of Greater Elements to the Right

## Approach: Direct Scan for Each Query

For each query index `p`:
1. Read the value `arr[p]`.
2. Scan every position `j` from `p + 1` through `N - 1`.
3. Increment the count only when `arr[j] > arr[p]` (strictly greater, not equal).
4. Append the count to the result in query order.

With at most 100 queries and `N <= 10^4`, this straightforward method is efficient enough.

## Complexity

- **Time:** `O(Q * N)` per test case in the worst case.
- **Auxiliary space:** `O(Q)` for the answer array, excluding input storage.
