# 303. Trapping Rainwater - Editorial

## Approach
Two Pointers approach:
1. Maintain pointers `left = 0`, `right = N - 1`, and variables `leftMax = 0`, `rightMax = 0`.
2. While `left <= right`:
   - If `height[left] <= height[right]`:
     - If `height[left] >= leftMax`, update `leftMax = height[left]`.
     - Else, trapped water at `left` is `leftMax - height[left]`.
     - Increment `left`.
   - Else:
     - If `height[right] >= rightMax`, update `rightMax = height[right]`.
     - Else, trapped water at `right` is `rightMax - height[right]`.
     - Decrement `right`.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(1)
