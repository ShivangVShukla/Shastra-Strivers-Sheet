# Editorial — Min Heap & Max Heap Implementation

## Difficulty
Learning

## Prerequisites
- Arrays
- Complete Binary Trees
- Binary Heaps
- Heapify Up
- Heapify Down

## Problem Statement

Implement two different heap structures:

1. A **Min Heap**, where every parent is smaller than or equal to its children.
2. A **Max Heap**, where every parent is greater than or equal to its children.

Both structures are implemented manually using arrays.

## Array Representation

For an element at index `i`:

- Parent = `(i - 1) / 2`
- Left child = `2 * i + 1`
- Right child = `2 * i + 2`

No explicit tree nodes are required.

## Min Heap

The Min Heap invariant is:

`parent <= child`

Therefore:

`heap[0]` is always the minimum element.

### Min Heap Insert

1. Add the value at the end.
2. Compare it with its parent.
3. Swap while the new value is smaller.

This is heapify-up.

### Min Heap Remove

1. Save the root.
2. Move the last element to index `0`.
3. Remove the last position.
4. Swap with the smaller child until the heap property is restored.

This is heapify-down.

## Max Heap

The Max Heap performs the same operations, but reverses the comparison.

The invariant is:

`parent >= child`

Therefore:

`heap[0]` is always the maximum element.

## Important Observation

Both heaps contain the same inserted values, so their sizes always remain equal.

For example, after inserting:

`5, 2, 8`

Min Heap may look like:

`[2, 5, 8]`

while Max Heap may look like:

`[8, 2, 5]`

Both representations are valid because heap order only constrains parent-child relationships.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static ArrayList<Long> minHeap = new ArrayList<>();
    static ArrayList<Long> maxHeap = new ArrayList<>();

    static void insertMin(long value) {
        minHeap.add(value);
        int i = minHeap.size() - 1;

        while (i > 0) {
            int parent = (i - 1) / 2;

            if (minHeap.get(parent) <= minHeap.get(i)) {
                break;
            }

            swap(minHeap, parent, i);
            i = parent;
        }
    }

    static void insertMax(long value) {
        maxHeap.add(value);
        int i = maxHeap.size() - 1;

        while (i > 0) {
            int parent = (i - 1) / 2;

            if (maxHeap.get(parent) >= maxHeap.get(i)) {
                break;
            }

            swap(maxHeap, parent, i);
            i = parent;
        }
    }

    static long removeMin() {
        long result = minHeap.get(0);
        int last = minHeap.size() - 1;

        if (last == 0) {
            minHeap.remove(last);
            return result;
        }

        minHeap.set(0, minHeap.get(last));
        minHeap.remove(last);
        heapifyDownMin(0);

        return result;
    }

    static long removeMax() {
        long result = maxHeap.get(0);
        int last = maxHeap.size() - 1;

        if (last == 0) {
            maxHeap.remove(last);
            return result;
        }

        maxHeap.set(0, maxHeap.get(last));
        maxHeap.remove(last);
        heapifyDownMax(0);

        return result;
    }

    static void heapifyDownMin(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < minHeap.size() &&
                minHeap.get(left) < minHeap.get(smallest)) {
                smallest = left;
            }

            if (right < minHeap.size() &&
                minHeap.get(right) < minHeap.get(smallest)) {
                smallest = right;
            }

            if (smallest == i) {
                break;
            }

            swap(minHeap, i, smallest);
            i = smallest;
        }
    }

    static void heapifyDownMax(int i) {
        while (true) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < maxHeap.size() &&
                maxHeap.get(left) > maxHeap.get(largest)) {
                largest = left;
            }

            if (right < maxHeap.size() &&
                maxHeap.get(right) > maxHeap.get(largest)) {
                largest = right;
            }

            if (largest == i) {
                break;
            }

            swap(maxHeap, i, largest);
            i = largest;
        }
    }

    static void swap(ArrayList<Long> heap, int i, int j) {
        long temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        int q = Integer.parseInt(br.readLine());
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < q; i++) {
            StringTokenizer st =
                new StringTokenizer(br.readLine());

            int type = Integer.parseInt(st.nextToken());

            if (type == 1) {
                long value = Long.parseLong(st.nextToken());
                insertMin(value);
                insertMax(value);
            } else if (type == 2) {
                if (minHeap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(removeMin()).append('\n');
                }
            } else if (type == 3) {
                if (maxHeap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(removeMax()).append('\n');
                }
            } else if (type == 4) {
                if (minHeap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(minHeap.get(0)).append('\n');
                }
            } else if (type == 5) {
                if (maxHeap.isEmpty()) {
                    out.append("EMPTY\n");
                } else {
                    out.append(maxHeap.get(0)).append('\n');
                }
            } else if (type == 6) {
                out.append(minHeap.size())
                   .append(' ')
                   .append(maxHeap.size())
                   .append('\n');
            }
        }

        System.out.print(out);
    }
}
```

## Complexity

For a heap containing `n` elements:

- Insert into Min Heap: `O(log n)`
- Insert into Max Heap: `O(log n)`
- Remove Min: `O(log n)`
- Remove Max: `O(log n)`
- Peek: `O(1)`
- Size: `O(1)`
- Space: `O(n)`

## Edge Cases

1. Peek when both heaps are empty.
2. Remove from an empty heap.
3. Duplicate values.
4. Negative values.
5. Increasing insertion order.
6. Decreasing insertion order.
7. Removing all elements.
8. Alternating insert and remove.
9. A single element.
10. Large number of operations.

## Why the Approach Works

The Min Heap and Max Heap independently maintain their respective heap invariants. Every insertion uses heapify-up and every root removal uses heapify-down, so the root always represents the required extreme value.
