# 313. Fruit Into Baskets - Editorial

## Approach
This problem reduces to finding the longest subarray containing at most 2 distinct elements:
1. Maintain a sliding window `[left, right]` and a hash map tracking the frequencies of elements in the window.
2. Advance `right` and update `freq[fruits[right]]`.
3. If `freq.size() > 2`:
   - Shrink window from `left`: decrement `freq[fruits[left]]`.
   - If count reaches 0, erase the element from `freq`.
   - Increment `left`.
4. The maximum window length `right - left + 1` across all valid windows is the answer.

## Complexity
- Time Complexity: O(N)
- Space Complexity: O(1) (at most 3 distinct elements in hash map at any time).
