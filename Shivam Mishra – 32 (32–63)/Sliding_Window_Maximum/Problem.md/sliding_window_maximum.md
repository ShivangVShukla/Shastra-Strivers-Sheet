
# Sliding Window Maximum

## Problem Statement

Given an integer array `arr` of size `N` and an integer `k`, there is a sliding window of size `k` that moves from the left of the array to the right, one position at a time.

At each position, consider only the `k` elements inside the window and find their maximum value.

Return an array containing the maximum element of every window, in the order in which the windows appear.

## Examples

### Example 1

**Input:**
```text
8 3
4 0 -1 3 5 3 6 8
```

**Output:**
```text
4 3 5 5 6 8
```

**Explanation:**

The array is `[4, 0, -1, 3, 5, 3, 6, 8]`, and the window size is `k = 3`.

The windows and their maximum values are:

- `[4, 0, -1]` → `4`
- `[0, -1, 3]` → `3`
- `[-1, 3, 5]` → `5`
- `[3, 5, 3]` → `5`
- `[5, 3, 6]` → `6`
- `[3, 6, 8]` → `8`

Therefore, the resulting array is `[4, 3, 5, 5, 6, 8]`.

### Example 2

**Input:**
```text
2 2
20 25
```

**Output:**
```text
25
```

**Explanation:**

Since the window size is equal to the array size, there is only one possible window: `[20, 25]`. Its maximum element is `25`.

## Constraints

- `1 <= N <= 10^5`
- `1 <= k <= N`
- `-10^9 <= arr[i] <= 10^9`

## Input Format

- The first line contains two space-separated integers `N` and `k`, representing the array size and sliding window size.
- The second line contains `N` space-separated integers representing the array elements.

## Output Format

Print the maximum element of each sliding window in order, separated by spaces.

## Expected Complexity

- **Time Complexity:** `O(N)`
- **Auxiliary Space Complexity:** `O(k)`
