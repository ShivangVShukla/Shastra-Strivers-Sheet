# Rotten Oranges — Editorial

## Difficulty
Medium

## Prerequisites
- Queue
- Breadth First Search (BFS)
- Matrix traversal
- Graph traversal
- Multi-source BFS

## Problem Statement
We are given an `n x m` grid containing empty cells, fresh oranges, and rotten oranges.

Every minute, a fresh orange that is directly adjacent to a rotten orange in one of the four directions — up, down, left, or right — becomes rotten.

We need to find the minimum number of minutes required to make every fresh orange rotten. If at least one fresh orange can never become rotten, return `-1`.

## Key Observation
There may be several rotten oranges initially. All of them start spreading at the same time.

Therefore, instead of starting BFS separately from every rotten orange, we put **all initially rotten oranges into one queue** with time `0`.

This is called **multi-source BFS**.

Because BFS processes states in increasing time order, the first time a fresh orange is reached is the earliest minute at which it can rot.

## Approach

### Step 1 — Count Fresh Oranges
Traverse the entire grid and count how many cells contain `1`.

### Step 2 — Add All Rotten Oranges
For every cell containing `2`, insert its row, column, and current time `0` into the queue.

### Step 3 — Run BFS
Repeatedly remove one state from the queue.

For each state, inspect the four possible directions:

- Up
- Down
- Left
- Right

If a neighbouring cell is inside the grid and contains a fresh orange:

1. Change it to rotten.
2. Decrease the fresh-orange count.
3. Add it to the queue with time `currentTime + 1`.
4. Update the maximum time.

Marking the orange rotten immediately is important because it prevents the same orange from being inserted into the queue multiple times.

### Step 4 — Decide the Answer
After BFS:

- If `fresh == 0`, return the maximum time.
- Otherwise, some fresh orange was unreachable, so return `-1`.

## Example Trace

For:

```text
2 1 1
1 1 0
0 1 1
```

Initially, the queue contains the rotten orange at `(0,0)`.

Minute 1:
```text
2 2 1
2 1 0
0 1 1
```

Minute 2:
```text
2 2 2
2 2 0
0 1 1
```

Minute 3:
```text
2 2 2
2 2 0
0 2 1
```

Minute 4:
```text
2 2 2
2 2 0
0 2 2
```

All fresh oranges are now rotten, so the answer is `4`.

## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    static class Cell {
        int row, col, time;

        Cell(int row, int col, int time) {
            this.row = row;
            this.col = col;
            this.time = time;
        }
    }

    public static int orangesRotting(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;

        Queue<Cell> queue = new ArrayDeque<>();
        int fresh = 0;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (grid[i][j] == 2) {
                    queue.offer(new Cell(i, j, 0));
                } else if (grid[i][j] == 1) {
                    fresh++;
                }
            }
        }

        int maxTime = 0;
        int[] dr = {-1, 1, 0, 0};
        int[] dc = {0, 0, -1, 1};

        while (!queue.isEmpty()) {
            Cell current = queue.poll();

            maxTime = Math.max(maxTime, current.time);

            for (int d = 0; d < 4; d++) {
                int nr = current.row + dr[d];
                int nc = current.col + dc[d];

                if (nr >= 0 && nr < n &&
                    nc >= 0 && nc < m &&
                    grid[nr][nc] == 1) {

                    grid[nr][nc] = 2;
                    fresh--;

                    queue.offer(new Cell(nr, nc, current.time + 1));
                }
            }
        }

        return fresh == 0 ? maxTime : -1;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        StringTokenizer st = new StringTokenizer(br.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        int[][] grid = new int[n][m];

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < m; j++) {
                grid[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        System.out.println(orangesRotting(grid));
    }
}
```

## Complexity Analysis

Let the grid contain `n * m` cells.

### Time Complexity
Every cell is processed at most once, and each cell checks four neighbours.

Therefore:

`O(n * m)`

### Space Complexity
In the worst case, the queue can contain `O(n * m)` cells.

Therefore:

`O(n * m)`

## Important Edge Cases

1. **No fresh oranges**

   ```text
   2 2
   0 2
   ```

   Answer: `0`

2. **Fresh oranges but no rotten orange**

   ```text
   1 1
   1 1
   ```

   Answer: `-1`

3. **Unreachable fresh orange**

   Any fresh orange separated from every rotten orange by empty cells remains fresh.

   Answer: `-1`

4. **Multiple initial rotten oranges**

   All of them must be inserted into the queue before BFS starts. This is what makes the solution multi-source BFS.

## Why This Approach Is Correct

Each rotting operation takes exactly one minute, so the problem can be viewed as finding the shortest distance from any initially rotten orange to every fresh orange.

Multi-source BFS starts simultaneously from all rotten oranges. BFS guarantees that cells are reached in nondecreasing distance from the nearest source.

Thus, the time assigned to each fresh orange is the earliest possible minute when it can become rotten. The largest such time is exactly the minimum time required to rot all reachable fresh oranges.

If any fresh orange remains after BFS, no rotten orange can reach it, so the required answer is `-1`.
