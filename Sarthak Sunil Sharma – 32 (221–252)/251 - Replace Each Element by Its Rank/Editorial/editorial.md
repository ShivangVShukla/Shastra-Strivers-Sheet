# Editorial — Replace Each Element by Its Rank

## Difficulty
Medium

## Prerequisites
- Sorting
- Hash Maps
- Arrays
- Handling Duplicate Values

## Problem Statement

Replace every array element with its rank. Rank `1` belongs to the smallest distinct value. Equal values receive equal ranks, and output order must match the original array.

## Explanation & Approach

We need ranks based on sorted order, but must print the results in the original order.

1. Copy the input array and sort the copy.
2. Walk through the sorted values.
3. When a value is encountered for the first time, assign the next rank and store it in a hash map.
4. Look up every original value in the map and print its rank.

For example, sorted distinct values `[2, 6, 15, 20, 26, 98]` receive ranks `1` through `6`.

## Example / Trace

Input:
```text
6
20 15 26 2 98 6
```

Mapping:
- `2 → 1`
- `6 → 2`
- `15 → 3`
- `20 → 4`
- `26 → 5`
- `98 → 6`

The original order becomes:
```text
4 3 5 1 6 2
```

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());

        StringTokenizer st = new StringTokenizer(br.readLine());
        int[] arr = new int[n];
        int[] sorted = new int[n];

        for (int i = 0; i < n; i++) {
            arr[i] = Integer.parseInt(st.nextToken());
            sorted[i] = arr[i];
        }

        Arrays.sort(sorted);
        HashMap<Integer, Integer> rank = new HashMap<>();
        int nextRank = 1;

        for (int value : sorted) {
            if (!rank.containsKey(value)) {
                rank.put(value, nextRank++);
            }
        }

        StringBuilder answer = new StringBuilder();
        for (int i = 0; i < n; i++) {
            if (i > 0) answer.append(' ');
            answer.append(rank.get(arr[i]));
        }
        System.out.println(answer);
    }
}
```

## Complexity

- Time: `O(n log n)` due to sorting.
- Space: `O(n)` for the sorted copy and rank map.

## Edge Cases

- All values are equal: every element gets rank `1`.
- Negative values.
- Repeated values in different positions.
- Already sorted or reverse-sorted arrays.
- Minimum and maximum allowed integer values.

## Why the Approach Works

Sorting puts distinct values in increasing order. Assigning a rank only when a new distinct value appears gives exactly the required rank. The hash map then translates each original element without changing its position, so duplicates get the same rank and the output preserves the original order.
