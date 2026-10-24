# Insert Before Given Node in Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

During exam seating at a college in Bhopal, roll numbers are arranged in a doubly linked list. A reference identifies one existing student who is guaranteed to be present and is not at the head. Insert a new roll number `X` immediately before that particular node. The new node points forward to the target and backward to its former predecessor; repair both neighbors so forward and backward walks still describe the same order. Do not locate the target by value: duplicate roll numbers do not replace the supplied node reference.
## Input format

The first line contains N, followed by N integers. The final line contains the target-node position K and new value X.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `n == number of nodes in the Linked List`
- `2 <= n <= 100`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`
- The given node is guaranteed to be in the list and not be its head.

## Sample input

```text
4
1 2 3 4
3 9
```

## Sample output

```text
1 2 9 3 4
4 3 9 2 1
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
