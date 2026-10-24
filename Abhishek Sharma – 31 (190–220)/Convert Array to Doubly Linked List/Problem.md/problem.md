# Convert Array to Doubly Linked List

**Striver A2Z Linked List practice — Basic**

## Problem statement

During inventory at a Delhi public library, shelf labels arrive as an array in the order they were scanned. Build a doubly linked list containing exactly those labels in that order: the first label is the head and the last is the tail. Every adjacent pair must be connected by `next` in the forward direction and `prev` in the reverse direction. Report one traversal from head to tail and another from tail to head, following the links rather than reversing or sorting the input values. Repeated labels are separate nodes; an empty array produces two empty traversals.
## Input format

The first line contains N, followed by N integers in array order.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `n == nums.length`
- `0 <= n <= 100`
- `0 <= nums[i] <= 100`

## Sample input

```text
4
1 2 3 4
```

## Sample output

```text
1 2 3 4
4 3 2 1
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
