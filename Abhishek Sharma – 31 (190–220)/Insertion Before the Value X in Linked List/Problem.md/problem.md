# Insertion Before the Value X in Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

Railway maintenance staff in Kolkata keep pending track sections in a singly linked list. A new section with value `val` must be inserted immediately before the first section whose code is `X`, searching from the head. If the same code occurs later, leave those later occurrences in place; the first match alone determines the insertion point. If `X` is absent, add no node. If the match is at the head, the inserted node becomes the new head. Print the resulting queue in its original relative order.
## Input format

The first line contains N, followed by N integers. The final line contains target X and new value Y.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `n == number of nodes in the Linked List`
- `1 <= n <= 1000`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`
- `0 <= val <= 100`
- The number of nodes with value `X` is `0` or `1`.

## Sample input

```text
4
10 20 30 40
30 25
```

## Sample output

```text
10 20 25 30 40
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
