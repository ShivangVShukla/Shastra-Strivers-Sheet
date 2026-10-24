# Editorial — Remove Nth Node from the Back of the LL

## Approach

Place a dummy node before the head. Advance `fast` by K nodes, then move `fast` and `slow` together until `fast` reaches the tail. The node after `slow` is the Kth node from the back and can be bypassed.

## Correctness argument

The algorithm selects the node specified by the problem and changes only the links needed for that operation. For traversal, search, and length tasks it visits the nodes in their existing order. For structural changes, unaffected nodes retain their relative order; for doubly linked lists, both forward and backward links are repaired. Therefore the resulting list and output satisfy the specification.

## Complexity

Time O(N) in one traversal; auxiliary space O(1).

## Edge cases

- Empty and single-node lists wherever allowed.
- Repeated values and absent search targets.
- Head, tail, first-valid, and last-valid positions.
- Large input lists and multiple operations in one case.
