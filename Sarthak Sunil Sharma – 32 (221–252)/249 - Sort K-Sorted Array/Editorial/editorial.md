# Editorial — Sort K-Sorted Array

## Difficulty
Medium

## Prerequisites
- Binary Heaps
- Min Heap / Priority Queue
- Arrays
- Time and Space Complexity

## Problem Statement

A `k`-sorted array has every element no more than `k` positions away from its position in the fully sorted array. Sort it in ascending order.

## Explanation & Approach

In a fully sorted array, the next smallest element must be among the next `k + 1` elements of the current unsorted region. An element cannot be more than `k` places away from its final position.

Use a **min heap**:
1. Insert the first `min(n, k + 1)` elements.
2. Remove the minimum and append it to the output.
3. Insert the next unseen element, if any.
4. Repeat until all input elements have been inserted and removed.

For `k = 0`, the input is already sorted, and the same procedure works with a heap of size one.

## Example / Trace

Input:
```text
arr = [6, 5, 3, 2, 8, 10, 9], k = 3
```

The heap initially contains `6, 5, 3, 2`. Removing minima while inserting the next unseen value eventually produces:
```text
2 3 5 6 8 9 10
```

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        StringBuilder answer = new StringBuilder();

        int initial = Math.min(n, k + 1);
        for (int i = 0; i < initial; i++) {
            minHeap.offer(Integer.parseInt(st.nextToken()));
        }

        for (int i = initial; i < n; i++) {
            answer.append(minHeap.poll()).append(' ');
            minHeap.offer(Integer.parseInt(st.nextToken()));
        }

        while (!minHeap.isEmpty()) {
            answer.append(minHeap.poll());
            if (!minHeap.isEmpty()) answer.append(' ');
        }

        System.out.println(answer);
    }
}
```

## Complexity
- Time: `O(n log(k + 1))` for `k ≥ 1`; `O(n)` when `k = 0`.
- Space: `O(min(n, k + 1))` for the heap, plus the output buffer.

## Edge Cases
- `n = 1`.
- `k = 0`: array is already sorted.
- `k ≥ n`: heap-based procedure sorts the entire array.
- Duplicate values.
- Negative values.
- Already sorted input.

## Why the Approach Works

The next element in sorted order must lie within the next `k + 1` candidates, because every element is at most `k` positions away from its final sorted position. The min heap returns the smallest candidate. Repeating this step emits every element in ascending order.
