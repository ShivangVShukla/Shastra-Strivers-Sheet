# Editorial: Largest Rectangle in Histogram

## Difficulty

Hard

## Prerequisites
- Arrays
- Stack
- Monotonic Stack
- Nearest Smaller Element

## Problem Statement

Given histogram bars of width `1`, find the maximum area of a rectangle formed by a contiguous sequence of bars.

## Explanation & Approach

### Step 1: Treat Each Bar as the Minimum Height

For a bar at index `i`, imagine that this bar is the limiting height of a rectangle. We need to find how far this height can extend to the left and right before meeting a bar shorter than it.

If the nearest smaller bar on the left is at `L` and the nearest smaller bar on the right is at `R`, then the usable width is `R - L - 1` and the area is:

`heights[i] * (R - L - 1)`.

### Step 2: Use an Increasing Monotonic Stack

Maintain a stack of indices whose heights are in non-decreasing order. When the current height is smaller than the height at the stack top, the current index becomes the right boundary for the popped bar.

After popping, the new stack top is the nearest smaller element on the left. This gives the exact width for the popped bar.

### Step 3: Flush the Stack

After processing all bars, some bars may still be in the stack because no smaller bar appeared on their right. Process them using `N` as the right boundary.

### Step 4: Complexity

Each index is pushed once and popped at most once.

- Time: `O(N)`
- Auxiliary space: `O(N)`

### Step 5: Edge Cases

- A single bar.
- All bars have the same height.
- Heights are strictly increasing.
- Heights are strictly decreasing.
- Zero-height bars split the histogram.
- A large histogram near the maximum constraints.

## Example

### Input:
```text
6
2 1 5 6 2 3
```

### Output:
```text
10
```

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    public static long largestRectangleArea(int[] heights) {
        int n = heights.length;
        Deque<Integer> stack = new ArrayDeque<>();
        long best = 0;

        for (int i = 0; i <= n; i++) {
            int current = (i == n) ? 0 : heights[i];

            while (!stack.isEmpty() && heights[stack.peek()] >= current) {
                int height = heights[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                long width = i - left - 1L;
                best = Math.max(best, height * width);
            }

            stack.push(i);
        }

        return best;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int n = Integer.parseInt(br.readLine().trim());
        int[] heights = new int[n];
        int index = 0;
        while (index < n) {
            String line = br.readLine();
            if (line == null) break;
            StringTokenizer st = new StringTokenizer(line);
            while (st.hasMoreTokens() && index < n) {
                heights[index++] = Integer.parseInt(st.nextToken());
            }
        }
        System.out.println(largestRectangleArea(heights));
    }
}
```
