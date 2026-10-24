### Problem 244: Min Heap & Max Heap Implementation
**Difficulty:** Learning | **Tags:** Heap, Min Heap, Max Heap, Heapify, Priority Queue

**Problem Statement**

Implement both a **Min Heap** and a **Max Heap** from scratch using arrays.

The data structure must support the following operations:

- `1 x` — insert `x` into both heaps
- `2` — remove and print the minimum element from the Min Heap
- `3` — remove and print the maximum element from the Max Heap
- `4` — print the minimum element without removing it
- `5` — print the maximum element without removing it
- `6` — print the current size of both heaps

For operations `2` and `4`, if the heaps are empty, print `EMPTY`.
For operations `3` and `5`, if the heaps are empty, print `EMPTY`.

Each output is printed on a separate line.

**Input Format**

The first line contains an integer `q`, the number of operations.

Each of the next `q` lines contains one operation:

- `1 x`
- `2`
- `3`
- `4`
- `5`
- `6`

**Output Format**

Print the result for every operation that produces output.

For operation `6`, print:

```text
minHeapSize maxHeapSize
```

**Constraints**

- `1 ≤ q ≤ 10^5`
- `-10^9 ≤ x ≤ 10^9`
- Remove and peek operations may be performed when the heaps are empty.

**Example 1**

**Input:**
```text
8
1 5
1 2
1 8
4
5
2
3
6
```

**Output:**
```text
2
8
2
8
2 2
```

**Explanation:**
The Min Heap keeps `2` at its root, while the Max Heap keeps `8` at its root. Operation `2` removes only from the Min Heap, and operation `3` removes only from the Max Heap.

**Example 2**

**Input:**
```text
7
4
5
1 10
1 3
4
5
6
```

**Output:**
```text
EMPTY
EMPTY
3
10
2 2
```

**Approach:**

Store the Min Heap and Max Heap separately in arrays. For insertion, append the new value and use heapify-up. For root removal, replace the root with the last element and use heapify-down. The only difference is the comparison rule: Min Heap keeps the smaller parent, while Max Heap keeps the larger parent.

**Complexity:**

- Insert: `O(log n)`
- Remove root: `O(log n)`
- Peek root: `O(1)`
- Size: `O(1)`
- Space: `O(n)`
