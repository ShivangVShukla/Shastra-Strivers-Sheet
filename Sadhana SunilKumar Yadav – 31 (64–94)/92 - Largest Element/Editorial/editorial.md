# Editorial: Largest Element

## Difficulty
Easy

## Algorithm
1. Initialize `maxElem = nums[0]`.
2. Iterate through each element from index $1$ to $N - 1$.
3. If $nums[i] > maxElem$, update `maxElem = nums[i]`.
4. Return `maxElem`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(1)$ auxiliary space.
