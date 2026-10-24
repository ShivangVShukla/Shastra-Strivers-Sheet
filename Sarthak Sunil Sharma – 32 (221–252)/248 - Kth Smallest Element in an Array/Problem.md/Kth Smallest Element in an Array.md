### Problem 248: Kth Smallest Element in an Array
**Difficulty:** Medium | **Tags:** Heap, Priority Queue, Min Heap, Arrays

**Problem Statement**

Given an array of integers and an integer `k`, find the `k`th smallest element in the array.

The array is not necessarily sorted, and duplicate values are allowed. The `k`th smallest element is counted according to its position in the sorted array, including duplicates.

**Input Format**

- The first line contains two integers `n` and `k`.
- The second line contains `n` space-separated integers representing the array.

**Output Format**

Print the `k`th smallest element.

**Constraints**

- `1 ≤ k ≤ n ≤ 10^5`
- `-10^9 ≤ arr[i] ≤ 10^9`

**Example 1**

**Input:**
```text
6 3
7 10 4 3 20 15
```

**Output:**
```text
7
```

**Explanation:** The sorted array is `[3, 4, 7, 10, 15, 20]`, so the 3rd smallest element is `7`.

**Example 2**

**Input:**
```text
5 4
7 10 4 3 20
```

**Output:**
```text
10
```

**Explanation:** The sorted array is `[3, 4, 7, 10, 20]`, so the 4th smallest element is `10`.

**Approach:**

Use a max heap containing at most `k` elements. Insert every array element into the max heap. If the heap size becomes greater than `k`, remove the largest element. After processing the whole array, the root of the max heap is the `k`th smallest element.

**Complexity:**

- Time: `O(n log k)`
- Space: `O(k)`
