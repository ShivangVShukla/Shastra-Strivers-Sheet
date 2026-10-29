
# Search in Rotated Sorted Array-I

Given an integer array `nums`, sorted in ascending order (with distinct values) and a target value `k`. The array is **rotated** at some pivot point that is unknown. Find the **index** at which `k` is present and if `k` is not present return `-1`.

### Example 1:

**Input:** `nums = [4, 5, 6, 7, 0, 1, 2], k = 0`

**Output:** `4`

**Explanation:** Here, the target is `0`. We can see that `0` is present in the given rotated sorted array, `nums`. Thus, we get output as `4`, which is the index at which `0` is present in the array.

### Example 2:

**Input:** `nums = [4, 5, 6, 7, 0, 1, 2], k = 3`

**Output:** `-1`

**Explanation:** Here, the target is `3`. Since `3` is not present in the given rotated sorted array, `nums`. Thus, the output is `-1`.

### Constraints

- `1 <= nums.length <= 10^5`
- `-10^4 <= nums[i] <= 10^4`
- All values of `nums` are distinct.
- `nums` is guaranteed to be rotated at some pivot.
- `-10^4 <= k <= 10^4`

### Expected Complexity

- **Time Complexity:** `O(log n)`
- **Auxiliary Space:** `O(1)`
