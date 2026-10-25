# Editorial: Linear Search

## Difficulty
Easy

## Algorithm
1. Traverse the array from index $0$ to $N - 1$.
2. For each index $i$, check if $nums[i] == target$.
3. If equal, return $i$ immediately (smallest index).
4. If loop completes without finding $target$, return $-1$.

## Complexity Analysis
- **Time Complexity:** $O(N)$ worst case, $O(1)$ best case.
- **Space Complexity:** $O(1)$ auxiliary space.
