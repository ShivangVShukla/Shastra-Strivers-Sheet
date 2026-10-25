# Editorial — LFU Cache

## Approach: Frequency Buckets + Doubly Linked Lists

To support both LFU eviction and LRU tie-breaking in average `O(1)` time, maintain:

- `keyToNode`: maps each key to its cache node (value and current frequency).
- `frequencyToList`: maps each frequency to a doubly linked list of nodes at that frequency.
- `minFrequency`: the smallest frequency currently present in the cache.

Within each frequency list, the most recently used node is placed at the front and the least recently used node at the back.

### `get(key)`
1. If the key is absent, return `-1`.
2. Otherwise, remove its node from the list for its current frequency.
3. Increment the node's frequency and insert it at the front of the next frequency's list.
4. If the old frequency list becomes empty and it was `minFrequency`, increment `minFrequency`.
5. Return the node's value.

### `put(key, value)`
1. If capacity is zero, do nothing (the stated constraints use positive capacity).
2. If the key already exists, update its value and treat the `put` as a use: promote the node's frequency.
3. Otherwise, if the cache is full, evict the least recently used node from the list at `minFrequency`.
4. Insert the new node with frequency `1` at the front of frequency list `1` and set `minFrequency = 1`.

## Complexity

- **Time:** `O(1)` average for `get` and `put`.
- **Auxiliary space:** `O(capacity)`.
