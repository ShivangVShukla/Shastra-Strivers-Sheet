# Editorial: Asteroid Collision


## Difficulty

Medium


## Prerequisites
- Stack (LIFO)
- Simulation


## Problem Statement

Asteroids move along a line at the same speed. The sign of an element is its direction (positive = right, negative = left) and its absolute value is its size. When a right-moving and a left-moving asteroid meet, the smaller one explodes (both explode if equal). Return the asteroids left after all collisions, in the original order.


## Explanation & Approach

### Step 1: Who Can Collide?

Two neighbours collide only when the **left one moves right** and the **right one moves left**. Every other combination (right-right, left-left, or left then right) moves apart or in parallel and never meets.

So when we add a new asteroid `cur`, it can only collide if `cur < 0` and the asteroid just before it (the nearest survivor) is moving right.

### Step 2: Keep the Survivors on a Stack

Process the asteroids from left to right and keep a stack of survivors. For each `cur`:

- Let `alive = true`.
- While `alive`, `cur < 0`, the stack is not empty, and the top is positive:
  - if `top < |cur|`: the top explodes, pop it and keep going (`cur` may hit the next one);
  - if `top == |cur|`: both explode, pop the top and set `alive = false`;
  - if `top > |cur|`: `cur` explodes, set `alive = false`.
- If `alive`, push `cur`.

At the end the stack, from bottom to top, is the answer.

### Step 3: Why a Stack Works

Everything in the stack has already settled with respect to everything before it. A new asteroid can only interact with the **nearest** survivor on its left, then the next one, and so on, which is exactly the last-in-first-out order. Each asteroid is pushed once and popped at most once, so the total work is O(n).

### Step 4: Things to Watch For

- **A left-mover can destroy several right-movers in a row.** Use a `while` loop, not a single `if`.
- **Equal sizes:** both explode, so pop the top and do **not** push `cur`.
- **No collision if the top is also negative** (or the stack is empty): negative asteroids just get pushed and later asteroids never reach them from the left.
- **Right-movers are always pushed**, they cannot collide with anything behind them.
- **The result may be empty** (for example `[5, -5]`). Print an empty line in that case.


## Example

### Input:

```text
1
8
5 10 -5 -10 8 -8 -3 12
```

### Trace:

| cur | What happens                                       | Stack (bottom → top) |
|-----|----------------------------------------------------|----------------------|
| 5   | positive, push                                     | 5                    |
| 10  | positive, push                                     | 5 10                 |
| -5  | top 10 > 5, `-5` explodes                          | 5 10                 |
| -10 | top 10 == 10, both explode (pop 10)                | 5                    |
| 8   | positive, push                                     | 5 8                  |
| -8  | top 8 == 8, both explode (pop 8)                   | 5                    |
| -3  | top 5 > 3, `-3` explodes                           | 5                    |
| 12  | positive, push                                     | 5 12                 |

Output: **5 12**


## Java Solution

```java
import java.io.*;
import java.util.*;

public class Solution {

    public static void main(String[] args) throws IOException {
        StreamTokenizer st = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));

        st.nextToken();
        int t = (int) st.nval;

        StringBuilder sb = new StringBuilder();
        while (t-- > 0) {
            st.nextToken();
            int n = (int) st.nval;

            // Stack of surviving asteroids
            int[] stack = new int[n];
            int top = 0;

            for (int i = 0; i < n; i++) {
                st.nextToken();
                int cur = (int) st.nval;
                boolean alive = true;

                // A collision happens only when the top moves right and cur moves left
                while (alive && cur < 0 && top > 0 && stack[top - 1] > 0) {
                    int right = stack[top - 1];
                    if (right < -cur) {
                        top--;              // top explodes, cur keeps moving
                    } else if (right == -cur) {
                        top--;              // both explode
                        alive = false;
                    } else {
                        alive = false;      // cur explodes
                    }
                }

                if (alive) {
                    stack[top++] = cur;
                }
            }

            for (int i = 0; i < top; i++) {
                if (i > 0) {
                    sb.append(' ');
                }
                sb.append(stack[i]);
            }
            sb.append('\n');
        }

        System.out.print(sb);
    }
}
```


## Time Complexity

Let $N$ be the number of asteroids and $T$ the number of test cases.

### Total Time Complexity:
$O(N)$ per test case (each asteroid is pushed once and popped at most once), so $O(T \cdot N)$ overall.

### Space Complexity:
$O(N)$ for the stack.
