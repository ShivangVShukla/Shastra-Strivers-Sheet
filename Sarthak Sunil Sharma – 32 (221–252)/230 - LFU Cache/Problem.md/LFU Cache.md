### Problem 230: LFU Cache
**Difficulty:** Hard | **Tags:** Hash Map, Doubly Linked List, Design, LFU, LRU

**Problem Statement**

Design and implement a data structure for a Least Frequently Used (LFU) cache.

Implement the LFU cache with the following operations:

* `LFUCache(int capacity)`: Initialize the cache with the specified capacity.
* `get(int key)`: Retrieve the value of the key if it exists in the cache; otherwise, return `-1`.
* `put(int key, int value)`: Update the value of the key if it is present in the cache, or insert the key if it is not already present.

If the cache has reached its capacity and a new key needs to be inserted, invalidate and remove the **least frequently used** key before inserting the new item.

In case of a tie, meaning two or more keys have the same minimum frequency, invalidate the **least recently used** key among those keys.

A use counter is maintained for every key in the cache. When a key is first inserted, its use counter is set to `1`. The use counter is incremented whenever a `get` or `put` operation is performed on an existing key.

The `get` and `put` operations should run in `O(1)` average time complexity.

**Input Format**

For this assignment's test harness:

* The first line contains two integers `capacity` and `Q`, where `capacity` is the cache capacity and `Q` is the number of operations.
* Each of the next `Q` lines describes one operation.
* `1 key value` represents `put(key, value)`.
* `2 key` represents `get(key)`.

Only `get` operations produce output.

**Output Format**

* Print the return value of every `get` operation in the order in which the operations are performed, separated by spaces.
* If the testcase contains no `get` operation, print `EMPTY`.

**Constraints**

* `1 ≤ capacity ≤ 10^3`
* `0 ≤ key ≤ 10^4`
* `0 ≤ value ≤ 10^5`
* At most `10^5` `get` and `put` operations are performed.

**Example**

**Input:**
```text
2 11
1 1 1
1 2 2
2 1
1 3 3
2 2
2 3
1 4 4
2 1
2 3
2 4
2 2
```

**Output:**
```text
1 -1 3 -1 3 4 -1
```

**Explanation:**

* `put(1,1)` inserts key `1` with frequency `1`.
* `put(2,2)` inserts key `2` with frequency `1`.
* `get(1)` returns `1` and increases the frequency of key `1` to `2`.
* `put(3,3)` requires an eviction because the cache is full. Key `2` has the lowest frequency, so key `2` is removed.
* `get(2)` returns `-1` because key `2` was evicted.
* `get(3)` returns `3` and increases its frequency.
* `put(4,4)` again requires an eviction. Keys `1` and `3` have the same frequency, so the least recently used key among them is removed.
* The remaining `get` operations return the values shown in the output.

**Approach:**

To achieve `O(1)` average time for both operations, maintain three structures:

* A hash map from `key` to its cache node, allowing direct access to a key.
* A map from `frequency` to a doubly linked list containing all keys with that frequency.
* `minFreq`, storing the smallest frequency currently present in the cache.

Within every frequency list, keep the most recently used key at the front and the least recently used key at the back.

For `get(key)`:

* If the key does not exist, return `-1`.
* Otherwise, remove the key from its current frequency list.
* Increase its frequency by `1`.
* Insert it at the front of the new frequency list.
* Return its value.

For `put(key, value)`:

* If the key already exists, update its value and increase its frequency.
* Otherwise, if the cache is full, remove the least recently used key from the `minFreq` list.
* Insert the new key with frequency `1`.
* Set `minFreq = 1`.

**Complexity:**

* Time Complexity: `O(1)` average for both `get` and `put`.
* Space Complexity: `O(capacity)`.
