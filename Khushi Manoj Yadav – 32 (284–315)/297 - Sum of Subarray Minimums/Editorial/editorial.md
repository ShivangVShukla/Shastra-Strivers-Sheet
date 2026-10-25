# 297. Sum of Subarray Minimums - Editorial

## Approach
Instead of finding minimums of all subarrays, calculate the contribution of each element `arr[i]` as the minimum:
1. Find Previous Less Element index `ple[i]` (strictly less) using a monotonic stack.
2. Find Next Less Element index `nle[i]` (less than or equal, to prevent double counting duplicates) using a monotonic stack.
3. The number of subarrays where `arr[i]` is the minimum is:
   `count = (i - ple[i]) * (nle[i] - i)`
4. Contribution of `arr[i]` is `arr[i] * count`.
5. Sum all contributions modulo `10^9 + 7`.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
