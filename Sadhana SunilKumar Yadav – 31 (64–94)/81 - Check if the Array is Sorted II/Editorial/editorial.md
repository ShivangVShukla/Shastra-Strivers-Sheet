# Editorial: Check if the Array is Sorted II

## Difficulty
Easy

## Prerequisites
- Recursion on Arrays

## Approach
- Base Case: If index `i >= nums.size() - 1`, all adjacent pairs have been checked; return `true`.
- Check: If `nums[i] > nums[i + 1]`, order is violated; return `false`.
- Recursive Step: Return `helper(nums, i + 1)`.

## Complexity Analysis
- **Time Complexity:** $O(N)$.
- **Space Complexity:** $O(N)$ recursion depth.
