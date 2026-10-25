# Delete Kth Element of Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a Bengaluru service centre, requests are kept in a doubly linked list so staff can scan from either end. The supervisor cancels the request at position `K`, with positions numbered from 1 at the head. Remove that node and connect its predecessor and successor directly; if it is an endpoint, update the head or tail instead. Request numbers may repeat, so position alone identifies the node. If `K` is outside the current list, leave the list unchanged.
## Input format

The first line contains N, followed by N integers. The final line contains position K.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `n == number of nodes in the linked list`
- `1 <= n <= 100`
- `0 <= ListNode.val <= 100`
- `1 <= k <= n`

## Sample input

```text
5
1 2 3 4 5
2
```

## Sample output

```text
1 3 4 5
5 4 3 1
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
