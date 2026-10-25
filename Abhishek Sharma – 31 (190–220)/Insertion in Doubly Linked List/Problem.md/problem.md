# Insertion in Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

An office in Kochi maintains its meeting agenda as a doubly linked list and receives several amendments before the meeting begins. Apply commands in order: add at the head, append at the tail, insert before a 1-based position, or insert before the first item matching a target value. A position is interpreted against the agenda as it exists at that moment; an invalid position or missing target skips only that command. Equal values are allowed, and value-based insertion uses the first match from the head. Print the agenda forward and backward with consistent links.
## Input format

The first line contains N, the second line contains N integers, and the next line contains Q. Each command is `H x`, `T x`, `K k x`, or `B target x`.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `0 <= N <= 1000`
- `0 <= Q <= 2000`
- Node values and command values satisfy `0 <= value <= 100`.
- Positions are 1-based. Invalid positions and missing target values cause no change.

## Sample input

```text
4
10 20 30 40
4
H 5
T 50
K 3 15
B 30 25
```

## Sample output

```text
5 10 15 20 25 30 40 50
50 40 30 25 20 15 10 5
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
