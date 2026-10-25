# Search in Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a campus help desk in Coimbatore, ticket IDs are kept in a singly linked list in arrival order. A caller supplies an ID `key`, and the desk must decide whether it has an open ticket with that ID. Walk from the head and return `true` as soon as a matching node is found; if the end is reached first, return `false`. The list is not sorted, so a larger or smaller value gives no reason to stop. Duplicates do not affect the answer, and the search must not modify the queue.
## Input format

The first line contains N, followed by N integers. The final line contains the target X.

## Output format

Print `true` if X is present, or `false` otherwise.

## Constraints

- `1 <= n <= 10^5`
- `1 <= Node.val <= 10^5`
- `1 <= key <= 10^5`

## Sample input

```text
4
10 20 30 40
30
```

## Sample output

```text
true
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
