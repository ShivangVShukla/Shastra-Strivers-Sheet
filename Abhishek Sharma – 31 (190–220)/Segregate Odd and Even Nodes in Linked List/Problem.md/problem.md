# Segregate Odd and Even Nodes in Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a Chennai post office, registered letters are represented by nodes in the order they entered a sorting line. Split the line by position: nodes at odd 1-based positions come first, followed by nodes at even 1-based positions. The first node is position 1 and belongs to the first group. Preserve the original relative order inside both groups and rearrange nodes by changing links, not by sorting stored values. A letter with an even code can still belong to the first group if its position is odd.
## Input format

The first line contains N, followed by N integers.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `0 <= number of nodes in the linked list <= 10^4`
- `-10^6 <= Node.val <= 10^6`

## Sample input

```text
5
1 2 3 4 5
```

## Sample output

```text
1 3 5 2 4
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
