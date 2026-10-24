# Editorial — Segregate Odd and Even Nodes in Linked List

## Approach

Maintain odd-position and even-position tails while rewiring existing links. Advance them in pairs, then connect the odd tail to the saved even head.

## Correctness argument

The algorithm selects the node specified by the problem and changes only the links needed for that operation. For traversal, search, and length tasks it visits the nodes in their existing order. For structural changes, unaffected nodes retain their relative order; for doubly linked lists, both forward and backward links are repaired. Therefore the resulting list and output satisfy the specification.

## Complexity

Time O(N); auxiliary space O(1).

## Edge cases

- Empty and single-node lists wherever allowed.
- Repeated values and absent search targets.
- Head, tail, first-valid, and last-valid positions.
- Large input lists and multiple operations in one case.
