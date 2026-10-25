# 309. LFU Cache - Editorial

## Approach
Maintain:
1. `keyTable`: maps `key` to its node `(val, freq, iter)`.
2. `freqTable`: maps `freq` to a doubly linked list of keys having that frequency.
3. `minFreq`: tracks the minimum frequency across all stored keys.

For `get(key)`:
- Increment key's frequency by 1.
- Move node from `freqTable[old_freq]` to front of `freqTable[new_freq]`.
- Update `minFreq` if `freqTable[old_freq]` becomes empty and `minFreq == old_freq`.

For `put(key, val)`:
- If key exists, update value and call frequency increment logic.
- If key is new:
  - If capacity reached, evict back of `freqTable[minFreq]` (least recently used among min frequency).
  - Add new key with frequency 1 to `freqTable[1]`.
  - Set `minFreq = 1`.

## Complexity
- Time Complexity: O(1) for both operations.
- Space Complexity: O(capacity)
