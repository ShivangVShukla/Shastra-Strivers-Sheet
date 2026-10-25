# Insert Node Before Kth Node in Doubly Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At an engineering college in Hyderabad, a doubly linked inventory records equipment labels in inspection order. Given a 1-based position `K` and a new label `X`, place a node immediately before the node currently at `K`; that original node and all later nodes shift one position right. The position is evaluated before insertion, so `K=1` inserts at the head. Repair every affected link in both directions, then print the list from head to tail and tail to head.
## Input format

The first line contains N, followed by N integers. The final line contains K and X.

## Output format

Print the resulting list from head to tail on the first line and from tail to head on the second line. Print `EMPTY` for an empty traversal.

## Constraints

- `n == number of nodes in the linked list`
- `1 <= n <= 100`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`
- `1 <= K <= n`

## Sample input

```text
4
1 2 3 4
3 9
```

## Sample output

```text
1 2 9 3 4
4 3 9 2 1
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
