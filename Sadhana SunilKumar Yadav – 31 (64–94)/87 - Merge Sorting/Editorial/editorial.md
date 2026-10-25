# Editorial: Merge Sorting

## Difficulty
Medium

## Algorithm
1. **Divide:** Recursively divide the array into two halves at $mid = low + (high - low) / 2$.
2. **Conquer:** Recursively sort each half until subarrays have length 1.
3. **Combine (Merge):** Merge the two sorted halves into a temporary buffer using two pointers, then copy back to original array.

## Complexity Analysis
- **Time Complexity:** $O(N \log N)$ in all cases (best, average, worst).
- **Space Complexity:** $O(N)$ auxiliary space for merge buffer.
