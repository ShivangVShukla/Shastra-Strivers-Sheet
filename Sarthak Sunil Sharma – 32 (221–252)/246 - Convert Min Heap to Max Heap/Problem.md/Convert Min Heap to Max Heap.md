### Problem 246: Convert Min Heap to Max Heap
**Difficulty:** Easy | **Tags:** Heap, Min Heap, Max Heap, Heapify

**Problem Statement**

Given an array representing a valid Min Heap, convert it into a valid Max Heap in-place.

**Input Format**

The first line contains an integer `n`. The second line contains `n` space-separated integers representing a valid Min Heap.

**Output Format**

Print the array after converting it into a Max Heap, with elements separated by spaces.

**Constraints**

- `1 ≤ n ≤ 10^5`
- `-10^9 ≤ arr[i] ≤ 10^9`
- The input array represents a valid Min Heap.

**Example 1**
**Input:**
```text
6
1 3 5 7 9 8
```

**Output:**
```text
9 7 8 1 3 5
```

**Explanation:**
The Min Heap is converted to a Max Heap using bottom-up max-heapification.

**Approach:**

Start from the last non-leaf node and apply Max-Heapify down to the root. This transforms the array into a Max Heap in-place.

**Complexity:**

- Time: `O(n)`
- Extra Space: `O(1)`
