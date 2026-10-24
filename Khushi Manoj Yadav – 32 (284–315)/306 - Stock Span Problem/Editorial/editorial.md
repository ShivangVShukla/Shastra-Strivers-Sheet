# 306. Stock Span Problem - Editorial

## Approach
For day `i`, the span is `i - previous_greater_index`:
1. Use a monotonic decreasing stack storing indices.
2. For each day `i`:
   - Pop indices while `price[stack.top()] <= price[i]`.
   - If the stack is empty, all previous days had price `<= price[i]`, so span is `i + 1`.
   - Otherwise, span is `i - stack.top()`.
   - Push `i` onto the stack.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
