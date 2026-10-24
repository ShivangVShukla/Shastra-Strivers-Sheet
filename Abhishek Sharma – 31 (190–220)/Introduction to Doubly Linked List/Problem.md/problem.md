# Introduction to Doubly Linked List

**Striver A2Z Linked List practice — Basic**

## Problem statement

On a Mumbai suburban railway control board, station codes arrive in the order a train visits them. Construct a doubly linked list from the supplied sequence, keeping the first station as head and the last as tail. Each adjacent pair must be connected by `next` forward and `prev` backward. Print the route from head to tail and then from tail to head. Repeated codes still represent separate stops; do not merge them. If the route is empty, both traversals are empty.
## Input format

The first line contains N, followed by N integers in forward order.

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
