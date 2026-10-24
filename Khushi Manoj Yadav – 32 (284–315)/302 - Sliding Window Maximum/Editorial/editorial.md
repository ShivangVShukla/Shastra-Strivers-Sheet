# 302. Sliding Window Maximum - Editorial

## Approach
Use a monotonic double-ended queue (`deque`) storing indices:
1. Maintain elements in decreasing order of value inside the deque.
2. For each element `nums[i]`:
   - Pop front if the index has fallen out of the window (`dq.front() < i - k + 1`).
   - Pop back while `nums[dq.back()] <= nums[i]`.
   - Push `i` to back.
   - If `i >= k - 1`, the maximum of current window is at `dq.front()`.

## Complexity
- Time Complexity: O(N) since each index is pushed and popped at most once.
- Space Complexity: O(K)
