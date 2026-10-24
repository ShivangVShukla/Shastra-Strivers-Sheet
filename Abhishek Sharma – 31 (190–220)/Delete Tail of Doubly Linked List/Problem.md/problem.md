# Delete Tail of Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a parcel hub in Pune, dispatch slips are arranged in a doubly linked list and the final slip is withdrawn after a cancelled pickup. Remove exactly the tail node; do not search for another slip with the same value. The node before the old tail becomes the new tail and must have `next = null`. If there was only one slip, both head and tail become null. Print the surviving slips from front to back and then back to front, following the appropriate links each time.
## Input format

The first line contains N, followed by N integers.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `n == number of nodes in the linked list`
- `1 <= n <= 100`
- `0 <= ListNode.val <= 100`

## Sample input

```text
4
1 2 3 4
```

## Sample output

```text
1 2 3
3 2 1
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
