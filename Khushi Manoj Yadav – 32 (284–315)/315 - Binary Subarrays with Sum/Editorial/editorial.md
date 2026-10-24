# 315. Binary Subarrays with Sum - Editorial

## Approach
Use the two-pointer sliding window `atMost` technique:
- The count of subarrays with exact sum `goal` is:
  `exact(goal) = atMost(goal) - atMost(goal - 1)`
- `atMost(k)`: Counts all subarrays with sum `<= k`.
  - For each `right`, add `nums[right]` to `currentSum`.
  - While `currentSum > k`, subtract `nums[left]` and advance `left++`.
  - Every subarray starting anywhere from `left` to `right` and ending at `right` is valid. There are `right - left + 1` such subarrays.
  - Sum `(right - left + 1)` across all `right`.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(1)
