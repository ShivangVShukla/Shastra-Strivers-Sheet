# Editorial: Quick Sorting

## Difficulty
Medium

## Algorithm
1. Choose a pivot element (e.g., $nums[low]$).
2. Partition the array such that elements $\le pivot$ are to the left, and elements $> pivot$ are to the right.
3. Place the pivot into its final partition index $pIndex$.
4. Recursively apply Quick Sort to left subarray $[low, pIndex - 1]$ and right subarray $[pIndex + 1, high]$.

## Complexity Analysis
- **Time Complexity:** $O(N \log N)$ average case, $O(N^2)$ worst case.
- **Space Complexity:** $O(\log N)$ recursion stack space.
