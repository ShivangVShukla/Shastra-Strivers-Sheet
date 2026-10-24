# Deletion of the Tail of Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

A delivery cooperative in Indore stores today's stops in a singly linked list, from the first pickup to the last. One customer cancels the final stop, so delete exactly the tail and preserve the order of every earlier stop. Since a singly linked list has no backward pointer, find the predecessor by traversing from the head. If there was one stop, the result is empty; otherwise the new tail must end with a null `next` link. Print the updated route.
## Input format

The first line contains N, followed by N integers.

## Output format

Print the resulting values separated by single spaces on one line. Print `EMPTY` if the resulting list contains no nodes.

## Constraints

- `1 <= number of nodes in the Linked List <= 1000`
- `0 <= ListNode.val <= 100`

## Sample input

```text
4
8 4 6 2
```

## Sample output

```text
8 4 6
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
