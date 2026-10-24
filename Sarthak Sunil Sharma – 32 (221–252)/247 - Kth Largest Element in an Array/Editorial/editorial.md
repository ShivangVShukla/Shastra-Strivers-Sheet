# Editorial — Kth Largest Element in an Array

## Difficulty
Medium

## Prerequisites
- Arrays
- Binary Heaps
- Min Heap / Priority Queue
- Time and space complexity

## Problem Statement

Given an integer array and an integer `k`, find the `k`th largest element. Duplicate values count as separate elements.

For example, for `[3,2,1,5,6,4]` and `k = 2`, the descending order is `[6,5,4,3,2,1]`, so the answer is `5`.

## Explanation & Approach

We only need to keep track of the largest `k` values. A min heap is ideal because its root always stores the smallest value among those `k` candidates.

For every value:

1. Insert the value into the min heap.
2. If the heap contains more than `k` values, remove the minimum.
3. Continue until all elements are processed.

Whenever the heap grows beyond `k`, the smallest candidate can no longer be among the `k` largest elements, so it is discarded.

At the end, exactly `k` largest elements remain in the heap. The smallest among them is precisely the `k`th largest element.

## Example / Trace

Input:

```text
6 2
3 2 1 5 6 4
```

Keep a min heap of size at most `2`:

- Add `3` → `[3]`
- Add `2` → `[2,3]`
- Add `1` → `[1,3,2]` → remove `1` → `[2,3]`
- Add `5` → `[2,3,5]` → remove `2` → `[3,5]`
- Add `6` → `[3,5,6]` → remove `3` → `[5,6]`
- Add `4` → `[4,6,5]` → remove `4` → `[5,6]`

The heap root is `5`, so the answer is `5`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static class MinHeap {
        private final ArrayList<Long> heap = new ArrayList<>();

        void add(long value) {
            heap.add(value);
            int i = heap.size() - 1;
            while (i > 0) {
                int p = (i - 1) / 2;
                if (heap.get(p) <= heap.get(i)) break;
                long temp = heap.get(p);
                heap.set(p, heap.get(i));
                heap.set(i, temp);
                i = p;
            }
        }

        long removeMin() {
            long result = heap.get(0);
            long last = heap.remove(heap.size() - 1);
            if (!heap.isEmpty()) {
                heap.set(0, last);
                int i = 0;
                while (true) {
                    int left = 2 * i + 1;
                    int right = 2 * i + 2;
                    int smallest = i;

                    if (left < heap.size() && heap.get(left) < heap.get(smallest))
                        smallest = left;
                    if (right < heap.size() && heap.get(right) < heap.get(smallest))
                        smallest = right;
                    if (smallest == i) break;

                    long temp = heap.get(i);
                    heap.set(i, heap.get(smallest));
                    heap.set(smallest, temp);
                    i = smallest;
                }
            }
            return result;
        }

        long peek() {
            return heap.get(0);
        }

        int size() {
            return heap.size();
        }
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int n = fs.nextInt();
        int k = fs.nextInt();

        MinHeap heap = new MinHeap();
        for (int i = 0; i < n; i++) {
            heap.add(fs.nextLong());
            if (heap.size() > k) heap.removeMin();
        }

        System.out.println(heap.peek());
    }

    static class FastScanner {
        private final InputStream in;
        private final byte[] buffer = new byte[1 << 16];
        private int ptr = 0, len = 0;

        FastScanner(InputStream in) { this.in = in; }

        private int read() throws IOException {
            if (ptr >= len) {
                len = in.read(buffer);
                ptr = 0;
                if (len <= 0) return -1;
            }
            return buffer[ptr++];
        }

        long nextLong() throws IOException {
            int c;
            do c = read(); while (c <= ' ');
            boolean neg = false;
            if (c == '-') { neg = true; c = read(); }
            long value = 0;
            while (c > ' ') {
                value = value * 10 + (c - '0');
                c = read();
            }
            return neg ? -value : value;
        }

        int nextInt() throws IOException { return (int) nextLong(); }
    }
}
```

## Complexity

For each of the `n` elements, heap insertion takes `O(log k)`. When the heap exceeds size `k`, removal also takes `O(log k)`.

- Time: `O(n log k)`
- Space: `O(k)`

## Edge Cases

- `k = 1`: return the maximum element.
- `k = n`: return the minimum element.
- `n = 1`: the only element is the answer.
- Duplicate values: duplicates are counted separately.
- Negative values: handled normally.
- Very large positive or negative values: `long` is used internally to avoid arithmetic issues.

## Why the Approach Works

After processing any prefix of the array, the heap contains the largest `k` elements from that prefix. If more than `k` elements are present, removing the minimum removes the one element that cannot belong to the top `k`.

Therefore, after all `n` elements are processed, the heap contains exactly the `k` largest elements of the complete array. Its minimum is consequently the `k`th largest element.
