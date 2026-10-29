
# Editorial: Sliding Window Maximum

## Topic
Sliding Window / Monotonic Deque

## Problem Statement

Given an integer array `nums` of size `N` and an integer `K`, find the maximum element in every contiguous subarray (window) of size `K`.

The window moves one position to the right at a time. Return the maximum value for each window in order.

### Example 1

**Input:**
```text
8 3
1 3 -1 -3 5 3 6 7
```

**Output:**
```text
3 3 5 5 6 7
```

**Explanation:**

The windows and their maximum values are:

- `[1, 3, -1]` → `3`
- `[3, -1, -3]` → `3`
- `[-1, -3, 5]` → `5`
- `[-3, 5, 3]` → `5`
- `[5, 3, 6]` → `6`
- `[3, 6, 7]` → `7`

Therefore, the answer is `[3, 3, 5, 5, 6, 7]`.

## Approach: Monotonic Deque

A straightforward approach checks every element in each window to find its maximum. This takes `O(N * K)` time, which can be inefficient for large arrays.

We can optimize the solution using a double-ended queue (deque) that stores array indices.

The deque maintains indices in decreasing order of their corresponding array values. Therefore, the index at the front always points to the maximum element in the current window.

### Algorithm

1. Initialize an empty deque `dq` and an empty answer array.
2. Traverse the array from left to right using index `i`.
3. Remove indices from the front while they are outside the current window. An index is outside when `dq.front() <= i - K`.
4. Remove indices from the back while their corresponding values are less than or equal to `nums[i]`. These elements cannot be the maximum while the current element remains in the window.
5. Add the current index `i` to the back of the deque.
6. Once `i >= K - 1`, the first complete window has been formed. Add `nums[dq.front()]` to the answer.
7. Continue until all elements have been processed.

## C++ Implementation

```cpp
class Solution {
public:
    vector<int> maxSlidingWindow(vector<int>& nums, int k) {
        deque<int> dq;
        vector<int> ans;
        int n = nums.size();

        for (int i = 0; i < n; i++) {
            // Remove indices outside the current window
            while (!dq.empty() && dq.front() <= i - k) {
                dq.pop_front();
            }

            // Maintain decreasing order of values
            while (!dq.empty() &&
                   nums[dq.back()] <= nums[i]) {
                dq.pop_back();
            }

            // Add the current index
            dq.push_back(i);

            // Record the maximum for each complete window
            if (i >= k - 1) {
                ans.push_back(nums[dq.front()]);
            }
        }

        return ans;
    }
};
```

## Dry Run

Consider:

`nums = [1, 3, -1, -3, 5, 3, 6, 7]`, `K = 3`

The deque stores indices, and the table shows the corresponding values after processing each element.

| Index `i` | Value | Deque values | Maximum |
|---:|---:|---|---:|
| 0 | 1 | `[1]` | — |
| 1 | 3 | `[3]` | — |
| 2 | -1 | `[3, -1]` | 3 |
| 3 | -3 | `[3, -1, -3]` | 3 |
| 4 | 5 | `[5]` | 5 |
| 5 | 3 | `[5, 3]` | 5 |
| 6 | 6 | `[6]` | 6 |
| 7 | 7 | `[7]` | 7 |

**Final output:** `[3, 3, 5, 5, 6, 7]`

Here, each deque value represents the array value at its stored index. The deque is updated by removing expired indices and removing smaller values that can no longer become a maximum.

## Why Does the Deque Work?

The deque maintains two important properties:

1. **Valid indices:** Every stored index belongs to the current window.
2. **Decreasing values:** Values corresponding to indices in the deque decrease from front to back.

Because of the second property, the front always holds the index of the largest value among the remaining candidates.

When a new value arrives, any smaller or equal value at the back can be removed. The new value is at least as large and will remain in the window longer, so the removed value can never be needed as a future maximum.

## Correctness Proof

At each iteration, expired indices are removed first. Next, all indices whose values are less than or equal to the current value are removed from the back, and the current index is inserted.

Consequently, the deque contains valid candidate indices in decreasing order of their values. Its front therefore identifies the maximum element in the current window.

For every index `i >= K - 1`, the current window has exactly `K` elements. Appending the value at the front produces the correct maximum for that window. Since every complete window is processed in order, the algorithm returns all window maximums correctly.

## Complexity Analysis

### Time Complexity: `O(N)`

Each array index is inserted into the deque exactly once and removed at most once. Therefore, the total number of deque operations is linear in the array size.

### Auxiliary Space Complexity: `O(K)`

The deque stores at most `K` indices. The output array requires `O(N - K + 1)` space and is excluded from auxiliary space complexity.

## Edge Cases

- `K = 1`: Each element is its own window, so the output equals the input array.
- `K = N`: Only one window exists, and the output contains the array maximum.
- All elements equal: Every window maximum equals that repeated value.
- Strictly increasing array: Smaller values are repeatedly removed from the back.
- Strictly decreasing array: The deque retains up to `K` indices until older indices expire.
- Negative values: The algorithm works without special handling because it compares actual array values.

## Conclusion

The monotonic deque optimizes the Sliding Window Maximum problem from `O(N * K)` to `O(N)` time. By storing only useful indices in decreasing order of their values, it efficiently identifies the maximum for every window while using `O(K)` auxiliary space.
