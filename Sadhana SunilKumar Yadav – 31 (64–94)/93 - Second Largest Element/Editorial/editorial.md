# Editorial: Second Largest Element

## Difficulty
Easy

## Algorithm
1. Initialize `largest = INT_MIN` and `secondLargest = INT_MIN`.
2. Traverse each element `x` in the array:
   - If `x > largest`:
     - Update `secondLargest = largest`.
     - Update `largest = x`.
   - Else if `x > secondLargest` and `x != largest`:
     - Update `secondLargest = x`.
3. If no second distinct largest was found (`secondLargest == INT_MIN`), return `-1`.
4. Otherwise, return `secondLargest`.

## Complexity Analysis
- **Time Complexity:** $O(N)$ single pass.
- **Space Complexity:** $O(1)$ auxiliary space.
