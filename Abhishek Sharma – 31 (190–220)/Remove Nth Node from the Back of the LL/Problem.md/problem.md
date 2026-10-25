# Remove Nth Node from the Back of the LL

**Striver A2Z Linked List practice — Core**

## Problem statement

An accounts office in Ahmedabad stores transaction records in a singly linked list from oldest to newest. An auditor requests removal of the `K`th record counted backward from the tail: `K=1` means the newest record, while `K=N` means the oldest record at the head. Remove exactly that node and retain the order of all survivors. The requested position is valid, including when it selects the head or the only node. Print the remaining records or the specified empty-list output.
## Input format

The first line contains N, followed by N integers. The final line contains K, the count from the tail.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `1 <= N <= 30`
- `0 <= ListNode.val <= 100`
- `1 <= K <= N`.

## Sample input

```text
5
1 2 3 4 5
2
```

## Sample output

```text
1 2 3 5
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
