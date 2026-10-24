# Insertion at the Tail of Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a cooperative bank in Mysuru, completed transactions are appended to a singly linked audit list in arrival order. Add a new transaction with value `X` after the current last node, without changing the order of any existing record. Because the list has no backward pointers, the current tail may have to be found by walking from the head. If the audit list is empty, the new transaction becomes its only node. Return and print the updated list.
## Input format

The first line contains N, followed by N integers. The final line contains the value X to append.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `0 <= number of nodes in the Linked List <= 1000`
- `0 <= ListNode.val <= 100`
- `0 <= X <= 100`

## Sample input

```text
4
5 10 15 20
25
```

## Sample output

```text
5 10 15 20 25
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
