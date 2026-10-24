# 286. Implement Queue using Linked List - Editorial

## Approach
Maintain two pointers:
- `head`: points to the front element (where deletions occur).
- `tail`: points to the rear element (where insertions occur).

- `push(x)`: Create node. If `tail == nullptr`, `head = tail = node`. Otherwise `tail->next = node; tail = node;`. Increment size.
- `pop()`: If `!head`, return `-1`. Save `val = head->val`. Advance `head = head->next`. If `head == nullptr`, set `tail = nullptr`. Decrement size. Return saved value.
- `front()`: If `!head`, return `-1`. Return `head->val`.
- `size()`: Return size.
- `empty()`: Return `head == nullptr ? 1 : 0`.

## Complexity
- Time Complexity: O(1) for all operations.
- Space Complexity: O(N).
