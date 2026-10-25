# Editorial — Deletion in Doubly Linked List

## Approach

Process commands in order and change only the links needed by each deletion. Deleting the tail of a singly linked list requires finding its predecessor; a doubly linked list can unlink the tail directly because its tail and `prev` links are maintained.

## Correctness argument

The algorithm selects the node specified by the problem and changes only the links needed for that operation. For traversal, search, and length tasks it visits the nodes in their existing order. For structural changes, unaffected nodes retain their relative order; for doubly linked lists, both forward and backward links are repaired. Therefore the resulting list and output satisfy the specification.

## Complexity

Each command is O(1) for a head/tail operation when endpoints suffice, or O(N) when a node/predecessor must be searched. Auxiliary space O(1) beyond the list.

## Edge cases

- Empty and single-node lists wherever allowed.
- Repeated values and absent search targets.
- Head, tail, first-valid, and last-valid positions.
- Large input lists and multiple operations in one case.
