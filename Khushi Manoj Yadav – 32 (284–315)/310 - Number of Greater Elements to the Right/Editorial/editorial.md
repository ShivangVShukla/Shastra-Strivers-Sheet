# 310. Number of Greater Elements to the Right - Editorial

## Approach
Offline processing with a Fenwick Tree (Binary Indexed Tree):
1. Coordinate compress all unique values in `arr`.
2. Group and sort queries by index descending.
3. Iterate from `i = N - 1` down to `0`:
   - For all queries at index `i`, query the Fenwick tree for the count of elements strictly greater than `rank(arr[i])`.
   - Insert `rank(arr[i])` into the Fenwick tree.
4. Output the recorded query answers in their original order.

## Complexity
- Coordinate Compression: O(N log N)
- Fenwick Tree Operations: O((N + Q) log N)
- Space Complexity: O(N + Q)
