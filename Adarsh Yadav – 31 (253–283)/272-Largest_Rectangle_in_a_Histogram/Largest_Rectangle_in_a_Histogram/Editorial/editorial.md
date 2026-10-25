# Editorial — Largest Rectangle in a Histogram

## Approach: Monotonic Increasing Stack

For each bar, the largest rectangle for which it is the limiting (minimum-height) bar extends until the first bar strictly shorter than it on both sides. A monotonic increasing stack lets us find these boundaries in linear time.

1. Iterate through all bars and then process one virtual bar of height `0` at index `N` to flush the stack.
2. Maintain indices in a stack so their heights are non-decreasing.
3. When the current height is smaller than the height at the top of the stack, pop that index. The popped bar's height is the rectangle height.
4. After popping, the new stack top is the previous smaller bar. The current index is the next smaller boundary. The rectangle width is `i` if the stack is empty, otherwise `i - stack.peek() - 1`.
5. Update the maximum area using `height * width`, then push the current index if it is a real bar.

With the constraints given, `long` is used for the area to make the arithmetic safe.

## Complexity

- **Time:** `O(N)` per test case, since each index is pushed and popped at most once.
- **Auxiliary space:** `O(N)` for the stack.
