# Editorial: Find Minimum in Rotated Sorted Array

## Topic
Binary Search

## Problem Statement
Given a rotated sorted array of distinct integers, find and return the minimum element in the array.

A rotated sorted array is formed by taking a sorted array and moving some of its elements from the beginning to the end. The array may also be rotated zero times, meaning it remains sorted.

**Example**

**Input:**
```text
5
3 4 5 1 2
```

**Output:**
```text
1
```

**Explanation:** The original sorted array is `[1, 2, 3, 4, 5]`. After rotation, it becomes `[3, 4, 5, 1, 2]`. The minimum element is `1`.

## Approach: Binary Search

The key observation is that a rotated sorted array consists of at most two sorted portions. The minimum element is located at the point where the rotation divides these portions.

Instead of checking every element, we use binary search to eliminate approximately half of the search range in each iteration.

Maintain two pointers:

- `low`: the beginning of the current search range.
- `high`: the end of the current search range.

At each iteration, calculate the middle index:

```cpp
int mid = low + (high - low) / 2;
```

Compare `nums[mid]` with `nums[high]` to determine which portion contains the minimum.

### Case 1: The current range is already sorted

If `nums[low] < nums[high]`, the elements in the current range are sorted in increasing order. Therefore, `nums[low]` is the minimum, and we can return it immediately.

### Case 2: The middle element is greater than the rightmost element

If `nums[mid] > nums[high]`, the minimum must lie strictly to the right of `mid`.

This is because the middle element belongs to the larger, pre-rotation portion of the array, while the minimum lies in the other portion.

Update:

```cpp
low = mid + 1;
```

### Case 3: The middle element is smaller than the rightmost element

If `nums[mid] < nums[high]`, the minimum lies at `mid` or somewhere to its left.

The middle element may itself be the minimum, so we must not discard it.

Update:

```cpp
high = mid;
```

Since all elements are distinct, equality between `nums[mid]` and `nums[high]` does not occur when `mid < high`.

Continue until `low == high`. At this point, both pointers identify the minimum element.

Return:

```cpp
return nums[low];
```

## C++ Implementation

The following implementation uses a `Solution` class and binary search.

```cpp
#include <bits/stdc++.h>
using namespace std;

class Solution {
public:
    int findMin(vector<int>& nums) {
        int low = 0;
        int high = nums.size() - 1;

        while (low < high) {
            // The current range is sorted
            if (nums[low] < nums[high]) {
                return nums[low];
            }

            int mid = low + (high - low) / 2;

            if (nums[mid] > nums[high]) {
                // Minimum is strictly to the right
                low = mid + 1;
            } else {
                // Minimum is at mid or to its left
                high = mid;
            }
        }

        return nums[low];
    }
};

int main() {
    ios::sync_with_stdio(false);
    cin.tie(nullptr);

    int n;
    cin >> n;

    vector<int> nums(n);

    for (int i = 0; i < n; i++) {
        cin >> nums[i];
    }

    Solution obj;
    cout << obj.findMin(nums) << '\n';

    return 0;
}
```

## Dry Run

Consider the array:

`nums = [3, 4, 5, 1, 2]`

| Iteration | low | high | mid | nums[mid] | nums[high] | Action |
|---|---:|---:|---:|---:|---:|---|
| 1 | 0 | 4 | 2 | 5 | 2 | `5 > 2`, so `low = 3` |
| 2 | 3 | 4 | 3 | 1 | 2 | `1 < 2`, so `high = 3` |

Now `low == high == 3`. The algorithm returns `nums[3] = 1`.

**Output:** `1`

## Correctness Proof

The algorithm maintains the invariant that the minimum element remains inside the search interval `[low, high]`.

- If the current range is sorted, its first element is the minimum, so returning `nums[low]` is correct.
- If `nums[mid] > nums[high]`, the minimum must occur to the right of `mid`, so setting `low = mid + 1` preserves the minimum.
- Otherwise, the minimum is at `mid` or to its left, so setting `high = mid` preserves the minimum.

Each update reduces the search interval while retaining the minimum. When `low == high`, the interval contains exactly one element, which must be the minimum. Therefore, the algorithm returns the correct answer.

## Complexity Analysis

**Time Complexity: O(log n)**

Binary search reduces the search interval by approximately half during every iteration. Consequently, the number of iterations is logarithmic in the array size.

**Auxiliary Space Complexity: O(1)**

The algorithm uses only a constant number of integer variables and does not allocate any additional data structures proportional to the input size.

## Edge Cases

1. **Single element:** `[7]` — returns `7`.
2. **No rotation:** `[1, 2, 3, 4, 5]` — returns `1`.
3. **Minimum at the beginning after rotation:** `[2, 3, 4, 5, 1]` — returns `1`.
4. **Minimum near the middle:** `[4, 5, 1, 2, 3]` — returns `1`.
5. **Two elements:** `[2, 1]` — returns `1`.

## Conclusion

The problem can be solved efficiently using binary search by comparing the middle element with the rightmost element. By eliminating the portion that cannot contain the minimum, the algorithm achieves **O(log n) time complexity and O(1) auxiliary space**, improving upon the O(n) time required by a linear scan.