# Deletion in Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

A warehouse in Surat tracks parcels in a doubly linked list and receives a batch of cancellation instructions before dispatch. Apply instructions strictly in input order: a command may remove the current head, current tail, node at a 1-based position, or first node matching a value. Each command sees the list left by the previous command, so positions are recalculated after every successful deletion. An invalid position or absent value has no effect; a value command removes just one match. Print the final sequence forward and backward with consistent links, even if it became empty.
## Input format

The first line contains N, the second line contains N integers, and the next line contains Q. Each command is `H`, `T`, `K k`, or `X x`.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `0 <= N <= 1000`
- `0 <= Q <= 2000`
- Node values and command values satisfy `0 <= value <= 100`.
- Positions are 1-based. Invalid positions and missing target values cause no change.

## Sample input

```text
5
1 2 3 4 5
4
H
T
K 2
X 3
```

## Sample output

```text
2 4
4 2
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
