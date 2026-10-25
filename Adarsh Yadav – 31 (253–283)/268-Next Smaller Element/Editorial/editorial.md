# Editorial: Next Smaller Element

## Difficulty
Medium

## Prerequisites
- Arrays
- Stack (LIFO)
- Monotonic stacks

## Problem Summary
For every array element, find the first strictly smaller element that appears to its right. If no such element exists, return `-1` for that position.

## Explanation & Approach

### Approach: Monotonic Stack
Process the array from right to left. Maintain a stack of candidate values, with the nearest useful candidate at the top.

For each element `arr[i]`:
1. While the stack is not empty and its top value is greater than or equal to `arr[i]`, pop it. Those values cannot be the next strictly smaller element for `arr[i]`.
2. If the stack is empty, set `ans[i] = -1`.
3. Otherwise, the top of the stack is the first smaller element to the right; set `ans[i]` to that value.
4. Push `arr[i]` onto the stack.

### Example Walkthrough
For `arr = [4, 8, 5, 2, 25]`, scan from right to left:
- `25`: no candidate, so answer `-1`; push `25`.
- `2`: pop `25`; no candidate remains, so answer `-1`; push `2`.
- `5`: top `2` is smaller, so answer `2`; push `5`.
- `8`: top `5` is smaller, so answer `5`; push `8`.
- `4`: pop `8` and `5`; top `2` is smaller, so answer `2`.

The result in original order is `[2, 5, 2, -1, -1]`.

## Java Solution
The solution scans from right to left and uses an array as a stack to avoid boxing overhead.

## Complexity Analysis
- **Time Complexity:** `O(N)` per test case, because each element is pushed once and popped at most once.
- **Space Complexity:** `O(N)` for the stack and answer array.

## Edge Cases
- A single element: answer is `-1`.
- Strictly increasing values: every answer is `-1`.
- Strictly decreasing values: each element except the last has the next element as its NSE.
- Duplicate values: equal values are popped because NSE must be strictly smaller.
- Negative numbers and values near the constraint limits.
