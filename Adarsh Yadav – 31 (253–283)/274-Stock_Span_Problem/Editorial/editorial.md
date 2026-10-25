# Editorial — Stock Span Problem

## Approach: Monotonic Decreasing Stack

For each day `i`, the span is the distance from the current day to the nearest previous day whose price is strictly greater than `arr[i]`. If no such day exists, the span is `i + 1`.

1. Maintain a stack of indices with stock prices in strictly decreasing order from bottom to top.
2. For each day `i`, pop indices while their prices are less than or equal to `arr[i]`. Those days are included in the current span.
3. If the stack is empty, the span is `i + 1`; otherwise, it is `i - stack.peek()`.
4. Push the current index onto the stack.

Each index is pushed once and popped at most once, so the algorithm runs in linear time.

## Complexity

- **Time:** `O(N)` per test case.
- **Auxiliary space:** `O(N)` for the stack and result array.
