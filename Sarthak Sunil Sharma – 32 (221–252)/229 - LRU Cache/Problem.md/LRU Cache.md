### Problem 229: LRU Cache
**Difficulty:** Medium | **Tags:** Stack and Queues, Hash Map, Doubly Linked List, Design

**Problem Statement**

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

The cache has a fixed positive capacity.

Implement two operations:

- `get(key)` — return the value associated with `key` if it exists; otherwise return `-1`. Accessing a key makes it the most recently used key.
- `put(key, value)` — insert or update the key. Updating or inserting a key makes it the most recently used key. If inserting a new key makes the cache exceed its capacity, remove the least recently used key.

Both operations should run in `O(1)` average time.

**Input Format**

* The first line contains two integers `capacity` and `Q`.
* The next `Q` lines describe operations.
* `1 key value` represents `put(key, value)`.
* `2 key` represents `get(key)`.

**Output Format**

* For every `get` operation, print its returned value on the same line, separated by spaces.
* If there are no `get` operations, print `EMPTY`.

**Constraints**

* `1 <= capacity <= 3000`
* `0 <= key <= 10000`
* `0 <= value <= 100000`
* `1 <= Q <= 200000`

**Example**

**Input:**
```text
2 10
1 1 1
1 2 2
2 1
1 3 3
2 2
1 4 4
2 1
2 3
2 4
2 2
```

**Output:**
```text
1 -1 -1 3 4 -1
```

**Explanation:**

The cache starts with capacity `2`.

After putting `(1,1)` and `(2,2)`, key `1` is accessed and becomes most recently used. When `(3,3)` is inserted, key `2` is the least recently used key and is evicted.

The same rule is applied throughout the remaining operations.

**Expected Complexity**

* `get`: O(1) average
* `put`: O(1) average
* Total: O(Q)
* Auxiliary Space: O(capacity)
