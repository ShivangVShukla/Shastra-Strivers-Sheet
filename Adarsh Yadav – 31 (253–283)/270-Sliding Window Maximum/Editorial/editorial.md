# Editorial: Sliding Window Maximum

## Difficulty
Hard

## Prerequisites
- Arrays
- Queue / Deque
- Sliding window
- Monotonic data structures

## Problem Summary
For each contiguous subarray of length `k`, output its maximum element.

## Approach: Monotonic Deque
Maintain a deque of array indices. Values at those indices are kept in decreasing order from front to back, so the front always represents the maximum of the current window.

For each index `i`:
1. Remove the front index if it is outside the current window (`index <= i - k`).
2. Remove indices from the back while their values are less than or equal to `arr[i]`; they cannot be the maximum while the new element is present.
3. Add index `i` to the back.
4. Once the first complete window has been processed (`i >= k - 1`), output the value at the front index.

## Example Walkthrough
For `arr = [1, 3, -1, -3, 5, 3, 6, 7]` and `k = 3`, the windows have maxima:
- `[1, 3, -1]` → `3`
- `[3, -1, -3]` → `3`
- `[-1, -3, 5]` → `5`
- `[-3, 5, 3]` → `5`
- `[5, 3, 6]` → `6`
- `[3, 6, 7]` → `7`

Answer: `[3, 3, 5, 5, 6, 7]`.

## Complexity Analysis
- **Time Complexity:** `O(N)` per test case; each index is added and removed at most once.
- **Space Complexity:** `O(K)` for the deque and `O(N-K+1)` for the result.

## Edge Cases
- `K = 1`: each element is its own window maximum.
- `K = N`: only one window exists.
- All values equal.
- Strictly increasing or decreasing arrays.
- Negative values and repeated maximum values.
