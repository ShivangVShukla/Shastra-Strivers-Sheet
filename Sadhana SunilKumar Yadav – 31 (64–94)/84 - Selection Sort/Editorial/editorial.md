# Editorial: Selection Sort

## Difficulty
Easy

## Algorithm
1. In each pass $i$ from $0$ to $N - 2$:
   - Find the index of the minimum element in the unsorted range $[i, N - 1]$.
   - Swap the minimum element with $nums[i]$.
2. After $N - 1$ passes, the array is completely sorted.

## Complexity Analysis
- **Time Complexity:** $O(N^2)$ in best, average, and worst cases ($N(N-1)/2$ comparisons).
- **Space Complexity:** $O(1)$ in-place.
