# Reverse a Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a monsoon control room in Mumbai, a coastal inspection route must be displayed in the order the team will return, rather than the order in which it departed. Reverse the doubly linked list in place: exchange `next` and `prev` for every node, then make the old tail the new head and the old head the new tail. Do not create a second list or change stored values. Print the new route forward and backward; the traversals must be reverses of one another.
## Input format

The first line contains N, followed by N integers.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `1 <= number of nodes <= 10^6`
- `0 <= node.val <= 10^4`

## Sample input

```text
4
1 2 3 4
```

## Sample output

```text
4 3 2 1
1 2 3 4
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
