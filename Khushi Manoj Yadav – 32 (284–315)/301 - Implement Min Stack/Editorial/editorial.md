# 301. Implement Min Stack - Editorial

## Approach
Store pairs `(value, current_minimum)` in a standard stack:
- `push(x)`: The new minimum is `min(x, current_min)`. Push `(x, new_min)`.
- `pop()`: Pop the pair and return `value`.
- `top()`: Return `pair.first`.
- `getMin()`: Return `pair.second`.

All operations execute in strictly O(1) time and avoid numerical overflow.

## Complexity
- Time Complexity: O(1) for all operations.
- Space Complexity: O(N)
