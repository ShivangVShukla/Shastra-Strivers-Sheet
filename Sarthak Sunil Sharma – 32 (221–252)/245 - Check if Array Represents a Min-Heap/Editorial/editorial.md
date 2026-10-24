# Editorial — Check if Array Represents a Min-Heap

## Difficulty
Learning

## Prerequisites
- Arrays
- Complete Binary Trees
- Binary Heap Representation
- Index arithmetic

## Problem Statement

A binary Min-Heap has two important properties:

1. It is a complete binary tree.
2. Every parent is less than or equal to its children.

The problem states that the given array already represents a complete binary tree. Therefore, we only need to check the second property.

## Array Representation

For a node at index `i`:

- Left child = `2 * i + 1`
- Right child = `2 * i + 2`

A node is a leaf when it has no children.

We only need to check indices:

`0` through `(n / 2) - 1`

because all indices from `n / 2` onward are leaves.

## Approach

For every non-leaf node:

1. Calculate its left child index.
2. Calculate its right child index.
3. If the left child exists and is smaller than the parent, return `NO`.
4. If the right child exists and is smaller than the parent, return `NO`.
5. If every comparison passes, return `YES`.

## Example

Consider:

`[1, 4, 2, 3, 5]`

At index `0`:

- `1 <= 4` ✓
- `1 <= 2` ✓

At index `1`:

- `4 <= 3` ✗

Therefore the array is not a Min-Heap.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br =
            new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine());

        StringTokenizer st =
            new StringTokenizer(br.readLine());

        long[] arr = new long[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Long.parseLong(st.nextToken());
        }

        for (int i = 0; i < n / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && arr[i] > arr[left]) {
                System.out.println("NO");
                return;
            }

            if (right < n && arr[i] > arr[right]) {
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");
    }
}
```

## Complexity

- Time: `O(n)`
- Space: `O(n)` for storing the input array.
- Auxiliary checking space: `O(1)`.

## Edge Cases

1. `n = 1` → always a valid Min-Heap.
2. Two elements → only the root-child relationship matters.
3. Duplicate values → valid because equality is allowed.
4. Negative values.
5. Strictly increasing array → valid Min-Heap.
6. A violation at the root.
7. A violation deep in the tree.
8. Very large positive and negative values.

## Why the Approach Works

Every parent-child relationship in a Min-Heap must satisfy `parent <= child`. Checking all non-leaf nodes checks every possible parent-child edge exactly once. If no violation exists, the complete tree satisfies the Min-Heap property.
