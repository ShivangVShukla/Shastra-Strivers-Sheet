# Editorial: 51. LRU Cache

## Difficulty

Hard

## Prerequisites

- Hash maps
- doubly linked lists
- data-structure design

## Problem Understanding

Implement an LRU cache with positive capacity. Operation 1 key value means put(key,value); operation 2 key means get(key). A get returns the value if present or -1 otherwise. A put updates an existing key or inserts a new key; if capacity is exceeded, evict the least recently used key. Both operations must run in O(1) average time.

## Approach

Maintain a hash map from each key to its node in a doubly linked list. The list stores entries from most recently used (MRU) at the front to least recently used (LRU) at the back. Dummy head and tail nodes remove edge cases.

For `get(key)`, look up the key in the map. If absent, return -1. If present, unlink its node, move it directly after the head, and return its value. For `put(key,value)`, update and promote an existing node if the key already exists. Otherwise, create a node, add it after the head, and register it in the map. If the map size exceeds capacity, remove the node before the tail and erase its key from the map.

The map gives average O(1) lookup; doubly linked pointers give O(1) promotion and eviction. A singly linked list would require finding a predecessor, which is not constant time.

## Step-by-Step Algorithm

1. Create a doubly linked list with dummy head and tail sentinels, and an unordered map from key to node.
2. Define a helper that detaches a node and another that inserts a node immediately after the head.
3. On `get`, return -1 when the key is absent; otherwise promote the node and return its value.
4. On `put`, update and promote an existing node, or create and insert a new node.
5. If the cache exceeds capacity, remove the least-recent node just before the tail and erase its map entry.

## Worked Example

Capacity 2; operations: put(1,1), put(2,2), get(1), put(3,3), get(2), put(4,4), get(1), get(3), get(4). The first get promotes key 1. Inserting key 3 evicts key 2; inserting key 4 later evicts key 1. The outputs are `null null 1 null -1 null -1 3 4`.

## Correctness Argument

The map always points to each cached key’s unique list node. Moving every accessed or updated entry to the front maintains the invariant that the head-adjacent entry is most recently used and the tail-adjacent entry is least recently used. When capacity is exceeded, removing the tail-adjacent entry therefore evicts exactly the LRU key.

## Complexity Analysis

**Time:** O(1) average per `get` and `put`. **Space:** O(capacity) for the map and list.

## Conclusion

A hash map plus a doubly linked list provides constant-time lookup, promotion, insertion, and eviction while preserving exact recency order.
