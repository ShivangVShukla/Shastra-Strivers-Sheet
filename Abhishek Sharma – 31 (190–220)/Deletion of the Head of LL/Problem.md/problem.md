# Deletion of the Head of LL

**Striver A2Z Linked List practice — Core**

## Problem statement

Before a local train leaves Churchgate, its route board stores station codes in a singly linked list, beginning with the station currently being served. A late platform change removes exactly the first station entry. Return the new head and retain every later entry in precisely its old order; do not inspect values to choose a node. If the board contains one station, it becomes empty, and if it has no station, nothing changes. Print the resulting route using the required empty-list representation when necessary.
## Input format

The first line contains N, followed by N integers.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `1 <= number of nodes in the Linked List <= 1000`
- `0 <= ListNode.data <= 100`

## Sample input

```text
4
8 4 6 2
```

## Sample output

```text
4 6 2
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
