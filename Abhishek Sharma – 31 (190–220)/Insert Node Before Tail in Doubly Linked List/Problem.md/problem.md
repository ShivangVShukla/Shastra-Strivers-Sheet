# Insert Node Before Tail in Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

For a road survey outside Nagpur, checkpoints are recorded in a doubly linked list and the final checkpoint is the return-to-office marker. Insert a new checkpoint `X` immediately before the existing tail, leaving that return marker last. Connect the new node to both neighbors in both directions. If the list has one node, the new checkpoint becomes the head and the old node remains the tail; if empty, the new node is the only node. Print forward and reverse traversals.
## Input format

The first line contains N, followed by N integers. The final line contains the value X to insert.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `n == number of nodes in the Linked List`
- `1 <= n <= 100`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`

## Sample input

```text
3
1 2 3
9
```

## Sample output

```text
1 2 9 3
3 9 2 1
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
