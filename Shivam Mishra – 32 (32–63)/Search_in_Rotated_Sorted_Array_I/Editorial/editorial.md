
# Editorial: Search in Rotated Sorted Array I

## Topic
Binary Search

## Problem Statement

Given an array `nums` sorted in ascending order with distinct elements, the array is rotated at an unknown pivot. Given a target value `k`, find and return the index of `k` in the array. If the target is not present, return `-1`.

The solution must achieve **O(log n)** time complexity.

### Example 1

**Input:**
```text
nums = [4, 5, 6, 7, 0, 1, 2]
k = 0
```

**Output:**
```text
4
```

**Explanation:** The target `0` is present at index `4`.

### Example 2

**Input:**
```text
nums = [4, 5, 6, 7, 0, 1, 2]
k = 3
```

**Output:**
```text
-1
```

**Explanation:** The target `3` is not present in the array.

## Approach: Modified Binary Search

A normal binary search works on a completely sorted array. However, a rotated sorted array is not entirely sorted, so we need to modify the binary search logic.

The key observation is:

**At least one half of the array is always sorted when the array contains distinct elements.**

We maintain two pointers:

- `low`: the starting index of the current search range.
- `high`: the ending index of the current search range.

At each iteration, calculate the middle index:

```cpp
int mid = low + (high - low) / 2;
```

Follow these steps:

1. If `nums[mid] == k`, return `mid` because the target has been found.
2. If `nums[low] <= nums[mid]`, the left half is sorted.
   - If `k` lies between `nums[low]` and `nums[mid]`, search the left half by setting `high = mid - 1`.
   - Otherwise, search the right half by setting `low = mid + 1`.
3. Otherwise, the right half is sorted.
   - If `k` lies between `nums[mid]` and `nums[high]`, search the right half by setting `low = mid + 1`.
   - Otherwise, search the left half by setting `high = mid - 1`.
4. Repeat until `low > high`.
5. If the loop ends without finding the target, return `-1`.

Because all elements are distinct, the sorted-half check and target-range comparisons are sufficient to determine which half can contain the target.

## C++ Solution

```cpp
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int search(vector<int>& nums, int k) {
        int low = 0;
        int high = (int)nums.size() - 1;

        while (low <= high) {
            int mid = low + (high - low) / 2;

            if (nums[mid] == k) {
                return mid;
            }

            if (nums[low] <= nums[mid]) {

                if (nums[low] <= k && k < nums[mid]) {
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }

            else {

                if (nums[mid] < k && k <= nums[high]) {
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return -1;
    }
};
```

## Dry Run

Consider:

```text
nums = [4, 5, 6, 7, 0, 1, 2]
k = 0
```

| Iteration | low | high | mid | nums[mid] | Decision |
|---|---:|---:|---:|---:|---|
| 1 | 0 | 6 | 3 | 7 | Left half is sorted. Target is not in [4, 7), so search right. |
| 2 | 4 | 6 | 5 | 1 | Right half is sorted. Target is not in (1, 2], so search left. |
| 3 | 4 | 4 | 4 | 0 | Target found at index 4. |

**Output:** `4`

## Complexity Analysis

### Time Complexity: O(log n)

In every iteration, we eliminate approximately half of the remaining search range. Therefore, the number of iterations grows logarithmically with the size of the array.

### Auxiliary Space: O(1)

The algorithm uses only a fixed number of variables, such as `low`, `high`, and `mid`. It does not require any additional array or recursion stack.

## Edge Cases

1. **Single-element array:** If the only element equals the target, return index `0`; otherwise, return `-1`.
2. **Target absent:** If the target does not occur in the array, the search range eventually becomes empty and the algorithm returns `-1`.
3. **Array rotated by zero positions:** The input is already sorted, and the algorithm still works correctly.
4. **Target at either boundary:** The first and last elements are handled by the same binary search logic.
5. **Two-element array:** The algorithm correctly handles both possible search ranges without accessing invalid indices.

## Key Takeaways

- Binary search can be adapted to search rotated sorted arrays.
- With distinct elements, at least one half of the current search interval is sorted.
- Checking the sorted half and the target's range lets us discard the other half.
- The optimal solution achieves `O(log n)` time and `O(1)` auxiliary space.
