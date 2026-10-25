### Problem 286: Implement Queue using Linked List
**Difficulty:** Easy | **Tags:** Linked List, Queue, Design

**Problem Statement**
Implement a First In First Out (FIFO) queue using a singly linked list with head and tail pointers.

Operations:
- `push(x)`: Inserts element `x` at the end (rear) of the queue.
- `pop()`: Removes and returns the front element. If empty, returns `-1`.
- `front()`: Returns the front element without removing it. If empty, returns `-1`.
- `size()`: Returns current number of elements.
- `empty()`: Returns `1` if empty, and `0` otherwise.

**Input Format**
- The first line contains an integer `Q`, the number of operations.
- The next `Q` lines each describe an operation:
  - `1 x` : push `x`
  - `2`   : pop
  - `3`   : front
  - `4`   : size
  - `5`   : empty

**Output Format**
- Print the results of operations `2`, `3`, `4`, and `5` separated by spaces on a single line.
- If no operations produce output, print `EMPTY`.

**Constraints**
- `1 <= Q <= 10^5`
- `-10^9 <= x <= 10^9`
