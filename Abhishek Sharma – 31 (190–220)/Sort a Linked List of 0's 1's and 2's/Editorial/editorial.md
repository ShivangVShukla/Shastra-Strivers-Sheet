# Editorial — Sort a Linked List of 0's 1's and 2's

## Approach

Detach each node and append it to one of three linked-list buckets for values 0, 1, and 2. Concatenate the non-empty buckets in numeric order, reusing the original nodes.

## Correctness argument

The algorithm selects the node specified by the problem and changes only the links needed for that operation. For traversal, search, and length tasks it visits the nodes in their existing order. For structural changes, unaffected nodes retain their relative order; for doubly linked lists, both forward and backward links are repaired. Therefore the resulting list and output satisfy the specification.

## Complexity

Time O(N); auxiliary space O(1), because the three bucket head/tail arrays have constant size.

## Edge cases

- Empty and single-node lists wherever allowed.
- Repeated values and absent search targets.
- Head, tail, first-valid, and last-valid positions.
- Large input lists and multiple operations in one case.
