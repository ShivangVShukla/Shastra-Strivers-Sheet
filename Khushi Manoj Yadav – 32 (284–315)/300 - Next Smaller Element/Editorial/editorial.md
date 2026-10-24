# 300. Next Smaller Element - Editorial

## Approach
Maintain a monotonic increasing stack from right to left:
1. For each element `arr[i]`:
   - Pop elements while `stack.top() >= arr[i]`.
   - If the stack is not empty, `stack.top()` is the next smaller element.
   - If the stack is empty, record `-1`.
   - Push `arr[i]` onto the stack.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
