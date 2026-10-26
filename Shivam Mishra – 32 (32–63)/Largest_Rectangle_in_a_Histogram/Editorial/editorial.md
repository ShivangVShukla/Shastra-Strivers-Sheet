
# Editorial: Largest Rectangle in a Histogram

## Topic
Monotonic Stack

## Problem Statement
Given an array of integers `heights`, where `heights[i]` represents the height of the `i-th` bar in a histogram and each bar has a width of `1`, find the maximum rectangular area that can be formed using one or more consecutive bars.

The rectangle must span consecutive bars, and its height cannot exceed the height of the shortest bar included in that range.

## Approach

### Monotonic Stack

We can solve this problem efficiently using a monotonic increasing stack that stores the indices of histogram bars in increasing order of height.

For each bar, we determine whether it can extend the rectangle formed by previously encountered bars.

### Algorithm

1. Initialize an empty stack `st` to store indices of bars and a variable `maxArea = 0` to track the largest rectangle found.
2. Traverse the histogram from index `0` to `n`, where `n` is the number of bars.
3. For each index `i`, set `currentHeight` to `heights[i]`. When `i == n`, use `0` as a sentinel height to ensure that all remaining bars are processed.
4. While the stack is not empty and the height of the bar at the top of the stack is greater than `currentHeight`:
   - Pop the top index from the stack.
   - Let the height of the popped bar be `height`.
   - If the stack is empty after popping, the rectangle extends from index `0` to `i - 1`, so its width is `i`.
   - Otherwise, the new stack top represents the nearest previous bar with a height less than or equal to the popped bar's height. The rectangle width is `i - st.top() - 1`.
   - Calculate the area as `height * width` and update `maxArea`.
5. If `i < n`, push the current index onto the stack.
6. After processing all bars, return `maxArea`.

### Why Does This Work?

The stack maintains indices in increasing order of bar heights. When a shorter bar is encountered, it establishes the right boundary for taller bars that are popped.

For each popped bar, we have identified the widest range in which that bar can serve as the limiting height. The nearest smaller bar on the left determines the left boundary, while the current index determines the right boundary.

Since every bar is pushed onto the stack once and popped at most once, the algorithm processes the histogram efficiently.

### Example Walkthrough

**Input:** `heights = [2, 1, 5, 6, 2, 3]`

- At index `0`, height `2` is pushed onto the stack.
- At index `1`, height `1` is smaller than `2`. Pop height `2`. Its width is `1`, so the area is `2 * 1 = 2`. Push index `1`.
- At index `2`, height `5` is pushed.
- At index `3`, height `6` is pushed because it is taller than `5`.
- At index `4`, height `2` is smaller than `6`. Pop height `6`. Its width is `1`, so the area is `6 * 1 = 6`.
- Height `5` is now greater than `2`, so pop it as well. Its width is `2`, so the area is `5 * 2 = 10`.
- Continue processing the remaining bars. No rectangle found in the remaining steps exceeds `10`.

**Output:** `10`

The largest rectangle has height `5` and width `2`.

## Cpp Solution

```cpp
class Solution {
public:
    int largestRectangleArea(vector<int>& heights) {
        int n = heights.size();
        stack<int> st;
        int maxArea = 0;

        for (int i = 0; i <= n; i++) {
            int currentHeight = (i == n) ? 0 : heights[i];

            while (!st.empty() &&
                   heights[st.top()] > currentHeight) {
                int height = heights[st.top()];
                st.pop();

                int width = st.empty()
                    ? i
                    : i - st.top() - 1;

                maxArea = max(maxArea, height * width);
            }

            if (i < n) {
                st.push(i);
            }
        }

        return maxArea;
    }
};
```

## Complexity

- **Time Complexity:** `O(n)`, where `n` is the number of bars. Every index is pushed onto and popped from the stack at most once.
- **Auxiliary Space:** `O(n)` in the worst case, when all bar heights are in increasing order and all indices remain in the stack until the final iteration.

## Implementation

See `Solution/solution.cpp`.

The implementation follows the standard LeetCode-style `Solution` class interface. It accepts a reference to the histogram heights and returns the maximum rectangular area.

The solution uses a monotonic stack and a sentinel height of `0` to process all remaining bars without requiring a separate cleanup loop.
