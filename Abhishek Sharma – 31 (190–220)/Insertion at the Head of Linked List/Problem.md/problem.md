# Insertion at the Head of Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a government polytechnic in Lucknow, lab requests wait in a singly linked list with the next request to handle at the head. A priority request with value `X` arrives and must be placed before every request already waiting. Link the new node to the former head and return the new head; do not overwrite or lose the old chain. If no request is waiting, the new request becomes the sole node. Print the queue with its original order preserved behind the new first entry.
## Input format

The first line contains N, followed by N integers. The final line contains the value X to insert.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `0 <= number of nodes in the Linked List <= 1000`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`

## Sample input

```text
4
5 10 15 20
2
```

## Sample output

```text
2 5 10 15 20
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
