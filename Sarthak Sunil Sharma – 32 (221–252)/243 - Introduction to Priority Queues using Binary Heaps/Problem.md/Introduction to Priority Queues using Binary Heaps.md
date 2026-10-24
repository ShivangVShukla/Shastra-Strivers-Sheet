### Problem 243: Introduction to Priority Queues using Binary Heaps
**Difficulty:** Learning | **Tags:** Priority Queue, Binary Heap, Min Heap, Heap Operations

**Problem Statement**

Implement a **min-priority queue** using a binary min-heap.

The priority queue must support the following operations:

- `1 x` — insert value `x`
- `2` — remove the minimum value
- `3` — print the minimum value without removing it
- `4` — print the current size

For operations `2`, `3`, and `4`, print the corresponding result on a separate line.

If operation `2` is performed when the priority queue is empty, print `EMPTY`.

If operation `3` is performed when the priority queue is empty, print `EMPTY`.

**Input Format**

The first line contains an integer `q`, the number of operations.

Each of the next `q` lines contains one operation:

- `1 x`
- `2`
- `3`
- `4`

**Output Format**

For every operation `2`, `3`, or `4`, print the required result on a separate line.

**Constraints**

- `1 ≤ q ≤ 10^5`
- `-10^9 ≤ x ≤ 10^9`
- Operation `2` and `3` may be called when the queue is empty.

**Example 1**

**Input:**
```text
8
1 5
1 2
1 8
3
2
3
4
2
```

**Output:**
```text
2
2
2
5
```

**Explanation:**
The minimum value is always kept at the root of the binary min-heap. After removing `2`, the next minimum is `5`.

**Example 2**

**Input:**
```text
7
3
1 10
1 4
1 7
3
2
3
```

**Output:**
```text
EMPTY
4
4
```

**Approach:**

Represent the binary min-heap using an array. For insertion, append the value and repeatedly swap it with its parent while it is smaller. For removal, replace the root with the last element, remove the last element, and repeatedly swap the root with its smaller child until the heap property is restored.

**Complexity:**

- Insert: `O(log n)`
- Remove minimum: `O(log n)`
- Peek minimum: `O(1)`
- Size: `O(1)`
