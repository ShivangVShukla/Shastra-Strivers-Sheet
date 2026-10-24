# Editorial: Maximal Rectangle


## Difficulty

Hard


## Prerequisites
- Monotonic Stack
- Largest Rectangle in Histogram
- Arrays
- Matrix Traversal


## Problem Statement

You are given a binary matrix containing only `0` and `1`. Find the area of the largest rectangle containing only `1`s.

The rectangle must be formed by consecutive rows and consecutive columns. If the matrix contains no `1`, the answer is `0`.


## Explanation & Approach

### Step 1: Convert Every Row into a Histogram

The main difficulty is that the rectangle can have any height and width, so checking every possible rectangle directly would be too expensive.

Instead, process the matrix row by row. For every column, maintain the number of consecutive `1`s ending at the current row.

For example, if the current rows produce:

```text
0 1 1 0 1
1 1 1 1 1
1 1 1 1 0
```

then after processing the third row the histogram heights are:

```text
2 3 3 2 0
```

Each height tells us how many consecutive `1`s are present vertically in that column, ending at the current row.


### Step 2: The Matrix Problem Becomes Largest Rectangle in Histogram

Once the heights for one row are known, imagine them as histogram bars.

Any rectangle of `1`s whose bottom edge is the current row corresponds to a rectangle inside this histogram.

Therefore, for every matrix row, we only need to solve:

> What is the largest rectangle that can be formed in this histogram?

This is exactly the **Largest Rectangle in Histogram** problem.

A monotonic increasing stack lets us solve it in `O(C)` time for one row, where `C` is the number of columns.


### Step 3: Use a Monotonic Increasing Stack

The stack stores indices of histogram bars whose heights are increasing.

When we encounter a bar whose height is smaller than the bar at the top of the stack, the taller bar can no longer extend to the right. We remove it from the stack and calculate the largest rectangle for that height.

Suppose the popped bar has height `H` and its index is `mid`.

- The current index `i` is the first position on the right with a smaller height.
- The new stack top is the first position on the left with a smaller height.
- Therefore, the rectangle width is:

```text
width = i - leftSmallerIndex - 1
```

and its area is:

```text
area = H × width
```

After processing the entire histogram, the maximum area found is the best rectangle ending at that matrix row.


### Step 4: Process Every Matrix Row

For every row:

1. Update the height of every column.
2. Reset the height to `0` when the current cell is `0`.
3. Increase the height by `1` when the current cell is `1`.
4. Find the largest rectangle in the resulting histogram.
5. Update the global maximum area.

The largest value obtained over all rows is the answer.


## Example

### Input:

```text
4 5
10100
10111
11111
10010
```

### Trace:

Initially:

```text
heights = [0, 0, 0, 0, 0]
```

After row 1 (`10100`):

```text
heights = [1, 0, 1, 0, 0]
maximum histogram area = 1
```

After row 2 (`10111`):

```text
heights = [2, 0, 2, 1, 1]
maximum histogram area = 3
```

After row 3 (`11111`):

```text
heights = [3, 1, 3, 2, 2]
maximum histogram area = 6
```

The rectangle responsible for area `6` is:

```text
1 1 1
1 1 1
```

After row 4 (`10010`):

```text
heights = [4, 0, 0, 3, 0]
maximum histogram area = 4
```

Therefore:

```text
Answer = 6
```


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {
    static int largestRectangle(int[] h) {
        int n = h.length, best = 0;
        Deque<Integer> stack = new ArrayDeque<>();
        for (int i = 0; i <= n; i++) {
            int cur = (i == n) ? 0 : h[i];
            while (!stack.isEmpty() && h[stack.peek()] >= cur) {
                int height = h[stack.pop()];
                int left = stack.isEmpty() ? -1 : stack.peek();
                best = Math.max(best, height * (i - left - 1));
            }
            if (i < n) stack.push(i);
        }
        return best;
    }

    static int maximalRectangle(char[][] matrix) {
        if (matrix.length == 0 || matrix[0].length == 0) return 0;
        int rows = matrix.length, cols = matrix[0].length;
        int[] heights = new int[cols];
        int best = 0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                heights[c] = matrix[r][c] == '1' ? heights[c] + 1 : 0;
            }
            best = Math.max(best, largestRectangle(heights));
        }
        return best;
    }

    public static void main(String[] args) throws Exception {
        FastScanner fs = new FastScanner(System.in);
        int rows = fs.nextInt(), cols = fs.nextInt();
        char[][] matrix = new char[rows][cols];
        for (int r = 0; r < rows; r++) matrix[r] = fs.next().toCharArray();
        System.out.println(maximalRectangle(matrix));
    }

    static class FastScanner {
        private final BufferedReader br;
        private StringTokenizer st;
        FastScanner(InputStream in) { br = new BufferedReader(new InputStreamReader(in)); }
        String next() throws IOException {
            while (st == null || !st.hasMoreTokens()) {
                String line = br.readLine();
                if (line == null) return null;
                st = new StringTokenizer(line);
            }
            return st.nextToken();
        }
        int nextInt() throws IOException { return Integer.parseInt(next()); }
    }
}
```


## Time Complexity

Let `R` be the number of rows and `C` be the number of columns.

For every row, we update all `C` histogram heights in `O(C)` time. We then solve the largest rectangle in that histogram in `O(C)` time because every index is pushed onto and popped from the monotonic stack at most once.

### Total Time Complexity:
`O(R × C)`.

### Space Complexity:
`O(C)` for the histogram heights and monotonic stack.


## Edge Cases

- Matrix containing only `0`s → answer is `0`.
- Matrix containing a single `1` → answer is `1`.
- Matrix containing only `1`s → answer is `R × C`.
- Single row matrix → reduces to Largest Rectangle in Histogram.
- Single column matrix → answer is the longest consecutive sequence of `1`s.
- Alternating `0`s and `1`s → prevents rectangles from extending through zero cells.


## Why the Approach Works

Every rectangle consisting entirely of `1`s has a unique bottom row. When that bottom row is processed, every column inside the rectangle stores at least the rectangle's height in `heights`.

Therefore, that rectangle appears as a valid rectangle in the histogram for that row. The largest-rectangle algorithm considers all such histogram rectangles, so the rectangle is considered when its bottom row is processed.

Since every possible all-`1` rectangle has some bottom row, taking the maximum histogram rectangle over every row gives the global maximum rectangle area.
