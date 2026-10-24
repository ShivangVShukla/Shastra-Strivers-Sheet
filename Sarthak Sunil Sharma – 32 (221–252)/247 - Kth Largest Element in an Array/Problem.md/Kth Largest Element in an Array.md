### Problem 247: Kth Largest Element in an Array
**Difficulty:** Medium | **Tags:** Heap, Priority Queue, Array, Min Heap

**Problem Statement**

Given an integer array `nums` and an integer `k`, find the `k`th largest element in the array.

The `k`th largest element is the element that would appear at index `k - 1` if the array were sorted in descending order. Duplicate values are counted separately.

**Input Format**

- The first line contains two integers `n` and `k`.
- The second line contains `n` integers representing the array `nums`.

**Output Format**

Print the `k`th largest element.

**Constraints**

- `1 ≤ k ≤ n ≤ 10^5`
- `-10^9 ≤ nums[i] ≤ 10^9`

**Example 1**
**Input:**
```text
6 2
3 2 1 5 6 4
```
**Output:**
```text
5
```
**Explanation:** The elements in descending order are `6, 5, 4, 3, 2, 1`, so the 2nd largest element is `5`.

**Example 2**
**Input:**
```text
5 4
3 2 3 1 2
```
**Output:**
```text
2
```
**Explanation:** The descending order is `3, 3, 2, 2, 1`, so the 4th largest element is `2`.

**Approach:**

Maintain a min heap containing the largest `k` elements seen so far. For every array element, insert it into the heap. If the heap size becomes greater than `k`, remove its minimum element. After processing all elements, the heap's minimum is the `k`th largest element.

**Complexity:**

- Time: `O(n log k)`
- Space: `O(k)`
