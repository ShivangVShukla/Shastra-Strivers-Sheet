# Insertion in Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a student services desk in Patna, records are kept in a singly linked queue while several corrections arrive together. Process each insertion command in order: place a record at the head, append at the tail, insert before a 1-based position, or insert before the first node matching a target value. Later commands operate on the updated queue, so successful insertions can change subsequent positions. Skip an invalid position or absent target without affecting later commands. Values may repeat; a value command uses the first match. Print the final queue.
## Input format

The first line contains N, the second line contains N integers, and the next line contains Q. Each of the next Q lines is `H x`, `T x`, `K k x`, or `B target x` for insertion at head, tail, position k, or before the first target value.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

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
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
