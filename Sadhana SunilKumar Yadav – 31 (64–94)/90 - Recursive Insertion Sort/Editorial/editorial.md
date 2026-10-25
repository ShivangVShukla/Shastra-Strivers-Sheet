# Editorial: Recursive Insertion Sort

## Difficulty
Easy

## Algorithm
1. Base Case: If index $i \ge n$, return.
2. Insert $nums[i]$ into its correct sorted position within sorted prefix $nums[0...i-1]$ by swapping backwards.
3. Recursively call `insertionSortHelper(nums, i + 1, n)`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ best case, $O(N^2)$ worst and average case.
- **Space Complexity:** $O(N)$ auxiliary stack space.
