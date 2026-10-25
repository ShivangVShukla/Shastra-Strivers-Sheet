### Problem 309: LFU Cache
**Difficulty:** Hard | **Tags:** Hash Map, Doubly Linked List, Design

**Problem Statement**
Design and implement a data structure for a Least Frequently Used (LFU) cache.

Operations:
- `1 key value` : updates or inserts the value of `key`.
- `2 key`       : gets the value of `key` if present; otherwise returns `-1`.

When the cache reaches its capacity, invalidate and remove the least frequently used key. When there is a tie, remove the least recently used key among them.

**Input Format**
- The first line contains two integers `capacity` and `Q`.
- The next `Q` lines describe operations:
  - `1 key value`
  - `2 key`

**Output Format**
- Print the return values of all `get` operations separated by spaces.
- If there are no `get` operations, print `EMPTY`.

**Constraints**
- `1 <= capacity <= 3000`
- `1 <= Q <= 10^5`
- `0 <= key <= 10^5`
- `0 <= value <= 10^9`

**Example**
**Input:**
```text
2 10
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
```
**Output:**
```text
1 -1 3 -1 3 4
```
