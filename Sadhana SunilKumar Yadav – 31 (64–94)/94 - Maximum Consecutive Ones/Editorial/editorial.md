# Editorial: Maximum Consecutive Ones

## Difficulty
Easy

## Algorithm
1. Maintain two variables: `maxCount = 0` and `currentCount = 0`.
2. Iterate through each element in the array:
   - If the element is `1`, increment `currentCount` and update `maxCount = max(maxCount, currentCount)`.
   - If the element is `0`, reset `currentCount = 0`.
3. Return `maxCount`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(1)$ auxiliary space.
