# Editorial: Recursive Bubble Sort

## Difficulty
Easy

## Algorithm
1. Base Case: If array size $n \le 1$, return.
2. In one pass, bubble the largest element of the current range $[0, n - 1]$ to the end at index $n - 1$.
3. Check if any swaps occurred. If no swaps, the array is already sorted.
4. Recursively call `bubbleSortHelper(nums, n - 1)`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ best case, $O(N^2)$ worst case.
- **Space Complexity:** $O(N)$ auxiliary stack space.
