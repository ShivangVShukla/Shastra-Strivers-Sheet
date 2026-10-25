# 299. Remove K Digits - Editorial

## Approach
A smaller leading digit creates a strictly smaller number regardless of subsequent digits.
Greedy strategy using a monotonic increasing stack:
1. Traverse digits of `num` from left to right.
2. While `k > 0`, stack is non-empty, and `stack.top() > current_digit`, pop from the stack and decrement `k`.
3. Push `current_digit`.
4. If `k` removals remain after traversal, pop the remaining `k` digits from the end.
5. Strip leading zeroes. If the result string is empty, return `"0"`.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
