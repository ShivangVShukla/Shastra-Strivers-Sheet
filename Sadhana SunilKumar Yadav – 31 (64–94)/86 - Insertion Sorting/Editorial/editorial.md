# Editorial: Insertion Sorting

## Difficulty
Easy

## Algorithm
1. Build the sorted array one element at a time.
2. For each element from index $1$ to $N - 1$:
   - Store $key = nums[i]$.
   - Shift elements of $nums[0...i-1]$ that are greater than $key$ one position to the right.
   - Insert $key$ at its correct sorted position.

## Complexity Analysis
- **Time Complexity:** $O(N)$ best case, $O(N^2)$ worst and average case.
- **Space Complexity:** $O(1)$ in-place.
