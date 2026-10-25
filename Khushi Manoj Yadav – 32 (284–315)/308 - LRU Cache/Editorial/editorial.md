# 308. LRU Cache - Editorial

## Approach
Use a hash map combined with a doubly linked list:
- Hash map: maps `key` to its corresponding linked list node, enabling O(1) lookup.
- Doubly linked list: maintains access order.
  - The most recently used node is kept at the front.
  - The least recently used node is at the back.
- `get(key)`: If present, move node to front and return value; else return `-1`.
- `put(key, value)`: If present, update value and move node to front. If absent, create node and insert at front; if capacity exceeded, remove node from back and erase from map.

## Complexity
- Time Complexity: O(1) for `get` and `put`.
- Space Complexity: O(capacity)
