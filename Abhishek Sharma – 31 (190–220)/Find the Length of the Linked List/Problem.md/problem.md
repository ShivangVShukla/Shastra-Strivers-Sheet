# Find the Length of the Linked List

**Striver A2Z Linked List practice — Basic**

## Problem statement

At a college laboratory in Chennai, each piece of equipment checked out for a practical is represented by one node in a singly linked list. The lab assistant needs the number of records on the chain, not the number of distinct equipment IDs. Traverse from the head to the null link and count every node, including repeated values. An empty chain has length zero. Return only the count; do not modify the list or infer its size from its values.
## Input format

The first line contains N, followed by N integers.

## Output format

Print the number of nodes as one integer.

## Constraints

- `0 <= number of nodes in the Linked List <= 10^5`
- `0 <= ListNode.val <= 10^4`

## Sample input

```text
5
7 7 3 9 1
```

## Sample output

```text
5
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
