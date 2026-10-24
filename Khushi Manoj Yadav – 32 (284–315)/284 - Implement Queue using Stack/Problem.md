### Problem 284: Implement Queue using Stack
**Difficulty:** Easy | **Tags:** Stack, Queue, Design

**Problem Statement**
Implement a First In First Out (FIFO) queue using two stacks.

The implemented queue should support all standard queue operations:
- `push(x)`: Inserts element `x` to the back of the queue.
- `pop()`: Removes the element from the front of the queue and returns it. If the queue is empty, returns `-1`.
- `peek()`: Returns the element at the front of the queue without removing it. If the queue is empty, returns `-1`.
- `empty()`: Returns `1` if the queue is empty, and `0` otherwise.

**Input Format**
- The first line contains an integer `Q`, the number of operations.
- The next `Q` lines each describe an operation:
  - `1 x` : push `x`
  - `2`   : pop
  - `3`   : peek
  - `4`   : empty

**Output Format**
- Print the results of all operations of type `2`, `3`, and `4` separated by spaces on a single line.
- If there are no such operations, print `EMPTY`.

**Constraints**
- `1 <= Q <= 10^5`
- `-10^9 <= x <= 10^9`

**Example**
**Input:**
```text
8
1 5
1 10
3
2
3
4
2
4
```
**Output:**
```text
5 5 10 0 10 1
```
