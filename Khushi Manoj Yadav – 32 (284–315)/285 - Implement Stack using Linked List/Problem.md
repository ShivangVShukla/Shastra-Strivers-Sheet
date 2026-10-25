### Problem 285: Implement Stack using Linked List
**Difficulty:** Easy | **Tags:** Linked List, Stack, Design

**Problem Statement**
Implement a Last In First Out (LIFO) stack using a singly linked list.

The operations to implement:
- `push(x)`: Inserts element `x` onto the top of the stack.
- `pop()`: Removes and returns the top element. If empty, returns `-1`.
- `top()`: Returns the top element without removing it. If empty, returns `-1`.
- `size()`: Returns current number of elements in the stack.
- `empty()`: Returns `1` if empty, and `0` otherwise.

**Input Format**
- The first line contains an integer `Q`, the number of operations.
- The next `Q` lines each describe an operation:
  - `1 x` : push `x`
  - `2`   : pop
  - `3`   : top
  - `4`   : size
  - `5`   : empty

**Output Format**
- Print the results of operations `2`, `3`, `4`, and `5` separated by spaces on a single line.
- If no operations produce output, print `EMPTY`.

**Constraints**
- `1 <= Q <= 10^5`
- `-10^9 <= x <= 10^9`
