
# Largest Rectangle in a Histogram

Given an array of integers `heights` representing the heights of bars in a histogram, where the width of each bar is `1`, return the area of the largest rectangle in the histogram.

## Example 1

**Input:** `heights = [2, 1, 5, 6, 2, 3]`

**Output:** `10`

**Explanation:** The largest rectangle has a height of `5` and a width of `2`, giving an area of `5 * 2 = 10` square units.

![Largest Rectangle Example 1](https://static.takeuforward.org/content/1791829354_gHNn3_qo.webp)

## Example 2

**Input:** `heights = [3, 5, 1, 7, 5, 9]`

**Output:** `15`

**Explanation:** The largest rectangle has a height of `5` and a width of `3`, giving an area of `5 * 3 = 15` square units.

![Largest Rectangle Example 2](https://static.takeuforward.org/content/1791829379_WfZl0HIT.webp)

## Constraints

- `1 <= heights.length <= 10^5`
- `0 <= heights[i] <= 10^4`

## Expected Complexity

- **Time Complexity:** `O(n)`
- **Auxiliary Space:** `O(n)`
