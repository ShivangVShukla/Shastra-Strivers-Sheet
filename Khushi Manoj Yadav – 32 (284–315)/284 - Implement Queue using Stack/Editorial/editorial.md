# 284. Implement Queue using Stack - Editorial

## Approach
A queue follows First-In-First-Out (FIFO) order, while a stack follows Last-In-First-Out (LIFO).
To simulate a FIFO queue using stacks, maintain two stacks:
- `s1`: used for incoming elements (`push`).
- `s2`: used for outgoing elements (`pop` / `peek`).

When `pop` or `peek` is requested:
1. If `s2` is non-empty, the front element is readily available at `s2.top()`.
2. If `s2` is empty, transfer all elements from `s1` into `s2`. This reverses their order, bringing the oldest element to `s2.top()`.
3. If both stacks are empty, return `-1`.

## Complexity
- `push`: O(1)
- `pop` / `peek`: Amortized O(1), worst-case O(N)
- `empty`: O(1)
- Auxiliary Space: O(N)
