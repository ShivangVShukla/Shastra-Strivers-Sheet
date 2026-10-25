# Editorial — Add Two Numbers in Linked List

## Approach

Walk both reversed-digit lists together, add the current digits and carry, and append `sum % 10` to the result list. Continue while either input or the carry remains.

## Correctness argument

The algorithm selects the node specified by the problem and changes only the links needed for that operation. For traversal, search, and length tasks it visits the nodes in their existing order. For structural changes, unaffected nodes retain their relative order; for doubly linked lists, both forward and backward links are repaired. Therefore the resulting list and output satisfy the specification.

## Complexity

Time O(max(N, M)); auxiliary space O(max(N, M)) for the result list.

## Edge cases

- Empty and single-node lists wherever allowed.
- Repeated values and absent search targets.
- Head, tail, first-valid, and last-valid positions.
- Large input lists and multiple operations in one case.
