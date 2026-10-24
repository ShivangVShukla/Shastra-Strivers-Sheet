# Deletion in Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a college fest in Jaipur, attendee tokens wait in a singly linked list while volunteers process a stream of corrections. A command removes the current first token, last token, token at a 1-based position, or first token whose value matches a supplied code. Commands are sequential: after a removal, later positions refer to the shortened list. If a value is absent or a position is invalid, skip only that command. Duplicate values are allowed, but a value command removes just the first match. Print the final queue in order.
## Input format

The first line contains N, the second line contains N integers, and the next line contains Q. Each of the next Q lines is one command: `H` (delete head), `T` (delete tail), `K k` (delete position k), or `X x` (delete first value x).

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

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
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
