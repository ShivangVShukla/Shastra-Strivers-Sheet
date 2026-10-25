### Problem 308: LRU Cache
**Difficulty:** Medium | **Tags:** Hash Map, Doubly Linked List, Design

**Problem Statement**
Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Operations:
- `1 key value` : insert or update `(key, value)`.
- `2 key`       : get `key`. If found, return value; else return `-1`.

Both operations run in `O(1)` average time complexity.

**Input Format**
- The first line contains two integers `capacity` and `Q`.
- The next `Q` lines each describe an operation:
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
2 9
1 1 1
1 2 2
2 1
1 3 3
2 2
1 4 4
2 1
2 3
2 4
```
**Output:**
```text
1 -1 -1 3 4
```
