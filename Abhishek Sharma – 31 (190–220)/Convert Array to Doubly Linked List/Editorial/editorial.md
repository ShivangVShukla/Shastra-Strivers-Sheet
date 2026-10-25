# Editorial — Convert Array to Doubly Linked List

## Approach

Create one node per array value and connect each new node to the previous tail in both directions.

## Correctness argument

The algorithm selects the node specified by the problem and changes only the links needed for that operation. For traversal, search, and length tasks it visits the nodes in their existing order. For structural changes, unaffected nodes retain their relative order; for doubly linked lists, both forward and backward links are repaired. Therefore the resulting list and output satisfy the specification.

## Complexity

Time O(N); auxiliary space O(1), excluding the nodes and output buffer.

## Edge cases

- Empty and single-node lists wherever allowed.
- Repeated values and absent search targets.
- Head, tail, first-valid, and last-valid positions.
- Large input lists and multiple operations in one case.
