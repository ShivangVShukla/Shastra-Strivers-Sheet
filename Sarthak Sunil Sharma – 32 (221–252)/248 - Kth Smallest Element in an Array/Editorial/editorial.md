# Editorial — Kth Smallest Element in an Array

## Difficulty
Medium

## Prerequisites
- Binary Heaps
- Priority Queues
- Max Heap
- Arrays
- Time and Space Complexity

## Problem Statement

Given an integer array and an integer `k`, find the `k`th smallest element. Duplicate values count separately.

## Explanation & Approach

A direct sorting solution would take `O(n log n)`. Since the problem is in the Heaps section, we can use a **max heap of size `k`**.

For every element:
1. Insert it into the max heap.
2. If the heap contains more than `k` elements, remove its maximum.
3. Therefore, only the `k` smallest elements seen so far remain in the heap.
4. The largest among those `k` elements is exactly the `k`th smallest element overall.

This gives `O(n log k)` time and `O(k)` extra space.

## Example / Trace

For:

```text
arr = [7, 10, 4, 3, 20, 15], k = 3
```

The three smallest values are eventually `[3, 4, 7]`.

The max heap root is `7`, so the answer is:

```text
7
```

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static class MaxHeap {
        ArrayList<Integer> heap = new ArrayList<>();

        void add(int x) {
            heap.add(x);
            int i = heap.size() - 1;

            while (i > 0) {
                int p = (i - 1) / 2;
                if (heap.get(p) >= heap.get(i)) break;
                swap(p, i);
                i = p;
            }
        }

        int removeMax() {
            int ans = heap.get(0);
            int last = heap.remove(heap.size() - 1);

            if (!heap.isEmpty()) {
                heap.set(0, last);
                heapifyDown(0);
            }
            return ans;
        }

        int peek() {
            return heap.get(0);
        }

        int size() {
            return heap.size();
        }

        void heapifyDown(int i) {
            while (true) {
                int largest = i;
                int left = 2 * i + 1;
                int right = 2 * i + 2;

                if (left < heap.size() && heap.get(left) > heap.get(largest))
                    largest = left;
                if (right < heap.size() && heap.get(right) > heap.get(largest))
                    largest = right;

                if (largest == i) break;
                swap(i, largest);
                i = largest;
            }
        }

        void swap(int i, int j) {
            int temp = heap.get(i);
            heap.set(i, heap.get(j));
            heap.set(j, temp);
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        MaxHeap heap = new MaxHeap();

        for (int i = 0; i < n; i++) {
            heap.add(Integer.parseInt(st.nextToken()));

            if (heap.size() > k) {
                heap.removeMax();
            }
        }

        System.out.println(heap.peek());
    }
}
```

## Complexity

- Time: `O(n log k)`
- Space: `O(k)`

## Edge Cases

- `k = 1`: returns the minimum element.
- `k = n`: returns the maximum element.
- Duplicate values are handled correctly.
- Negative numbers are supported.
- A single-element array is valid.

## Why the Approach Works

At every step, the heap stores exactly the `k` smallest elements encountered so far. Because it is a max heap, its root is the largest among those `k` elements. Once all elements have been processed, that root is the `k`th smallest element of the entire array.
