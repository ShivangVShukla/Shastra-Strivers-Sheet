### Problem 250: Merge M Sorted Lists
**Difficulty:** Medium | **Tags:** Heap, Priority Queue, Linked Lists, K-Way Merge

**Problem Statement**

You are given `m` sorted lists of integers. Merge all the lists into one sorted list in ascending order and print the merged result. Duplicate values must be preserved.

**Input Format**

- The first line contains an integer `m`, the number of lists.
- The next `m` lines each begin with an integer `len`, followed by `len` sorted integers.

**Output Format**

Print all elements from all lists in ascending order, separated by a single space. If all lists are empty, print an empty line.

**Constraints**

- `1 ≤ m ≤ 10^4`
- `0 ≤ len ≤ 10^4` for each list
- The total number of elements across all lists is at most `10^5`
- Each list is sorted in non-decreasing order.
- `-10^9 ≤ value ≤ 10^9`

**Example 1**

**Input:**
```text
3
3 1 4 5
3 1 3 4
2 2 6
```

**Output:**
```text
1 1 2 3 3 4 4 5 6
```

**Explanation:** The algorithm keeps the current smallest unmerged element from each non-empty list in a min heap. Each time it removes the smallest element, it adds the next element from that same list.

**Example 2**

**Input:**
```text
2
0
3 -2 0 7
```

**Output:**
```text
-2 0 7
```

**Explanation:** Empty lists are skipped while the non-empty list is included in the final sorted result.

**Approach:**

Insert the first element of every non-empty list into a min heap, storing its value, list index, and index within that list. Repeatedly extract the minimum and insert the next element from the same list, if one exists.

**Complexity:**

- Time: `O(N log m)`, where `N` is the total number of elements.
- Space: `O(m)` for the heap, excluding stored input and output.
