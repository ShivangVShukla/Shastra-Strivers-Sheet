# Maximum Product Subarray in an Array

## Problem Statement

Given an integer array `nums`, find the **subarray with the largest product** and return the product of the elements present in that subarray.

A **subarray** is a contiguous, non-empty sequence of elements within an array.

## Example 1

**Input:**
```text
nums = [4, 5, 3, 7, 1, 2]
```

**Output:**
```text
840
```

**Explanation:**

The largest product is obtained from the entire array:

`4 × 5 × 3 × 7 × 1 × 2 = 840`

## Example 2

**Input:**
```text
nums = [-5, 0, -2]
```

**Output:**
```text
0
```

**Explanation:**

The largest product is achieved by subarrays containing zero, such as `[0]`, `[-5, 0]`, `[0, -2]`, and `[-5, 0, -2]`.

## Input Format

- The first line contains an integer `n`, representing the size of the array.
- The second line contains `n` space-separated integers representing the array elements.

## Output Format

Print a single integer representing the maximum product of any non-empty contiguous subarray.

## Constraints

- The array must contain at least one element.
- The subarray must be contiguous and non-empty.
- Array elements may be positive, negative, or zero.

## Expected Complexity

- **Time Complexity:** `O(n)`
- **Auxiliary Space Complexity:** `O(1)`

## Approach

Maintain the maximum and minimum products ending at each position. Both are necessary because multiplying by a negative number can turn a minimum product into a maximum product. Update the global maximum after processing each element.
