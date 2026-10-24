### Problem 251: Replace Each Element by Its Rank
**Difficulty:** Medium | **Tags:** Heap, Sorting, Hash Map, Arrays

**Problem Statement**

Given an array of integers, replace each element with its rank in the array. The smallest distinct value has rank `1`, the next distinct value has rank `2`, and so on. Equal values must receive the same rank.

**Input Format**

- The first line contains an integer `n`.
- The second line contains `n` space-separated integers.

**Output Format**

Print the rank of each array element in the original order, separated by a single space.

**Constraints**

- `1 ≤ n ≤ 10^5`
- `-10^9 ≤ arr[i] ≤ 10^9`

**Example 1**

**Input:**
```text
6
20 15 26 2 98 6
```

**Output:**
```text
4 3 5 1 6 2
```

**Explanation:** The sorted distinct values are `2, 6, 15, 20, 26, 98`. Their ranks are `1, 2, 3, 4, 5, 6`, respectively.

**Example 2**

**Input:**
```text
5
1 3 1 5 3
```

**Output:**
```text
1 2 1 3 2
```

**Explanation:** The distinct values in sorted order are `1, 3, 5`. Both occurrences of `1` get rank `1`, and `3` gets rank `2`.

**Approach:**

Copy the array and sort the copy. Assign increasing ranks to distinct values, storing each value-to-rank mapping in a hash map. Then use the map to print each original element's rank.

**Complexity:**

- Time: `O(n log n)`
- Space: `O(n)`
