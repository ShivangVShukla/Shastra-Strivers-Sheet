# Editorial: Reverse an array 2

## Difficulty
Easy

## Prerequisites
- Recursion on Arrays

## Approach
- Base Case: If `left >= right`, stop.
- Recursive Step: Swap `nums[left]` and `nums[right]`, recurse on `left + 1` and `right - 1`.

## Complexity Analysis
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ recursion depth.
