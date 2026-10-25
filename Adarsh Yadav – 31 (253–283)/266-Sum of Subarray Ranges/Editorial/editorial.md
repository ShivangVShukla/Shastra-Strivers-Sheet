# Editorial: Sum of Subarray Ranges

## Difficulty
Medium

## Prerequisites
- Arrays
- Monotonic stacks
- Contribution counting
- 64-bit integer arithmetic

## Problem Summary
For every non-empty subarray, its range is `maximum - minimum`. We need the sum of this quantity over all subarrays.

Instead of calculating every subarray separately, split the total into two sums:

`sum of subarray ranges = sum of subarray maximums - sum of subarray minimums`

## Explanation & Approach

### Step 1: Count each element's contribution as a maximum
For an element `nums[i]`, determine how many subarrays have it as their selected maximum.

Use monotonic stacks to find:
- `prevGreater[i]`: the closest index to the left with a value strictly greater than `nums[i]`, or `-1` if none exists.
- `nextGreaterOrEqual[i]`: the closest index to the right with a value greater than or equal to `nums[i]`, or `N` if none exists.

The number of subarrays in which this element is counted as a maximum is:

`(i - prevGreater[i]) * (nextGreaterOrEqual[i] - i)`

Its maximum contribution is that count multiplied by `nums[i]`.

The asymmetric strict/non-strict comparisons consistently handle duplicate values without counting a subarray's maximum more than once.

### Step 2: Count each element's contribution as a minimum
Similarly, find:
- `prevSmaller[i]`: the closest index to the left with a value strictly smaller than `nums[i]`, or `-1`.
- `nextSmallerOrEqual[i]`: the closest index to the right with a value smaller than or equal to `nums[i]`, or `N`.

The number of subarrays in which the element is counted as a minimum is:

`(i - prevSmaller[i]) * (nextSmallerOrEqual[i] - i)`

Its minimum contribution is that count multiplied by `nums[i]`.

### Step 3: Subtract the totals
Add all maximum contributions and all minimum contributions, then return:

`maximumTotal - minimumTotal`

### Step 4: Algorithm
1. Traverse from left to right with a monotonic stack to calculate previous-greater and previous-smaller boundaries.
2. Traverse from right to left to calculate next-greater-or-equal and next-smaller-or-equal boundaries.
3. Sum each element's maximum and minimum contribution using `long`.
4. Return the difference.

## Java Solution
The implementation below computes all four boundaries in linear time. `long` is required because the result can exceed a 32-bit integer.

## Complexity Analysis
- **Time Complexity:** `O(N)` per test case. Every index is pushed onto and popped from each stack at most once.
- **Space Complexity:** `O(N)` for the boundary arrays and stacks.

## Edge Cases
- A one-element array has answer `0`.
- An array containing only equal values has answer `0`.
- Strictly increasing and strictly decreasing arrays.
- Negative values and mixtures of negative and positive values.
- Duplicate minimums or maximums.
- Values near `-10^9` and `10^9`, requiring `long` arithmetic.
