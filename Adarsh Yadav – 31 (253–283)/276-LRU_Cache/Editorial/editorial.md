# Editorial — LRU Cache

## Approach: HashMap + Doubly Linked List

A hash map provides average `O(1)` access to a cache node by key. A doubly linked list keeps nodes ordered by recency:
- The node nearest the front is most recently used.
- The node nearest the back is least recently used.

Use sentinel head and tail nodes to simplify insertion and removal.

### `get(key)`
1. Look up the key in the hash map.
2. If absent, return `-1`.
3. If present, unlink its node and insert it immediately after the head.
4. Return its value.

### `put(key, value)`
1. If the key already exists, update its value and move its node to the front.
2. Otherwise, create a node, add it to the map, and insert it after the head.
3. If the number of cached keys exceeds capacity, remove the node immediately before the tail and delete its key from the map.

Both operations take average `O(1)` time.

## Complexity

- **Time:** `O(1)` average for each `get` and `put`.
- **Auxiliary space:** `O(capacity)`.
