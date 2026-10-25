### Problem 301: Implement Min Stack
**Difficulty:** Medium | **Tags:** Stack, Design

**Problem Statement**
Design a stack that supports `push`, `pop`, `top`, and retrieving the minimum element in constant time `O(1)`.

Operations:
- `1 x` : push `x` onto the stack.
- `2`   : pop the top element and return it. If the stack is empty, return `-1`.
- `3`   : return the top element without removing it. If empty, return `-1`.
- `4`   : retrieve the minimum element in the stack. If empty, return `-1`.

**Input Format**
- The first line contains an integer `Q`, the number of operations.
- The next `Q` lines each describe an operation:
  - `1 x`
  - `2`
  - `3`
  - `4`

**Output Format**
- Print the results of operations `2`, `3`, and `4` separated by spaces on a single line.
- If no operations produce output, print `EMPTY`.

**Constraints**
- `1 <= Q <= 10^5`
- `-10^9 <= x <= 10^9`

**Example**
**Input:**
```text
7
1 -2
1 0
1 -3
4
2
3
4
```
**Output:**
```text
-3 -3 0 -2
```
