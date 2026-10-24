# Removing Given Node in Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a software office in Bengaluru, active incidents are stored in a doubly linked list so engineers can move to previous or next tickets quickly. A reference points to a node guaranteed to belong to the list and not be its head. Remove that exact node by joining its predecessor to its successor; if it was the tail, update the tail too. Ticket values may be identical, so do not search by value. Print the remaining tickets from both ends with all links consistent.
## Input format

The first line contains N, followed by N integers. The final line contains the 1-based position of a node that is not the head.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `2 <= number of nodes in the list <= 100`
- `0 <= ListNode.val <= 100`
- The node is guaranteed to be in the list and not be the head.

## Sample input

```text
4
10 20 30 40
3
```

## Sample output

```text
10 20 40
40 20 10
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
