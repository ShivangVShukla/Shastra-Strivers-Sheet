# Deletion of the Kth Element of Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a Mumbai college admission counter, application IDs are maintained in a singly linked queue. A supervisor asks to cancel the entry at 1-based position `K`, counted from the current head. Remove that node and connect the node before it directly to the node after it; when `K=1`, the head changes. Duplicate IDs are possible and must not affect the choice, because position identifies the entry. If `K` is invalid for the current queue, leave it unchanged and print the IDs in order.
## Input format

The first line contains N, followed by N integers. The final line contains the 1-based position K.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `1 <= n <= 1000`
- `0 <= ListNode.val <= 100`
- `1 <= k <= n`

## Sample input

```text
5
10 20 30 40 50
3
```

## Sample output

```text
10 20 40 50
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
