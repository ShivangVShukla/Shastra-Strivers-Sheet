# Delete Head of Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

A city bus depot stores planned stops in a doubly linked list, with the first stop at the head. A route update cancels precisely that first stop, so remove the head node and leave every later stop in its original order. The new first node must not point backward to the removed node: set its `prev` link to null. If the route had one stop, both ends become empty; otherwise the tail remains the same. Return the head of the modified route.
## Input format

The first line contains N, followed by N integers.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `n == number of nodes in the linked list`
- `1 <= n <= 100`
- `0 <= ListNode.data <= 100`

## Sample input

```text
3
1 2 3
```

## Sample output

```text
2 3
3 2
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
