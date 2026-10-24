### Problem 249: Sort K-Sorted Array
**Difficulty:** Medium | **Tags:** Heap, Priority Queue, Min Heap, Sorting, Arrays

**Problem Statement**

A `k`-sorted array is an array in which every element is at most `k` positions away from its position in the fully sorted array. Given a `k`-sorted array and the integer `k`, sort the array in ascending order and print the result.

**Input Format**

- The first line contains two integers `n` and `k`.
- The second line contains `n` space-separated integers.

**Output Format**

Print the sorted array in ascending order, with elements separated by a single space.

**Constraints**

- `1 ≤ n ≤ 10^5`
- `0 ≤ k ≤ n`
- `-10^9 ≤ arr[i] ≤ 10^9`
- The input array is guaranteed to be `k`-sorted.

**Example 1**

**Input:**
```text
7 3
6 5 3 2 8 10 9
```

**Output:**
```text
2 3 5 6 8 9 10
```

**Explanation:** Every element is at most 3 positions away from its position in the sorted array. A min heap of size at most `k + 1` can produce the sorted order efficiently.

**Example 2**

**Input:**
```text
5 1
2 1 4 3 5
```

**Output:**
```text
1 2 3 4 5
```

**Explanation:** Each element is at most one position away from its sorted position.

**Approach:**

Insert the first `k + 1` elements into a min heap. Repeatedly remove the minimum and append it to the answer, adding the next unseen array element each time. Finally, remove the remaining heap elements in ascending order.

**Complexity:**

- Time: `O(n log(k + 1))` for `k ≥ 1`; `O(n)` when `k = 0`.
- Space: `O(min(n, k + 1))` for the heap, plus the output buffer.
