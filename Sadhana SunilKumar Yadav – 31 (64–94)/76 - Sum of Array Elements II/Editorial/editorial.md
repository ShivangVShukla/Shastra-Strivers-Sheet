# Editorial: Sum of Array Elements II

## Difficulty
Easy

## Prerequisites
- Recursion on Arrays

## Approach
- Base Case: If the index reaches `nums.size()`, return 0.
- Recursive Step: Return `nums[i] + helper(nums, i + 1)`.

## Complexity Analysis
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ stack space.
