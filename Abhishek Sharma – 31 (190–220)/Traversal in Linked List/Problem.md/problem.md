# Traversal in Linked List

**Striver A2Z Linked List practice — Basic**

## Problem statement

At a Mumbai suburban railway control desk, station IDs for one train's route are chained in a singly linked list. The controller needs the route exactly as stored: begin at the head, follow each `next` link, and report every station until null is reached. Do not sort IDs, reverse the route, or suppress repeated IDs; two visits with the same code are still two stops. If the route is empty, print the required empty-list representation. This task only reports the chain and must not change any link.
## Input format

The first line contains N. The second line contains N integers.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `0 <= number of nodes in the Linked List <= 10^5`
- `0 <= ListNode.val <= 10^4`

## Sample input

```text
5
18 15 5 3 27
```

## Sample output

```text
18 15 5 3 27
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
