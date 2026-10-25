# Add Two Numbers in Linked List

**Striver A2Z Linked List practice — Core**

## Problem statement

At a neighbourhood kirana shop, two running tabs are recorded as singly linked lists so the cashier can add them from the smallest place value upward. Each node stores one decimal digit, and the units digit is at the head, so the list is read in reverse order compared with the number it represents. Add the two represented non-negative integers and print the result in the same format. The lists may have different lengths: continue with the longer list after the shorter one ends, carrying whenever a digit sum reaches ten. If a carry remains after both lists end, it becomes a new final node; do not lose it or reverse the meaning of the digits.
## Input format

The first line contains N, the second line contains N digits for the first number, the third line contains M, and the fourth line contains M digits for the second number. Digits are stored in reverse order.

## Output format

Print the sum as a linked-list digit sequence, least significant digit first. Print `0` for a zero sum.

## Constraints

- `1 <= N, M <= 100`
- Every list value is a decimal digit from 0 through 9.

## Sample input

```text
3
2 4 3
3
5 6 4
```

## Sample output

```text
7 0 8
```

## Notes

- Positions are 1-based. When a value is targeted, only the first matching node is affected unless the operation is explicitly positional.
- Every input file is a complete independent case. The Java solution also accepts multiple cases concatenated on standard input until end-of-file.
