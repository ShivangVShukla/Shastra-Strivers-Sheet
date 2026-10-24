# Delete the Element with Value X

**Striver A2Z Linked List practice — Core**

## Problem statement

An Ahmedabad library keeps returned-book codes in a singly linked list, but a correction slip may name a code that occurs more than once. Given `X`, scan from the head and remove only the first node whose value equals `X`; later matching nodes must remain in their original order. If the match is at the head, update the head; if it is the only node, the list becomes empty. If no node has value `X`, print the list unchanged. Values are compared for equality, not treated as positions.
## Input format

The first line contains N, followed by N integers. The final line contains the target value X.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `1 <= n <= 1000`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`
- The number of nodes with value `X` is either `0` or `1`.

## Sample input

```text
5
1 2 3 2 4
2
```

## Sample output

```text
1 3 2 4
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
