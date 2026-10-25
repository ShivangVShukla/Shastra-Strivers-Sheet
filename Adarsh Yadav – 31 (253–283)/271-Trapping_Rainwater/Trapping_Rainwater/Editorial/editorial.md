# Editorial — Trapping Rainwater

## Approach: Two Pointers

Water above index `i` is determined by the shorter of the tallest bars to its left and right, minus `height[i]`:

`water[i] = max(0, min(maxLeft[i], maxRight[i]) - height[i])`

We can calculate the total without storing the two maximum arrays by using two pointers.

1. Initialize `left = 0`, `right = N - 1`, `leftMax = 0`, `rightMax = 0`, and `water = 0`.
2. While `left <= right`:
   - If `height[left] <= height[right]`, the left side is the limiting side. Update `leftMax` and add `leftMax - height[left]` to the answer, then move `left` forward.
   - Otherwise, the right side is the limiting side. Update `rightMax` and add `rightMax - height[right]` to the answer, then move `right` backward.
3. Print the accumulated total.

Use `long` for the accumulated water because the answer can exceed the range of a 32-bit signed integer under the stated constraints.

## Complexity

- **Time:** `O(N)` per test case — each pointer moves inward at most `N` times.
- **Auxiliary space:** `O(1)` beyond the input array.
