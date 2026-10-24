# 294. Next Greater Element - Editorial

## Approach
Use a monotonic decreasing stack traversing the array from right to left:
1. For each element `arr[i]`:
   - Pop elements from the stack while `stack.top() <= arr[i]`.
   - If the stack is not empty, `stack.top()` is the Next Greater Element for `arr[i]`.
   - If the stack is empty, no greater element exists to the right, so record `-1`.
   - Push `arr[i]` onto the stack.
2. This ensures each element is pushed and popped at most once.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
