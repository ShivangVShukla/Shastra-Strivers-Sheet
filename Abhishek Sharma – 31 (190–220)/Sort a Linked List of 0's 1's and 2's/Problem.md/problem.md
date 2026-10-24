# Sort a Linked List of 0's 1's and 2's

**Striver A2Z Linked List practice — Core**

## Problem statement

At a ration shop in Jaipur, each token in a singly linked line carries a category code: `0` for priority, `1` for standard, or `2` for later service. The tokens arrived in arbitrary order, and the supervisor wants all 0s first, then 1s, then 2s. Reorder the list in nondecreasing order by relinking existing nodes; do not allocate replacement nodes or alter a token's code. The input contains no value outside `{0,1,2}`. Equal-code nodes may remain in their original order, and an already sorted line must remain valid.
## Input format

The first line contains N, followed by N integers; every value is 0, 1, or 2.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `0 <= number of nodes in the Linked List <= 10^5`
- `0 <= ListNode.val <= 2`

## Sample input

```text
6
2 1 0 2 1 0
```

## Sample output

```text
0 0 1 1 2 2
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
