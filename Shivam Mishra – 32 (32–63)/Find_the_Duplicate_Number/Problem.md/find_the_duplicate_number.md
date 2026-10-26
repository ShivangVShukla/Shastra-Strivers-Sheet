# Find the Duplicate Number

## Problem Statement

Given an array of integers `nums` containing `n + 1`
integers where each integer is in the range `[1, n]`
inclusive, there is only one repeated number in `nums`
(but can be repeated more than once), return this
duplicate number.

You must not modify the array (assume it is read-only),
and you must use only constant extra space.

Your algorithm should run in less than O(n²) time.

## Example 1

**Input:**
nums = [1,3,4,2,2]

**Output:**
2

## Example 2

**Input:**
nums = [3,1,3,4,2]

**Output:**
3

## Constraints

- 1 <= n <= 10^5
- nums.length == n + 1
- 1 <= nums[i] <= n
- Only one distinct number is duplicated, and it may
  occur more than twice.
