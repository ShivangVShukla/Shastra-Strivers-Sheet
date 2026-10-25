# Insertion at the Kth Position of Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

An office in Gurugram keeps employee requests in a singly linked list, but an urgent request may need to be inserted in the middle. Given `K` and `X`, insert a node with value `X` at 1-based position `K`, before the node currently occupying that position. Thus the old `K`th node moves to `K+1`; `K=N+1` appends after the tail, and `K=1` replaces the head. An invalid position changes nothing. Preserve all other nodes and print the final sequence.
## Input format

The first line contains N, followed by N integers. The final line contains K and X.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `n == number of nodes in the Linked List`
- `0 <= n <= 1000`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`
- `1 <= K <= n + 1`

## Sample input

```text
4
10 20 30 40
3 25
```

## Sample output

```text
10 20 25 30 40
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
