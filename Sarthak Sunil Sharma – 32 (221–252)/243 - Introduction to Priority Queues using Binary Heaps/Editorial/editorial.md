# Editorial — Introduction to Priority Queues using Binary Heaps

## Difficulty
Learning

## Prerequisites
- Arrays
- Complete Binary Trees
- Binary Heaps
- Basic time-complexity analysis

## Problem Statement

A priority queue does not process elements according to insertion order. Instead, the element with the highest priority is processed first.

For a **min-priority queue**, the smallest value has the highest priority.

A binary min-heap is an efficient way to implement this structure.

## Binary Heap Representation

A binary heap is a complete binary tree stored in an array.

For an element at index `i`:

- Parent = `(i - 1) / 2`
- Left child = `2 * i + 1`
- Right child = `2 * i + 2`

The min-heap property is:

`parent <= children`

Therefore, the minimum element is always at index `0`.

## Operations

### 1. Insert

To insert `x`:

1. Add `x` at the end of the array.
2. Compare it with its parent.
3. If it is smaller than its parent, swap them.
4. Continue upward until the heap property is restored.

This is called **heapify up**.

Time: `O(log n)`.

### 2. Remove Minimum

To remove the minimum:

1. Save the root.
2. Move the last element to index `0`.
3. Remove the last position.
4. Compare the new root with its children.
5. Swap it with the smaller child whenever necessary.

This is called **heapify down**.

Time: `O(log n)`.

### 3. Peek

The minimum value is always at index `0`.

Time: `O(1)`.

### 4. Size

The current number of elements is simply the array size.

Time: `O(1)`.

## Example

Operations:

```text
1 5
1 2
1 8
3
2
3
```

After inserting:

`[5]`

then:

`[2, 5]`

then:

`[2, 5, 8]`

Peek returns `2`.

After removing `2`, the heap becomes:

`[5, 8]`

Peek now returns `5`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static ArrayList<Long> heap = new ArrayList<>();

    static void insert(long value) {
        heap.add(value);
        int i = heap.size() - 1;

        while (i > 0) {
            int parent = (i - 1) / 2;

            if (heap.get(parent) <= heap.get(i)) {
                break;
            }

            swap(parent, i);
            i = parent;
        }
    }

    static long removeMin() {
        long result = heap.get(0);
        int last = heap.size() - 1;

        if (last == 0) {
            heap.remove(last);
            return result;
        }

        heap.set(0, heap.get(last));
        heap.remove(last);

        int i = 0;

        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < heap.size() && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }

            if (right < heap.size() && heap.get(right) < heap.get(smallest)) {
                smallest = right;
            }

            if (smallest == i) {
                break;
            }

            swap(i, smallest);
            i = smallest;
        }

        return result;
    }

    static long peek() {
        return heap.get(0);
    }

    static void swap(int i, int j) {
        long temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine());

        StringBuilder out = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                long value = Long.parseLong(st.nextToken());
                insert(value);
            } else if (type == 2) {
                if (heap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(removeMin()).append('\n');
                }
            } else if (type == 3) {
                if (heap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(peek()).append('\n');
                }
            } else if (type == 4) {
                out.append(heap.size()).append('\n');
            }
        }

        System.out.print(out);
    }
}
```

## Complexity

For a heap containing `n` elements:

- Insert: `O(log n)`
- Remove minimum: `O(log n)`
- Peek: `O(1)`
- Size: `O(1)`
- Space: `O(n)`

## Edge Cases

1. Peek on an empty queue.
2. Remove from an empty queue.
3. Insert duplicate values.
4. Insert negative values.
5. Insert values in decreasing order.
6. Insert values in increasing order.
7. Remove until the queue becomes empty.
8. Repeatedly insert and remove.
9. A queue containing a single element.
10. A large number of operations.

## Why the Approach Works

The heap always maintains the min-heap invariant: every parent is less than or equal to its children. Consequently, the root is always the smallest element. Heapify-up restores this invariant after insertion, while heapify-down restores it after removing the root.
