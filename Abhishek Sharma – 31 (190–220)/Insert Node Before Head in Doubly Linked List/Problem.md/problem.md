# Insert Node Before Head in Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a residents' association meeting in Noida, agenda items are kept in a doubly linked list for review from either end. A newly submitted item `X` must be placed immediately before the current first item. Make the new node the head, set its `prev` link to null, and make the old head point back to it. If the list is empty, the new item is both head and tail. Preserve all existing items' relative order and print both traversals after insertion.
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
0
```

## Sample output

```text
0 1 2 3
3 2 1 0
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
