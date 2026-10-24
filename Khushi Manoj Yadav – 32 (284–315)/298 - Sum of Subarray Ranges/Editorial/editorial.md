# 298. Sum of Subarray Ranges - Editorial

## Approach
By linearity of summation:
`Sum of Ranges = (Sum of Subarray Maximums) - (Sum of Subarray Minimums)`
We compute both terms in O(N) using monotonic stacks:
- For maximums: Compute previous greater/equal and next greater element boundaries.
- For minimums: Compute previous less/equal and next less element boundaries.
- The total runtime is linear, avoiding the O(N^2) brute force.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(N)
