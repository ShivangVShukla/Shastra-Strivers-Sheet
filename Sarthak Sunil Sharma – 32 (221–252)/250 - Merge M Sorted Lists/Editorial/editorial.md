# Editorial — Merge M Sorted Lists

## Difficulty
Medium

## Prerequisites
- Min Heap / Priority Queue
- Arrays and Lists
- K-way Merge
- Time and Space Complexity

## Problem Statement

Given `m` individually sorted integer lists, combine them into one ascending list. Preserve all duplicates.

## Explanation & Approach

Merging two sorted lists repeatedly is possible, but doing that one list at a time can be less efficient. A min heap lets us always choose the smallest currently available element across all lists.

Each heap entry stores:
- `value`: the current value
- `listIndex`: which input list it belongs to
- `elementIndex`: its position within that list

Steps:
1. Add the first element of every non-empty list to the min heap.
2. Remove the smallest entry and append its value to the answer.
3. If that entry's list has another element, insert the next element from that list.
4. Continue until the heap is empty.

There are `N` total elements and each heap operation costs `O(log m)`, so the total time is `O(N log m)`.

## Example / Trace

Lists:
```text
[1, 4, 5]
[1, 3, 4]
[2, 6]
```

The smallest available values are repeatedly extracted, producing:
```text
1 1 2 3 4 4 5 6
```

(For the sample in the problem statement, the second list contains `3` before `4`, so the full merged output is `1 1 2 3 3 4 4 5 6`.)

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static class Node {
        int value, listIndex, elementIndex;
        Node(int value, int listIndex, int elementIndex) {
            this.value = value;
            this.listIndex = listIndex;
            this.elementIndex = elementIndex;
        }
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int m = Integer.parseInt(br.readLine().trim());

        int[][] lists = new int[m][];
        PriorityQueue<Node> minHeap = new PriorityQueue<>((a, b) -> {
            int cmp = Integer.compare(a.value, b.value);
            if (cmp != 0) return cmp;
            cmp = Integer.compare(a.listIndex, b.listIndex);
            if (cmp != 0) return cmp;
            return Integer.compare(a.elementIndex, b.elementIndex);
        });

        for (int i = 0; i < m; i++) {
            StringTokenizer st = new StringTokenizer(br.readLine());
            int len = Integer.parseInt(st.nextToken());
            lists[i] = new int[len];
            for (int j = 0; j < len; j++) lists[i][j] = Integer.parseInt(st.nextToken());
            if (len > 0) minHeap.offer(new Node(lists[i][0], i, 0));
        }

        StringBuilder answer = new StringBuilder();
        while (!minHeap.isEmpty()) {
            Node cur = minHeap.poll();
            if (answer.length() > 0) answer.append(' ');
            answer.append(cur.value);

            int nextIndex = cur.elementIndex + 1;
            if (nextIndex < lists[cur.listIndex].length) {
                minHeap.offer(new Node(lists[cur.listIndex][nextIndex], cur.listIndex, nextIndex));
            }
        }

        System.out.println(answer);
    }
}
```

## Complexity

- Time: `O(N log m)`, where `N` is the total number of elements.
- Heap space: `O(m)`.
- Input and output storage are additional.

## Edge Cases

- Some or all lists are empty.
- A single non-empty list.
- Duplicate values across different lists.
- Negative integers.
- One element per list.
- Lists of very different lengths.

## Why the Approach Works

At any point, the heap contains the first unmerged element from each non-empty list. Since each list is sorted, no later element in a list can be smaller than its current element. Therefore, the minimum heap entry is the smallest unmerged element overall. Extracting it and adding the next item from its list maintains this invariant until all elements are merged.
