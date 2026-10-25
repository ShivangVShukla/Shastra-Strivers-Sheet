# 295. Next Greater Element - 2 - Editorial

## Approach
Because the array is circular, we can simulate an array of size `2N` (concatenating the array with itself).
We traverse from index `2N - 1` down to `0`:
1. Use modulo arithmetic `i % N` to access elements.
2. Maintain a monotonic stack: pop elements while `stack.top() <= nums[i % N]`.
3. When `i < N`, the answer for `nums[i]` is `stack.top()` if the stack is non-empty, else `-1`.
4. Push `nums[i % N]` onto the stack.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
