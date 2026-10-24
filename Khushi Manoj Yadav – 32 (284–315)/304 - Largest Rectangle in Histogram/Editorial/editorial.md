# 304. Largest Rectangle in Histogram - Editorial

## Approach
For every bar `heights[i]`, the largest rectangle using `heights[i]` as the shortest bar extends to:
- The first bar to its left with height `< heights[i]`
- The first bar to its right with height `< heights[i]`

Using a monotonic increasing stack of bar indices:
1. Append a dummy bar of height `0` to flush remaining bars at the end.
2. For each index `i`:
   - While `heights[stack.top()] >= heights[i]`:
     - Pop the top bar: its height is `h`.
     - The width is `i` if stack is empty, else `i - stack.top() - 1`.
     - Update `maxArea = max(maxArea, h * width)`.
   - Push `i`.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
