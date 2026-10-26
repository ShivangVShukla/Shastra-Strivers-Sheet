
# Find Minimum in Rotated Sorted Array

## Problem Statement

Given an integer array `nums` of size `N`, sorted in ascending order with distinct values and then rotated an unknown number of times (between `1` and `N`), find the minimum element in the array.

Return the minimum element of the rotated sorted array.

## Examples

### Example 1

**Input:**
```text
8
4 5 6 7 0 1 2 3
```

**Output:**
```text
0
```

**Explanation:**

The minimum element in the rotated sorted array `[4, 5, 6, 7, 0, 1, 2, 3]` is `0`.

### Example 2

**Input:**
```text
5
3 4 5 1 2
```

**Output:**
```text
1
```

**Explanation:**

The minimum element in the rotated sorted array `[3, 4, 5, 1, 2]` is `1`.

## Constraints

- `1 <= N <= 10^5`
- `-10^9 <= nums[i] <= 10^9`
- All elements of `nums` are distinct.
- The array is sorted in ascending order before rotation.

## Expected Complexity

- **Time Complexity:** `O(log N)`
- **Auxiliary Space Complexity:** `O(1)`

## Input Format

- The first line contains an integer `N`, the number of elements in the array.
- The second line contains `N` space-separated integers representing the rotated sorted array.

## Output Format

Print a single integer representing the minimum element in the array.
