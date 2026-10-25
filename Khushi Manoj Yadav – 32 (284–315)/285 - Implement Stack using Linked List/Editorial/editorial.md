# 285. Implement Stack using Linked List - Editorial

## Approach
A stack can be implemented with dynamic sizing using a singly linked list.
The `top` of the stack corresponds to the `head` of the linked list:
- `push(x)`: Create a new node with value `x` and pointer to `head`. Set `head = new_node`. Increment size.
- `pop()`: If `head == nullptr`, return `-1`. Otherwise, save `head->val`, advance `head = head->next`, free old node, decrement size, and return the saved value.
- `top()`: If `head == nullptr`, return `-1`. Otherwise, return `head->val`.
- `size()`: Return the maintained count.
- `empty()`: Return `1` if `head == nullptr`, else `0`.

## Complexity
- All operations run in O(1) time.
- Auxiliary Space: O(N) for nodes.
