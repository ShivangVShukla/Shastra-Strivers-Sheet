### Problem 245: Check if Array Represents a Min-Heap
**Difficulty:** Learning | **Tags:** Binary Heap, Min Heap, Complete Binary Tree, Arrays

**Problem Statement**

Given an array representing a complete binary tree, determine whether the array satisfies the **Min-Heap** property.

An array represents a Min-Heap if for every node:

- Its value is less than or equal to the value of its left child, if the child exists.
- Its value is less than or equal to the value of its right child, if the child exists.

The array is already assumed to represent a complete binary tree, so only the heap-order property needs to be checked.

**Input Format**

The first line contains an integer `n`.

The second line contains `n` integers representing the array.

**Output Format**

Print `YES` if the array represents a valid Min-Heap. Otherwise, print `NO`.

**Constraints**

- `1 ≤ n ≤ 10^5`
- `-10^9 ≤ arr[i] ≤ 10^9`

**Example 1**

**Input:**
```text
5
1 2 3 4 5
```

**Output:**
```text
YES
```

**Explanation:**
Every parent is less than or equal to both of its children.

**Example 2**

**Input:**
```text
5
1 3 2 7 6
```

**Output:**
```text
YES
```

**Example 3**

**Input:**
```text
5
1 4 2 3 5
```

**Output:**
```text
NO
```

**Explanation:**
The node at index `1` has value `4`, while its left child has value `3`. This violates the Min-Heap property.

**Approach:**

For every non-leaf node at index `i`, check its left child at `2*i+1` and right child at `2*i+2`, if they exist. If any child is smaller than its parent, the array is not a Min-Heap.

**Complexity:**

- Time: `O(n)`
- Space: `O(1)`
