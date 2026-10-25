# LFU Cache

## Problem Statement

Design and implement a Least Frequently Used (LFU) cache.

Implement a cache with the following operations:

- `LFUCache(capacity)`: Initialize the cache with the given positive capacity.
- `get(key)`: Return the value associated with `key` if it exists; otherwise return `-1`.
- `put(key, value)`: Insert a new key-value pair or update an existing key. When inserting into a full cache, evict the key with the smallest usage frequency. If multiple keys have the same frequency, evict the least recently used among them.

A new key's frequency is initialized to `1` by the insertion. Every successful `get` and every `put` on an existing key increments that key's frequency. Both `get` and `put` must run in `O(1)` average time.

## Input Format

- The first line contains an integer `T`, the number of test cases.
- For each test case:
  - The first line contains two integers `capacity` and `Q`, the cache capacity and number of operations.
  - Each of the next `Q` lines contains either:
    - `1 key value` for `put(key, value)`, or
    - `2 key` for `get(key)`.

## Output Format

For each test case, print the result of every operation separated by spaces:
- Print `null` for each `put`.
- Print the returned integer for each `get`.

## Examples

### Example 1

Input:
```text
1
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
```
Output:
```text
null null 1 null -1 3 null -1 3 4
```

### Example 2

Input:
```text
1
3 10
1 5 7
1 4 6
1 3 5
1 2 4
1 1 3
2 1
2 2
2 3
2 4
2 5
```
Output:
```text
null null null null null 3 4 5 -1 -1
```

### Example 3

Input:
```text
1
1 5
1 1 10
2 1
1 2 20
2 1
2 2
```
Output:
```text
null 10 null -1 20
```

## Constraints

- `1 <= capacity <= 10^3`
- `0 <= key <= 10^4`
- `0 <= value <= 10^5`
- At most `10^5` operations per test case.
